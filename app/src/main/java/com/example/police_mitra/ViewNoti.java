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

import java.text.ParseException;
import java.util.ArrayList;

public class ViewNoti extends AppCompatActivity {
    private ListView listview;
    private ArrayAdapter<String>listadapter;
    UserHelperClass2 user=new UserHelperClass2();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_noti2);
        setTitle("View Notification");



        listview=(ListView)findViewById(R.id.listView);
        ArrayList<String> notilist=new ArrayList<String>();
        ArrayAdapter adapter=new ArrayAdapter<String>(this,R.layout.viewlist,notilist);
        listview.setAdapter(adapter);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("GOINOTI");
        Toast.makeText(getApplicationContext(),"Showing All Notifications",Toast.LENGTH_SHORT).show();
        /*reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    try {

                        user = snapshot1.getValue(UserHelperClass2.class);
                        String sub = user.getSub().toString();
                        String date = user.getPdate().toString();
                        String desc = user.getDesc().toString();
                        String noti = user.getNoti().toString();
                        System.out.println("*************************************** "+noti);
                        notilist.add("Date : " + user.getPdate() + "\tSubject : " + user.getSub());

                    } catch (Exception e) {
                            System.out.println(e);
                    }

                }
                listview.setAdapter(adapter);
                 adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });*/

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                user = snapshot.getValue(UserHelperClass2.class);
                /*String sub = user.getSub().toString();
                        String date = user.getPdate().toString();
                        String desc = user.getDesc().toString();
                        String noti = user.getNoti().toString();
                        System.out.println("*************************************** "+noti);*/
                notilist.add("Date : " + user.getPdate() + "\tSubject : " + user.getSub());
                //listview.setAdapter(adapter);
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
                String derDate=start[2];
                String derSub=start[9];
                Toast.makeText(ViewNoti.this,"Date :"+derDate+" Desub : "+derSub,Toast.LENGTH_SHORT).show();
                Intent i=new Intent(ViewNoti.this,viewfullnoti.class);
                i.putExtra("Sub",derSub);
                i.putExtra("Date",derDate);
                startActivity(i);

            }
        });

    }
}