package com.seekting.bitmap.compressor;

import android.graphics.Bitmap;
import android.util.SparseArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2017/11/26.
 */

public class FileEncoder implements Encoder<File> {

    @Override
    public File encode(Bitmap bitmap, SparseArray sparseArray) {

        String dir = (String) sparseArray.get(EncoderConfigKeys.TARGET_FILE_DIR_STRING);
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        try {
            File file = new File(dir, System.currentTimeMillis() + ".jpg");
            float factor = (float) sparseArray.get(EncoderConfigKeys.COMPRESS_FACTOR, 1f);
            bitmap.compress(Bitmap.CompressFormat.WEBP, (int) (60 * factor), new FileOutputStream(file));
            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
