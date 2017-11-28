package com.seekting.bitmap.compressor.source;

/**
 * Created by seekting on 2017/11/27.
 */

public class StringSourceFactory implements SourceFactory<String> {
    @Override
    public StringSource create(String s) {
        return new StringSource(s);
    }
}
