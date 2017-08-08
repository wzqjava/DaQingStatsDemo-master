package com.stats.daqing.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/5/20.
 */

public class MainItemBean implements Parcelable {


    private int imgResId;

    private String name;

    private String url;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.imgResId);
        dest.writeString(this.name);
        dest.writeString(this.url);
    }

    public MainItemBean() {
    }

    protected MainItemBean(Parcel in) {
        this.imgResId = in.readInt();
        this.name = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<MainItemBean> CREATOR = new Parcelable.Creator<MainItemBean>() {
        @Override
        public MainItemBean createFromParcel(Parcel source) {
            return new MainItemBean(source);
        }

        @Override
        public MainItemBean[] newArray(int size) {
            return new MainItemBean[size];
        }
    };
}
