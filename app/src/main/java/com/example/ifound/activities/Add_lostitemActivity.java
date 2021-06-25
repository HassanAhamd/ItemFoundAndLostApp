package com.example.ifound.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ifound.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Add_lostitemActivity extends AppCompatActivity {

    Button SubmitbtnLost;
    EditText title,discription,status,location,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lostitem);

        SubmitbtnLost = findViewById(R.id.SubmitbtnLost);
        title = findViewById(R.id.losttitle);
        discription = findViewById(R.id.lostDescription);
        status = findViewById(R.id.lostStatus);
        location = findViewById(R.id.lostLocation);
        time = findViewById(R.id.lostTime);

        SubmitbtnLost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddDatainFirebaseLostitem();
            }
        });
    }
             public void AddDatainFirebaseLostitem() {
                 FirebaseDatabase receiverFirebaseDatabase = FirebaseDatabase.getInstance();
                 DatabaseReference receiverDatabaseReference = receiverFirebaseDatabase.getReference("Lost Items").push();

                String key = receiverDatabaseReference.push().getKey();

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("title", title.getText().toString());
                hashMap.put("discription", title.getText().toString());
                hashMap.put("status", title.getText().toString());
                hashMap.put("location", title.getText().toString());
                hashMap.put("time", title.getText().toString());
                hashMap.put("key", key);

                 receiverDatabaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Data Uploaded Sucessfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Add_lostitemActivity.this, dashboard.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(getApplicationContext(), "Lost Item Error", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}