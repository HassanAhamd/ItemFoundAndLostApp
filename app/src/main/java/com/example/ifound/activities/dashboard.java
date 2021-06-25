package com.example.ifound.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.ifound.R;
import com.example.ifound.fragments.found;
import com.example.ifound.fragments.lost;
import com.example.ifound.fragments.profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
public class dashboard extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;
    private ChipNavigationBar chipNavigationBar;
    private Fragment fragment;
    String EmailHolder;
    TextView Email;
    Button LogOUT ;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    FirebaseUser mUser;
    //@SuppressLint("SetTextI18n")
    public static final String TAG="LOGIN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        chipNavigationBar = findViewById(R.id.chipNavigation);

        chipNavigationBar.setItemSelected(R.id.founditem, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new found()).commit();

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.founditem:
                        fragment = new found();
                        break;
                    case R.id.lostitem:
                        fragment = new lost();
                        break;
                    case R.id.profile:
                        fragment = new profile();
                        break;

                }

                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                }
            }
        });

        LogOUT = (Button)findViewById(R.id.logoutbtn);

        Intent intent = getIntent();

        // Receiving User Email Send By MainActivity.
        EmailHolder = intent.getStringExtra(MainActivity.userEmail);

        // Adding click listener to Log Out button.

        LogOUT.setOnClickListener(new View.OnClickListener() {
             @Override
            public void onClick(View v) {


                //Finishing current DashBoard activity on button click.
                finish();

                Toast.makeText(dashboard.this,"Log Out Successfull", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(dashboard.this,MainActivity.class);
                startActivity(intent);


            }
        });



    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
                finish();
                System.exit(0);
            }
        }, 2000);

    }

}