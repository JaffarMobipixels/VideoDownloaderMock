package com.practice.videodownloader.Model;

public class DownloadModelClass {
    public String getTextView() {
        return textView;
    }

    public void setTextView(String textView) {
        this.textView = textView;
    }

    public Boolean getDownloadStatus() {
        return DownloadStatus;
    }

    public void setDownloadStatus(Boolean downloadStatus) {
        DownloadStatus = downloadStatus;
    }

    public DownloadModelClass(String textView, Boolean downloadStatus) {
        this.textView = textView;
        DownloadStatus = downloadStatus;
    }

    String textView;
    Boolean DownloadStatus;
}
