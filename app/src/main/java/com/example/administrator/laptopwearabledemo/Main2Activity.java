package com.example.administrator.laptopwearabledemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class    Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Thread thread = new Thread(){
            public void run() {
                try {
                    sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally{
                    Intent intent = new Intent(Main2Activity.this,SensorList.class);
                    startActivity(intent);
                }

            }

        };
        thread.start();
    }

    @Override
    public void onPause(){
        super.onPause();
        finish();
    }
}
