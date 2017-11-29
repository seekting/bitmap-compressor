package com.seekting.compressor;

import android.graphics.Bitmap;
import android.util.SparseArray;

import com.seekting.bitmap.compressor.encoder.EncodeException;
import com.seekting.bitmap.compressor.encoder.Encoder;

/**
 * Created by Administrator on 2017/11/29.
 */

public class BitmapEncoder implements Encoder<Bitmap> {
    @Override
    public Bitmap encode(Bitmap bitmap, String id, SparseArray sparseArray) throws EncodeException {
        return bitmap;
    }
}
