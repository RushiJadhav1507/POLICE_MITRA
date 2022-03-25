package com.example.police_mitra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class update extends AppCompatActivity {
    private static final String TAG = "Error";
    Button btnregister;
    EditText name,mobile,aadhar,pincode,address,date;
    TextView Email;
    FirebaseAuth mAuth;
    String userName;
    DatabaseReference reff;
    FirebaseStorage storage;
    StorageReference storageReference;
    int SELECT_PICTURE=200;
    Uri selectedImageUri;

    RadioButton r,r1;
    int year,month,day;
    String DOB;
    String a, mail;
    String user;
    ImageButton img;
    ImageView imageView;
    private final int PICK_IMAGE_REQUEST = 22;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        TextView t;

        img = findViewById(R.id.imageButton);
        imageView = findViewById(R.id.imageView3);
        btnregister = findViewById(R.id.register);
        Email = findViewById(R.id.email);
        mAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.fullname);
        mobile = findViewById(R.id.mob);
        aadhar = findViewById(R.id.adharcard);
        pincode = findViewById(R.id.pin);
        address = findViewById(R.id.addr);
        date = findViewById(R.id.DOB);
        r = findViewById(R.id.male);
        r1 = findViewById(R.id.female);
        Calendar cal=Calendar.getInstance();
        userName=getIntent().getStringExtra("userName");
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userName).child("Profile");
        Query checkUser=databaseReference;
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    Email.setText(snapshot.child("email").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error fetching data",Toast.LENGTH_SHORT).show();
            }
        });
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mail = Email.getText().toString().trim();
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });


        date.setOnClickListener(view -> {
            year= cal.get(Calendar.YEAR);
            month=cal.get(Calendar.MONTH);
            day=cal.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dt=new DatePickerDialog(update.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    DOB=i2+"/"+i1+"/"+i;
                    date.setText(DOB);
                }
            },year,month,day);
            dt.show();
        });

        btnregister.setOnClickListener(view -> {
            uploadImage();
            submission();


        });

    }

    private Boolean validateName() {

        String namevalue = name.getText().toString();
        if (namevalue.isEmpty()) {
            name.setError("Name should not be null");
            return false;
        } else {
            name.setError(null);
            return true;
        }
    }
    private Boolean validatemob() {

        String mobval = mobile.getText().toString();
        if (mobval.isEmpty()) {
            mobile.setError("mobile no should not be null");
            return false;
        } else if(mobile.length()<10||mobile.length()>10)
        {
            mobile.setError("Enter valid mobile no");
            return false;
        }
        else
        { mobile.setError(null);
            return true;
        }
    }
    private Boolean validatepin() {

        String pinval = pincode.getText().toString();
        if (pinval.isEmpty()) {
            pincode.setError("pin should not be null");
            return false;
        } else
        if(pinval.length()<6||pinval.length()>6)
        {
            pincode.setError("Enter valid pin code");
            return false;
        }
        else{
            pincode.setError(null);
            return true;
        }
    }
    private Boolean validateadhar() {

        String adharvalue = aadhar.getText().toString();
        if (adharvalue.isEmpty()) {
            aadhar.setError("Name should not br null");
            return false;
        }
        else if(adharvalue.length()<12||adharvalue.length()>12)
        {
            aadhar.setError("Enter valid adhar card number");
            return false;
        }else {
            aadhar.setError(null);
            return true;
        }
    }


    private void submission()
    {

        if(!validateName() | !validatemob() |  !validateadhar() |  !validatepin())
        {
            return;
        }
        FirebaseDatabase fd=FirebaseDatabase.getInstance();
        String a1=r.getText().toString();
        String a2=r1.getText().toString();
        if(r.isChecked())
        {
            a=a1;
        }
        if(r1.isChecked())
        {
            a=a2;
        }
        String gender=a;
        String nameval=name.getText().toString();

        String mobileno=mobile.getText().toString();
        String aadharno=aadhar.getText().toString();
        String pincodeno=pincode.getText().toString();
        String addr=address.getText().toString();
        String dateofbirth=date.getText().toString();
        mail = Email.getText().toString().trim();
        UserHelperClass3 helper=new UserHelperClass3(nameval,mail,addr,pincodeno,mobileno,aadharno,dateofbirth,gender);
        DatabaseReference ref=fd.getReference("users");
        //String nameOnly = username.substring(0,username.indexOf('@'));
        ref.child(userName).child("Profile").setValue(helper);
        //reff = FirebaseDatabase.getInstance().getReference().child("Users");
        createDialog();

    }
    private void createDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Details updated successfully");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(update.this, Profile.class);
                startActivity(intent);
                finish();
            }
        });
        builder.create();
        builder.show();
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
                    imageView.setImageURI(selectedImageUri);
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
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadImage()
    {
        if (selectedImageUri != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            //String username=Email.getText().toString();
            //String nameOnly = username.substring(0,username.indexOf('@'));
            // Defining the child of storageReference
            StorageReference ref = storageReference.child("image/users/" + userName + "/profile/photo");

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
                                    Toast.makeText(getApplicationContext(), "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(getApplicationContext(),
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