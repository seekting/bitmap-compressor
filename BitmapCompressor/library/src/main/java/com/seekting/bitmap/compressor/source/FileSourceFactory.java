package com.seekting.bitmap.compressor.source;

import java.io.File;

/**
 * Created by seekting on 2017/11/27.
 */

public class FileSourceFactory implements SourceFactory<File> {
    @Override
    public FileSource create(File file) {
        return new FileSource(file);
    }
}
