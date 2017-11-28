package com.seekting.bitmap.compressor.encoder;

import android.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by seekting on 2017/11/26.
 */
@IntDef({EncoderConfigKeys.TARGET_FILE_DIR_STRING})
@Retention(RetentionPolicy.SOURCE)
public @interface EncoderConfigKeys {
    int TARGET_FILE_DIR_STRING = 1;
    int COMPRESS_FACTOR = 2;
}
