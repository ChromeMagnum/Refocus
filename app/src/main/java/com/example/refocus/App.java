package com.example.refocus;

import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;

public class App {
    ApplicationInfo info;
    String name;
    String category;
    Drawable icon;

    public App() {
    }

    public ApplicationInfo getInfo() {
        return info;
    }

    public void setInfo(ApplicationInfo info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setImage(Drawable icon) {
        this.icon = icon;
    }

    public Drawable getImage() {
        return icon;
    }
}
