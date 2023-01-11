package com.practice.videodownloader;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.practice.videodownloader.Adapter.DownloadAdapter;

public class DownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("checkdownload" , "download completed");

        Log.d("checkdownloadid" , "downloaded id ids  "+DownloadAdapter.myDownloaded);
        long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        Log.d("checkdownloadid" , "downloadedasdsadas id ids  "+id);

        if (DownloadAdapter.myDownloaded == id) {
            Toast.makeText(context, "Download Completed", Toast.LENGTH_SHORT).show();


        }
//sadsadsaoudsaoidusaudoisad


    }
}
