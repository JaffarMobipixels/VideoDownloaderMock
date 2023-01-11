package com.practice.videodownloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.practice.videodownloader.Adapter.DownloadAdapter;
import com.practice.videodownloader.Model.DownloadModelClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<DownloadModelClass> downloadModelClasses;
    DownloadAdapter downloadAdapter;
    RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //startService(new Intent(this, appNotification.class));
            //for less than 33
            Dexter.withContext(getApplicationContext())
                    .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {


                            Log.d("perismisison" , "granted");
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            Log.d("permission" , "denied");
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).check();


        recyclerView = findViewById(R.id.recyclerView);

        downloadModelClasses = new ArrayList<>();

        downloadModelClasses.add(new DownloadModelClass("Downloading" , true));
        downloadModelClasses.add(new DownloadModelClass("Downloading1" , true));
        downloadModelClasses.add(new DownloadModelClass("Downloading2" , true));
        downloadModelClasses.add(new DownloadModelClass("Downloading3" , true));
        downloadModelClasses.add(new DownloadModelClass("Downloading4" , true));


        downloadAdapter = new DownloadAdapter(MainActivity.this , downloadModelClasses);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(downloadAdapter);

    }
}