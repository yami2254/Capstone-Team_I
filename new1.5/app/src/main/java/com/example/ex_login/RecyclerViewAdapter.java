package com.example.ex_login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.lang.UScript;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<study_list> listData = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

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
        public LinearLayout timetable;

        ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            textView1 = itemView.findViewById(R.id.room_name);
            textView2 = itemView.findViewById(R.id.room_num);
            textView3 = itemView.findViewById(R.id.room_time);
            textView4 = itemView.findViewById(R.id.textpeople);
            timetable = (LinearLayout)itemView.findViewById(R.id.timetable);


        }
        @Override
        public void onClick(View v) {

            System.out.println(getPosition());
            Intent intent = new Intent(v.getContext() , ActReserveActivity.class);
            intent.putExtra("StudtRoom_name",textView1.getText());
            intent.putExtra("StudtRoom_num",textView2.getText());
            intent.putExtra("StudtRoom_time",textView3.getText());
            intent.putExtra("StudtRoom_people",textView4.getText());
            intent.putStringArrayListExtra("StudtRoom_tablev",listData.get(getPosition()).getReserveL());
            intent.putExtra("endtime",listData.get(getPosition()).getTimeend());
            intent.putExtra("starttime",listData.get(getPosition()).getTimestart());
            intent.putExtra("StudtRoom_maxpeople",listData.get(getPosition()).getMaxpeople());
            intent.putExtra("StudtRoom_minpeople",listData.get(getPosition()).getMinpeople());
            intent.putExtra("curdate",listData.get(getPosition()).getCurdate());
            v.getContext().startActivity(intent);
        }


        void onBind(study_list data) {

            textView1.setText(data.getName());
            textView2.setText(data.getNum());
            textView3.setText(data.getTime());
            textView4.setText(data.getPeople());

            ArrayList<String> list = data.getReserveL();

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(85 ,80);
            TextView  textView = new TextView(mContext);
            timetable.setGravity(Gravity.CENTER);
            for(int k=data.getTimestart();k<=data.getTimeend();k++) {
                timetable.removeAllViews();
            }


            for(int j=data.getTimestart();j<data.getTimeend();j++) {

                textView.setText("" + j);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundResource(R.drawable.layer_nonselcetfull);
                for (int k = 0; k < list.size(); k++) {

                    if (Integer.parseInt(list.get(k)) == j) {

                        textView.setBackgroundResource(R.drawable.layer_selectfull);
                    }

                }


                timetable.addView(textView, layoutParams);
                textView = new TextView(mContext);
            }
            }

        }
    }



