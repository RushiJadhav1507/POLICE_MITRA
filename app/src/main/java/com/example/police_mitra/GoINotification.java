package com.example.police_mitra;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Year;
import java.util.Calendar;

public class GoINotification extends AppCompatActivity {

    private EditText sub,date,desp,notif;
    private Button viewall,submit1;
    private Calendar cal;
    private int day,month,year;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase rootnode=FirebaseDatabase.getInstance();
    DatabaseReference mrootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_inotification);
        setTitle("Government Notification");

        sub=(EditText)findViewById(R.id.esubnoti);
        desp=(EditText)findViewById(R.id.edesp);
        notif=(EditText)findViewById(R.id.enotifrom);
        viewall=(Button) findViewById(R.id.butvan);
        submit1=(Button)findViewById(R.id.butsub);

        date=(EditText)findViewById(R.id.edate);
        cal=Calendar.getInstance();
        day=cal.get(Calendar.DAY_OF_MONTH);
        month=cal.get(Calendar.MONTH);
        year=cal.get(Calendar.YEAR);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                showDialog(0);

            }
        });

        submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String Sub=sub.getText().toString();
                final String Date=date.getText().toString();
                final String Desc=desp.getText().toString();
                final String Noti=notif.getText().toString();

                if (TextUtils.isEmpty(Sub)){
                    sub.setError("please enter Subject");
                }
                else if (TextUtils.isEmpty(Date)) {
                    date.setError("please enter your full name");
                }
                else if (TextUtils.isEmpty(Desc)) {
                    desp.setError("please enter your aadhar number");
                }
                else if (TextUtils.isEmpty(Noti)) {
                    notif.setError("please enter your age");
                }
                else{

                    rootnode=FirebaseDatabase.getInstance();
                    mrootRef=rootnode.getReference("GOINOTI");
                    UserHelperClass2 userHelperClass2=new UserHelperClass2(Sub,Date,Desc,Noti);
                    mrootRef.child(Sub).setValue(userHelperClass2);
                    Toast.makeText(GoINotification.this,"Successfull",Toast.LENGTH_LONG).show();
                    sub.setText("");
                    desp.setText("");
                    notif.setText("");
                    date.setText("");


                }

            }
        });

        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(GoINotification.this,ViewNoti.class);
                startActivity(i);

            }
        });

    }




    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            date.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                    + selectedYear);
        }
    };
}