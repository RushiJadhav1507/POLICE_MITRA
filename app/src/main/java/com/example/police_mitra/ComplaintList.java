package com.example.police_mitra;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ComplaintList extends AppCompatActivity {
    DB_Register dbr = new DB_Register();
    // creating variables for our list view.
    private ListView complaintLV;

    // creating a new array list.
    ArrayAdapter<String>listadapter;
    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_list);
        userName=getIntent().getStringExtra("userName");
        setTitle("Complaint List");
        ArrayList<String> crilist = new ArrayList<>();
        listadapter = new ArrayAdapter<>(this, R.layout.viewlist2, crilist);

        complaintLV = (ListView)findViewById(R.id.Complaints_list);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(userName).child("Complaint");


        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                dbr=snapshot.getValue(DB_Register.class);
                crilist.add("Complaint " + dbr.getComplaintNo());
                complaintLV.setAdapter(listadapter);
                listadapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                listadapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
                crilist.remove(snapshot.getValue(String.class));
                listadapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        complaintLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String usr=(String) complaintLV.getItemAtPosition(position);
                String[] start=usr.split("[\\s]");
                int j=1;
                for(String s:start){
                    System.out.println(j+" "+s);
                    j++;
                }
                String no=start[1];
//                String Name=start[2].concat(" "+start[3]);
                //              Toast.makeText(ComplaintList.this,id+" "+Name,Toast.LENGTH_SHORT).show();
                Intent i=new Intent(ComplaintList.this,Status.class);
                i.putExtra("no",no);
                i.putExtra("userName",userName);
                startActivity(i);
            }
        });
    }
}