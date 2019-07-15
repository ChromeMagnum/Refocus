package com.example.refocus;

import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class App implements Parcelable {
    ApplicationInfo info;
    String name;
    String category;
    Drawable icon;
    int hours;
    int minutes;

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

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return hours;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public App(Parcel source) {
        name = source.readString();
        category = source.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(category);

    }

    public static final Creator<App> CREATOR = new Creator<App>(){
        @Override
        public App createFromParcel(Parcel source) {
            return new App(source);
        }

        @Override
        public App[] newArray(int size) {
            return new App[size];
        }
    };
}
