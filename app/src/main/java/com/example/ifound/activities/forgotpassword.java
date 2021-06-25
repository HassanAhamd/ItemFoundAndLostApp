package com.example.ifound.activities;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpassword extends AppCompatActivity {

    private EditText email;
    private Button resetbtn;
    private TextView sinp;
    private ProgressBar bar;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        email = (EditText) findViewById(R.id.emaill);
        resetbtn = (Button) findViewById(R.id.reset);
        sinp = (TextView)  findViewById(R.id.signup);
        bar = (ProgressBar) findViewById(R.id.progressbar);

        auth = FirebaseAuth.getInstance();

        resetbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                resetPassword();

            }
        });
    }



    private void resetPassword()
    {
        String emaill = email.getText().toString().trim();

        if (emaill.isEmpty())
        {
            email.setError("Email Required!");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emaill).matches())
        {
            email.setError("Please provide valid email");
            email.requestFocus();
            return;
        }

        bar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(emaill).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
            {
                Toast.makeText(forgotpassword.this, "Check your email to reset your password!", Toast.LENGTH_LONG).show();
            }
                else
                    {
                        Toast.makeText(forgotpassword.this, "Try again, something went wrong!", Toast.LENGTH_LONG).show();
                    }
                    }
        });

        sinp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(forgotpassword.this, registration.class);
                startActivity(intent);


            }
        });


    }
}