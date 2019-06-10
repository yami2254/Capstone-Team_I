package com.example.ex_login;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Vector;

public class ReserveViewAdapter extends RecyclerView.Adapter<ReserveViewAdapter.ItemViewHolder> {

    private  ArrayList<reserve_list> datalist = new ArrayList<>();
    private Context mContext;
    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;
    public ReserveViewAdapter(Context context) {
        this.mContext = context;
    }

    private View view;
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reserve_item,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(datalist.get(position),position);

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public void addItem(reserve_list data) {
        // 외부에서 item을 추가시킬 함수입니다.
        datalist.add(data);
    }
    public void clear()
    {
        datalist.clear();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textView1;
        private TextView textView2;
        private TextView textView3;
        private TextView textView4;
        private LinearLayout imageView2;
        private ImageButton reserve_enter_btn;
        private  ImageButton reserve_out_btn;
        private ImageButton reserve_cancle_btn;

        private reserve_list data;
        private int position;

        ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            textView1 = itemView.findViewById(R.id.reserve_date);
            textView2 = itemView.findViewById(R.id.reserve_time);
            textView3 = itemView.findViewById(R.id.reserve_room_name);
            textView4 = itemView.findViewById(R.id.enter_enable_time);
            imageView2 = (LinearLayout)itemView.findViewById(R.id.linearitem2R);

            reserve_enter_btn = itemView.findViewById(R.id.reserve_enter_btn);
            reserve_out_btn = itemView.findViewById(R.id.reserve_out_btn);
            reserve_cancle_btn = itemView.findViewById(R.id.reserve_cancle_btn);

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.linearItemR:
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
                case R.id.reserve_enter_btn:
                    System.out.println("예약현황 입실버튼 클릭");
                    if(!data.getMajor().equals("") && !data.getMinor().equals("")) {
                        Intent intent = new Intent(v.getContext(), beacon.class);
                        intent.putExtra("major", data.getMajor());
                        intent.putExtra("minor", data.getMinor());
                        intent.putExtra("uuid", data.getUuid());
                        intent.putExtra("starttime", data.getReserve_time());

                        v.getContext().startActivity(intent);
                    }

                    v.setBackgroundResource(R.drawable.enter_check);
                    break;
                case R.id.reserve_cancle_btn:
                    view = v;
                    HttpTask httptask = new HttpTask();
                    httptask.settime(data.getReserve_time(),data.getReserve_usetime(),data.reserve_name);
                    httptask.setdate(data.getReserve_date());
                    httptask.execute();

                    break;
                case R.id.reserve_out_btn:
                    v.setBackgroundResource(R.drawable.out_check);
                    break;
            }
        }

        void onBind(reserve_list data,int position) {

            this.data = data;
            this.position = position;
            String endtime = ""+(Integer.parseInt(data.getReserve_time()) + Integer.parseInt(data.reserve_usetime));


            textView1.setText(data.getReserve_date().substring(0,4)+ "년 "+data.getReserve_date().substring(4,6)+"월 "+data.getReserve_date().substring(6,8)+"일");
            textView2.setText(data.getReserve_time()+":00 ~ "+endtime+":00");
            textView3.setText(data.getReserve_name());
            textView4.setText("입실가능시간 : "+data.getReserve_time()+":00 ~ "+data.getReserve_time()+":20" );
            changeVisibility(selectedItems.get(position));
            itemView.setOnClickListener(this);

            reserve_enter_btn.setOnClickListener(this);
            reserve_cancle_btn.setOnClickListener(this);
            reserve_out_btn.setOnClickListener(this);

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

    class HttpTask extends AsyncTask<Void, Void, String> {

        private  String  curdate;
        private  String urlPath = "http://interface518.dothome.co.kr/caps/delete2.php";
        private  String starttime;
        private  String usetime;
        private  String studyroom;

        public void setdate(String curdate)
        {
            this.curdate = curdate;
            urlPath = urlPath + curdate;
        }

        public void settime(String starttime , String usetime,String studyroom)
        {
            this.starttime = starttime;
            this.usetime = usetime;
            this.studyroom = String.valueOf(Integer.parseInt(studyroom.substring(4,6)));

        }

        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
            try{
                HttpPost request = new HttpPost("http://interface518.dothome.co.kr/caps/delete2.php");
                //전달할 인자들
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();

                nameValue.add(new BasicNameValuePair("userid", MainActivity.id));
                nameValue.add(new BasicNameValuePair("rsname", studyroom));
                nameValue.add(new BasicNameValuePair("rstime", starttime));
                nameValue.add(new BasicNameValuePair("rusetime", usetime));
                nameValue.add(new BasicNameValuePair("rdate", curdate));
                //웹 접속 - utf-8 방식으로

                HttpEntity enty = new UrlEncodedFormEntity(nameValue,"utf-8");
                request.setEntity(enty);
                System.out.println(nameValue);

                HttpClient client = new DefaultHttpClient();
                HttpResponse res = client.execute(request);
                //웹 서버에서 값받기
                HttpEntity entityResponse = res.getEntity();
                InputStream im = entityResponse.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(im, "utf-8"));

                String total = "";
                String tmp = "";
                //버퍼에있는거 전부 더해주기
                //readLine -> 파일내용을 줄 단위로 읽기
                while((tmp = reader.readLine())!= null)
                {
                    if(tmp != null)
                    {
                        total += tmp;
                    }
                }
                im.close();
                //결과창뿌려주기 - ui 변경시 에러
                //testtv.setText(total);
                return total;
            }catch(UnsupportedEncodingException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
            //오류시 null 반환
            return null;
        }
        //asyonTask 3번째 인자와 일치 매개변수값 -> doInBackground 리턴값이 전달됨
        //AsynoTask 는 preExcute - doInBackground - postExecute 순으로 자동으로 실행됩니다.
        //ui는 여기서 변경
        protected void onPostExecute(String value){
            super.onPostExecute(value);

            System.out.println(value);
            if(value.equals("OK"))
            {
                System.out.println("성공");
                Intent intent = new Intent(view.getContext(),cancel.class);
                intent.putExtra("flag", "1");
                view.getContext().startActivity(intent);

            }
            else
            {
                System.out.println("실패");
                Intent intent = new Intent(view.getContext(),cancel.class);
                intent.putExtra("flag", "0");
                view.getContext().startActivity(intent);

            }
        }
    }
}
