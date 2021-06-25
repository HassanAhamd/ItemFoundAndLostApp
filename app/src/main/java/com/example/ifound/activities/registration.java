package com.example.ifound.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ifound.R;
import com.example.ifound.model.User;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class registration extends AppCompatActivity implements View.OnClickListener {
    EditText name, email, password, phone;
    Button mRegisterbtn;
    CallbackManager c;
    FirebaseAuth mAuth;
    DatabaseReference mdatabase;
    String Name, Email, Password;
    ProgressDialog mDialog;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.pasword);
        //phone = (EditText) findViewById(R.id.phone);
        mRegisterbtn = (Button) findViewById(R.id.regbtn);
        Button fblogin =  findViewById(R.id.login_button);
        c = CallbackManager.Factory.create();

        progressBar = findViewById(R.id.progressbar);
        // mLoginPageBack = (TextView)findViewById(R.id.login);
        // for authentication using FirebaseAuth.
        mAuth = FirebaseAuth.getInstance();
        mRegisterbtn.setOnClickListener(this);
        //   mLoginPageBack.setOnClickListener(this);
        mDialog = new ProgressDialog(this);
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users");

    }

    @Override
    public void onClick(View v) {
        if (v == mRegisterbtn) {
            UserRegister();
        }
    }

    private void UserRegister() {
        Name = name.getText().toString().trim();
        Email = email.getText().toString().trim();
        Password = password.getText().toString().trim();
        //Phone = phone.getText().toString().trim();

        if (Name.isEmpty()) {
            name.setError("Pleas enter Name");
            name.requestFocus();
            return;
        }

        if (Email.isEmpty()) {
            email.setError("Pleas enter valid Email");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("Pleas enter valid Email");
            email.requestFocus();
            return;
        }

        //if (Phone.isEmpty()) {
        //    phone.setError("Pleas enter Phone.no");
         //   phone.requestFocus();
         //   return;
       // }

        if (Password.isEmpty()) {
            password.setError("Pleas enter Password");
            password.requestFocus();
            return;
        }

        if (Password.length() < 8) {
            password.setError("Minimun 8 character required for password");
            password.requestFocus();
            return;
        }

            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(Email, Password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                User user = new User(Name, Email);

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            progressBar.setVisibility(View.GONE);

                                            Toast.makeText(registration.this, "Registeration sucessfull", Toast.LENGTH_SHORT).show();
                                            Intent phone = new Intent(registration.this, MainActivity.class);
                                            startActivity(phone);


                                        } else {
                                            Toast.makeText(registration.this, "Registeration Failed", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(registration.this, "The Email is already in use by another account", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });


        }


}