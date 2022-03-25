package com.example.police_mitra;

import androidx.annotation.NonNull;
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

import org.jetbrains.annotations.NotNull;

public class login extends AppCompatActivity {
    EditText username,password;
    Button login;
    TextView fpass;
    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Police Mitra");
        login =(Button)findViewById(R.id.buttonlogin);
        username=(EditText)findViewById(R.id.editTextusername);
        password=(EditText)findViewById(R.id.editTextpassword);
        fpass=(TextView) findViewById(R.id.rpass);
        mauth=FirebaseAuth.getInstance();
        FirebaseUser user=mauth.getCurrentUser();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String user_name=username.getText().toString();
                final String pass_word=password.getText().toString();
                if (TextUtils.isEmpty(user_name)) {
                    username.setError("Invalid User Name");
                } else if (TextUtils.isEmpty(pass_word)) {
                    password.setError("Enter password");
                }
                mauth.signInWithEmailAndPassword(user_name,pass_word).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            FirebaseUser user=mauth.getCurrentUser();
                            finish();
                            Intent i=new Intent(login.this,MainMenu.class);
                            startActivity(i);
                            Toast.makeText(getApplicationContext(),"Logging You In",Toast.LENGTH_SHORT).show();
                        }
                        else{

                            Toast.makeText(getApplicationContext()," Login Unsuccessull Please Check Credientials",Toast.LENGTH_SHORT).show();

                        }

                    }
                });


            }
        });

        fpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(login.this,forgetpass.class);
                startActivity(i);

            }
        });
    }
}