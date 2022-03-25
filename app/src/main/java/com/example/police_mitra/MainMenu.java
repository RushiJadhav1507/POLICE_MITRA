package com.example.police_mitra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class MainMenu extends AppCompatActivity {
    ImageView cpolicelogin,viewnoti,wcriminals,complaints,logout;

    FirebaseAuth fa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setTitle("MAIN MENU");

        fa= FirebaseAuth.getInstance();

        cpolicelogin= (ImageView) findViewById(R.id.PoliceLogin);
        viewnoti= (ImageView) findViewById(R.id.GovNoti);
        wcriminals= (ImageView) findViewById(R.id.WantedCriminals);
        complaints=(ImageView) findViewById(R.id.Complaints);
        logout=(ImageView) findViewById(R.id.logout);

        viewnoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(MainMenu.this,GoINotification.class);
                startActivity(i);

            }
        });

        cpolicelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(MainMenu.this,adminlogin.class);
                startActivity(i);

            }
        });

        wcriminals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(MainMenu.this,Criminals.class);
                startActivity(i);

            }
        });

        complaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainMenu.this,ViewCity.class);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fa.signOut();
                finish();
                Intent i=new Intent(MainMenu.this,login.class);
                startActivity(i);

            }
        });


    }
}