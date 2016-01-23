package com.gang.micro.core.Utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManager {

    public FileManager(){

    }

    public static File createFolder(String folder) {
        File exportDir = new File(folder);
        if (!exportDir.exists() || !exportDir.isDirectory()) {
            exportDir.mkdir();
        }
        return exportDir;
    }

    public static boolean saveJpegImageFromBitmap(String path,String name, Bitmap bitmap){
        //Save the picture
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(path + name + ".jpg");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        } catch (Exception e) {
            //return false;
        }
        finally {
            try {
                if (fOut != null) {
                    fOut.flush();
                    fOut.close();
                }

            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

}
