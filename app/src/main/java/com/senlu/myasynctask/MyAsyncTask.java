package com.senlu.myasynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/10/19.
 */
public class MyAsyncTask extends AsyncTask<String,Void,Bitmap>{

    private Bitmap bitmap;
    private HttpURLConnection connection;
    private OnCompletedListener listener;

    public interface OnCompletedListener{
        void onCompleted(Bitmap bitmap);
    }
    public void setOnCompleted(OnCompletedListener listener){
        this.listener = listener;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String urlString = params[0];
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        if (null != listener){
            listener.onCompleted(bitmap);
        }

    }
}
