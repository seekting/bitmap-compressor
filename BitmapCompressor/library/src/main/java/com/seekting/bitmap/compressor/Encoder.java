package com.seekting.bitmap.compressor;

import android.graphics.Bitmap;
import android.util.SparseArray;

/**
 * Created by Administrator on 2017/11/26.
 */

public interface Encoder<Target> {
    Target encode(Bitmap bitmap, SparseArray sparseArray);
}
