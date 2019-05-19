package com.example.ex_login;

import android.content.Intent;
import android.graphics.Color;
import android.icu.lang.UScript;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
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

    public void clear()
    {
        listData.clear();
    }
    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView textView1;
        private TextView textView2;
        private TextView textView3;
        private TextView textView4;
        private TextView[] tablev;
        private TableRow tabler1;
        private TableRow tabler2;
        private TableLayout tableL;

        ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            textView1 = itemView.findViewById(R.id.room_name);
            textView2 = itemView.findViewById(R.id.room_num);
            textView3 = itemView.findViewById(R.id.room_time);
            textView4 = itemView.findViewById(R.id.textpeople);
            tabler1 = itemView.findViewById(R.id.tableR1);
            tabler2 = itemView.findViewById(R.id.tableR2);
            tableL = itemView.findViewById(R.id.tableL);

            tablev = new TextView[]{
                    itemView.findViewById(R.id.tableV1),
                    itemView.findViewById(R.id.tableV2),
                    itemView.findViewById(R.id.tableV3),
                    itemView.findViewById(R.id.tableV4),
                    itemView.findViewById(R.id.tableV5),
                    itemView.findViewById(R.id.tableV6),
                    itemView.findViewById(R.id.tableV7),
                    itemView.findViewById(R.id.tableV8),
                    itemView.findViewById(R.id.tableV9),
                    itemView.findViewById(R.id.tableV10),
                    itemView.findViewById(R.id.tableV11),
                    itemView.findViewById(R.id.tableV12),
                    itemView.findViewById(R.id.tableV13),
                    itemView.findViewById(R.id.tableV14),
                    itemView.findViewById(R.id.tableV15),
                    itemView.findViewById(R.id.tableV16),
                    itemView.findViewById(R.id.tableV17),
                    itemView.findViewById(R.id.tableV18),
                    itemView.findViewById(R.id.tableV19),
                    itemView.findViewById(R.id.tableV20),
                    itemView.findViewById(R.id.tableV21),
                    itemView.findViewById(R.id.tableV22),
                    itemView.findViewById(R.id.tableV23),
                    itemView.findViewById(R.id.tableV24),
            };
        }
        @Override
        public void onClick(View v) {

            System.out.println(getPosition());
            Intent intent = new Intent(v.getContext() , ActReserveActivity.class);
            v.getContext().startActivity(intent);
        }


        void onBind(study_list data) {

            textView1.setText(data.getName());
            textView2.setText(data.getNum());
            textView3.setText(data.getTime());
            textView4.setText(data.getPeople());

            ArrayList<String> list = data.getReserveL();

            for(int i=data.getTimestart();i <=data.getTimeend();i++) {
                tablev[i].setBackgroundColor(Color.WHITE);
            }
            for (int j = 0; j < data.getTimestart(); j++) {
                tablev[j].setBackgroundColor(Color.BLACK);
            }
            for (int k = data.getTimeend()+1; k < 24 ; k++){
                tablev[k].setBackgroundColor(Color.BLACK);
            }
            for (int i = 0; i < list.size(); i++) {
                tablev[Integer.parseInt(list.get(i))].setBackgroundColor(Color.YELLOW);
            }

        }
    }
}


