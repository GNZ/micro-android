/**
 *
 */
package com.gang.micro.stream.mjpeg;

import android.graphics.Bitmap;

/**
 * @author Micho Garcia
 */
public interface MjpegViewListener {

    void sucess();

    void error();

    void hasBitmap(Bitmap bm);
}
