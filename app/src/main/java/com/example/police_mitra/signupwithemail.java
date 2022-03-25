package com.example.police_mitra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class signupwithemail extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText em,ps;
    TextView tr;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupwithemail);
        mAuth=FirebaseAuth.getInstance();
        em=findViewById(R.id.signupmail);
        ps=findViewById(R.id.pass);
        b=findViewById(R.id.signupbutton);
        tr=findViewById(R.id.t2);
        b.setOnClickListener(view -> {
            createUser();
        });
        tr.setOnClickListener(view -> {
            startActivity(new Intent(signupwithemail.this,CitizenloginActivity.class));
        });

    }

    private void createUser()
    {
        String email=em.getText().toString().trim();
        String pass=ps.getText().toString().trim();
        if(TextUtils.isEmpty(email))
        {
            em.setError("Email can not be empty.");
            em.requestFocus();
        }else if(TextUtils.isEmpty(pass)){
            ps.setError("password can not empty");
            ps.requestFocus();
        }else
        {
            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        FirebaseUser user=mAuth.getCurrentUser();
                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                //Toast.makeText(signupwithemail.this,"Verification email has been sent.Verify and Login again",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure( Exception e) {
                                Log.d("tag", "onFailure: Email not sent"+e.getMessage());
                            }
                        });
                        Toast.makeText(signupwithemail.this,"user created",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(signupwithemail.this, Signup_Activity.class);
                        intent.putExtra("keyname",email);
                        startActivity(intent);

                    }else{
                        Toast.makeText(signupwithemail.this,"Registration error:"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
