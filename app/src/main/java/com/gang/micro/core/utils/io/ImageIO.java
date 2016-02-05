package com.gang.micro.core.utils.io;

import android.graphics.Bitmap;
import android.os.Environment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gang.micro.core.image.Image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageIO {

    public final static String PICTURE_EXTENSION = ".jpg";
    public final static String ANALYSIS_EXTENSION = ".json";
    static final String SUB_FOLDER = "micro";
    public static final String FOLDER = Environment.getExternalStorageDirectory() + File.separator + SUB_FOLDER + File.separator;

    public ImageIO() {
        File file = new File(FOLDER);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public Image readImage(String path) throws IOException {

        Image image;

        String analysisPath = path + ANALYSIS_EXTENSION;

        // Get data
        image = new ObjectMapper().readValue(new File(analysisPath), Image.class);

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

    public List<String> getFilenames(String path) {

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

    public boolean deleteImage(String path) {
        File bitmap = new File(path + PICTURE_EXTENSION);
        File image = new File(path + ANALYSIS_EXTENSION);
        if (bitmap.exists() && image.exists())
            return bitmap.delete() && image.delete();
        return false;
    }

    public boolean saveImage(Image image, Bitmap bitmap) {
        String id = image.getId().toString();
        try {
            // Save bitmap
            FileOutputStream outputStream = new FileOutputStream(FOLDER + id + PICTURE_EXTENSION);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            // Save image json
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(FOLDER + id + ANALYSIS_EXTENSION), image);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getPicturePath(Image image) {
        return FOLDER + image.getId().toString() + PICTURE_EXTENSION;
    }


}
