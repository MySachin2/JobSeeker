package com.phacsin.jobseeker.dataClasses;

public class JobListItem {
    private String url,title,subtitle;


    public JobListItem(String url, String title, String subtitle) {
        this.url = url;
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }
}
