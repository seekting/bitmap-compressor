package com.seekting.bitmap.compressor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseArray;


/**
 * Created by Administrator on 2017/11/26.
 */

public class StringDecoder implements Decoder<String> {
    @Override
    public Bitmap decode(String s, SparseArray sparseArray) {
        return BitmapFactory.decodeFile(s);
    }
}
