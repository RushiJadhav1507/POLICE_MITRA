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
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ViewCity extends AppCompatActivity {

    private ListView citylist;
    private ArrayAdapter<String> listadapter;
    UserHelperClass4 city=new UserHelperClass4();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_city);


        citylist=(ListView)findViewById(R.id.listView);
        ArrayList<String> listcity=new ArrayList<String>();
        ArrayAdapter adapter=new ArrayAdapter<String>(this,R.layout.viewlist,listcity);


        /*DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Complaint");
        Toast.makeText(getApplicationContext(),"Showing All Cities",Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "Inside Datasnapshot" + reference, Toast.LENGTH_LONG).show();

        reference.child("Complaint").addValueEventListener(new ValueEventListener() {
                                                               @Override
                                                               public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                                   for (DataSnapshot child : snapshot.getChildren()) {

                                                                       listcity.add(child.getKey());   //Pega o nome de cada tipo de arte
                                                                       adapter.notifyDataSetChanged();
                                                                   }
                                                               }
                                                               @Override
                                                               public void onCancelled(@NonNull DatabaseError error) {

                                                               }
                                                           });
        /*
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                city = snapshot.getValue(UserHelperClass4.class);

                listcity.add("City : " + city.getCity());
                citylist.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }

        });*/

        listcity.add("City : Pune");
        listcity.add("City : Kolhapur");
        citylist.setAdapter(adapter);


        citylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                String usr=(String) citylist.getItemAtPosition(position);
                String[] start=usr.split("[\\s]");
                int j=1;
                for(String s:start){

                    System.out.println(j+" "+s);
                    j++;

                }
                String derCity=start[2];

                Toast.makeText(ViewCity.this,"City :"+derCity,Toast.LENGTH_SHORT).show();
                Intent i=new Intent(ViewCity.this,ViewBranch.class);
                i.putExtra("City",derCity);
                startActivity(i);

            }
        });


    }


        }