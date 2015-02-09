package com.myinc.keikha.myfunpicasso;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by keikha on 2/3/15.
 */
public class Kittens {

    private static Kittens instance;

    protected void mew2(String url , ImageView ivImage)
    {
        new ImageLoaderTask(ivImage).execute(url);
    }
    public static void mew(String url , ImageView ivImage)
    {
        if(instance == null)
        {
            instance = new Kittens();
        }
        instance.mew2(url , ivImage);

        // Launach a background
        // make a URL connection
        // Open up a stream
        // Convert the bytes into a Bitmap
        // ToDo: error handling
        // TODO : resize image
        // TODO : caching disk with persistence
        // TODO: generalize the insertion bryond imageview
        // TODO : promise or callback

        // pass context?



    }

    public class ImageLoaderTask extends AsyncTask<String , Void, Bitmap> {

        ImageView imageView;
        public ImageLoaderTask(ImageView image)
        {
            imageView = image;
        }
        // run on the UI thread
        // can be used for showing progress bar
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        // Spawn a thread
        // Pass data to the thread
        // Excuse the thread
        // Return the result of the thread to onPostExcute
        @Override
        protected Bitmap doInBackground(String... params) {
            // make a url connection
            // open up a stream
            // convert the bytes into a Bitmap

            Bitmap bmp = null;

            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.connect();

                InputStream stream = con.getInputStream();
                bmp = BitmapFactory.decodeStream(stream);
                stream.close();


            } catch (IOException e) {
                e.printStackTrace();
            }

            return bmp;
        }


        // runs on the UI Thread
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            this.imageView.setImageBitmap(bitmap);
        }
    }
}
