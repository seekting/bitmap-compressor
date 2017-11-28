package com.seekting.bitmap.compressor.decoder;

import android.graphics.Bitmap;
import android.util.SparseArray;

import com.seekting.bitmap.compressor.source.StringSource;

import java.io.File;


/**
 * Created by seekting on 2017/11/26.
 */

public class StringDecoder implements Decoder<StringSource> {
    @Override
    public Bitmap decode(StringSource source, SparseArray sparseArray) throws DecodeException {
        Bitmap b = null;
        try {
            b = DecodeUtil.decode(new File(source.getFrom()), sparseArray);

        } catch (Throwable t) {
            throw new DecodeException(t, "BitmapFactory decode fail!! ");
        }

        if (b == null) {
            throw new DecodeException(null, "BitmapFactory decode fail!! ");
        }
        return b;
    }
}
