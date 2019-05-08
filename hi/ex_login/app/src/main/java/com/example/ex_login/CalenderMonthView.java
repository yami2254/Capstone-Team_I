package com.example.ex_login;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

class CalendarMonthView extends GridView {


    private OnDataSelectionListener selectionListener;

    /**
     * 어댑터 객체
     */
    CalendarMonthAdapter adapter;

    /**
     * 생성자
     *
     * @param context
     */
    public CalendarMonthView(Context context) {
        super(context);

        init();
    }

    /**
     * 생성자
     *
     * @param context
     * @param attrs
     */
    public CalendarMonthView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }
    public void changeColor()
    {
        setBackgroundColor(Color.YELLOW);
    }
    private void init() {
        setBackgroundColor(Color.GRAY);
        setVerticalSpacing(1);
        setHorizontalSpacing(1);
        setStretchMode(GridView.STRETCH_COLUMN_WIDTH);

        // 칼럼의 갯수 설정
        setNumColumns(7);

        // 그리드뷰의 원래 이벤트 처리 리스너 설정
        setOnItemClickListener(new OnItemClickAdapter());
    }

    /**
     * 어댑터 설정
     *
     * @param adapter
     */
    public void setAdapter(BaseAdapter adapter) {
        super.setAdapter(adapter);

        this.adapter = (CalendarMonthAdapter) adapter;
    }

    /**
     * 어댑터 객체 리턴
     *
     * @return
     */
    public BaseAdapter getAdapter() {
        return (BaseAdapter)super.getAdapter();
    }

    /**
     * 리스너 설정
     *
     * @param listener
     */
    public void setOnDataSelectionListener(OnDataSelectionListener listener) {
        this.selectionListener = listener;
    }

    /**
     * 리스너 객체 리턴
     *
     * @return
     */
    public OnDataSelectionListener getOnDataSelectionListener() {
        return selectionListener;
    }

    class OnItemClickAdapter implements AdapterView.OnItemClickListener {

        public OnItemClickAdapter() {

        }

        public void onItemClick(AdapterView parent, View v, int position, long id) {

            if (adapter != null) {
                adapter.setSelectedPosition(position);
                adapter.notifyDataSetInvalidated();
            }

            if (selectionListener != null) {
                selectionListener.onDataSelected(parent, v, position, id);
            }


        }

    }

}