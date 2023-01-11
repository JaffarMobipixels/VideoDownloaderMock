package com.practice.videodownloader.Adapter;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.practice.videodownloader.MainActivity;
import com.practice.videodownloader.Model.DownloadModelClass;
import com.practice.videodownloader.R;

import java.io.File;
import java.util.ArrayList;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.DownloaderViewHolder> {


    Context context;
    ArrayList<DownloadModelClass> downloadModelClasses;
    DownloadManager dm;
    public static long myDownloaded;

     public DownloadAdapter(Context context, ArrayList<DownloadModelClass> downloadModelClasses)
    {
        this.context = context;
        this.downloadModelClasses = downloadModelClasses;
    }

    @NonNull
    @Override
    public DownloaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.downloadlayout , parent , false);
        return new DownloaderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloaderViewHolder holder, @SuppressLint("RecyclerView") int position) {

        DownloadModelClass downloadModelClass = downloadModelClasses.get(position);
        holder.getDownloadStatusText.setText(downloadModelClass.getTextView());


     holder.getDownloadIcon.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Log.d("download" , "started");

             //progressbar
             new Thread(new Runnable() {

                 @SuppressLint("Range")
                 @Override
                 public void run() {
                     DownloadManager.Request request =  new DownloadManager.Request(Uri.parse("https://www.youtube.com"))
                             .setTitle("Downloaded started"+".mp4")
                             .setDescription("video is downloading")
                             .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                             .setAllowedOverMetered(true)
                             .setVisibleInDownloadsUi(false);
                     request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"mp4");
                     request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);


                     dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

                     myDownloaded = dm.enqueue(request);

                     request.setDestinationInExternalFilesDir(context.getApplicationContext() , "/file","Questions.mp4");


                     boolean downloading = true;

                     while (downloading) {

                         DownloadManager.Query q = new DownloadManager.Query();
                         q.setFilterById(myDownloaded);
                         Cursor cursor = dm.query(q);
                         cursor.moveToFirst();
                         @SuppressLint("Range") int bytes_downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                         @SuppressLint("Range") int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                         new Handler(Looper.getMainLooper()).post(new Runnable() {
                             @Override
                             public void run() {
                                 holder.downloadValue.setText(" "+bytes_downloaded);
                                 holder.totalDownloadedValue.setText(" "+bytes_total);
                             }
                         });
                         Log.d("checkdownloadper" , "downloadedbutyes"+bytes_downloaded+"total size"+bytes_total);

                         if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                             downloading = false;
                             new Handler(Looper.getMainLooper()).post(new Runnable() {
                                 @Override
                                 public void run() {
                                     Log.d("checklisposition" , "poisiton is "+position);
//                                     holder.getDownloadStatusText.setText("Downloaded");
                                     holder.downloadValue.setText("0");
                                     holder.totalDownloadedValue.setText("0");
//                                     downloadModelClasses.remove(position);
                                     holder.progressBar.setProgress(100);
                                 }
                             });
                         }

                         final int dl_progress = (int) ((bytes_downloaded * 100l) / bytes_total);


                         new Handler(Looper.getMainLooper()).post(new Runnable() {
                             @Override
                             public void run() {

                                 holder.progressBar.setProgress(12);
                             }
                         });
                         cursor.close();
                     }
                 }
             }).start();

             Log.d("download" , "started asdasdasdasdasdasd");
         }
     });
     holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
                if(dm!=null)
                {
                    Log.d("delete" , "called");
                    dm.remove(myDownloaded);
                }
         }
     });


     holder.pauseBtn.setOnClickListener(new View.OnClickListener() {
         @SuppressLint("Range")
         @Override
         public void onClick(View view) {

         }
     });
    }

//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return downloadModelClasses.size();
    }

    public static class DownloaderViewHolder extends RecyclerView.ViewHolder {
        TextView getDownloadStatusText, totalDownloadedValue, downloadValue;
        ImageView getDownloadIcon;
        Button cancelBtn, pauseBtn;
        public static ProgressBar progressBar;

        public DownloaderViewHolder(@NonNull View itemView) {
            super(itemView);
            getDownloadStatusText = itemView.findViewById(R.id.downloadText);
            getDownloadIcon = itemView.findViewById(R.id.downloadICon);
            cancelBtn = itemView.findViewById(R.id.stopBtn);
            pauseBtn = itemView.findViewById(R.id.pauseBtn);
            progressBar = itemView.findViewById(R.id.progress);
            totalDownloadedValue = itemView.findViewById(R.id.totalDownloadedValue);
            downloadValue = itemView.findViewById(R.id.downloadedValue);
        }
    }
}
