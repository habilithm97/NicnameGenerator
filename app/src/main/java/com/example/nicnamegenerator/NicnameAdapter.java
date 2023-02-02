package com.example.nicnamegenerator;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NicnameAdapter extends RecyclerView.Adapter<NicnameAdapter.ViewHolder> {

    List<Nicname> mNicnameList;
    //static List<Nicname> nicnameList = new ArrayList<>();

    public NicnameAdapter(List<Nicname> nicnameList) {
        //mNicnameList = new ArrayList<>();
        mNicnameList = nicnameList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView idTv, tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            idTv = itemView.findViewById(R.id.idTv);
            tv = itemView.findViewById(R.id.tv);
        }

        public void setItem(Nicname item) {
            idTv.setText(String.valueOf(item.getId()));
            tv.setText(item.getStr());
        }
    }

    @NonNull
    @Override
    public NicnameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = (LayoutInflater.from(parent.getContext()).inflate(R.layout.nicname_item, parent, false));
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NicnameAdapter.ViewHolder holder, int position) {
        Nicname item = mNicnameList.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        if(mNicnameList != null) {
            return mNicnameList.size();
        }
        return 0;
    }

    public void addItem(Nicname item) {
        mNicnameList.add(item);
    }
}
