package com.example.police_mitra;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;

public class Profile extends AppCompatActivity {
    DatabaseReference databaseReference;
    TextView name;
    TextView aadhar_no;
    TextView date;
    TextView gender;
    TextView address;
    TextView email;
    String userName;
    TextView mobile_no, pincode;
    Button update;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userName=getIntent().getStringExtra("userName");
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userName).child("Profile");

        imageView = (ImageView)findViewById(R.id.imageView);
        name = (TextView)findViewById(R.id.name);
        aadhar_no = (TextView)findViewById(R.id.aadNumber);
        date = (TextView)findViewById(R.id.date);
        gender = (TextView)findViewById(R.id.gender);
        address = (TextView)findViewById(R.id.Address);
        email = (TextView)findViewById(R.id.email);
        mobile_no = (TextView)findViewById(R.id.Mobile_No);
        pincode = (TextView)findViewById(R.id.Pin_code);
        update = (Button)findViewById(R.id.update);
        Toast.makeText(getApplicationContext(),"Hii",Toast.LENGTH_SHORT).show();
        Query checkUser=databaseReference;
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    name.setText(snapshot.child("name").getValue().toString());
                    aadhar_no.setText(snapshot.child("adhar").getValue().toString());
                    date.setText(snapshot.child("dob").getValue(String.class));
                    gender.setText(snapshot.child("gd").getValue(String.class));
                    address.setText(snapshot.child("address").getValue(String.class));
                    email.setText(snapshot.child("email").getValue(String.class));
                    mobile_no.setText(snapshot.child("mob").getValue(String.class));
                    pincode.setText(snapshot.child("pin").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error fetching data",Toast.LENGTH_SHORT).show();
            }
        });

        StorageReference storageReference= FirebaseStorage.getInstance().getReference("image/users/" + userName + "/profile/photo");
        try {
            File localFile=File.createTempFile("pic",".png");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap= BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    imageView.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, update.class);
                intent.putExtra("userName",userName);
                startActivity(intent);
                finish();
            }
        });
    }
}