package com.example.ex_login;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<study_list> listData = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    public void addItem(study_list data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView textView1;
        private TextView textView2;
        private TextView textView3;
        private TextView textView4;
        private TextView textView5;
        ItemViewHolder(View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.room_name);
            textView2 = itemView.findViewById(R.id.room_num);
            textView3 = itemView.findViewById(R.id.room_time);
            textView4 = itemView.findViewById(R.id.timetable);
            textView5 = itemView.findViewById(R.id.timetablenum);

        }

        void onBind(study_list data) {
            textView1.setText(data.getName());
            textView2.setText(data.getNum());
            textView3.setText(data.getTime());
            textView4.setText(data.getTimetable());
            textView5.setText(data.getTimenum());
        }
    }
}


