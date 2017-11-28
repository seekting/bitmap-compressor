package com.seekting.bitmap.compressor;

/**
 * Created by seekting on 2017/11/27.
 */

public class CompressResult<From, To> {
    From mFrom;
    To mTo;
    ResultError mResultError;

    public From getFrom() {
        return mFrom;
    }

    public void setFrom(From from) {
        this.mFrom = from;
    }

    public To getTo() {
        return mTo;
    }

    public void setTo(To to) {
        mTo = to;
    }

    public ResultError getResultError() {
        return mResultError;
    }

    public void setResultError(ResultError resultError) {
        mResultError = resultError;
    }

    @Override
    public String toString() {
        return "CompressResult{" +
                "mFrom=" + mFrom +
                ", mTo=" + mTo +
                ", mResultError=" + mResultError +
                '}';
    }
}
