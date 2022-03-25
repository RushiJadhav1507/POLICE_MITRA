package com.example.police_mitra;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainActivity2 extends AppCompatActivity {

    private Button button;
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        button=(Button) findViewById(R.id.button);
        button1=(Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openCitizenActivity();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPoliceActivity();
            }
        });
    }
    public void openCitizenActivity()
    {
        Intent intent=new Intent(this, CitizenloginActivity.class);
        startActivity(intent);
    }
    public void openPoliceActivity()
    {
        Intent intent=new Intent(this, login.class);
        startActivity(intent);
    }
}