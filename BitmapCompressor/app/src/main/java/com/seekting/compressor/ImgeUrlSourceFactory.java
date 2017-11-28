package com.seekting.compressor;


import com.seekting.bitmap.compressor.source.SourceFactory;

/**
 * Created by seekting on 2017/11/28.
 */

public class ImgeUrlSourceFactory implements SourceFactory<ImageUrl> {
    @Override
    public ImageUrlSource create(ImageUrl imageUrl) {
        return new ImageUrlSource(imageUrl);
    }
}
