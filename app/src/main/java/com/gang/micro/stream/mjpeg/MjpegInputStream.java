/**
 *
 */
package com.gang.micro.stream.mjpeg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.gang.micro.utils.image.ImageUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MjpegInputStream extends DataInputStream {

    private final byte[] SOI_MARKER = {(byte) 0xFF, (byte) 0xD8};
    private final byte[] EOF_MARKER = {(byte) 0xFF, (byte) 0xD9};

    private final static int HEADER_MAX_LENGTH = 100;
    private final static int FRAME_MAX_LENGTH = 40000 + HEADER_MAX_LENGTH;

    private final byte[] frameData = new byte[500000];
    private final byte[] header = new byte[20000];

    private BitmapFactory.Options bmOptions;

    public static MjpegInputStream read(String url) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;

        try {
            response = client.newCall(request).execute();
            return new MjpegInputStream(response.body().byteStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public MjpegInputStream(InputStream in) {
        super(new BufferedInputStream(in, FRAME_MAX_LENGTH));

        Bitmap bitmap = Bitmap.createBitmap(ImageUtils.WIDTH, ImageUtils.HEIGHT, Bitmap.Config.ARGB_8888);

        bmOptions = new BitmapFactory.Options();
        bmOptions.inBitmap = bitmap;
    }

    private int getEndOfSequence(DataInputStream in, byte[] sequence)
            throws IOException {
        int seqIndex = 0;
        byte c;
        for (int i = 0; i < FRAME_MAX_LENGTH; i++) {
            c = (byte) in.readUnsignedByte();
            if (c == sequence[seqIndex]) {
                seqIndex++;
                if (seqIndex == sequence.length)
                    return i + 1;
            } else
                seqIndex = 0;
        }
        return -1;
    }

    private int getStartOfSequence(DataInputStream in, byte[] sequence)
            throws IOException {
        int end = getEndOfSequence(in, sequence);
        return (end < 0) ? (-1) : (end - sequence.length);
    }

    private int parseContentLength(byte[] headerBytes) throws IOException,
            NumberFormatException {
        ByteArrayInputStream headerIn = new ByteArrayInputStream(headerBytes);
        Properties props = new Properties();
        props.load(headerIn);

        return Integer.parseInt(props.getProperty("Content-Length"));
    }


    public Bitmap readMjpegFrame() throws IOException {
        mark(FRAME_MAX_LENGTH);
        int headerLen = getStartOfSequence(this, SOI_MARKER);
        reset();
        readFully(header, 0, headerLen);

        int mContentLength;
        try {
            mContentLength = parseContentLength(header);
        } catch (NumberFormatException nfe) {
            mContentLength = getEndOfSequence(this, EOF_MARKER);
        }

        reset();
        skipBytes(headerLen);
        readFully(frameData, 0, mContentLength);
        return BitmapFactory.decodeStream(new ByteArrayInputStream(frameData), null, bmOptions);
    }
}