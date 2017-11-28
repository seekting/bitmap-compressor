package com.seekting.bitmap.compressor.encoder;

import android.graphics.Bitmap;
import android.util.SparseArray;

/**
 * Created by seekting on 2017/11/26.
 */

public interface Encoder<To> {
    String ENCODER_NAME = "compress";

    To encode(Bitmap bitmap, String id, SparseArray sparseArray) throws EncodeException;
}
