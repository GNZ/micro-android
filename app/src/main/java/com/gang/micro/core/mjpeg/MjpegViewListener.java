/**
 *
 */
package com.gang.micro.core.mjpeg;

import android.graphics.Bitmap;

/**
 * @author Micho Garcia
 */
public interface MjpegViewListener {

    void sucess();

    void error();

    void hasBitmap(Bitmap bm);
}
