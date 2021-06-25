package com.example.ifound.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ifound.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class opening extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton btnn, btnn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        btnn = (FloatingActionButton) findViewById(R.id.login);
        btnn2 = (FloatingActionButton) findViewById(R.id.register);

        btnn.setOnClickListener(this);
        btnn2.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.login:
                Intent intent = new Intent(opening.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.register:
                Intent intent2 = new Intent(opening.this, registration.class);
                startActivity(intent2);
                break;
        }
        }
}