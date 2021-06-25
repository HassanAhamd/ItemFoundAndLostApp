package com.example.ifound.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ifound.R;
import com.example.ifound.activities.Add_lostitemActivity;
import com.example.ifound.fragments.adapters.FounditemrecviewAdopter;
import com.example.ifound.fragments.adapters.LostitemrecviewAdopter;
import com.example.ifound.model.Lostitem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class lost extends Fragment {

    private RecyclerView recyclerView;
    LinearLayoutManager manager;
    private  List<Lostitem> list;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lost, container, false);

        recyclerView = v.findViewById(R.id.lostitemrecview);
        manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        list = new ArrayList<>();

      Button  b = v.findViewById(R.id.btnLost);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), Add_lostitemActivity.class);
                startActivity(intent);


            }
        });
        getLostDatafromFirebase();


        return v;
    }

    private void getLostDatafromFirebase() {
        FirebaseDatabase receiverFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference receiverDatabaseReference = receiverFirebaseDatabase.getReference("Lost Items");
        receiverDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()){
                    for (DataSnapshot data : snapshot.getChildren()){
                        //String title = "jnjk";
                        String key = data.getKey();
                        String title = Objects.requireNonNull(data.child("title").getValue()).toString();
                        String discription = Objects.requireNonNull(data.child("discription").getValue()).toString();
                        String status = Objects.requireNonNull(data.child("status").getValue()).toString();
                        String location = Objects.requireNonNull(data.child("location").getValue()).toString();
                        String time = Objects.requireNonNull(data.child("time").getValue()).toString();

                        Lostitem lostitem = new Lostitem(title,discription,status,location,time,key);
                        list.add(lostitem);
                    }
                    LostitemrecviewAdopter lostitemrecviewAdopter = new LostitemrecviewAdopter(list);
                    recyclerView.setAdapter(lostitemrecviewAdopter);
                    lostitemrecviewAdopter.notifyDataSetChanged();

            }

            }

            @Override
            public void onCancelled(@NotNull DatabaseError error) {

            }
        });
    }

}