package com.example.android.newsapp;

class News {

    private String webTitle;

    private String webUrl;

    private String sectionName;

    private String webPublicationDate;

    private String author;

    News(String webTitle, String webUrl, String sectionName, String webPublicationDate, String author) {
        this.webTitle = webTitle;
        this.webUrl = webUrl;
        this.sectionName = sectionName;
        this.webPublicationDate = webPublicationDate;
        this.author = author;
    }

    String getWebTitle() {
        return webTitle;
    }

    String getWebUrl() {
        return webUrl;
    }

    String getSectionName() {
        return sectionName;
    }

    String getWebPublicationDate() {
        return webPublicationDate;
    }

    String getAuthor() {
        return author;
    }
}
