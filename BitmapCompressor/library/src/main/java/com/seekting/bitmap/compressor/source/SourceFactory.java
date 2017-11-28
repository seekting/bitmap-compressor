package com.seekting.bitmap.compressor.source;

/**
 * Created by seekting on 2017/11/27.
 */

public interface SourceFactory<From> {

    Source<From> create(From from);

}
