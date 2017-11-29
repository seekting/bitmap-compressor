package com.seekting.bitmap.compressor;

/**
 * Created by Administrator on 2017/11/29.
 */

public class ObjectHelper {
    public static <T> T requireNotNull(T t, String msg) {
        if (t == null) {
            throw new NullPointerException(msg);
        }
        return t;
    }
}
