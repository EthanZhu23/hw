package com.example.administrator.laptopwearabledemo;


import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.concurrent.CopyOnWriteArrayList;

public class Upload implements Runnable {
    private Context context;
    private CopyOnWriteArrayList<HeartRateData> HeartRateData;
    private int FILE_STATE;
    private String FileName;
    private final static String TAG = "FileWriter";

    public void setHeartRateData(CopyOnWriteArrayList<HeartRateData> list){
        this.HeartRateData = list;
    }


    public void setContext(Context MainContext){
        this.context = MainContext;
    }

    public void setState(int FILE_STATE_INPUT){
        this.FILE_STATE = FILE_STATE_INPUT;
    }

    public void setTime(String time){
        this.FileName = time;
    }

    private void WriteFileInternal(CopyOnWriteArrayList<HeartRateData> list, int FILE_STATE, String FileName) {

        BufferedWriter writer;
        File file = new File(context.getFilesDir(), FileName + ".txt");

        if(!file.exists()) {

            try {

                FileOutputStream fos = new FileOutputStream(file,true);
                OutputStreamWriter outWriter = new OutputStreamWriter(fos);
                writer = new BufferedWriter(outWriter);
                writer.write("Time" + "\t");
                writer.write("HeartRate"+ "\t");
                writer.write("HeartRate1" + "\t");
                writer.write("HeartRate2" + "\t");
                writer.write("HeartRate3" + "\n");
                //writer.write("AccelerometerX"+ "\t");
                //writer.write("AccelerometerY"+ "\t");
                //writer.write("AccelerometerZ"+ "\t");
                //writer.write("GyroscopeX"+ "\t");
                //writer.write("GyroscopeY"+ "\t");
                //writer.write("GyroscopeZ"+ "\n");
                for (HeartRateData s : list) {
                    writer.write(String.valueOf(s) + "\n");
                }
                writer.write("\n");
                writer.flush();
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.d(TAG,"Writing into a new file");
        }else if(file.exists()){
            try{

                FileOutputStream fos = new FileOutputStream(file,true);
                OutputStreamWriter outWriter = new OutputStreamWriter(fos);
                writer = new BufferedWriter(outWriter);
                for (HeartRateData s : list) {
                    writer.append(String.valueOf(s) + "\n");
                    }
                    writer.append("\n");
                    writer.flush();
                    writer.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.d(TAG,"Appending for previous file");

            }


        if(FILE_STATE < 500){
            Log.d(TAG,"Didn't got 500 files yet");

        }else if(FILE_STATE == 500){
            upload(file);
            context.deleteFile(file.getName());
            Log.d(TAG,"Uploading...");
        }



    }



    public void upload(File dataFile){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        Uri file = Uri.fromFile(new File(dataFile.getAbsolutePath()));
        StorageReference dataRef = storageRef.child( "HeartRateData/" + file.getLastPathSegment());


        UploadTask uploadTask = dataRef.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });
    }


    @Override
    public void run() {

        try {


                synchronized (this) {
                    WriteFileInternal(HeartRateData, FILE_STATE, FileName);

                }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}


