package com.example.administrator.laptopwearabledemo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class HeartRateData{
    private String time;
    private String heartrate;
    private String heartrate1;
    private String heartrate2;
    private String heartrate3;
    private String accelerometerValues1;
    private String accelerometerValues2;
    private String accelerometerValues3;
    private String gyroscopeValues1;
    private String gyroscopeValues2;
    private String gyroscopeValues3;


    public HeartRateData(String time, String heartrate,String heartrate1,String heartrate2, String heartrate3){
        this.time = time;
        this.heartrate = heartrate;
        this.heartrate1 = heartrate1;
        this.heartrate2 = heartrate2;
        this.heartrate3 = heartrate3;
        this.accelerometerValues1 = accelerometerValues1;
        this.accelerometerValues2 = accelerometerValues2;
        this.accelerometerValues3 = accelerometerValues3;
        this.gyroscopeValues1 = gyroscopeValues1;
        this.gyroscopeValues2 = gyroscopeValues2;
        this.gyroscopeValues3 = gyroscopeValues3;

    }


    public String getValues(){
        return this.heartrate + this.heartrate1 + this.heartrate2 + this.heartrate3;
    }


    @Override

    public String toString(){

        return String.format("%s\t%s\t%s\t%s\t%s", this.time, this.heartrate,this.heartrate1,
                this.heartrate2,this.heartrate3);
    }



}
