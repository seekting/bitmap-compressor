package com.seekting.bitmap.compressor.source;

import java.io.File;

/**
 * Created by seekting on 2017/11/27.
 */

public class FileSource extends Source<File> {
    public FileSource(File file) {
        super(file);

    }

    @Override
    public void generateId(File file) {
        if (!file.exists()) {
            return;
        }
        String name = mFrom.getName();
        int last = name.lastIndexOf('.');
        if (last > -1) {
            id = name.substring(0, last);
        } else {
            id = name;
        }
    }


//    public static void main(String args[]){
//
//        String s = "ss.fff.jpg";
//        System.out.println(s.substring(0,s.lastIndexOf('.')));
//
//    }
}
