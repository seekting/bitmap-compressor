package com.seekting.bitmap.compressor.source;

/**
 * Created by seekting on 2017/11/27.
 */

public abstract class Source<From> {


    protected final From mFrom;
    protected String id;

    public Source(From from) {
        this.mFrom = from;
        generateId(from);
    }


    public abstract void generateId(From from);

    public final String getId() {
        return id;
    }

    public From getFrom() {
        return mFrom;
    }
}
