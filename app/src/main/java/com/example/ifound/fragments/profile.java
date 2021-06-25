package com.example.ifound.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ifound.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.w3c.dom.Text;


public class profile extends Fragment {

    private CircularImageView profileimg;
    private TextView pname;
    private TextView pemail;
    private TextView pphone;
    private Button btn;
    private FirebaseAuth ath;
    private FirebaseDatabase firedb;
    private FirebaseUser fireuser;
    FirebaseStorage storage;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = (inflater.inflate(R.layout.fragment_profile, container, false));
        profileimg = (CircularImageView) v.findViewById(R.id.dp);
        pname = (TextView) v.findViewById(R.id.username);
        pemail = (TextView) v.findViewById(R.id.useremail);
        pphone = (TextView) v.findViewById(R.id.userphone);
        TextView pphone = (TextView) v.findViewById(R.id.userphone);
        btn = (Button) v.findViewById(R.id.profileupdate);

        profileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 33);
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                upadateUserprofile();
            }
        });


        ath = FirebaseAuth.getInstance();
        firedb = FirebaseDatabase.getInstance();
        fireuser = FirebaseAuth.getInstance().getCurrentUser();
        storage = FirebaseStorage.getInstance();
        fireuser.getUid();
        Uri img_uri = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();

        Log.d("TAG", String.valueOf(img_uri));

        profileimg.setImageURI(img_uri);


        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
       // ref.child(us)
        //String displayName="";
        //for (UserInfo userInfo : fireuser.getProviderData()) {
          //  if (userInfo.getDisplayName() != null) {
            //    displayName = userInfo.getDisplayName();
            //}
           // else{
             //   displayName = "custom";
            //}
        //}
        //Log.d("TAG", "Welcome: ".concat(displayName));

        pname.setText(fireuser.getDisplayName());
        pemail.setText(fireuser.getEmail());

        return v;
    }

    private void upadateUserprofile() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "onActivityResult: ujjytyjtujtrt");
        if (data.getData() != null)
        {
            Uri profileUri = data.getData();
            profileimg.setImageURI(profileUri);

            final StorageReference sref = storage.getReference().child("profile_picture").child(FirebaseAuth.getInstance().getUid());
            Log.d("TAG", String.valueOf(sref));
            sref.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(),"uploaded", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}