package com.example.ex_login;

import android.content.Context;
import android.graphics.Color;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarMonthAdapter extends BaseAdapter {

    public static final String TAG = "CalendarMonthAdapter";

    Context mContext;

    public static int oddColor = Color.rgb(225, 225, 225);
    public static int headColor = Color.rgb(12, 32, 158);
    public static int enableColor = Color.rgb(110,207,251);
    public static  int selectColor = Color.rgb(255,208,207);

    private int selectedPosition = -1;

    private MonthItem[] items;

    private int countColumn = 7;

    int mStartDay;
    int startDay;
    int curYear;
    int curMonth;
    boolean available = true;
    int firstDay;
    int lastDay;
    String curDay2;
    int curDay;
    int realmon;
    String realmon2;
    Calendar mCalendar;
    int saveidx;
    int lastindx;
    int startidx;
    public CalendarMonthAdapter(Context context) {
        super();

        mContext = context;
        init();

    }


    public CalendarMonthAdapter(Context context, AttributeSet attrs) {
        super();

        mContext = context;

        init();
    }

    private void init() {
        items = new MonthItem[7 * 6];

        mCalendar = Calendar.getInstance();
        recalculate();
        resetDayNumbers();

    }

    public void recalculate() {

        // set to the first day of the month
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);

        // get week day
        int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
        firstDay = getFirstDay(dayOfWeek);


        mStartDay = mCalendar.getFirstDayOfWeek();
        curYear = mCalendar.get(Calendar.YEAR);
        curMonth = mCalendar.get(Calendar.MONTH);


        lastDay = getMonthLastDay(curYear, curMonth);



        int diff = mStartDay - Calendar.SUNDAY - 1;
        startDay = getFirstDayOfWeek();


    }

    public void setPreviousMonth() {
        mCalendar.add(Calendar.MONTH, -1);
        recalculate();

        resetDayNumbers();
        selectedPosition = -1;
    }

    public void setNextMonth() {
        mCalendar.add(Calendar.MONTH, 1);
        recalculate();

        resetDayNumbers();
        selectedPosition = -1;
    }

    public void resetDayNumbers() {

        java.util.Date date = new Date();
        SimpleDateFormat today = new SimpleDateFormat("dd");
        SimpleDateFormat todaymon = new SimpleDateFormat("MM");
        curDay2  = today.format(date);
        curDay = Integer.parseInt(curDay2);
        realmon2 = todaymon.format(date);
        realmon = Integer.parseInt(realmon2);
        for (int i = 0; i < 42; i++) {
            // calculate day number
            int dayNumber = (i+1) - firstDay;
            if (dayNumber < 1 || dayNumber > lastDay) {
                dayNumber = 0;
            }

            // save as a data item
            if(dayNumber == curDay+1)
                saveidx = i;
            if(dayNumber == 2)
                startidx  = i;
            if(dayNumber == Calendar.getInstance().getActualMaximum(Calendar.DATE))
                lastindx = i;
            items[i] = new MonthItem(dayNumber);
        }



    }

    private int getFirstDay(int dayOfWeek) {
        int result = 0;
        if (dayOfWeek == Calendar.SUNDAY) {
            result = 0;
        } else if (dayOfWeek == Calendar.MONDAY) {
            result = 1;
        } else if (dayOfWeek == Calendar.TUESDAY) {
            result = 2;
        } else if (dayOfWeek == Calendar.WEDNESDAY) {
            result = 3;
        } else if (dayOfWeek == Calendar.THURSDAY) {
            result = 4;
        } else if (dayOfWeek == Calendar.FRIDAY) {
            result = 5;
        } else if (dayOfWeek == Calendar.SATURDAY) {
            result = 6;
        }

        return result;
    }


    public int getCurYear() {
        return curYear;
    }

    public int getCurMonth() {
        return curMonth;
    }


    public int getNumColumns() {
        return 7;
    }

    public int getCount() {
        return 7 * 6;
    }

    public Object getItem(int position) {
        return items[position];
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        java.util.Date date = new Date();
        SimpleDateFormat today = new SimpleDateFormat("dd");
        SimpleDateFormat todaymon = new SimpleDateFormat("MM");
        curDay2 = today.format(date);
        curDay = Integer.parseInt(curDay2);
        realmon2 = todaymon.format(date);
        realmon = Integer.parseInt(realmon2);
        MonthItemView itemView;
        if (convertView == null) {
            itemView = new MonthItemView(mContext);
        } else {
            itemView = (MonthItemView) convertView;
        }

        // create a params
        GridView.LayoutParams params = new GridView.LayoutParams(
                GridView.LayoutParams.MATCH_PARENT,
                120);

        // calculate row and column
        int rowIndex = position / countColumn;
        int columnIndex = position % countColumn;


        itemView.setItem(items[position]);
        itemView.setLayoutParams(params);
        itemView.setPadding(2, 2, 2, 2);

        // set properties
        itemView.setGravity(Gravity.CENTER);

        if (columnIndex == 0) {
            itemView.setTextColor(Color.RED);
        } else {
            itemView.setTextColor(Color.BLACK);
        }



        if (  curDay + 7 <=  Calendar.getInstance().getActualMaximum(Calendar.DATE)) {
            if (position == getSelectedPosition() && getSelectedPosition() >= saveidx - 1 && getSelectedPosition() <= saveidx + 6 && getCurMonth() == realmon - 1 ) {
                itemView.setBackgroundColor(selectColor);
            }
            else if(position >= saveidx - 1 && position < saveidx + 6 && getCurMonth() == realmon - 1 )
                itemView.setBackgroundColor(enableColor);
            else
                itemView.setBackgroundColor(Color.WHITE);
        }
        else
        {
            if (position == getSelectedPosition() && getSelectedPosition() >= saveidx - 1 && getSelectedPosition() <= lastindx&& getCurMonth() == realmon - 1) {
                itemView.setBackgroundColor(selectColor);
            }
            else if (getCurMonth() == realmon && position == getSelectedPosition() && getSelectedPosition() >= startidx-1 && getSelectedPosition() < startidx + 5- (Calendar.getInstance().getActualMaximum(Calendar.DATE) -  curDay)    ) {

                itemView.setBackgroundColor(selectColor);

            }
            if(position >= saveidx - 1 && position <= lastindx  && getCurMonth() == realmon - 1 )
                itemView.setBackgroundColor(enableColor);
            else if(position >= startidx-1 && position< startidx + 5- (Calendar.getInstance().getActualMaximum(Calendar.DATE) -  curDay)  && getCurMonth() == realmon )
                itemView.setBackgroundColor(enableColor);
            else
                itemView.setBackgroundColor(Color.WHITE);
        }

        return itemView;
    }


    /**
     * Get first day of week as android.text.format.Time constant.
     * @return the first day of week in android.text.format.Time
     */
    public static int getFirstDayOfWeek() {
        int startDay = Calendar.getInstance().getFirstDayOfWeek();
        if (startDay == Calendar.SATURDAY) {
            return Time.SATURDAY;
        } else if (startDay == Calendar.MONDAY) {
            return Time.MONDAY;
        } else {
            return Time.SUNDAY;
        }
    }


    /**
     * get day count for each month
     *
     * @param year
     * @param month
     * @return
     */
    private int getMonthLastDay(int year, int month){
        switch (month) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return (31);

            case 3:
            case 5:
            case 8:
            case 10:
                return (30);

            default:
                if(((year%4==0)&&(year%100!=0)) || (year%400==0) ) {
                    return (29);   // 2월 윤년계산
                } else {
                    return (28);
                }
        }
    }









    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    /**
     * get selected row
     *
     * @return
     */
    public int getSelectedPosition() {
        return selectedPosition;
    }


}
class MonthItem {

    private int dayValue;
    private boolean flag =true;
    public MonthItem() {

    }
    public void setflag(boolean flag)
    {
        this.flag  = flag;
    }
    public boolean getflag()
    {
        return this.flag;
    }
    public MonthItem(int day) {
        dayValue = day;
    }

    public int getDay() {
        return dayValue;
    }

    public void setDay(int day) {
        this.dayValue = day;
    }



}