package com.example.refocus;

import android.content.pm.ApplicationInfo;

public class App {
    ApplicationInfo info;
    String name;
    String category;
    int imageId;

    public App(ApplicationInfo info, String name, String category, int imageId) {
        this.info = info;
        this.name = name;
        this.category = category;
        this.imageId = imageId;
    }

    public ApplicationInfo getInfo() {
        return info;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getImageId() {
        return imageId;
    }
}
