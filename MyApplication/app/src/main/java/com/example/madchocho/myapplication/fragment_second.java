package com.example.madchocho.myapplication;

import android.Manifest;
import android.content.Context;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jp.wasabeef.glide.transformations.CropSquareTransformation;

import static android.app.Activity.RESULT_OK;


public class fragment_second extends Fragment
{
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public int inSampleSize = 1;
    Fragment f=this;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;
    private static String basePath;

    public float imageViewRotation = 90;
    public String TAG = "Camera Example :: ";

    DisplayMetrics mMetrics;
    GridView gridview;
int REQ_PICK_CODE=0;
    ImageView im;


    private Context mContext=getActivity();
    ImageView imageView;
    Button openimg;
    private int PICK_IMAGE_REQUEST = 1;


    public fragment_second()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permissionCheck== PackageManager.PERMISSION_DENIED) {

           requestPermissions(
                   new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    CODE_READ_EXTERNAL_STORAGE);
        }
    }
    class MyAdapter extends BaseAdapter {
        Context context;
        int layout;
        ArrayList<String> l;
        LayoutInflater inf;

        public MyAdapter(Context context, int layout, ArrayList<String> lst) {
            this.context = context;
            this.layout = layout;
            this.l = lst;
            inf = (LayoutInflater) context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return l.size();
        }

        @Override
        public Object getItem(int position) {
            return l.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null)
                convertView = inf.inflate(layout, null);
            //Context c=getContext();
            ImageView iv = (ImageView)convertView.findViewById(R.id.imageView1);
           // Toast.makeText(context,l.get(position),Toast.LENGTH_SHORT).show();
            Glide.with(getActivity()).load(l.get(position)).override(400,300).into(iv);
            //iv.setImageResource(img[position]);

            return convertView;
        }
    }
    public class CustomBitmapPool implements BitmapPool {
        @Override
        public int getMaxSize() {
            return 0;
        }

        @Override
        public void setSizeMultiplier(float sizeMultiplier) {

        }

        @Override
        public boolean put(Bitmap bitmap) {
            return false;
        }

        @Override
        public Bitmap get(int width, int height, Bitmap.Config config) {
            return null;
        }

        @Override
        public Bitmap getDirty(int width, int height, Bitmap.Config config) {
            return null;
        }

        @Override
        public void clearMemory() {

        }

        @Override
        public void trimMemory(int level) {

        }
    }

    class Data implements Serializable {

        int data1;
        int data2;

        public int getData1(){
            return data1;
        }

        public int getData2(){
            return data2;
        }

        public void setData1(int data1){
            this.data1 = data1;
        }

        public void setData2(int data2){
            this.data2 = data2;
        }
    }



int CODE_READ_EXTERNAL_STORAGE=11;
int CODE_WRITE_EXTERNAL_STORAGE=12;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.image_list, container, false);
        gridview=layout.findViewById(R.id.ImgGridView);
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE);
       // int permissionCheck2 = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionCheck== PackageManager.PERMISSION_DENIED) {

           // FragmentTransaction ft=getFragmentManager().beginTransaction();
            //ft.detach(this).attach(this).commit();
            //Intent in=getActivity().getIntent();
           // in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            //getActivity().finish();
           // startActivity(in);

        }
        else {

            File[] listFiles = (new File(Environment.getExternalStorageDirectory() + "/dcim/camera/").listFiles());
            ArrayList<String> list = new ArrayList<String>();
            for (File file : listFiles) {
                if (file.getName().endsWith(".jpg") )
                {
                    String s=file.toString();

                    list.add(s);}
            }


        MyAdapter adapter = new MyAdapter (
                getActivity().getApplicationContext(),
                R.layout.row,       // GridView 항목의 레이아웃 row.xml
                list);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long rowID) {

                                                   String f= ((String)parent.getItemAtPosition(position));
                                                   //String s= f.toString();
                                                   //Uri uri=Uri.parse(s);
                                                   Intent in = new Intent(getActivity(), picture.class);
                                                   in.putExtra("data",f);
                                                   startActivity(in);


                                                }

        });

        }
  //      im = layout.findViewById(R.id.image);

        /*
        Button gallery = (Button) layout.findViewById(R.id.Gallery);
        gallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent pickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                pickerIntent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                pickerIntent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickerIntent, REQ_PICK_CODE);
            }
        });
*/

/*
if(mak){FragmentTransaction ft=getFragmentManager().beginTransaction();
    ft.detach(this).attach(this).commit();}
*/

        return layout;
    }
/*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LayoutInflater gridInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = gridInflater.inflate(R.layout.sample, null);
        GridView gridView = (GridView) v.findViewById(R.id.gridview);
        ImageAdapter imageAdapter = new ImageAdapter(getActivity());
        gridView.setAdapter(imageAdapter);


    }
*/






}











