package com.example.madchocho.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class DefaultActivity extends AppCompatActivity
{
    Context ct=this;
    Activity a=this;
    ViewPager vp;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);

        vp = (ViewPager)findViewById(R.id.vp);
        Button btn_first = (Button)findViewById(R.id.btn_first);
        Button btn_second = (Button)findViewById(R.id.btn_second);
        Button btn_third = (Button)findViewById(R.id.btn_third);
    pagerAdapter pgA=new pagerAdapter(getSupportFragmentManager());
        vp.setAdapter(pgA);
        vp.setCurrentItem(0);

        btn_first.setOnClickListener(movePageListener);
        btn_first.setTag(0);
        btn_second.setOnClickListener(movePageListener);
        btn_second.setTag(1);
        btn_third.setOnClickListener(movePageListener);
        btn_third.setTag(2);
    }

    View.OnClickListener movePageListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int tag = (int) v.getTag();
            pagerAdapter pgA2=new pagerAdapter(getSupportFragmentManager());
            vp.setAdapter(pgA2);
            vp.setCurrentItem(tag);
        }
    };

    private class pagerAdapter extends FragmentStatePagerAdapter
    {

        public pagerAdapter(android.support.v4.app.FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {

            switch(position)
            {
                case 0:
                    int permissionCheck = ContextCompat.checkSelfPermission(ct, android.Manifest.permission.READ_CONTACTS);






                    return  new fragment_first();
                case 1:

                    return new fragment_second();
                case 2:
                    return new fragment_third();
                default:
                    return null;
            }
        }
        @Override
        public int getCount()
        {
            return 3;
        }
    }
    final int MY_PERMISSIONS_REQUEST_READ_CONTACTS=10;
    final int CODE_READ_EXTERNAL_STORAGE=11;

/*
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:

                if ( grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(ct, "주소록 접근이 승인되었습니다.", Toast.LENGTH_SHORT).show();
                   // startActivity(a.getIntent());
                } else {
                    Toast.makeText(ct, "주소록 접근이 거절되었습니다. 추가 승인이 필요합니다.", Toast.LENGTH_SHORT).show();
                }

                break;


            case CODE_READ_EXTERNAL_STORAGE:
                if ( grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(ct, "카메라 접근이 승인되었습니다.", Toast.LENGTH_SHORT).show();
                    //fragment_second.mak=true;
                   // startActivity(a.getIntent());
                } else {
                    Toast.makeText(ct, "카메라 접근이 거절되었습니다. 추가 승인이 필요합니다.", Toast.LENGTH_SHORT).show();
                }







        }

    }

*/private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                       int[] grantResults) {
    if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        GpsActivity.isAccessFineLocation = true;

    } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
            && grantResults[0] == PackageManager.PERMISSION_GRANTED){

        GpsActivity.isAccessCoarseLocation = true;
    }

    if (GpsActivity.isAccessFineLocation && GpsActivity.isAccessCoarseLocation) {
        GpsActivity.isPermission = true;
    }
}

}




