package com.demoapp.android.core.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.demoapp.android.core.http.HttpRequest;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by yasirmahmood on 12/06/2017.
 */

public class ImageHelper
{
    public static Bitmap decodeSampledBitmapFromStream(URL uri, int reqWidth,
                                                       int reqHeight) {
        HttpRequest http = new HttpRequest();
        BitmapFactory.Options options = new BitmapFactory.Options();

        try
        {
            InputStream is = http.getInputStream(uri);
            options.inJustDecodeBounds = true;
            // BitmapFactory.decodeResource(res, resId, options);

            BitmapFactory.decodeStream(is, null, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            is.reset();

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;

            return BitmapFactory.decodeStream(http.getInputStream(uri), null, options);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;

    }

    static class FlushedInputStream extends FilterInputStream
    {
        public FlushedInputStream(InputStream inputStream)
        {
            super(inputStream);
        }

        @Override
        public long skip(long n) throws IOException
        {
            long totalBytesSkipped = 0L;

            while (totalBytesSkipped < n)
            {
                long bytesSkipped = in.skip(n - totalBytesSkipped);

                if (bytesSkipped == 0L)
                {
                    int bytes = read();

                    if (bytes < 0)
                    {
                        break;  // we reached EOF
                    }
                    else
                    {
                        bytesSkipped = 1; // we read one byte
                    }
                }

                totalBytesSkipped += bytesSkipped;
            }

            return totalBytesSkipped;
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth)
        {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth)
            {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
