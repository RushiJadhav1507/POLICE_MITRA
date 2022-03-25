package com.example.police_mitra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class CitizenloginActivity extends AppCompatActivity {
    private TextView forgotlink,signuplink;
    EditText Loginmail,Loginpass;
    Button LoginButton;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizenlogin);
        Loginmail=findViewById(R.id.mail);
        Loginpass=findViewById(R.id.password);
        LoginButton=findViewById(R.id.Login);
        mAuth=FirebaseAuth.getInstance();
        LoginButton.setOnClickListener(view -> {
            loginUser();
        });

        forgotlink=(TextView)findViewById(R.id.forgot);
        signuplink=(TextView)findViewById(R.id.textView2);
        forgotlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotActivity();
            }
        });
        signuplink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupActivity();
            }
        });
    }
    public void signupActivity()
    {
        Intent intent=new Intent(this,signupwithemail.class);
        startActivity(intent);
    }
    public void forgotActivity()
    {
        Intent intent=new Intent(this,forgetpass.class);
        startActivity(intent);
    }

    private void loginUser(){
        String email=Loginmail.getText().toString().trim();
        String pass=Loginpass.getText().toString().trim();
        if(TextUtils.isEmpty(email))
        {
            Loginmail.setError("Email can not be empty.");
            Loginmail.requestFocus();
        }else if(TextUtils.isEmpty(pass)){
            Loginpass.setError("password can not empty");
            Loginpass.requestFocus();
        }else
        {
            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete( Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        FirebaseUser user=mAuth.getCurrentUser();
                        if (user.isEmailVerified()) {
                            Toast.makeText(CitizenloginActivity.this, "User Login Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CitizenloginActivity.this, Menu_Citizen.class);
                            String email1 = email.substring(0,email.indexOf('@'));
                            intent.putExtra("userName",email1);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(CitizenloginActivity.this,"You haven't verify your email yet.",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(CitizenloginActivity.this,"Log in error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    }
}