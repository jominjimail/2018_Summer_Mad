package com.example.madchocho.misea;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView)findViewById(R.id.textView2);

        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        // 위치관리자 객체를 얻어온다

//        lm.getBestProvider(criteria, enabledOnly)
        List<String> list = lm.getAllProviders(); // 위치제공자 모두 가져오기

        String str = ""; // 출력할 문자열
        for (int i = 0; i < list.size(); i++) {
            str += "위치제공자 : " + list.get(i) + ", 사용가능여부 -"
                    + lm.isProviderEnabled(list.get(i)) +"\n";
        }
        tv.setText(str);



    }
}
