package com.example.police_mitra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgetpass extends AppCompatActivity {

    EditText fp;
    Button submit;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass);

        fp=(EditText) findViewById(R.id.editTextusername);
        submit=(Button) findViewById(R.id.forgetpass);
        firebaseAuth=FirebaseAuth.getInstance();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = fp.getText().toString().trim();
                if (email.equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter Registered Email ID", Toast.LENGTH_SHORT);
                } else {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Password Reset Link Send to Email", Toast.LENGTH_SHORT);
                                finish();
                                startActivity(new Intent(forgetpass.this, login.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Some Error in sending Link to Email", Toast.LENGTH_SHORT);
                            }
                        }
                    });
                }
            }

        });

    }
}