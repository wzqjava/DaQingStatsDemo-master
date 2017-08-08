package com.stats.daqing.feature.banner;

import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;

/**
 * Created by Administrator on 2017/2/14.
 */

public class BannerImageLoader extends ImageLoader {



    @Override
    public void displayImage(Context context, Object obj, ImageView imageView) {

        com.nostra13.universalimageloader.core.ImageLoader imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
        imageLoader.displayImage((String) obj,imageView);
    }




}
