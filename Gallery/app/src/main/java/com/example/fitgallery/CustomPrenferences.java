package com.example.fitgallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class CustomPrenferences {
    SharedPreferences preferences;
    public CustomPrenferences(Context context){
        preferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
    }

    public void setNightModeState(int state){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("NightMode",state);
        editor.commit();
    }

    public int loadNightModeState(){
        Integer state = preferences.getInt("NightMode", 0);

        return state;
    }

    public void setPassword(String password){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Password",password);
        editor.commit();
    }

    public String getPassword(){
        String password = preferences.getString("Password","");

        return password;
    }

    public void setPassMode(Integer passMode){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("PassMode",passMode);
        editor.commit();
    }

    public Integer getPassMode(){
        //passMode = 0 là chế độ login, passMode = 1 là chế độ sửa đổi password, passMode = 2 là chế độ xoá password
        Integer passMode = preferences.getInt("PassMode",0);

        return passMode;
    }

    void SetNumberOfColumns(Integer[] columns) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("columnVertical",columns[0]);
        editor.putInt("columnHorizontal",columns[1]);
        editor.apply();
    }

    Integer[] getNumberOfColumns() {
        Integer[] columns = new Integer[2];
        columns[0] = preferences.getInt("columnVertical", 3);
        columns[1] = preferences.getInt("columnHorizontal", 4);

        return columns;
    }

    public void setStartHour(int hour){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("StartHour",hour);
        editor.commit();
    }
    public int loadStartHour(){
        Integer hour = preferences.getInt("StartHour", 0);
        return hour;
    }
    public void setStartMinute(int minute){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("StartMinute",minute);
        editor.commit();
    }
    public int loadStartMinute(){
        Integer minute = preferences.getInt("StartMinute", 0);
        return minute;
    }
    public void setEndHour(int hour){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("EndHour",hour);
        editor.commit();
    }
    public int loadEndHour(){
        Integer hour = preferences.getInt("EndHour", 0);
        return hour;
    }
    public void setEndMinute(int minute){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("EndMinute",minute);
        editor.commit();
    }
    public int loadEndMinute(){
        Integer minute = preferences.getInt("EndMinute", 0);
        return minute;
    }
}
