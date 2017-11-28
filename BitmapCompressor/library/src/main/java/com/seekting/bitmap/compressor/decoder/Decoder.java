package com.seekting.bitmap.compressor.decoder;

import android.graphics.Bitmap;
import android.util.SparseArray;

/**
 * Created by seekting on 2017/11/26.
 */

public interface Decoder<From> {
    Bitmap decode(From source, SparseArray sparseArray) throws DecodeException;
}
