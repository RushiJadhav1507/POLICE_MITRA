package com.example.police_mitra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Menu_Citizen extends AppCompatActivity {
    DatabaseReference ref;
    private static final String TAG = "Error";
    Button btnlogout;
    String userID;
    FirebaseAuth mAuth;
    FirebaseDatabase fdata;
    DatabaseReference dref;
    String userName;
    ImageView logout;
    ImageView registerComplaint,notifications,wcriminals;
    ImageButton profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_citizen);
        Button btn = findViewById(R.id.button);

        mAuth=FirebaseAuth.getInstance();
        fdata=FirebaseDatabase.getInstance();
        dref=fdata.getReference();
        userName=getIntent().getStringExtra("userName");
        userID=mAuth.getCurrentUser().getUid();
        FirebaseUser user=mAuth.getCurrentUser();

        logout = (ImageView)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(Menu_Citizen.this,CitizenloginActivity.class));
                finish();
            }
        });

        ref = FirebaseDatabase.getInstance().getReference("Citizen");
        registerComplaint = (ImageView)findViewById(R.id.registerComplaint);
        registerComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu_Citizen.this,Register_Complaint.class);
                intent.putExtra("userName",userName);
                startActivity(intent);
            }
        });

        profile = (ImageButton)findViewById(R.id.imageButton3);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu_Citizen.this,Profile.class);
                intent.putExtra("userName",userName);
                startActivity(intent);
            }
        });

        ImageView Complaints = findViewById(R.id.Complaints);
        Complaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu_Citizen.this,ComplaintList.class);
                intent.putExtra("userName",userName);
                startActivity(intent);
            }
        });

        notifications=findViewById(R.id.GovNoti);
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Citizen.this,ViewNoti.class);
                startActivity(intent);
            }
        });

        wcriminals=findViewById(R.id.WantedCriminals);
        wcriminals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Citizen.this,Criminals2.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();
        if(user==null)
        {
            startActivity(new Intent(Menu_Citizen.this,CitizenloginActivity.class));
        }
    }
}