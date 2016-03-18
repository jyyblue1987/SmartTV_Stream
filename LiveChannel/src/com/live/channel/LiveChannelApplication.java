package com.live.channel;



import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import common.design.layout.ScreenAdapter;
import common.library.utils.DataUtils;
import common.library.utils.MessageUtils;
import common.network.utils.NetworkUtils;
import io.vov.vitamio.Vitamio;


public class LiveChannelApplication extends Application {
	
	   @Override
	    public void onCreate() {
	        super.onCreate();
	        
	        Vitamio.isInitialized(this);
	        initScreenAdapter();
	        setContextToComponents();
	        initImageLoader(this);
	   }   
	 
	   private void initScreenAdapter()
	   {
		   ScreenAdapter.setDefaultSize(1080, 1920);        
	       ScreenAdapter.setApplicationContext(this.getApplicationContext());
	   }
	   
	   private void setContextToComponents()
	   {
		   DataUtils.setContext(this);
		   NetworkUtils.setContext(this);
		   MessageUtils.setApplicationContext(this);
	   }
	   
	   private void initImageLoader(Context context) {
		   DisplayImageOptions defaultDisplayImageOptions = new DisplayImageOptions.Builder()
			.cacheInMemory(true)
			.cacheOnDisk(true)
			.imageScaleType(ImageScaleType.EXACTLY)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.considerExifParams(true).build();
			
		    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
		    .threadPriority(Thread.NORM_PRIORITY)
		    .denyCacheImageMultipleSizesInMemory()
		    .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
		    .defaultDisplayImageOptions(defaultDisplayImageOptions)
		    .threadPoolSize(3)
		    .diskCacheFileCount(100) // default
		    .memoryCacheSizePercentage(80)
		    .tasksProcessingOrder(QueueProcessingType.LIFO)
		    .build();
		    
		    ImageLoader imageLoader = ImageLoader.getInstance();
		    imageLoader.init(config);	
//		    imageLoader.clearDiskCache();
//		   ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));	
	   }
	   
	   public static LiveChannelApplication getApplication(Context context) {
	       return (LiveChannelApplication) context.getApplicationContext();
	   }   
	   
	   
	  
	   
}
