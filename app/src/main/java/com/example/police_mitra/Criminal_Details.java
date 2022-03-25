package com.example.police_mitra;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Criminal_Details extends AppCompatActivity {

    ImageButton BSelectImage;
    int SELECT_PICTURE=200;
    public static final String Database_Path = "Wanted Criminals";
    int Image_Request_Code = 7;
    Uri selectedImageUri;
    EditText e1,e2,e3,e4,e5,e6;
    private FirebaseAuth mAuth ;
    FirebaseUser currentUser;
    FirebaseDatabase rootNode=FirebaseDatabase.getInstance();
    CriminalDataHolder CDH=new CriminalDataHolder();
    StorageReference storageReference;
    DatabaseReference databaseReference2;
   // List<ImageUploadInfo> upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criminal_details);
        setTitle("Criminal Details");

        storageReference = FirebaseStorage.getInstance().getReference();

        String id1=getIntent().getExtras().getString("Id").trim();
        String name1=getIntent().getExtras().getString("Name").trim();

        e1=(EditText)findViewById(R.id.ET_Criminal_ID);
        e2=(EditText)findViewById(R.id.ET_Criminal_Name);
        e3=(EditText)findViewById(R.id.ET_Age);
        e4=(EditText)findViewById(R.id.ET_Booked);
        e5=(EditText)findViewById(R.id.ET_Identity);
        e6=(EditText)findViewById(R.id.ET_City);
        BSelectImage=(ImageButton)findViewById(R.id.Criminal_Image);

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Wanted Criminals").child(id1);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                CDH = snapshot.getValue(CriminalDataHolder.class);
                e1.setText(e1.getText().append(CDH.getID()));
                e2.setText(e2.getText().append(CDH.getName()));
                e3.setText(e3.getText().append(CDH.getAge()));
                e4.setText(e4.getText().append(CDH.getBooked_Under()));
                e5.setText(e5.getText().append(CDH.getIdentity_Remarks()));
                e6.setText(e6.getText().append(CDH.getCity()));

                StorageReference storageReference= FirebaseStorage.getInstance().getReference("Wanted_Criminals/"+id1);
                try {
                    File localFile=File.createTempFile("pic",".png");
                    storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap= BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            BSelectImage.setImageBitmap(bitmap);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error)
            {

            }
        });
/*
        upload=new ArrayList<>();
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Wanted Criminals Images ");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    ImageUploadInfo imageUploadInfo = postSnapshot.getValue(ImageUploadInfo.class);

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });*/

    }
}
