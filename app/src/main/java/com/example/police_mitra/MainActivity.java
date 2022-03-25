package com.example.police_mitra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText username,name,aadhar,age,gender,phone,email,desg,asspolice,pass;
    private Button register;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase rootNode=FirebaseDatabase.getInstance();
    DatabaseReference mrootRef;

    private static final Pattern ADHAR_PATTERN=Pattern.compile("^[2-9]{1}[0-9]{3}\\s[0-9]{4}\\s[0-9]{4}$");
    private static final Pattern MOBILE_PATTERN =Pattern.compile("^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$");
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{4,}" +                // at least 4 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Police Registration");
        firebaseAuth=FirebaseAuth.getInstance();

        username=(EditText)findViewById(R.id.editTextTextPersonName);
        name=(EditText)findViewById(R.id.editTextTextPersonName2);
        aadhar=(EditText)findViewById(R.id.editTextNumber);
        age=(EditText)findViewById(R.id.editTextNumber2);
        gender=(EditText)findViewById(R.id.editTextTextPersonName3);
        phone=(EditText)findViewById(R.id.editTextPhone);
        email=(EditText)findViewById(R.id.editTextTextEmailAddress);
        desg=(EditText)findViewById(R.id.editTextTextPersonName4);
        asspolice=(EditText)findViewById(R.id.editTextTextPersonName5);
        pass=(EditText)findViewById(R.id.editTextTextPersonName6);
        register=(Button)findViewById(R.id.button);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String user_name=username.getText().toString();
                final String pname=name.getText().toString();
                final String paadhar=aadhar.getText().toString();
                final String page=age.getText().toString();
                final String pgender=gender.getText().toString();
                final String pphone=phone.getText().toString();
                final String pemail=email.getText().toString();
                final String pdesg=desg.getText().toString();
                final String passpolice=asspolice.getText().toString();
                final String ppass=pass.getText().toString();

                if (TextUtils.isEmpty(user_name)) {
                    username.setError("please enter username");
                }
                else if (TextUtils.isEmpty(pname)) {
                    name.setError("please enter your full name");
                }
                else if (TextUtils.isEmpty(paadhar)) {
                    aadhar.setError("please enter your aadhar number");
                }
                else if (TextUtils.isEmpty(page)) {
                    age.setError("please enter your age");
                }
                else if (TextUtils.isEmpty(pgender)) {
                    gender.setError("please enter your gender");
                }
                else if (TextUtils.isEmpty(pemail)) {
                    email.setError("please enter your email address");
                }
                else if (TextUtils.isEmpty(pphone)) {
                    phone.setError("please enter your phone number");
                }
                else if (TextUtils.isEmpty(pdesg)) {
                    desg.setError("please enter your Designation");
                }
                else if (TextUtils.isEmpty(passpolice)) {
                    asspolice.setError("please enter your assigned police station");
                }
                else if (TextUtils.isEmpty(ppass)) {
                    pass.setError("please enter your password");
                }
                else if(!validateAadhar() | !validateMobile())
                {
                    return;
                }
                else if (!validateEmail() | !validatePassword()) {
                    return;
                }
                else{
                    firebaseAuth.createUserWithEmailAndPassword(pemail,ppass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                rootNode=FirebaseDatabase.getInstance();
                                mrootRef=rootNode.getReference("PoliceData");
                                mrootRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        Toast.makeText(getApplicationContext(), "Police Data Inside", Toast.LENGTH_SHORT).show();
                                        UserHelperClass userHelperClass=new UserHelperClass(user_name,pname,paadhar,page,pgender,pemail,pphone,pdesg,passpolice,ppass);
                                        mrootRef.child(user_name).setValue(userHelperClass);
                                        Toast.makeText(getApplicationContext(), "Police Data Added Successfully", Toast.LENGTH_SHORT).show();


                                    }

                                    @Override
                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                    }
                                });

                                Toast.makeText(MainActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                                username.setText("");
                                name.setText("");
                                aadhar.setText("");
                                age.setText("");
                                gender.setText("");
                                phone.setText("");
                                email.setText("");
                                desg.setText("");
                                asspolice.setText("");
                                pass.setText("");

                            }
                            else{

                                task.getException().printStackTrace();
                                Toast.makeText(getApplicationContext(),"Unsuccessfull",Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

                }

            }
        });
    }

    private boolean validateEmail() {

        // Extract input from EditText
        String emailInput = email.getText().toString().trim();

        // if the email input field is empty
        if (emailInput.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        }

        // Matching the input email to a predefined email pattern
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError("Please enter a valid email address");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = pass.getText().toString().trim();
        // if password field is empty
        // it will display error message "Field can not be empty"
        if (passwordInput.isEmpty()) {
            pass.setError("Field can not be empty");
            return false;
        }

        // if password does not matches to the pattern
        // it will display an error message "Password is too weak"
        else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            pass.setError("Password is too weak");
            return false;
        } else {
            pass.setError(null);
            return true;
        }
    }

    private boolean validateMobile() {

        // Extract input from EditText
        String mobileInput = phone.getText().toString().trim();

        // if the email input field is empty
        if (mobileInput.isEmpty()) {
            phone.setError("Field can not be empty");
            return false;
        }

        // Matching the input email to a predefined email pattern
        else if (!MOBILE_PATTERN.matcher(mobileInput).matches()) {
            phone.setError("Please enter a valid phone number");
            return false;
        } else {
            phone.setError(null);
            return true;
        }
    }

    private boolean validateAadhar() {

        // Extract input from EditText
        String aadharInput = aadhar.getText().toString().trim();

        // if the email input field is empty
        if (aadharInput.isEmpty()) {
            aadhar.setError("Field can not be empty");
            return false;
        }

        // Matching the input email to a predefined email pattern
        else if (!ADHAR_PATTERN.matcher(aadharInput).matches()) {
            aadhar.setError("Please enter a valid Aadhar Number");
            return false;
        } else {
            aadhar.setError(null);
            return true;
        }
    }

}