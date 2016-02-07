package com.gang.micro.core.utils.io;

import android.graphics.Bitmap;
import android.os.Environment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gang.micro.core.image.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageIO {

    public final static String PICTURE_EXTENSION = ".jpg";
    public final static String ANALYSIS_EXTENSION = ".json";
    static final String SUB_FOLDER = "micro";
    public static final String FOLDER = Environment.getExternalStorageDirectory() + File.separator + SUB_FOLDER + File.separator;
    public static final String TEMP_PICTURE_FILE = FOLDER + "temp_picture" + PICTURE_EXTENSION;

    public ImageIO() {
        File file = new File(FOLDER);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static Image readImage(String path) throws IOException {

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

    public static List<String> getFilenames(String path) {

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

    public static boolean deleteImage(Image image) {
        File imageToDelete = new File(FOLDER +
                image.getId().toString() + ANALYSIS_EXTENSION);
        return imageToDelete.delete();
    }

    public static boolean deletePicture(Image image) {
        return deletePicture(getPicturePath(image));
    }

    public static boolean deletePicture(String path) {
        File picture = new File(path);
        return picture.delete();
    }

    public static boolean savePicture(Bitmap bitmap, Image image) {
        String id = image.getId().toString();
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(FOLDER + id + PICTURE_EXTENSION);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static File savePicture(Bitmap bitmap, String path) {

        File picture = new File(path);
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(picture);
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.flush();
            } finally {
                outputStream.close();
            }
        } catch (IOException e) {
            return null;
        }
        return picture;
    }

    public static boolean saveImage(Image image) {
        String id = image.getId().toString();
        try {
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
