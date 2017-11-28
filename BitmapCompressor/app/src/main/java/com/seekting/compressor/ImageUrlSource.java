package com.seekting.compressor;

import com.seekting.bitmap.compressor.source.Source;


/**
 * Created by seekting on 2017/11/28.
 */

public class ImageUrlSource extends Source<ImageUrl> {


    public ImageUrlSource(ImageUrl imageUrl) {
        super(imageUrl);
    }

    @Override
    public void generateId(ImageUrl imageUrl) {

    }
}
