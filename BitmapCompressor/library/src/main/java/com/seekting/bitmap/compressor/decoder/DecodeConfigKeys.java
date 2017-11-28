package com.seekting.bitmap.compressor.decoder;

import android.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by seekting on 2017/11/26.
 */

@IntDef({DecodeConfigKeys.MIN_FILE_SIZE,
        DecodeConfigKeys.MIN_BITMAP_WIDTH,
        DecodeConfigKeys.MIN_BITMAP_HEIGHT,
})
@Retention(RetentionPolicy.SOURCE)
public @interface DecodeConfigKeys {
    int MIN_FILE_SIZE = 0;
    int MIN_BITMAP_WIDTH = 1;
    int MIN_BITMAP_HEIGHT = 2;
}
