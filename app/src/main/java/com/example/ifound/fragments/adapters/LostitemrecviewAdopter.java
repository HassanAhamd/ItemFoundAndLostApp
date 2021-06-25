package com.example.ifound.fragments.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ifound.R;
import com.example.ifound.model.Lostitem;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class LostitemrecviewAdopter extends RecyclerView.Adapter <LostitemrecviewAdopter.ViewHolder>{
    private final List<Lostitem> listdata;

    // RecyclerView recyclerView;

    public LostitemrecviewAdopter(List<Lostitem> listdata) {
        this.listdata = listdata;
    }

    @Override
    public @NotNull ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.lost_itemview, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Lostitem lostitem = listdata.get(position);
        final String title = lostitem.getTitle();
        final String discriotion = lostitem.getDiscriotion();
        final String status = lostitem.getStatus();
        final String location = lostitem.getLocation();
        final String time = lostitem.getTime();

        holder.tv_title.setText(title);
        holder.tv_discription.setText(discriotion);
        holder.tv_status.setText(status);
        holder.tv_location.setText(location);
        holder.tv_time.setText(time);

    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_title;
        private final TextView tv_discription;
        private final TextView tv_status;
        private final TextView tv_location;
        private final TextView tv_time;
        public ViewHolder(View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            tv_discription = itemView.findViewById(R.id.tv_description);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_location = itemView.findViewById(R.id.tv_location);
            tv_time = itemView.findViewById(R.id.tv_time);

        }
    }
}