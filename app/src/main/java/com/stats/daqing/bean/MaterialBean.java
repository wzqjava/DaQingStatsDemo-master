package com.stats.daqing.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据查询Bean
 * Created by Administrator on 2017/6/13.
 */

public class MaterialBean implements Parcelable {


    /**
     * counts : 1
     * totalPage : 1
     * materialList : [{"columnId":"4","columnName":"数据查询","createBy":"18210281168","createTime":1497362975000,"fileContentType":"doc","fileName":"1497205328806.doc","fileSize":"0.13M","fileUrl":"http://202.97.194.240:9080/upload/files/1497362188319.doc","id":2,"imgUrl":"http://202.97.194.240:9080/upload/1497362163328.jpg","name":"统计年鉴20170613-1","publishFlag":0,"publishTime":1497283200000,"typeId":48,"typeName":"统计年鉴"}]
     * pageSize : 20
     * currentPage : 1
     */

    private int counts;
    private int totalPage;
    private int pageSize;
    private int currentPage;
    private List<MaterialListBean> materialList;

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<MaterialListBean> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<MaterialListBean> materialList) {
        this.materialList = materialList;
    }

    public static class MaterialListBean implements Parcelable {
        /**
         * columnId : 4
         * columnName : 数据查询
         * createBy : 18210281168
         * createTime : 1497362975000
         * fileContentType : doc
         * fileName : 1497205328806.doc
         * fileSize : 0.13M
         * fileUrl : http://202.97.194.240:9080/upload/files/1497362188319.doc
         * id : 2
         * imgUrl : http://202.97.194.240:9080/upload/1497362163328.jpg
         * name : 统计年鉴20170613-1
         * publishFlag : 0
         * publishTime : 1497283200000
         * typeId : 48
         * typeName : 统计年鉴
         */

        private String columnId;
        private String columnName;
        private String createBy;
        private long createTime;
        private String fileContentType;
        private String fileName;
        private String fileSize;
        private String fileUrl;
        private int id;
        private String imgUrl;
        private String name;
        private int publishFlag;
        private long publishTime;
        private int typeId;
        private String typeName;

        public String getColumnId() {
            return columnId;
        }

        public void setColumnId(String columnId) {
            this.columnId = columnId;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getFileContentType() {
            return fileContentType;
        }

        public void setFileContentType(String fileContentType) {
            this.fileContentType = fileContentType;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileSize() {
            return fileSize;
        }

        public void setFileSize(String fileSize) {
            this.fileSize = fileSize;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPublishFlag() {
            return publishFlag;
        }

        public void setPublishFlag(int publishFlag) {
            this.publishFlag = publishFlag;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
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
            dest.writeString(this.columnId);
            dest.writeString(this.columnName);
            dest.writeString(this.createBy);
            dest.writeLong(this.createTime);
            dest.writeString(this.fileContentType);
            dest.writeString(this.fileName);
            dest.writeString(this.fileSize);
            dest.writeString(this.fileUrl);
            dest.writeInt(this.id);
            dest.writeString(this.imgUrl);
            dest.writeString(this.name);
            dest.writeInt(this.publishFlag);
            dest.writeLong(this.publishTime);
            dest.writeInt(this.typeId);
            dest.writeString(this.typeName);
        }

        public MaterialListBean() {
        }

        protected MaterialListBean(Parcel in) {
            this.columnId = in.readString();
            this.columnName = in.readString();
            this.createBy = in.readString();
            this.createTime = in.readLong();
            this.fileContentType = in.readString();
            this.fileName = in.readString();
            this.fileSize = in.readString();
            this.fileUrl = in.readString();
            this.id = in.readInt();
            this.imgUrl = in.readString();
            this.name = in.readString();
            this.publishFlag = in.readInt();
            this.publishTime = in.readLong();
            this.typeId = in.readInt();
            this.typeName = in.readString();
        }

        public static final Creator<MaterialListBean> CREATOR = new Creator<MaterialListBean>() {
            @Override
            public MaterialListBean createFromParcel(Parcel source) {
                return new MaterialListBean(source);
            }

            @Override
            public MaterialListBean[] newArray(int size) {
                return new MaterialListBean[size];
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
        dest.writeInt(this.totalPage);
        dest.writeInt(this.pageSize);
        dest.writeInt(this.currentPage);
        dest.writeList(this.materialList);
    }

    public MaterialBean() {
    }

    protected MaterialBean(Parcel in) {
        this.counts = in.readInt();
        this.totalPage = in.readInt();
        this.pageSize = in.readInt();
        this.currentPage = in.readInt();
        this.materialList = new ArrayList<MaterialListBean>();
        in.readList(this.materialList, MaterialListBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<MaterialBean> CREATOR = new Parcelable.Creator<MaterialBean>() {
        @Override
        public MaterialBean createFromParcel(Parcel source) {
            return new MaterialBean(source);
        }

        @Override
        public MaterialBean[] newArray(int size) {
            return new MaterialBean[size];
        }
    };
}
