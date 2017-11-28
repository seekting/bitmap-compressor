package com.seekting.bitmap.compressor.encoder;

import com.seekting.bitmap.compressor.BaseException;

/**
 * Created by seekting on 2017/11/27.
 */

public class EncodeException extends BaseException {
    public EncodeException(Throwable throwable, String msg) {
        super(throwable, msg);
    }
}
