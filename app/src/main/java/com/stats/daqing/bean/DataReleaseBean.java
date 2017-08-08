package com.stats.daqing.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 分类
 * Created by Administrator on 2017/6/4.
 */

public class DataReleaseBean implements Parcelable {


    /**
     * counts : 36
     * currentPage : 1
     * pageSize : 20
     * totalPage : 2
     * typesList : [{"createTime":1496226554000,"createUser":"18210281168","id":36,"isShow":0,"parentId":32,"typeName":"全省各市（地）地区生产总值"},{"createTime":1496226541000,"createUser":"18210281168","id":35,"isShow":0,"parentId":32,"typeName":"各县区地区生产总值"},{"createTime":1496226530000,"createUser":"18210281168","id":34,"isShow":0,"parentId":32,"typeName":"分行业地区生产总值"},{"createTime":1496226516000,"createUser":"18210281168","id":33,"isShow":0,"parentId":32,"typeName":"地区生产总值主要指标"},{"createTime":1496226493000,"createUser":"18210281168","id":32,"isShow":0,"parentId":4,"typeName":"GDP"},{"createTime":1496226458000,"createUser":"18210281168","id":31,"isShow":0,"parentId":28,"typeName":"畜禽出栏及产品产量"},{"createTime":1496226442000,"createUser":"18210281168","id":30,"isShow":0,"parentId":28,"typeName":"畜禽存栏"},{"createTime":1496226430000,"createUser":"18210281168","id":29,"isShow":0,"parentId":28,"typeName":"农林牧渔业增加值"},{"createTime":1496226408000,"createUser":"18210281168","id":28,"isShow":0,"parentId":4,"typeName":"农业"},{"createTime":1496226344000,"createUser":"18210281168","id":27,"isShow":0,"parentId":21,"typeName":"规上工业主要产品产量及用电量"},{"createTime":1496226311000,"createUser":"18210281168","id":26,"isShow":0,"parentId":21,"typeName":"未生产企业名单"},{"createTime":1496226247000,"createUser":"18210281168","id":25,"isShow":0,"parentId":21,"typeName":"规上工业工业总产值增长面"},{"createTime":1496226234000,"createUser":"18210281168","id":24,"isShow":0,"parentId":21,"typeName":"规上工业主要效益指标"},{"createTime":1496226219000,"createUser":"18210281168","id":23,"isShow":0,"parentId":21,"typeName":"地方规上工业十大产业增加值"},{"createTime":1496226190000,"createUser":"18210281168","id":22,"isShow":0,"parentId":21,"typeName":"规上工业增加值"},{"createTime":1496226166000,"createUser":"18210281168","id":21,"isShow":0,"parentId":3,"typeName":"工业和能源"},{"createTime":1496226124000,"createUser":"18210281168","id":20,"isShow":0,"parentId":19,"typeName":"建筑业主要指标"},{"createTime":1496226080000,"createUser":"18210281168","id":19,"isShow":0,"parentId":4,"typeName":"建筑业"},{"createTime":1496226042000,"createUser":"18210281168","id":18,"isShow":0,"parentId":15,"typeName":"房地产开发主要指标"},{"createTime":1496226027000,"createUser":"18210281168","id":17,"isShow":0,"parentId":15,"typeName":"分县区固定资产投资"}]
     */

    private int counts;
    private int currentPage;
    private int pageSize;
    private int totalPage;
    private List<TypesListBean> typesList;

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<TypesListBean> getTypesList() {
        return typesList;
    }

    public void setTypesList(List<TypesListBean> typesList) {
        this.typesList = typesList;
    }

    public static class TypesListBean implements Parcelable {
        /**
         * createTime : 1496226554000
         * createUser : 18210281168
         * id : 36
         * isShow : 0
         * parentId : 32
         * typeName : 全省各市（地）地区生产总值
         */

        private long createTime;
        private String createUser;
        private int id;
        private int isShow;
        private int parentId;
        private String typeName;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsShow() {
            return isShow;
        }

        public void setIsShow(int isShow) {
            this.isShow = isShow;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.createTime);
            dest.writeString(this.createUser);
            dest.writeInt(this.id);
            dest.writeInt(this.isShow);
            dest.writeInt(this.parentId);
            dest.writeString(this.typeName);
        }

        public TypesListBean() {
        }

        protected TypesListBean(Parcel in) {
            this.createTime = in.readLong();
            this.createUser = in.readString();
            this.id = in.readInt();
            this.isShow = in.readInt();
            this.parentId = in.readInt();
            this.typeName = in.readString();
        }

        public static final Parcelable.Creator<TypesListBean> CREATOR = new Parcelable.Creator<TypesListBean>() {
            @Override
            public TypesListBean createFromParcel(Parcel source) {
                return new TypesListBean(source);
            }

            @Override
            public TypesListBean[] newArray(int size) {
                return new TypesListBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.counts);
        dest.writeInt(this.currentPage);
        dest.writeInt(this.pageSize);
        dest.writeInt(this.totalPage);
        dest.writeTypedList(this.typesList);
    }

    public DataReleaseBean() {
    }

    protected DataReleaseBean(Parcel in) {
        this.counts = in.readInt();
        this.currentPage = in.readInt();
        this.pageSize = in.readInt();
        this.totalPage = in.readInt();
        this.typesList = in.createTypedArrayList(TypesListBean.CREATOR);
    }

    public static final Parcelable.Creator<DataReleaseBean> CREATOR = new Parcelable.Creator<DataReleaseBean>() {
        @Override
        public DataReleaseBean createFromParcel(Parcel source) {
            return new DataReleaseBean(source);
        }

        @Override
        public DataReleaseBean[] newArray(int size) {
            return new DataReleaseBean[size];
        }
    };
}
