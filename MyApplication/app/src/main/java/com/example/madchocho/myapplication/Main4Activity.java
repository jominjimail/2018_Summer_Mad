package com.example.madchocho.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main4Activity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Button button= (Button) findViewById(R.id.contact);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Main4Activity.this,
                        TestAddressBook.class);
                startActivity(myIntent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getApplicationContext(),
                "Button3 Clicked!", Toast.LENGTH_SHORT).show();
    }
}
