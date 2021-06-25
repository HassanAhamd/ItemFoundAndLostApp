package com.example.ifound.fragments;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ifound.R;
import com.example.ifound.activities.Add_found_itemActivity;
import com.example.ifound.activities.Add_lostitemActivity;
import com.example.ifound.fragments.adapters.FounditemrecviewAdopter;
import com.example.ifound.model.Founditem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.ifound.activities.dashboard.TAG;


public class found extends Fragment {


    private RecyclerView recyclerView;
    private  List<Founditem> list;
    private Button b;

    LinearLayoutManager manager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_found, container, false);
        manager = new LinearLayoutManager(getContext());
        //recyclerView = new RecyclerView(Objects.requireNonNull(getContext()));
        recyclerView = v.findViewById(R.id.founditemrecview);
        list = new ArrayList<>();
        getFoundDatafromFirebase();
         b = v.findViewById(R.id.Btnaddfounditm);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), Add_found_itemActivity.class);
                startActivity(intent);


            }
        });



        return v;
    }

    private void getFoundDatafromFirebase() {
        FirebaseDatabase receiverFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference receiverDatabaseReference = receiverFirebaseDatabase.getReference("Found Items");
        receiverDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()){
                    for (DataSnapshot data : snapshot.getChildren()){
                        //String key = Objects.requireNonNull(data.child("key").getValue()).toString();
                       String key = data.getKey();
                        String title = Objects.requireNonNull(data.child("title").getValue()).toString();
                        String discription = Objects.requireNonNull(data.child("discription").getValue()).toString();
                        String status = Objects.requireNonNull(data.child("status").getValue()).toString();
                        String location = Objects.requireNonNull(data.child("location").getValue()).toString();
                        String time = Objects.requireNonNull(data.child("time").getValue()).toString();

                        Founditem founditem = new Founditem(title,discription,status,location,time,key);
                        list.add(founditem);
                    }
                    Log.d(TAG, "onDataChange: length:"+list.size());

                    FounditemrecviewAdopter founditemrecviewAdopter = new FounditemrecviewAdopter(list);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(founditemrecviewAdopter);
                    founditemrecviewAdopter.notifyDataSetChanged();


                }else {
                    Log.d(TAG, "onDataChange: no exist");
                }
                Log.d(TAG, "onDataChange:snapshot:"+snapshot.toString());

            }

            @Override
            public void onCancelled(@NotNull DatabaseError error) {
                Log.d(TAG, "onDataChange: error:"+error.getMessage());


            }
        });
        Log.d(TAG, "onDataChange: length1:"+list.size());

    }

}