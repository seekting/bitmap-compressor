package com.seekting.bitmap.compressor.encoder;

import android.graphics.Bitmap;
import android.util.SparseArray;

/**
 * Created by seekting on 2017/11/26.
 */

public class BitmapEncoder implements Encoder<Bitmap> {


    @Override
    public Bitmap encode(Bitmap bitmap, String id, SparseArray sparseArray) {
        return bitmap;
    }
}
