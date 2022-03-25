package com.example.police_mitra;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Register_Complaint extends AppCompatActivity implements  OnMapReadyCallback{
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;

    int complaint = 1;
    Spinner spinner1, spinner2, spinner3;
    EditText culprit_name;
    EditText culprit_age;
    RadioButton gender;
    RadioGroup rg;
    EditText mobile_no;
    ImageView imageDisplay;
    ImageButton camerabut;
    Bitmap bitmap;
    ByteBuffer byteBuffer;
    private DatabaseReference databaseReference;
    private DatabaseReference puneShivaji;
    int psno = 1;
    private DatabaseReference puneViman;
    int pvno = 1;
    private DatabaseReference kolhapurShahupuri;
    int ksno = 1;
    private DatabaseReference kolhapurRajarampuri;
    int krno = 1;
    byte[] Array;
    int complaint_cnt = 1;
    EditText detailsBox;
    String userName;
    double lati, longi;
    int numbercomp=1;
    String district;
    String station;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_complaint);
        userName=getIntent().getStringExtra("userName");
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.Google_map);
        client = LocationServices.getFusedLocationProviderClient(this);
        checkpermission();

        culprit_name = (EditText)findViewById(R.id.culprit_name);
        culprit_age = (EditText)findViewById(R.id.culprit_age);
        rg = (RadioGroup)findViewById(R.id.genderGroup);
        mobile_no = (EditText)findViewById(R.id.phone_no);
        detailsBox = (EditText)findViewById(R.id.details);

        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userName).child("Complaint");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren())
                {

                    Map<String,Object> map= (Map<String,Object>) ds.getValue();
                    Object totaloop = map.get("Complaint");
                    String comp =String.valueOf(totaloop);
                    complaint_cnt++;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        puneShivaji = FirebaseDatabase.getInstance().getReference("Complaint").child("Pune").child("Shivajinagar");
        puneShivaji.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren())
                {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object totaloop = map.get("Shivajinagar");
                    String comp = String.valueOf(totaloop);
                    psno++;
                }
               // Toast.makeText(getApplicationContext(),"psno: "+psno,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        puneViman = FirebaseDatabase.getInstance().getReference("Complaint").child("Pune").child("Viman nagar");
        puneViman.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren())
                {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object totaloop = map.get("Viman nagar");
                    String comp = String.valueOf(totaloop);
                    pvno++;
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        kolhapurShahupuri = FirebaseDatabase.getInstance().getReference("Complaint").child("Kolhapur").child("Shahupuri");
        kolhapurShahupuri.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren())
                {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object totaloop = map.get("Shahupuri");
                    String comp = String.valueOf(totaloop);
                    ksno++;
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        kolhapurRajarampuri = FirebaseDatabase.getInstance().getReference("Complaint").child("Kolhapur").child("Rajarampuri");
        kolhapurRajarampuri.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren())
                {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object totaloop = map.get("Rajarampuri");
                    String comp = String.valueOf(totaloop);
                    krno++;
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        List<String> list = new ArrayList<String>();
        list.add("Select crime");
        list.add("abduction");
        list.add("blackmail");
        list.add("assassination");
        list.add("assault");
        list.add("drunk driving");
        list.add("fraud");
        list.add("hit and run");

        spinner2 = (Spinner)findViewById(R.id.spinner2);
        List<String> list2 = new ArrayList<String>();
        list2.add("Select District");
        list2.add("Pune");
        list2.add("Kolhapur");

        spinner3 = (Spinner)findViewById(R.id.spinner3);
        List<String> puneStation = new ArrayList<String>();
        puneStation.add("Select Station");
        puneStation.add("Shivajinagar");
        puneStation.add("Viman nagar");

        List<String> kolhapurStation = new ArrayList<String>();
        kolhapurStation.add("Select Station");
        kolhapurStation.add("Shahupuri");
        kolhapurStation.add("Rajarampuri");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter2);

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, puneStation);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, kolhapurStation);
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sp2 = (String) spinner2.getSelectedItem();
                if(sp2.equals("Pune"))
                {
                    spinner3.setVisibility(View.VISIBLE);
                    spinner3.setAdapter(dataAdapter3);
                }
                else if(sp2.equals("Kolhapur"))
                {
                    spinner3.setVisibility(View.VISIBLE);
                    spinner3.setAdapter(dataAdapter4);
                }
                else
                {
                    spinner3.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Button register = (Button)findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                complaintregister();
                process(view);

                Intent i=new Intent(Register_Complaint.this,MapsActivity.class);
                System.out.println("----------------------------------------"+numbercomp);
                i.putExtra("District",district);
                i.putExtra("Station",station);
                i.putExtra("Count",numbercomp);
                startActivity(i);
            }

        });

    }

    private void complaintregister() {

        if(!validatemob())
        {
            return;
        }
        if(spinner1.getSelectedItem().equals("Select crime"))
        {
            Toast.makeText(Register_Complaint.this, "Please Select Crime", Toast.LENGTH_LONG).show();
            return;
        }

        if(spinner2.getSelectedItem().equals("Select District"))
        {
            Toast.makeText(Register_Complaint.this, "Please select District", Toast.LENGTH_LONG).show();
            return;
        }
        if(spinner3.getSelectedItem().equals("Select Station"))
        {
            Toast.makeText(Register_Complaint.this, "Please Select Station", Toast.LENGTH_LONG).show();
            return;
        }


        String sp1 = spinner1.getSelectedItem().toString().trim();
        String culprit_name_str = culprit_name.getText().toString().trim();
        if(culprit_name_str.isEmpty())
        {
            culprit_name_str = "NULL";
        }
        String age = culprit_age.getText().toString().trim();
        if(age.isEmpty())
        {
            age = "NULL";
        }
        Long mob_no = Long.parseLong(mobile_no.getText().toString().trim());
        String details = detailsBox.getText().toString().trim();
        if(details.isEmpty())
        {
            details = "NULL";
        }

        int radioID = rg.getCheckedRadioButtonId();
        gender = findViewById(radioID);

        String genderStr = gender.getText().toString().trim();
        district = (String) spinner2.getSelectedItem();
        station = (String) spinner3.getSelectedItem();

        //int com = complaint_cnt;
        //int com=numbercomp;
       // DB_Register db = new DB_Register(culprit_name_str, com, age, genderStr, mob_no, sp1, details, lati, longi, district, station, userName);
       // FirebaseDatabase fb = FirebaseDatabase.getInstance();
       // DatabaseReference node = fb.getReference("Complaint").child(district).child(station);

        if(district.equals("Pune") && station.equals("Shivajinagar"))
        {
            int com=psno;
            DB_Register db = new DB_Register(culprit_name_str, com, age, genderStr, mob_no, sp1, details, lati, longi, district, station, userName);
            FirebaseDatabase fb = FirebaseDatabase.getInstance();
            DatabaseReference node = fb.getReference("Complaint").child(district).child(station);

            node.child("Complaint "+psno).setValue(db);
            numbercomp=psno;

        }
        else if(district.equals("Pune") && station.equals("Viman nagar"))
        {
            int com=pvno;
            DB_Register db = new DB_Register(culprit_name_str, com, age, genderStr, mob_no, sp1, details, lati, longi, district, station, userName);
            FirebaseDatabase fb = FirebaseDatabase.getInstance();
            DatabaseReference node = fb.getReference("Complaint").child(district).child(station);

            node.child("Complaint " + pvno).setValue(db);
            numbercomp=pvno;
        }
        else if(district.equals("Kolhapur") && station.equals("Shahupuri"))
        {
            int com=ksno;
            DB_Register db = new DB_Register(culprit_name_str, com, age, genderStr, mob_no, sp1, details, lati, longi, district, station, userName);
            FirebaseDatabase fb = FirebaseDatabase.getInstance();
            DatabaseReference node = fb.getReference("Complaint").child(district).child(station);

            node.child("Complaint " + ksno).setValue(db);
            numbercomp=ksno;
        }
        else if(district.equals("Kolhapur") && station.equals("Rajarampuri"))
        {
            int com=krno;
            DB_Register db = new DB_Register(culprit_name_str, com, age, genderStr, mob_no, sp1, details, lati, longi, district, station, userName);
            FirebaseDatabase fb = FirebaseDatabase.getInstance();
            DatabaseReference node = fb.getReference("Complaint").child(district).child(station);

            node.child("Complaint " + krno).setValue(db);
            numbercomp=krno;
        }
    }

    private Boolean validatemob() {

        String mobval = mobile_no.getText().toString();
        if (mobval.isEmpty()) {
            mobile_no.setError("mobile no should not be null");
            return false;
        } else if(mobile_no.length()<10||mobile_no.length()>10)
        {
            mobile_no.setError("Enter valid mobile no");
            return false;
        }
        else
        { mobile_no.setError(null);
            return true;
        }
    }

    public void process(View view)
    {
        if(!validatemob())
        {
            return;
        }
        if(spinner1.getSelectedItem().equals("Select crime"))
        {
            Toast.makeText(Register_Complaint.this, "Please Select Crime", Toast.LENGTH_LONG).show();
            return;
        }
        if(spinner2.getSelectedItem().equals("Select District"))
        {
            Toast.makeText(Register_Complaint.this, "Please select District", Toast.LENGTH_LONG).show();
            return;
        }
        if(spinner3.getSelectedItem().equals("Select Station"))
        {
            Toast.makeText(Register_Complaint.this, "Please Select Station", Toast.LENGTH_LONG).show();
            return;
        }

        String sp1 = spinner1.getSelectedItem().toString().trim();
        String culprit_name_str = culprit_name.getText().toString().trim();
        if(culprit_name_str.isEmpty())
        {
            culprit_name_str = "NULL";
        }
        String age = culprit_age.getText().toString().trim();
        if(age.isEmpty())
        {
            age = "NULL";
        }
        Long mob_no = Long.parseLong(mobile_no.getText().toString().trim());
        String details = detailsBox.getText().toString().trim();
        if(details.isEmpty())
        {
            details = "NULL";
        }

        int radioID = rg.getCheckedRadioButtonId();
        gender = findViewById(radioID);

        String genderStr = gender.getText().toString().trim();

        String district = (String) spinner2.getSelectedItem();
        String station = (String) spinner3.getSelectedItem();

        DB_Register db = new DB_Register(culprit_name_str, age, genderStr, mob_no, sp1, details, lati, longi, district, station, "NA", complaint_cnt);
        FirebaseDatabase fb = FirebaseDatabase.getInstance();
        DatabaseReference node = fb.getReference("users").child(userName).child("Complaint");

        node.child("Complaint "+complaint_cnt).setValue(db);

        //ref = FirebaseDatabase.getInstance().getReference("Citizen");

        Toast.makeText(getApplicationContext(),"Complaint Registered",Toast.LENGTH_SHORT).show();
        this.finish();
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
                assignLocation(location);
                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        LatLng lating = new LatLng(location.getLatitude(), location.getLongitude());

                        MarkerOptions markerOptions = new MarkerOptions().position(lating).title("You are here...!!");
                        googleMap.addMarker(markerOptions);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lating, 15));
                    }
                });
            }
        });
    }

    private void assignLocation(Location location) {
        lati =  location.getLatitude();
        longi = location.getLongitude();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
    }
}