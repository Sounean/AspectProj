package com.example.aspectproj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getTime();
        getTime2();
    }

    @ExecuteTime
    public void getTime(){
        /*long startTime = System.currentTimeMillis();*/
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*long endTime = System.currentTimeMillis();
        Log.e("AspectJ--->", "正常方式执行时间: "+(endTime-startTime) );*/
    }

    @ExecuteTime
    public void getTime2(){
        /*long startTime = System.currentTimeMillis();*/
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*long endTime = System.currentTimeMillis();
        Log.e("AspectJ--->", "正常方式执行时间: "+(endTime-startTime) );*/
    }
}