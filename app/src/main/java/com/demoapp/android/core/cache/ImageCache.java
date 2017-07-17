package com.demoapp.android.core.cache;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import java.net.URL;

/**
 * Created by yasirmahmood on 12/06/2017.
 */

public class ImageCache extends LruCache<String, Bitmap>
{

    private final static int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    // Use 1/8th of the available memory for this memory cache.
    private final static int cacheSize = maxMemory / 8;
    private int reqWidth = 100;
    private int reqHeight = 100;

    //private LruCache<String, Bitmap> mMemoryCache;

    public ImageCache(int maxSize) {
        super((maxSize == 0) ? cacheSize : maxSize);
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null && bitmap != null) {
            this.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        if(key != null){
            return this.get(key);
        }
        else
        {
            return null;
        }

    }

    @Override
    protected int sizeOf(String key, Bitmap bitmap) {
        // The cache size will be measured in kilobytes rather than
        // number of items.
        return bitmap.getByteCount() / 1024;
    }

    public void loadBitmap(Activity context, String url, ImageView imageView) {
        //final String imageKey = String.valueOf(resId);

        final Bitmap bitmap = getBitmapFromMemCache(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
        else
        {
            //imageView.setImageResource(R.drawable.default_image_holder);
            BitmapWorkerTask task = new BitmapWorkerTask();
            task.setContext(context);
            task.execute(url, imageView);
        }
    }

    class BitmapWorkerTask extends AsyncTask<Object, Void, Bitmap> {

        private Activity context;
        private ImageView imageView;

        public void setContext(Activity Context){
            BitmapWorkerTask.this.context = context;
        }

        public BitmapWorkerTask(){

        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Object... params) {
            this.imageView = (ImageView) params[1];
            //HttpRequest http = new HttpRequest();
            Bitmap bitmap = null;

            try
            {
                bitmap = ImageHelper.decodeSampledBitmapFromStream(new URL((String) params[0]), reqWidth, reqHeight);
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            this.imageView.setImageBitmap(result);
        }
    }
}
