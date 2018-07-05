package com.example.madchocho.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class fragment_third extends Fragment
{
    final static String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/TestLog/dailylocation.txt";
    final static String filePath2 = Environment.getExternalStorageDirectory().getAbsolutePath()+"/TestLog/day.txt";
    final static String foldername = Environment.getExternalStorageDirectory().getAbsolutePath()+"/TestLog";
    final static String filename = "day.txt";
    TextView txtRead;
    TextView txttime;
    String[] blist={"N1","인문사회과학동","문화기술대학원", "산디동", "교분", "카마","매점","아름관","소망관","사랑관","진리관","성실관","신뢰관","지혜관","스컴","기계동", "학부운동장","태울관","인간이 살기엔 너무나 멀고 척박한 곳","건물은 많은데 자기인생은 없는 곳" };
static boolean gg=false;


    public fragment_third()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_fragment_third, container, false);
        Button b= (Button)layout.findViewById(R.id.gpsb);
        txtRead = (TextView)layout.findViewById(R.id.txtRead);
        txttime = (TextView)layout.findViewById(R.id.tv1);
        Button b2= (Button)layout.findViewById(R.id.bt2);




        String read = ReadTextFile(filePath);
        txtRead.setText(read);
        txttime.setText(ReadTextFile2(filePath2)+"부터 당신은");





        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                File f=new File(filePath);
                if(gg) {
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    String getTime = sdf.format(date);
                    //String fin=getTime+now;
                    WriteTextFile(foldername,filename,getTime+"\n");

                }
                Intent in= new Intent(getActivity(),GpsActivity2.class);

                //gg=false;
                startActivity(in);

            }




        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File f=new File(filePath);
                File f2=new File(filePath2);
                f2.delete();
                f.delete();
                txttime.setText("측정정보가 초기화되어 장미한송이 놓고갑니다*^^*{@}----");
                txtRead.setText("");
                gg=true;
            }
        });
        return layout;
    }
    public String ReadTextFile(String path){
        StringBuffer strBuffer = new StringBuffer();
        try{
            InputStream is = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line="";
            int[] occ={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
            while((line=reader.readLine())!=null){
                for(int h=0;h<20;h++){
                if(line.equals(blist[h])){
                    occ[h]=occ[h]+1;
                }
                }


             //   strBuffer.append(line+"\n");
            }
            for(int j=0;j<20;j++){
                if(occ[j]!=0){
                    strBuffer.append(blist[j]+"에서"+occ[j]*3+"분의 시간을 보냈습니다"+"\n");
                }

            }
            reader.close();
            is.close();
        }catch (IOException e){
            e.printStackTrace();
            return "";
        }
        return strBuffer.toString();
    }
    public String ReadTextFile2(String path){
        StringBuffer strBuffer = new StringBuffer();
        try{
            InputStream is = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line="";


                strBuffer.append(reader.readLine());


                //   strBuffer.append(line+"\n");


            reader.close();
            is.close();
        }catch (IOException e){
            e.printStackTrace();
            return "";
        }
        return strBuffer.toString();
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

}

