package com.example.ex_login;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.estimote.sdk.EstimoteSDK.getApplicationContext;

public class Free_RecyclerViewAdapter extends RecyclerView.Adapter<Free_RecyclerViewAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<study_list> listData = new ArrayList<>();
    private Context mContext;

    String changedate;
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;

    public Free_RecyclerViewAdapter(Context context) {
        this.mContext = context;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.free_recycler_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.

        holder.onBind(listData.get(position), position);
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

    public void clear() {
        listData.clear();
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textView1;
        private TextView textView2;
        private TextView textView3;
        private TextView textView4;
        public LinearLayout timetable;
        private LinearLayout imageView2;
        private study_list data;
        private int position;
        private ImageButton free_enter_btn;
        private ImageButton free_out_btn;

        @SuppressLint("WrongViewCast")
        ItemViewHolder(View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.room_nameF);
            textView2 = itemView.findViewById(R.id.room_numF);
            textView3 = itemView.findViewById(R.id.room_timeF);
            textView4 = itemView.findViewById(R.id.textpeopleF);
            timetable = (LinearLayout) itemView.findViewById(R.id.timetableF);
            free_out_btn = itemView.findViewById(R.id.free_reserve_out_btn);
            free_enter_btn = itemView.findViewById(R.id.free_reserve_enter_btn);
            imageView2 = (LinearLayout)itemView.findViewById(R.id.imageView2F);

        }

        @Override
        public void onClick(View v) {


            switch (v.getId()) {
                case R.id.linearItemF:
                    if (selectedItems.get(position)) {
                        // 펼쳐진 Item을 클릭 시
                        selectedItems.delete(position);
                    } else {
                        // 직전의 클릭됐던 Item의 클릭상태를 지움
                        selectedItems.delete(prePosition);
                        // 클릭한 Item의 position을 저장
                        selectedItems.put(position, true);
                    }
                    // 해당 포지션의 변화를 알림
                    if (prePosition != -1) notifyItemChanged(prePosition);
                    notifyItemChanged(position);
                    // 클릭된 position 저장
                    prePosition = position;
                    break;
                case R.id.free_reserve_enter_btn:


                    System.out.println("꽁터디룸 입실버튼 클릭");
                    if(!data.getMajor().equals("") && !data.getMinor().equals("")) {
                        Intent intent = new Intent(v.getContext(), beacon.class);
                        intent.putExtra("major", data.getMajor());
                        intent.putExtra("minor", data.getMinor());
                        intent.putExtra("uuid", data.getUuid());
                        intent.putExtra("starttime", String.valueOf(data.getTimestart()));
                        v.getContext().startActivity(intent);
                    }

                    v.setBackgroundResource(R.drawable.enter_check);
                    break;
                case R.id.free_reserve_out_btn:

                    v.setBackgroundResource(R.drawable.out_check);
                    break;
            }
        }


        void onBind(study_list data, int position) {

            this.data = data;
            this.position = position;

            textView1.setText(data.getName());
            textView2.setText(data.getNum());
            textView3.setText(data.getTime());
            textView4.setText(data.getPeople());


            ArrayList<String> list = data.getReserveL();

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(85, 80);
            TextView textView = new TextView(mContext);
            timetable.setGravity(Gravity.CENTER);
            for (int k = data.getTimestart(); k <= data.getTimeend(); k++) {
                timetable.removeAllViews();
            }



            for (int j = data.getTimestart(); j < data.getTimeend(); j++) {

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

            changeVisibility(selectedItems.get(position));
            itemView.setOnClickListener(this);
            free_enter_btn.setOnClickListener(this);
            free_out_btn.setOnClickListener(this);
        }

        private void changeVisibility(final boolean isExpanded) {
            // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용
            int dpValue = 90;
            float d = mContext.getResources().getDisplayMetrics().density;
            int height = (int) (dpValue * d);

            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0);
            // Animation이 실행되는 시간, n/1000초
            va.setDuration(600);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // value는 height 값
                    int value = (int) animation.getAnimatedValue();
                    // imageView의 높이 변경

                    imageView2.getLayoutParams().height = value;
                    imageView2.requestLayout();
                    // imageView가 실제로 사라지게하는 부분
                    imageView2.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                }
            });
            // Animation start
            va.start();
        }
    }
    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }
}



