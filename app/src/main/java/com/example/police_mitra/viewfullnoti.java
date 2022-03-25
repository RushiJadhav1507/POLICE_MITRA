package com.example.police_mitra;

import android.os.Bundle;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class viewfullnoti extends AppCompatActivity {
    private EditText sub,date,desp,notif;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase rootnode=FirebaseDatabase.getInstance();
    UserHelperClass2 user=new UserHelperClass2();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewfullnoti);

        this.setTitle("Notification");
        final String sub1=getIntent().getExtras().getString("Sub").trim();
        final String date1=getIntent().getExtras().getString("Date").trim();

        sub=(EditText)findViewById(R.id.esubnoti);
        desp=(EditText)findViewById(R.id.edesp);
        notif=(EditText)findViewById(R.id.enotifrom);
        date=(EditText)findViewById(R.id.edate);

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("GOINOTI").child(sub1);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                user=snapshot.getValue(UserHelperClass2.class);
                sub.setText(user.getSub());
                desp.setText(user.getDesc());
                notif.setText(user.getNoti());
                date.setText(user.getPdate());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}