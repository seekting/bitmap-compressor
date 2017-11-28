package com.seekting.bitmap.compressor.source;

import java.io.File;

/**
 * Created by seekting on 2017/11/27.
 */

public class StringSource extends Source<String> {


    public StringSource(String s) {
        super(s);
    }

    @Override
    public void generateId(String s) {

        int separator = s.lastIndexOf(File.separator);
        int dot = s.lastIndexOf('.');
        if (dot > separator + 1) {
            id = s.substring(separator + 1, dot);
        } else {

        }
    }


}
