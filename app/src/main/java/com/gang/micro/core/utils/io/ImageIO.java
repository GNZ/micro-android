package com.gang.micro.core.utils.io;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gang.micro.core.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageIO {

    public final static String PICTURE_EXTENSION = ".jpg";
    public final static String ANALYSIS_EXTENSION = ".json";
    static final String SUB_FOLDER = "micro";
    public static final String FOLDER = Environment.getExternalStorageDirectory() + File.separator + SUB_FOLDER + File.separator;

    public ImageIO() {
    }

    public Image readImage(String path) throws IOException {

        Image image;

        String analysisPath = path + ANALYSIS_EXTENSION;
        String bitmapPath = path + PICTURE_EXTENSION;

        // Get data
        image = new ObjectMapper().readValue(new File(analysisPath), Image.class);

        // Get bitmap
        Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(bitmapPath));
        image.setBitmap(bitmap);

        return image;
    }

    public List<Image> readImages() throws IOException {
        List<Image> images = new ArrayList<>();

        List<String> filenames = getFilenames(FOLDER);

        for (String filename : filenames) {
            images.add(readImage(filename));
        }

        return images;
    }

    private List<String> getFilenames(String path) {

        List<String> filenames = new ArrayList<>();

        File folder = new File(path);

        File files[] = folder.listFiles();

        for (File file : files) {
            String name = file.getAbsolutePath();

            if (name.endsWith(PICTURE_EXTENSION)) {
                int pos = name.lastIndexOf(".");
                if (pos > 0)
                    name = name.substring(0, pos);
                filenames.add(name);
            }
        }

        return filenames;
    }

}
