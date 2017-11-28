package com.seekting.bitmap.compressor.decoder;

import com.seekting.bitmap.compressor.BaseException;

/**
 * Created by seekting on 2017/11/27.
 */

public class DecodeException extends BaseException {

    public DecodeException(Throwable throwable, String msg) {
        super(throwable, msg);
    }
}
