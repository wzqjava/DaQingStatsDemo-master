package com.stats.daqing.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/5/22.
 */

public class DataInterpretationBean implements Parcelable {



    private String title;
    private String imgUrl;
    private String createtime;
    private String url;
    private int imgResId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.imgUrl);
        dest.writeString(this.createtime);
        dest.writeString(this.url);
        dest.writeInt(this.imgResId);
    }

    public DataInterpretationBean() {
    }

    protected DataInterpretationBean(Parcel in) {
        this.title = in.readString();
        this.imgUrl = in.readString();
        this.createtime = in.readString();
        this.url = in.readString();
        this.imgResId = in.readInt();
    }

    public static final Parcelable.Creator<DataInterpretationBean> CREATOR = new Parcelable.Creator<DataInterpretationBean>() {
        @Override
        public DataInterpretationBean createFromParcel(Parcel source) {
            return new DataInterpretationBean(source);
        }

        @Override
        public DataInterpretationBean[] newArray(int size) {
            return new DataInterpretationBean[size];
        }
    };
}
