package com.example.police_mitra;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static android.content.ContentValues.TAG;

public class Criminal_Profile_Update extends AppCompatActivity {

    ImageButton BSelectImage;
    int SELECT_PICTURE=200;
    Uri selectedImageUri;
    EditText e1,e2,e3,e4,e5,e6;

    FirebaseStorage storage;
    StorageReference storageReference;
    private FirebaseAuth mAuth;


    private final int PICK_IMAGE_REQUEST = 22;

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criminal_profile_update);

// ...
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();



        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();



        BSelectImage=findViewById(R.id.Criminal_Image);

        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });


    }

    void imageChooser()
    {
       /*Intent i=new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Select Image"),SELECT_PICTURE);

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);*/


        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    BSelectImage.setImageURI(selectedImageUri);
                }
            }
        }

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            selectedImageUri = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                selectedImageUri);
                BSelectImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    @RequiresApi(api = Build.VERSION_CODES.M)
    public void SubmitData(View view) {
        e1=(EditText)findViewById(R.id.ET_Criminal_ID);
        e2=(EditText)findViewById(R.id.ET_Criminal_Name);
        e3=(EditText)findViewById(R.id.ET_Age);
        e4=(EditText)findViewById(R.id.ET_Booked);
        e5=(EditText)findViewById(R.id.ET_Identity);
        e6=(EditText)findViewById(R.id.ET_City);

        String id=e1.getText().toString().trim();
        String name=e2.getText().toString().trim();
        String age=e3.getText().toString().trim();
        String booked=e4.getText().toString().trim();
        String identity=e5.getText().toString().trim();
        String city=e6.getText().toString().trim();


        CriminalDataHolder dhobj=new CriminalDataHolder(id,name,age,booked,identity,city);

        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference nodeWC=db.getReference("Wanted Criminals");


        nodeWC.child(id).setValue(dhobj);
        uploadImage();


        Toast.makeText(getApplicationContext(),"Data Submitted",Toast.LENGTH_LONG).show();
        BSelectImage.setImageIcon(null);


    }



   /* public void ImageUpload()
    {
        StorageReference ref
                = storageReference
                .child(
                        "Wanted_Criminals/"
                                + UUID.randomUUID().toString());

        // adding listeners on upload
        // or failure of image
        ref.putFile(selectedImageUri)
                .addOnSuccessListener(
                        new OnSuccessListener<UploadTask.TaskSnapshot>() {

                            @Override
                            public void onSuccess(
                                    UploadTask.TaskSnapshot taskSnapshot)
                            {

                                // Image uploaded successfully


                                Toast.makeText(Criminal_Profile_Update.this,
                                                "Image Uploaded!!",
                                                Toast.LENGTH_SHORT)
                                        .show();
                            }
                        })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {

                        // Error, Image not uploaded
                        Toast
                                .makeText(Criminal_Profile_Update.this,
                                        "Failed " + e.getMessage(),
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }*/

    public void uploadImage()
    {
        if (selectedImageUri != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "Wanted_Criminals/"
                                    +e1.getText().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(selectedImageUri)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(Criminal_Profile_Update.this, "Image Uploaded!!", Toast.LENGTH_SHORT)
                                            .show();

                                    e1.setText("");
                                    e2.setText("");
                                    e3.setText("");
                                    e4.setText("");
                                    e5.setText("");
                                    e6.setText("");

                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(Criminal_Profile_Update.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }
}