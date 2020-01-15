package com.example.administrator.laptopwearabledemo;




import android.content.Context;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import java.io.File;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;



public class MainActivity extends WearableActivity implements SensorEventListener, View.OnClickListener{

    private TextView xHeart;
    private TextView xAxisText;
    private TextView yAxisText;
    private TextView zAxisText;
    private TextView xText;
    private TextView yText;
    private TextView zText;
    private SensorManager mSensorManager;
    private Sensor sensorHeartRate;
    private Sensor sensorAccelerometer;
    private Sensor sensorGyroscope;
    private String heartrate = String.valueOf(0);
    private String accelerometer1 = String.valueOf(0);
    private String accelerometer2 = String.valueOf(0);
    private String accelerometer3 = String.valueOf(0);
    private String gyroscope1 = String.valueOf(0);
    private String gyroscope2 = String.valueOf(0);
    private String gyroscope3 = String.valueOf(0);
    private String heartrate1 = String.valueOf(0);
    private String heartrate2 = String.valueOf(0);
    private String heartrate3 = String.valueOf(0);
    private Drawable background;
    private Context context = MainActivity.this;
    public CopyOnWriteArrayList<HeartRateData> mHeartRateData;
    public CopyOnWriteArrayList<HeartRateData> mHeartRateData1;

    public Button startAndStop;

    public int CURRENT_STATE;

    public String FileName;



    private final static String TAG = "Add State";


    public boolean append = false;
    public boolean append1 = false;

    public boolean startThread = true;
    public boolean startThread1 = false;

    public boolean HeartRateSwitch = false;
    public boolean AccelerometerSwitch = true;
    public boolean GyroscopeSwitch = true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHeartRateData = new CopyOnWriteArrayList<>();
        mHeartRateData1 = new CopyOnWriteArrayList<>();


        CURRENT_STATE = 0;

        FileName = null;



        //Heart Rate Sensor Data Streaming
        xHeart = findViewById(R.id.HR);

        //Accelerometer Sensor Data Streaming
        xAxisText = findViewById(R.id.xAxis);
        yAxisText = findViewById(R.id.yAxis);
        zAxisText = findViewById(R.id.zAxis);

        //Gyroscope Sensor Data Streaming
        xText = findViewById(R.id.X);
        yText = findViewById(R.id.Y);
        zText = findViewById(R.id.Z);


        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        String HeartRate = getIntent().getStringExtra("HeartRate");
        String Accelerometer = getIntent().getStringExtra("Accelerometer");
        String Gyroscope = getIntent().getStringExtra("Gyroscope");

        try{
            if (HeartRate != null){
                sensorHeartRate = mSensorManager.getDefaultSensor(65537);
            }

            if(Accelerometer != null){
                sensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            }

            if(Gyroscope != null){
                sensorGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            }
        }catch(Exception e){
            e.printStackTrace();
        }



         startAndStop = findViewById(R.id.button2);
         background = startAndStop.getBackground();
         startAndStop.setOnClickListener(this);

         setAmbientEnabled();


    }


    @Override
    protected void onResume() {
        super.onResume();
        if (sensorHeartRate != null) {
            mSensorManager.registerListener(this, sensorHeartRate,SensorManager.SENSOR_DELAY_FASTEST);
        }

        //if (sensorAccelerometer != null) {
            //mSensorManager.registerListener(this, sensorAccelerometer,SensorManager.SENSOR_DELAY_FASTEST);
        //}

        //if (sensorGyroscope != null) {
            //mSensorManager.registerListener(this, sensorGyroscope,SensorManager.SENSOR_DELAY_FASTEST);
        //}

    }

    @Override
    public void onClick(View v) {



        if (startAndStop.getBackground() == background) {

            HeartRateSwitch = true;
            AccelerometerSwitch = true;
            GyroscopeSwitch = true;

            if(!append && !append1){
                append = true;
            }

            startAndStop.setBackgroundResource(R.drawable.stop1);

        } else if(startAndStop.getBackground() != background) {

            HeartRateSwitch = false;
            AccelerometerSwitch = false;
            GyroscopeSwitch = false;

            if(append || append1){
                append = false;
                append1= false;

            }else{
                Log.d(TAG, "Everything is going well!");
            }

            new Thread(new Upload() {
                @Override
                public void run() {
                    if(getFilesDir().length() == 0){

                    }else{
                        File[] listOfFiles = getFilesDir().listFiles();
                        for (File file :listOfFiles){
                            if(file.isFile()){
                                upload(file);
                                deleteFile(file.getName());

                            }
                        }
                    }

                }
            }).start();


            startAndStop.setBackground(background);


        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



    @Override
    public void onSensorChanged(SensorEvent event){



        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String time = sdfDate.format(now);



        if (event.sensor.getType() == 65537 && HeartRateSwitch ){

            heartrate = Float.toString(event.values[0]);
            heartrate1 = Float.toString(event.values[1]);
            heartrate2 = Float.toString(event.values[2]);
            heartrate3 = Float.toString(event.values[3]);

            xHeart.setText(heartrate);

        }

        //if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER && AccelerometerSwitch){
            //accelerometer1 = Float.toString(event.values[0]);

            //accelerometer2 = Float.toString(event.values[1]);

           // accelerometer3 = Float.toString(event.values[2]);

          //  xAxisText.setText(accelerometer1);
          //  yAxisText.setText(accelerometer2);
            //zAxisText.setText(accelerometer3);

       // }

       // if(event.sensor.getType()== Sensor.TYPE_GYROSCOPE && GyroscopeSwitch){
           // gyroscope1 = Float.toString(event.values[0]);

           // gyroscope2 = Float.toString(event.values[1]);

           // gyroscope3 = Float.toString(event.values[2]);

           // xText.setText(gyroscope1);
           // yText.setText(gyroscope2);
           // zText.setText(gyroscope3);

      //  }


        try {


            if (append) {
                mHeartRateData.add(new HeartRateData(time, heartrate, heartrate1, heartrate2, heartrate3));
                if (mHeartRateData.size() ==100) {
                    append = false;
                    append1 = true;
                    mHeartRateData1.clear();
                    Upload j = new Upload();
                    Thread i = new Thread(j);
                    while (startThread) {
                        if (CURRENT_STATE == 0) {

                            FileName = time;
                            j.setTime(FileName);
                            j.setContext(context);
                            j.setState(CURRENT_STATE);
                            j.setHeartRateData(mHeartRateData);
                            startThread = false;
                            startThread1 = true;
                            Log.d(TAG, "first list is full");
                            CURRENT_STATE++;


                        } else if (CURRENT_STATE > 0 && CURRENT_STATE < 500) {

                            j.setTime(FileName);
                            j.setContext(context);
                            j.setState(CURRENT_STATE);
                            j.setHeartRateData(mHeartRateData);
                            startThread = false;
                            startThread1 = true;
                            Log.d(TAG, "list is full and keep collecting data till have 500 files");
                            CURRENT_STATE++;

                        } else if (CURRENT_STATE == 500) {

                            j.setTime(FileName);
                            j.setContext(context);
                            j.setState(CURRENT_STATE);
                            j.setHeartRateData(mHeartRateData);
                            startThread = false;
                            startThread1 = true;
                            Log.d(TAG, "Got 10 files");
                            CURRENT_STATE = 0;

                        }

                        i.start();

                    }
                }
            } else if (append1) {
                mHeartRateData1.add(new HeartRateData(time, heartrate, heartrate1,heartrate2, heartrate3));



                if (mHeartRateData1.size() == 100) {
                    append1 = false;
                    append = true;
                    mHeartRateData.clear();
                    Upload q= new Upload();
                    Thread k = new Thread(q);


                    while (startThread1) {
                        if (CURRENT_STATE == 0) {

                            FileName = time;
                            q.setTime(FileName);
                            q.setContext(context);
                            q.setState(CURRENT_STATE);
                            q.setHeartRateData(mHeartRateData1);
                            startThread1 = false;
                            startThread = true;
                            Log.d(TAG, "first list is full");
                            CURRENT_STATE++;


                        } else if (CURRENT_STATE > 0 && CURRENT_STATE < 500) {

                            q.setTime(FileName);
                            q.setContext(context);
                            q.setState(CURRENT_STATE);
                            q.setHeartRateData(mHeartRateData1);
                            startThread1 = false;
                            startThread = true;
                            Log.d(TAG, "list is full and keep collecting data till have 500 files");
                            CURRENT_STATE++;

                        } else if (CURRENT_STATE == 500) {

                            q.setTime(FileName);
                            q.setContext(context);
                            q.setState(CURRENT_STATE);
                            q.setHeartRateData(mHeartRateData1);
                            startThread1 = false;
                            startThread = true;
                            Log.d(TAG, "Got 10 files");
                            CURRENT_STATE = 0;

                        }

                        k.start();


                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
    }






}

