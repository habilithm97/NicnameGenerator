package com.example.nicnamegenerator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NicnameAdapter extends RecyclerView.Adapter<NicnameAdapter.ViewHolder> {

    List<Nicname> nicnameList = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nicnameTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nicnameTv = itemView.findViewById(R.id.nicnameTv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //setMultipleSelection(getAdapterPosition());
                }
            });
        }

        public void setItem(Nicname item) {
            nicnameTv.setText(item.getStr());
        }
    }

    /*
    private void setMultipleSelection(int adapterPosition) { // 다중 선택 기능
        // 반대의 값을 넣어줌
        if(nicnameList.get(adapterPosition).isSelected()) {
            nicnameList.get(adapterPosition).setSelected(false);
        } else {
            nicnameList.get(adapterPosition).setSelected(true);
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
        Nicname item = nicnameList.get(position);
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
        /*
        if(nicnameList != null) {
            return nicnameList.size();
        }
        return 0; */
        return nicnameList.size();
    }

    public void addItem(Nicname item) {
        nicnameList.add(item);
    }
}
