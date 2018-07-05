package com.example.madchocho.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.LOCATION_SERVICE;

/** GPS 샘플 */
public class GpsActivity2 extends Activity {
    final static String foldername = Environment.getExternalStorageDirectory().getAbsolutePath()+"/TestLog";
    final static String filename = "dailylocation.txt";
    int juststarted=0;



    String[] buildinglist={"N1","인문사회과학동","문화기술대학원", "산디동", "교분", "카마","매점","아름관","소망관","사랑관","진리관","성실관","신뢰관","지혜관","스컴","기계동", "학부운동장","태울관","인간이 살기엔 너무나 멀고 척박한 곳","건물은 많은데 자기인생은 없는 곳" };
    double[] xlist={36.374281,36.373243,36.373988,36.373928,36.374140,36.373760,36.374181,36.373885,36.373812,36.373739,36.374704,36.374294,36.375138,36.375838,36.372437,36.372371,36.372254,36.373139,36.369040,36.369076};
    double[] ylist={127.365437,127.362732,127.363527,127.362068,127.360381,127.359233,127.359761,127.356701,127.357532,127.358361,127.359206,127.358892,127.359131,127.358584,127.361570,127.358689,127.360320,127.360020,127.358797,127.364941};
    private Button btnShowLocation;
    private TextView txtLat;
    private TextView txtLon;
    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private final int PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1002;
    static boolean isAccessFineLocation = false;
    static boolean isAccessCoarseLocation = false;
    static  boolean isWriteFineStorage=false;
    static boolean isPermission = false;

    // GPSTracker class
    private GpsInfo gps;
    //Calendar mCalender=Calendar.getInstance();
    ArrayList<Calendar> callist= new ArrayList<Calendar>();
    int count=0;
    final int requestCode=99;

    @Override
    public void onCreate(Bundle savedInstanceState) {



     /*
        for(int k=0;k<24;k++) {
            for (int j = 0; j < 6; j++) {
                Calendar mCalender = Calendar.getInstance();
                mCalender.set(Calendar.HOUR_OF_DAY, k);
                mCalender.set(Calendar.MINUTE, j * 10);
                mCalender.set(Calendar.SECOND, 0);
                callist.add(mCalender);

            }
        }
*/




//fragment_third.gg=true;

        Intent mAlarmIntent =new Intent(this,AlarmReceiver.class);
        PendingIntent mPendingIntent = PendingIntent.getBroadcast(this, requestCode, mAlarmIntent,0);
        AlarmManager mAlarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        mAlarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000,180000,mPendingIntent);
      mAlarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+180000,mPendingIntent);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gps);

       // ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.activity_gps, container, false);
        btnShowLocation = (Button) findViewById(R.id.btn_start);
        txtLat = (TextView) findViewById(R.id.tv_latitude);
        txtLon = (TextView) findViewById(R.id.tv_longitude);

        // GPS 정보를 보여주기 위한 이벤트 클래스 등록
       // btnShowLocation.setOnClickListener(new View.OnClickListener() {
      //      public void onClick(View arg0) {
                // 권한 요청을 해야 함
                if (!isPermission) {
                    callPermission();
                    return;
                }
                //LocationManager locationManager;
                // locationManager= (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                // boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                gps = new GpsInfo(GpsActivity2.this);
                // GPS 사용유무 가져오기
                if (gps.isGetLocation())
                //if(true)
                {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    txtLat.setText(String.valueOf(latitude));
                    txtLon.setText(String.valueOf(longitude));
                    double curmin=100000.0;
                    int curminobj=0;
                    for(int j=0;j<20;j++){
                        double r= Math.sqrt(Math.pow(Math.abs(xlist[j]-latitude),2)+Math.pow(Math.abs(ylist[j]-longitude),2));
                        if(r<curmin){
                            curmin=r;
                            curminobj=j;
                        }
                    }
                    String hoho=buildinglist[curminobj];

                    String lala = String.valueOf(latitude);
                    String lolo = longitude+"\n";
                  //  WriteTextFile(foldername,filename,lala+" ");
                   // WriteTextFile(foldername,filename,lolo);
                    WriteTextFile(foldername,filename,hoho+"\n");


                    Toast.makeText(
                            getApplicationContext(),
                            "당신의 위치 - \n위도: " + latitude + "\n경도: " + longitude,
                            Toast.LENGTH_LONG).show();
                } else {
                    // GPS 를 사용할수 없으므로
                    gps.showSettingsAlert();
                }
       //     }
       // });

        callPermission();  // 권한 요청을 해야 함
        juststarted++;
        finish();


    }
    public void WriteTextFile(String foldername, String filename, String contents){

        try{
            File dir = new File(foldername);
            //디렉토리 폴더가 없으면 생성함
            if(!dir.exists()){
                dir.mkdir();
            }
            //파일 output stream 생성
            FileOutputStream fos = new FileOutputStream(foldername+"/"+filename, true);
            //파일쓰기
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(contents);
            writer.flush();

            writer.close();
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocation = true;

        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            isAccessCoarseLocation = true;
        }else if(requestCode == PERMISSIONS_WRITE_EXTERNAL_STORAGE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            isWriteFineStorage=true;

        }

        if (isAccessFineLocation && isAccessCoarseLocation&&isWriteFineStorage) {
            isPermission = true;
        }
    }

    // 전화번호 권한 요청
    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                &&checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSIONS_WRITE_EXTERNAL_STORAGE);

        }
        else{
            isPermission = true;
        }
    }
}