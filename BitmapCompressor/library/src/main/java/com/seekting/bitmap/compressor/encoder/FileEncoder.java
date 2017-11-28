package com.seekting.bitmap.compressor.encoder;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.SparseArray;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 * Created by seekting on 2017/11/26.
 */

public class FileEncoder implements Encoder<File> {

    @Override
    public File encode(Bitmap bitmap, String id, SparseArray sparseArray) throws EncodeException {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        File sdcard = Environment.getExternalStorageDirectory();
        File defaultCompressDir = new File(sdcard, "compress");
        String dir = (String) sparseArray.get(EncoderConfigKeys.TARGET_FILE_DIR_STRING, defaultCompressDir.getAbsolutePath());
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        if (!dirFile.exists()) {
//            if compress dir not exists ,throw exception
            throw new EncodeException(null, "no such dir " + dirFile + " to store compressed files.");
        }
        try {
            String fileName = new StringBuffer().append(ENCODER_NAME).append("_").append(id).append(".jpg").toString();
            File file = new File(dir, fileName);
            float factor = (float) sparseArray.get(EncoderConfigKeys.COMPRESS_FACTOR, 1f);
            bitmap.compress(Bitmap.CompressFormat.WEBP, (int) (60 * factor), new FileOutputStream(file));
            return file;
        } catch (Throwable e) {
            throw new EncodeException(e, "encode error!");
        }
    }
}
