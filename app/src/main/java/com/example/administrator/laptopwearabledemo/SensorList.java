package com.example.administrator.laptopwearabledemo;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SensorList extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {


    Switch HeartRate1;
    Switch Accelerometer1;
    Switch Gyroscope1;
    Switch Light1;
    Switch Geomagnetic1;
    Switch Capacitance1;
    Switch Barometer1;
    String HeartRate;
    String Accelerometer;
    String Gyroscope;
    Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_list);

        HeartRate1 = findViewById(R.id.switch20);
        Accelerometer1 = findViewById(R.id.switch21);
        Gyroscope1 = findViewById(R.id.switch22);
        Light1 = findViewById(R.id.switch23);
        Geomagnetic1 = findViewById(R.id.switch24);
        Capacitance1 = findViewById(R.id.switch25);
        Barometer1 = findViewById(R.id.switch26);
        button = findViewById(R.id.button);
        button.setOnClickListener(this);

        HeartRate1.setOnCheckedChangeListener(this);
        Accelerometer1.setOnCheckedChangeListener(this);
        Gyroscope1.setOnCheckedChangeListener(this);
        Light1.setOnCheckedChangeListener(this);
        Geomagnetic1.setOnCheckedChangeListener(this);
        Capacitance1.setOnCheckedChangeListener(this);
        Barometer1.setOnCheckedChangeListener(this);



    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b){
        if(HeartRate1.isChecked()){

            HeartRate = "HeartRate is Enabled";

        }else{

            HeartRate = null;
        }

        if(Accelerometer1.isChecked()){

            Accelerometer = "Accelerometer is Enabled";

        }else{

            Accelerometer = null;
        }

        if(Gyroscope1.isChecked()){

          Gyroscope = "Gyroscope is Enabled";

        }else{

            Gyroscope = null ;
        }

  }

  @Override
     public void onClick(View v){
        if (button.isPressed()) {
            Intent passdata_intent = new Intent(SensorList.this, MainActivity.class);

            passdata_intent.putExtra("HeartRate",HeartRate);
            passdata_intent.putExtra("Accelerometer", Accelerometer);
            passdata_intent.putExtra("Gyroscope", Gyroscope);

            startActivity(passdata_intent);
        }
  }

    @Override
    public void onPause(){
        super.onPause();
        finish();
    }

}
