package com.example.ifound.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ifound.R;
import com.example.ifound.model.Founditem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Add_found_itemActivity extends AppCompatActivity {
    Button SubmitbtnFound;
    EditText title, discription, status, location, time;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_found_item);

        mAuth = FirebaseAuth.getInstance();
        SubmitbtnFound = findViewById(R.id.SubmitbtnFound);
        title = findViewById(R.id.addTitle);
        discription = findViewById(R.id.addDescription);
        status = findViewById(R.id.addStatus);
        location = findViewById(R.id.addLocation);
        time = findViewById(R.id.addTime);

        SubmitbtnFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDatainFirebaseforFound();

            }
        });

}
    public void AddDatainFirebaseforFound() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Found Items").push();
        String key = ref.push().getKey();




                    Map<String, String> founditemMap = new HashMap<>();
        founditemMap.put("title", title.getText().toString());
        founditemMap.put("discription", discription.getText().toString());
        founditemMap.put("status", status.getText().toString());
        founditemMap.put("location", location.getText().toString());
        founditemMap.put("time", time.getText().toString());
        founditemMap.put("key", key);


        ref.setValue(founditemMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Data Uploaded Sucessfully",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Add_found_itemActivity.this, dashboard.class);
                            startActivity(intent);

                        }else {
                            Toast.makeText(getApplicationContext(),"Found Item Error",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
    }