package com.gang.micro.core.mjpeg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class MjpegInputStream extends DataInputStream {

    private final byte[] SOI_MARKER = {(byte) 0xFF, (byte) 0xD8};
    private final byte[] EOF_MARKER = {(byte) 0xFF, (byte) 0xD9};
    private final String CONTENT_LENGTH = "Content-Length";
    private final String CONTENT_END = "\r\n";
    private final static int HEADER_MAX_LENGTH = 100;
    private final static int FRAME_MAX_LENGTH = 400000 + HEADER_MAX_LENGTH;
    private int mContentLength = -1;
    private static final byte[] gHeader = new byte[HEADER_MAX_LENGTH];
    private static final byte[] gFrameData = new byte[FRAME_MAX_LENGTH];
    private BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

    byte[] CONTENT_LENGTH_BYTES;
    byte[] CONTENT_END_BYTES;

    public MjpegInputStream(InputStream in) {
        super(new BufferedInputStream(in, FRAME_MAX_LENGTH));

        bitmapOptions.inSampleSize = 1;
        bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        bitmapOptions.inPreferQualityOverSpeed = false;

        try {
            CONTENT_LENGTH_BYTES = CONTENT_LENGTH.getBytes("UTF-8");
            CONTENT_END_BYTES = CONTENT_END.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private int getEndOfSeqeunce(DataInputStream in, byte[] sequence) throws IOException {
        int seqIndex = 0;
        byte c;
        for (int i = 0; i < FRAME_MAX_LENGTH; i++) {
            c = (byte) in.readUnsignedByte();
            if (c == sequence[seqIndex]) {
                seqIndex++;
                if (seqIndex == sequence.length) return i + 1;
            } else seqIndex = 0;
        }
        return -1;
    }

    private int getStartOfSequence(DataInputStream in, byte[] sequence) throws IOException {
        int end = getEndOfSeqeunce(in, sequence);
        return (end < 0) ? (-1) : (end - sequence.length);
    }

    public Bitmap readMjpegFrame() throws IOException {
        mark(HEADER_MAX_LENGTH);
        int headerLen = getStartOfSequence(this, SOI_MARKER);

        if (headerLen < 0)
            return null;

        reset();

        readFully(gHeader, 0, headerLen);

        try {
            mContentLength = parseContentLength(gHeader, headerLen);
        } catch (NumberFormatException nfe) {
            mContentLength = getEndOfSeqeunce(this, EOF_MARKER);
        }

        readFully(gFrameData, 0, mContentLength);

        Bitmap bitmap = BitmapFactory.decodeByteArray(gFrameData, 0, mContentLength, bitmapOptions);

        bitmapOptions.inBitmap = bitmap.copy(Bitmap.Config.RGB_565, true);

        return bitmap;
    }

    private int findPattern(byte[] buffer, int bufferLen, byte[] pattern, int offset) {
        int seqIndex = 0;
        for (int i = offset; i < bufferLen; ++i) {
            if (buffer[i] == pattern[seqIndex]) {
                ++seqIndex;
                if (seqIndex == pattern.length) {
                    return i + 1;
                }
            } else {
                seqIndex = 0;
            }
        }

        return -1;
    }

    private int parseContentLength(byte[] headerBytes, int length) throws IOException, NumberFormatException {
        int begin = findPattern(headerBytes, length, CONTENT_LENGTH_BYTES, 0);
        int end = findPattern(headerBytes, length, CONTENT_END_BYTES, begin) - CONTENT_END_BYTES.length;

        // converting string to int
        int number = 0;
        int radix = 1;
        for (int i = end - 1; i >= begin; --i) {
            if (headerBytes[i] > 47 && headerBytes[i] < 58) {
                number += (headerBytes[i] - 48) * radix;
                radix *= 10;
            }
        }

        return number;
    }

}