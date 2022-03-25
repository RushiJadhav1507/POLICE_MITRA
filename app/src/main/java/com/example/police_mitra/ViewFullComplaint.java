package com.example.police_mitra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class ViewFullComplaint extends AppCompatActivity{

    TextView stationName, stationAddress,culname,culage,culgen,vicmob,vicusrname, details, status;
    Button loc,feed;

    UserHelperClass5 comdetails=new UserHelperClass5();


    DatabaseReference databaseReference;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    String userName,strci,strbr,strno;
    double lati, longi;
    byte[] Array;
    Bitmap bitmap;
    boolean isPermissionGranter;
    ImageView imageView5;
    EditText f1;
    String user;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_full_complaint);
        setTitle("Complaint Details");


        final String ComNo1=getIntent().getExtras().getString("ComNo").trim();
        strno=ComNo1;
        final String CulName1=getIntent().getExtras().getString("CulName").trim();
        final String city1=getIntent().getExtras().getString("City").trim();
        strci=city1;
        final String branch1=getIntent().getExtras().getString("Branch").trim();
        strbr=branch1;



        stationName = (TextView) findViewById(R.id.stationName);
        stationAddress = (TextView) findViewById(R.id.StationAdd);
        culname=findViewById(R.id.nameofcul);
        culage=findViewById(R.id.ageofcul);
        culgen=findViewById(R.id.genderofcul);
        vicmob=findViewById(R.id.phoneofvictim);
        vicusrname=findViewById(R.id.usernameofvictim);
        details = (TextView) findViewById(R.id.details);
        //status = (TextView) findViewById(R.id.status);


        loc=findViewById(R.id.Location);






        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Complaint").child(city1).child(branch1).child("Complaint "+ComNo1);
        Query checkUser = reference;
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {

                   // comdetails=snapshot.getValue(UserHelperClass5.class);
                    stationName.setText(snapshot.child("station").getValue().toString().trim());
                    stationAddress.setText(snapshot.child("district").getValue().toString());
                    culname.setText(snapshot.child("culprit_name").getValue().toString());
                    culage.setText(snapshot.child("culprit_age").getValue().toString());
                    culgen.setText(snapshot.child("gender").getValue().toString());
                    vicmob.setText(snapshot.child("phone_no").getValue().toString());
                    vicusrname.setText(snapshot.child("username").getValue().toString());
                    details.setText(snapshot.child("details").getValue().toString().trim());
                    //status.setText(snapshot.child("status").getValue().toString().trim());

                    user=vicusrname.getText().toString();

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });


        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ViewFullComplaint.this,MapsActivity2.class);
                //Toast.makeText(getApplicationContext(),"Hiiiiii:"+ComNo1,Toast.LENGTH_LONG).show();
                i.putExtra("ComNo",ComNo1);
                i.putExtra("Branch",strbr);
                i.putExtra("City",strci);
                startActivity(i);
            }
        });

        /*
        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String feedback=f1.getText().toString();

                if (TextUtils.isEmpty(feedback)) {
                    f1.setError("please enter username");
                }else{

                    DatabaseReference reference2= FirebaseDatabase.getInstance().getReference().child("users").child("Complaint").child().child("Complaint "+ComNo1);

                }

            }
        });

/*
        checkpermission();

        if (!isPermissionGranter) {
            if (checkGooglePlaServices()) {
                supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.Google_map);
                supportMapFragment.getMapAsync(this);
            } else {
                Toast.makeText(this, "Google Playservices Not Avalable", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private boolean checkGooglePlaServices() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int result = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (result == ConnectionResult.SUCCESS) {
            return true;
        } else if (googleApiAvailability.isUserResolvableError(result)) {
            Dialog dialog = googleApiAvailability.getErrorDialog(this, result, 201, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    Toast.makeText(ViewFullComplaint.this, "User Canceled Dialog", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show();
        }
        return false;
    }


    private void checkpermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                getMyLocation();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), "");
                intent.setData(uri);
                startActivity(intent);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }


    private void getMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {


                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Complaint").child(strci).child(strbr).child("Complaint "+strno);
                        Query checkUser = databaseReference;
                        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if (snapshot.exists()) {

                                    lati = snapshot.child("lati").getValue(Double.class);
                                    longi = snapshot.child("longi").getValue(Double.class);

                                    LatLng lating = new LatLng(lati, longi);

                                    MarkerOptions markerOptions = new MarkerOptions().position(lating).title("Victim");


                                    googleMap.addMarker(markerOptions);
                                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lating, 15));
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                Toast.makeText(getApplicationContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                });
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Toast.makeText(Status.this, "longi  " + longi, Toast.LENGTH_SHORT).show();
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
*/

    }
}