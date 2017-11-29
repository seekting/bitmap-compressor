package com.seekting.bitmap.compressor.decoder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseArray;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by seekting on 2017/11/28.
 */

public class DecodeUtil {

    public static Bitmap decode(File file, SparseArray config) throws DecodeException {
        if (!file.exists()) {
            throw new DecodeException(null, "File Not Exit:" + file.getAbsolutePath());
        }
        InputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new DecodeException(null, "File Not Exit:" + file.getAbsolutePath());
        }

        Bitmap result = decodeExpectBitmap(fileInputStream, (int) file.length(), config);
        if (fileInputStream != null) {
            try {
                fileInputStream.close();
            } catch (IOException e) {
            }
        }
        return result;


    }

    public static Bitmap decodeExpectBitmap(InputStream inputStream, int size, SparseArray config) throws DecodeException {
        byte[] buffer = new byte[256];
        RecyclableBufferedInputStream recyclableBufferedInputStream = new RecyclableBufferedInputStream(inputStream, buffer);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        recyclableBufferedInputStream.mark(size);
        BitmapFactory.decodeStream(recyclableBufferedInputStream, null, options);
        int minBitmapWidth = (int) config.get(DecodeConfigKeys.MIN_BITMAP_WIDTH, options.outWidth);
        int minBitmapHeight = (int) config.get(DecodeConfigKeys.MIN_BITMAP_HEIGHT, options.outHeight);
        int minFileSize = (int) config.get(DecodeConfigKeys.MIN_FILE_SIZE, size);
        // only expect size > real size,we decode the whole File
        if (minFileSize >= size && minBitmapWidth >= options.outWidth && minBitmapHeight > options.outHeight) {
            options.inJustDecodeBounds = false;
            try {
                recyclableBufferedInputStream.reset();
            } catch (IOException e) {
                throw new DecodeException(e, "reset fail");
            }
            return decodeFromInputStreamAndClose(recyclableBufferedInputStream, options);
        } else {
            int realWidth = options.outWidth;
            int realHeight = options.outHeight;
            int inSimple = 1;
            while (minBitmapWidth < realWidth || minBitmapHeight < realHeight) {
                realHeight = realHeight >> 1;
                realWidth = realWidth >> 1;
                inSimple = inSimple << 1;
            }

            options.inJustDecodeBounds = false;
            options.inSampleSize = inSimple;

            try {
                recyclableBufferedInputStream.reset();
            } catch (IOException e) {
                throw new DecodeException(e, "reset fail");
            }
            return decodeFromInputStreamAndClose(recyclableBufferedInputStream, options);
        }
    }

    public static Bitmap decodeFromInputStreamAndClose(InputStream inputStream, BitmapFactory.Options options) throws DecodeException {
        try {

            Bitmap bm = null;
            bm = BitmapFactory.decodeStream(inputStream, null, options);
            return bm;
        } catch (Exception e) {
            throw new DecodeException(e, "decode fail!");
        } catch (OutOfMemoryError o) {
            throw new DecodeException(o, "oom!");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // do nothing here
                }
            }
        }
    }
}
