package com.example.police_mitra;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Complainsss extends AppCompatActivity {

    UserHelperClass5 complaints=new UserHelperClass5();

    // creating variables for our list view.
    private ListView listview;
    private ArrayAdapter<String>listadapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complainsss);
        setTitle("Complaints");

        //Toast.makeText(getApplicationContext(),"Inside Complainss Activity 11111111111",Toast.LENGTH_LONG).show();
        final String branch1=getIntent().getExtras().getString("City").trim();
        final String city1=getIntent().getExtras().getString("OGCity").trim();
        //Toast.makeText(getApplicationContext(),"Inside Complainss Activity",Toast.LENGTH_LONG).show();

        listview=(ListView)findViewById(R.id.listView);
        ArrayList<String> notilist=new ArrayList<String>();
        ArrayAdapter adapter=new ArrayAdapter<String>(this,R.layout.viewlist,notilist);
        listview.setAdapter(adapter);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Complaint").child(city1).child(branch1);
        Toast.makeText(this,"Complaints List",Toast.LENGTH_LONG).show();

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                complaints=snapshot.getValue(UserHelperClass5.class);
                notilist.add(complaints.getComplaintNo()+"  "+complaints.getCulprit_name());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
                notilist.remove(snapshot.getValue(String.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                String usr=(String) listview.getItemAtPosition(position);
                String[] start=usr.split("[\\s]");
                int j=1;
                for(String s:start){

                    System.out.println(j+" "+s);
                    j++;

                }
                String derComNo=start[0];
                String derCulName=start[2];
                Toast.makeText(getApplicationContext(),"Complaint No :"+derComNo+" Culprit Name : "+derCulName,Toast.LENGTH_SHORT).show();
                Intent i=new Intent(Complainsss.this,ViewFullComplaint.class);
                i.putExtra("ComNo",derComNo);
                i.putExtra("CulName",derCulName);
                i.putExtra("Branch",branch1);
                i.putExtra("City",city1);
                startActivity(i);

            }
        });


    }
}