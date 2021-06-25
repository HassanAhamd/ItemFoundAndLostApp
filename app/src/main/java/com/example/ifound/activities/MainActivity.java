package com.example.ifound.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ifound.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.Nullable;


public class MainActivity extends AppCompatActivity {
    EditText Email, Password;
    Button LogInButton;
    TextView RegisterButton;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    FirebaseUser mUser;
    String email, password;
    ProgressBar progressBar;
    TextView forgotBtn;
    CheckBox remember;

    public static final String userEmail="";

    public static final String TAG="LOGIN";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogInButton = (Button) findViewById(R.id.loginn);

        RegisterButton = (TextView) findViewById(R.id.sinupbtn);

        Email = (EditText) findViewById(R.id.usernam);
        Password = (EditText) findViewById(R.id.pass);
        forgotBtn = (TextView) findViewById(R.id.forgot_pwd);
        remember = (CheckBox) findViewById(R.id.rememberme) ;
        progressBar = findViewById(R.id.progressbar);
        mAuth = FirebaseAuth.getInstance();
        SharedPreferences spref;
        spref = getSharedPreferences(  "checkbox", MODE_PRIVATE);
        String remember =spref.getString("remember", "");
        if (remember.equals("true"))
        {

        }
        else if (remember.equals("false"))
        {
            Toast.makeText(this,"sininagain", Toast.LENGTH_SHORT).show();
        }
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mUser != null) {
                    Intent intent = new Intent(MainActivity.this, dashboard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else
                {
                    Log.d(TAG,"AuthStateChanged:Logout");
                }

            }
        };

        // LogInButton.setOnClickListener((View.OnClickListener) this);
        //RegisterButton.setOnClickListener((View.OnClickListener) this);
        //Adding click listener to log in button.
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling EditText is empty or no method.
                userSign();


            }
        });
        forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, forgotpassword.class);
                startActivity(intent);


            }
        });

        // Adding click listener to register button.
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, registration.class);
                startActivity(intent);


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //removeAuthSateListner is used  in onStart function just for checking purposes,it helps in logging you out.
        mAuth.removeAuthStateListener(mAuthListner);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListner != null) {
            mAuth.removeAuthStateListener(mAuthListner);
        }

    }

    @Override
    public void onBackPressed() {
        MainActivity.super.finish();
    }



    private void userSign() {
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(MainActivity.this, "Enter the correct Email", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(MainActivity.this, "Enter the correct password", Toast.LENGTH_SHORT).show();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(MainActivity.this, "Incorrect Email or Password", Toast.LENGTH_SHORT).show();

                } else {
                    progressBar.setVisibility(View.GONE);

                    Intent intent = new Intent(MainActivity.this, dashboard.class);

                    // Sending Email to Dashboard Activity using intent.
                    intent.putExtra(userEmail,email);

                    startActivity(intent);

                }
            }
        });

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (buttonView.isChecked()){
                SharedPreferences spref = getSharedPreferences(  "checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = spref.edit();
                editor.putString("remember", "true");
                editor.apply();
                Toast.makeText(MainActivity.this, "checked", Toast.LENGTH_SHORT).show();
                }
                else if (!buttonView.isChecked())
                {
                    SharedPreferences spref = getSharedPreferences(  "checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = spref.edit();
                    editor.putString("remember", "flase");
                    editor.apply();
                }
            }
        });

    }

}


