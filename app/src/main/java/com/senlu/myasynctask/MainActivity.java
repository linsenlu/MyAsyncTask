package com.senlu.myasynctask;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv;
    private Button btn;
    String url = "http://a1.att.hudong.com/36/65/300001062059132062654118155_950.jpg";
    private LruCache<String, Bitmap> cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = (ImageView) findViewById(R.id.iv);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
        initLruCachce();


    }

    private void initLruCachce() {
        int maxSize = (int) (Runtime.getRuntime().maxMemory()/8);
        cache = new LruCache<String,Bitmap>(maxSize);
    }

    @Override
    public void onClick(View v) {
        Bitmap bitmap = cache.get(url);
        if (bitmap == null){
            MyAsyncTask task = new MyAsyncTask();
            task.execute(url);
            task.setOnCompleted(new MyAsyncTask.OnCompletedListener() {
                @Override
                public void onCompleted(Bitmap bitmap) {
                    cache.put(url,bitmap);
                    iv.setImageBitmap(bitmap);
                    Toast.makeText(MainActivity.this,"from Internet",Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            iv.setImageBitmap(bitmap);
            Toast.makeText(MainActivity.this,"from Cache",Toast.LENGTH_SHORT).show();
        }


    }
}
