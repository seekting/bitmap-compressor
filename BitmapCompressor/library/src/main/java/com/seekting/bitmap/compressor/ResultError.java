package com.seekting.bitmap.compressor;

import com.seekting.bitmap.compressor.decoder.DecodeException;
import com.seekting.bitmap.compressor.encoder.EncodeException;

/**
 * Created by seekting on 2017/11/27.
 */

public class ResultError extends Error {

    DecodeException mDecodeException;

    EncodeException mEncodeException;
    public ResultError(EncodeException encodeException) {
        mEncodeException = encodeException;
    }


    public ResultError() {
    }

    public DecodeException getDecodeException() {
        return mDecodeException;
    }

    public void setDecodeException(DecodeException decodeException) {
        mDecodeException = decodeException;
    }

    public EncodeException getEncodeException() {
        return mEncodeException;
    }

    public void setEncodeException(EncodeException encodeException) {
        mEncodeException = encodeException;
    }


    public ResultError(DecodeException decodeException) {
        super(decodeException);
        mDecodeException = decodeException;
    }


    public ResultError(DecodeException decodeException, EncodeException encodeException) {
        mDecodeException = decodeException;
        mEncodeException = encodeException;
    }

    @Override
    public Throwable getCause() {
        if (mDecodeException != null) {
            return mDecodeException;
        }
        if (mEncodeException != null) {
            return mEncodeException;
        }
        return this;
    }

    @Override
    public void printStackTrace() {
        if (mDecodeException != null) {
            mDecodeException.printStackTrace();
        }
        if (mEncodeException != null) {
            mEncodeException.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "ResultError{" +
                "mDecodeException=" + mDecodeException +
                ", mEncodeException=" + mEncodeException +
                '}';
    }
}
