package com.example.nicnamegenerator;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
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

    /*
    SparseBooleanArray booleanArray = new SparseBooleanArray(0); // position 별 선택 상태를 저장하는 구조
    // -> 리스트뷰에서 다중 선택 시 선택한 position에 대한 정보를 보관하는 객체로서, get()을 호출해 선택된 position 값을 구함.
    // get()은 해당 position에 값이 존재하면 true를 리턴함
     */

    public NicnameAdapter(List<Nicname> nicnameList) {
        //mNicnameList = new ArrayList<>();
        mNicnameList = nicnameList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView idTv, tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            idTv = itemView.findViewById(R.id.idTv);
            tv = itemView.findViewById(R.id.tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //setMultipleSelection(getAdapterPosition());
                }
            });
        }

        public void setItem(Nicname item) {
            idTv.setText(String.valueOf(item.getId()));
            tv.setText(item.getStr());
        }
    }

    /*
    private void setMultipleSelection(int adapterPosition) { // 다중 선택 기능
        // 반대의 값을 넣어줌
        if(mNicnameList.get(adapterPosition).isSelected()) {
            mNicnameList.get(adapterPosition).setSelected(false);
        } else {
            mNicnameList.get(adapterPosition).setSelected(true);
        }
        notifyDataSetChanged();
    } */

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

        /*
        if(item.isSelected()) { // 아이템 선택값에 따른 배경색 설정
            holder.itemView.setBackgroundColor(Color.parseColor("#FFCCFF")); // 선택된 상태
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFB6C1")); // 선택이 안 된 상태
        } */
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
