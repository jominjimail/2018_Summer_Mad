package com.example.madchocho.myapplication;


import android.content.Intent;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class first extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        ImageView iv = (ImageView)findViewById(R.id.imageView3);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(iv);
        Glide.with(this).load(R.raw.fix).into(imageViewTarget);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(),DefaultActivity.class);
                startActivity(intent);
                finish();
            }

        },2000);
    }
}