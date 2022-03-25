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

public class Criminals extends AppCompatActivity {
    CriminalDataHolder CDH=new CriminalDataHolder();

    // creating variables for our list view.
    private ListView criminalsLV;

    // creating a new array list.
    ArrayAdapter<String>listadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criminals);
        setTitle("Wanted Criminals");

        criminalsLV =findViewById(R.id.criminals_list);
        ArrayList<String> crilist = new ArrayList<>();
        listadapter = new ArrayAdapter<>(this, R.layout.viewlist2, crilist);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Wanted Criminals");
        //Toast.makeText(getApplicationContext(), "Inside Datasnapshot" + reference, Toast.LENGTH_SHORT).show();

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                CDH=snapshot.getValue(CriminalDataHolder.class);
                crilist.add(CDH.getID()+"  "+CDH.getName());
                criminalsLV.setAdapter(listadapter);
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


        criminalsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String usr=(String) criminalsLV.getItemAtPosition(position);
                String[] start=usr.split("[\\s]");
                int j=1;
                for(String s:start){
                    System.out.println(j+" "+s);
                    j++;
                }
                String id=start[0];
                String Name=start[2].concat(" "+start[3]);
                Toast.makeText(Criminals.this,id+" "+Name,Toast.LENGTH_SHORT).show();
                Intent i=new Intent(Criminals.this,Criminal_Details.class);
                i.putExtra("Id",id);
                i.putExtra("Name",Name);
                startActivity(i);
            }
        });
    }


    public void InsertData(View view)
    {
        Intent i = new Intent(Criminals.this, Criminal_Profile_Update.class);
        startActivity(i);

    }
}

