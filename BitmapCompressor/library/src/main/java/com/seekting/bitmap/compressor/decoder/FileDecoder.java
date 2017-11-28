package com.seekting.bitmap.compressor.decoder;

import android.graphics.Bitmap;
import android.util.SparseArray;

import com.seekting.bitmap.compressor.source.FileSource;

/**
 * Created by seekting on 2017/11/26.
 */

public class FileDecoder implements Decoder<FileSource> {


    @Override
    public Bitmap decode(FileSource fileSource, SparseArray sparseArray) throws DecodeException {
        Bitmap b = null;
        try {
            b = DecodeUtil.decode(fileSource.getFrom(), sparseArray);

        } catch (Throwable t) {
            throw new DecodeException(t, "BitmapFactory decode fail!! ");
        }

        if (b == null) {
            throw new DecodeException(null, "BitmapFactory decode fail!! ");
        }
        return b;
    }
}
