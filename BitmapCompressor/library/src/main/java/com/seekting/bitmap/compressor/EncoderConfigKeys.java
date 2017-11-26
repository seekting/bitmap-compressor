package com.seekting.bitmap.compressor;

import android.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2017/11/26.
 */
@IntDef({EncoderConfigKeys.SOURCE_FILE_NAME,
        EncoderConfigKeys.SOURCE_DIR_NAME,
        EncoderConfigKeys.TARGET_FILE_DIR_STRING})
@Retention(RetentionPolicy.SOURCE)
public @interface EncoderConfigKeys {
    int SOURCE_FILE_NAME = 0;
    int SOURCE_DIR_NAME = 1;
    int TARGET_FILE_DIR_STRING = 2;
    int COMPRESS_FACTOR = 3;
}
