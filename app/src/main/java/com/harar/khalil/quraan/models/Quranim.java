package com.harar.khalil.quraan.models;

import android.net.Uri;

public class Quranim {
    private Uri url;
    private String name;

    public Quranim() {

    }

    public Quranim(Uri url, String name) {
        this.url = url;
        this.name = name;
    }

    public Uri getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public void setUrl(Uri url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }
}
