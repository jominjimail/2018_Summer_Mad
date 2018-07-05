package com.example.madchocho.myapplication;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class fragment_first extends Fragment
{
    public fragment_first()
    {
    }
    static boolean mark=false;
    private static final String ARG_PARAM1="param1";
    private static final String ARG_PARAM2="param2";
    private String mParam1;
    private String mParam2;
    private ArrayList<String> mList;
    ListView lv;
    Context context=getActivity();

    int MY_PERMISSIONS_REQUEST_READ_CONTACTS=10;
    int CODE_READ_EXTERNAL_STORAGE=11;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
       // ArrayList<Person> m_orders = new ArrayList<Person>();


        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_CONTACTS);



        if(permissionCheck== PackageManager.PERMISSION_DENIED){


            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);


        }


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        mark=false;
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.activity_main, container, false);
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_CONTACTS);
        if(!(permissionCheck== PackageManager.PERMISSION_DENIED)) {
            mList = new ArrayList<String>();
            // lv = (ListView)findViewById(R.id.list);
            ArrayList<Person> m_orders = new ArrayList<Person>();
            Map<String, String> phone_address = ContactUtil.getAddressBook(getActivity());
            lv = (ListView) view.findViewById(R.id.list);
            Iterator ite = phone_address.keySet().iterator();
            while (ite.hasNext()) {
                String phone = ite.next().toString();
                String name = phone_address.get(phone).toString();
                m_orders.add(new Person(name, phone));
            }
            PersonAdapter m_adapter = new PersonAdapter(getActivity(), R.layout.view_friend_list, m_orders);
            lv.setAdapter(m_adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long rowID) {
                    mark=true;
                    View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_layout, null);
                    final PopupWindow pw = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, 1700);

                    ListView popup_list = (ListView) popupView.findViewById(R.id.popup_list);
                    ArrayList<Person> sel_orders = new ArrayList<Person>();
                    sel_orders.add((Person) parent.getItemAtPosition(position));
                    PersonAdapter sel_adapter = new PersonAdapter(getActivity(),R.layout.view_friend_list,sel_orders);
                    popup_list.setAdapter(sel_adapter);

                    pw.showAsDropDown(popupView, 0, 0);
                    Button popclose=(Button)popupView.findViewById(R.id.btn_popup);
                    popclose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            pw.dismiss();
                        }
                    });
                   // doSelectFriend((Person) parent.getItemAtPosition(position));
                }
            });
        }else {


            // Intent in=getActivity().getIntent();
            //in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            //getActivity().finish();
           // startActivity(in);
            FragmentTransaction ft=getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();

        }
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if(requestCode==MY_PERMISSIONS_REQUEST_READ_CONTACTS&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
          //  FragmentTransaction ft=getFragmentManager().beginTransaction();
          //  ft.detach(this).attach(this).commit();
            Toast.makeText(context,"주소록 접근이 승인되었습니다.",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"주소록 접근이 거절되었습니다. 추가 승인이 필요합니다.",Toast.LENGTH_SHORT).show();

        }


    }
    // 한명 선택했을 때
   // public void doSelectFriend(Person p)
   // {
    //    Log.e("####", p.getName() + ", " + p.getNumber());
   // }

    public class PersonAdapter extends ArrayAdapter<Person>
    {
        private ArrayList<Person> items;

        public PersonAdapter(Context context, int textViewResourceId, ArrayList<Person> items)
        {
            super(context, textViewResourceId, items);
            this.items = items;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View v = convertView;
            if (v == null)
            {
                LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.view_friend_list, null);
            }
            Person p = items.get(position);
            if (p != null)
            {
                TextView tt = (TextView) v.findViewById(R.id.name);
                TextView bt = (TextView) v.findViewById(R.id.msg);
                if (tt != null)
                {
                    tt.setText(p.getName());
                }
                if(bt != null)
                {
                    if(p.getNumber()!="foobar"){
                        if(mark){
                    bt.setText("전화번호: "+ p.getNumber());}}
                }
            }
            return v;
        }
    }
    class Person
    {
        private String Name;
        private String Number;

        public Person(String _Name, String _Number)
        {
            this.Name = _Name;
            this.Number = _Number;
        }
        public Person(String _Name){
            this.Name=_Name;
            this.Number = "foobar";

        }

        public String getName()
        {
            return Name;
        }

        public String getNumber()
        {
            return Number;
        }
    }


}


