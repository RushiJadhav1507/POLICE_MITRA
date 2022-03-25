package com.example.police_mitra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewBranch extends AppCompatActivity {

    private ListView branchlist;
    private ArrayAdapter<String> listadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_branch);

        final String city1=getIntent().getExtras().getString("City").trim();

        //Toast.makeText(getApplicationContext()," "+city1,Toast.LENGTH_LONG);

        branchlist=(ListView)findViewById(R.id.listView);
        ArrayList<String> listcity=new ArrayList<String>();
        ArrayAdapter adapter=new ArrayAdapter<String>(this,R.layout.viewlist,listcity);

        if(city1.equals("Pune")){


            listcity.add("Branch : Shivajinagar");
            listcity.add("Branch : Viman nagar");
            branchlist.setAdapter(adapter);


            branchlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                    String usr=(String) branchlist.getItemAtPosition(position);
                    String[] start=usr.split("[\\s]");
                    int j=1;
                    for(String s:start){

                        System.out.println(j+" "+s);
                        j++;

                    }
                    String derCity=start[2];

                    if (derCity.equals("Shivajinagar")) {

                        //Toast.makeText(ViewBranch.this, "City :" + derCity, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ViewBranch.this, Complainsss.class);
                        i.putExtra("City", derCity);
                        i.putExtra("OGCity",city1);
                        startActivity(i);
                    }
                    else{

                        //Toast.makeText(ViewBranch.this, "City :" + derCity, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ViewBranch.this, Complainsss.class);
                        i.putExtra("City", derCity+" nagar");
                        i.putExtra("OGCity",city1);
                        startActivity(i);

                    }

                }
            });




        }
        else{

            listcity.add("Branch : Shahupuri");
            listcity.add("Branch : Rajarampuri");
            branchlist.setAdapter(adapter);

            branchlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                    String usr=(String) branchlist.getItemAtPosition(position);
                    String[] start=usr.split("[\\s]");
                    int j=1;
                    for(String s:start){

                        System.out.println(j+" "+s);
                        j++;

                    }
                    String derCity=start[2];

                    //Toast.makeText(ViewBranch.this,"City :"+derCity,Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(ViewBranch.this,Complainsss.class);
                    i.putExtra("City",derCity);
                    i.putExtra("OGCity",city1);
                    startActivity(i);

                }
            });



        }

    }
}