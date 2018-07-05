package com.example.madchocho.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

public class picture extends AppCompatActivity {
Uri ff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        Intent inte=getIntent();
        ImageView v=findViewById(R.id.imageViewf);
        String fs=(String)inte.getStringExtra("data");

        //ff=inte.getParcelableExtra("data");
       // Uri uri= Uri.parse(fs);
        Glide.with(this).load(fs).into(v);
        
    }
}
