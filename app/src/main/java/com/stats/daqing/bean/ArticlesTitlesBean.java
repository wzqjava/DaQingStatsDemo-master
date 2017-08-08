package com.stats.daqing.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 * 和文章详情里的数据一样;
 */

public class ArticlesTitlesBean implements Parcelable {


    /**
     * articlesList : [{"colCell":"","columnId":"1","columnName":"数据发布","content":"","createTime":1499237949000,"createUser":"姜楠","id":92,"imageSmallList":"","importType":1,"introductionBase":"","isFistTopFlip":1,"publicCell":"","publishFlag":0,"publishTime":1499184000000,"remark":"","rowCell":"","title":"5-3  金  融","typeId":52,"typeName":"金融"},{"colCell":"","columnId":"1","columnName":"数据发布","content":"","createTime":1499143207000,"createUser":"18210281168","id":60,"imageSmallList":"","importType":1,"introductionBase":"","isFistTopFlip":1,"publicCell":"","publishFlag":0,"publishTime":1499097600000,"remark":"","rowCell":"","title":"test","typeId":52,"typeName":"金融","updateTime":1499143207000}]
     * counts : 2
     * currentPage : 1
     * pageSize : 20
     * totalPage : 1
     */

    private int counts;
    private int currentPage;
    private int pageSize;
    private int totalPage;
    private List<ArticlesListBean> articlesList;

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

    public List<ArticlesListBean> getArticlesList() {
        return articlesList;
    }

    public void setArticlesList(List<ArticlesListBean> articlesList) {
        this.articlesList = articlesList;
    }

    public static class ArticlesListBean implements Parcelable {
        /**
         * colCell :
         * columnId : 1
         * columnName : 数据发布
         * content :
         * createTime : 1499237949000
         * createUser : 姜楠
         * id : 92
         * imageSmallList :
         * importType : 1
         * introductionBase :
         * isFistTopFlip : 1
         * publicCell :
         * publishFlag : 0
         * publishTime : 1499184000000
         * remark :
         * rowCell :
         * title : 5-3  金  融
         * typeId : 52
         * typeName : 金融
         * updateTime : 1499143207000
         */

        private String colCell;
        private String columnId;
        private String columnName;
        private String content;
        private long createTime;
        private String createUser;
        private int id;
        private String imageSmallList;
        private int importType;
        private String introductionBase;
        private int isFistTopFlip;
        private String publicCell;
        private int publishFlag;
        private long publishTime;
        private String remark;
        private String rowCell;
        private String title;
        private int typeId;
        private String typeName;
        private long updateTime;

        public String getColCell() {
            return colCell;
        }

        public void setColCell(String colCell) {
            this.colCell = colCell;
        }

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

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

        public String getImageSmallList() {
            return imageSmallList;
        }

        public void setImageSmallList(String imageSmallList) {
            this.imageSmallList = imageSmallList;
        }

        public int getImportType() {
            return importType;
        }

        public void setImportType(int importType) {
            this.importType = importType;
        }

        public String getIntroductionBase() {
            return introductionBase;
        }

        public void setIntroductionBase(String introductionBase) {
            this.introductionBase = introductionBase;
        }

        public int getIsFistTopFlip() {
            return isFistTopFlip;
        }

        public void setIsFistTopFlip(int isFistTopFlip) {
            this.isFistTopFlip = isFistTopFlip;
        }

        public String getPublicCell() {
            return publicCell;
        }

        public void setPublicCell(String publicCell) {
            this.publicCell = publicCell;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRowCell() {
            return rowCell;
        }

        public void setRowCell(String rowCell) {
            this.rowCell = rowCell;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.colCell);
            dest.writeString(this.columnId);
            dest.writeString(this.columnName);
            dest.writeString(this.content);
            dest.writeLong(this.createTime);
            dest.writeString(this.createUser);
            dest.writeInt(this.id);
            dest.writeString(this.imageSmallList);
            dest.writeInt(this.importType);
            dest.writeString(this.introductionBase);
            dest.writeInt(this.isFistTopFlip);
            dest.writeString(this.publicCell);
            dest.writeInt(this.publishFlag);
            dest.writeLong(this.publishTime);
            dest.writeString(this.remark);
            dest.writeString(this.rowCell);
            dest.writeString(this.title);
            dest.writeInt(this.typeId);
            dest.writeString(this.typeName);
            dest.writeLong(this.updateTime);
        }

        public ArticlesListBean() {
        }

        protected ArticlesListBean(Parcel in) {
            this.colCell = in.readString();
            this.columnId = in.readString();
            this.columnName = in.readString();
            this.content = in.readString();
            this.createTime = in.readLong();
            this.createUser = in.readString();
            this.id = in.readInt();
            this.imageSmallList = in.readString();
            this.importType = in.readInt();
            this.introductionBase = in.readString();
            this.isFistTopFlip = in.readInt();
            this.publicCell = in.readString();
            this.publishFlag = in.readInt();
            this.publishTime = in.readLong();
            this.remark = in.readString();
            this.rowCell = in.readString();
            this.title = in.readString();
            this.typeId = in.readInt();
            this.typeName = in.readString();
            this.updateTime = in.readLong();
        }

        public static final Parcelable.Creator<ArticlesListBean> CREATOR = new Parcelable.Creator<ArticlesListBean>() {
            @Override
            public ArticlesListBean createFromParcel(Parcel source) {
                return new ArticlesListBean(source);
            }

            @Override
            public ArticlesListBean[] newArray(int size) {
                return new ArticlesListBean[size];
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
        dest.writeTypedList(this.articlesList);
    }

    public ArticlesTitlesBean() {
    }

    protected ArticlesTitlesBean(Parcel in) {
        this.counts = in.readInt();
        this.currentPage = in.readInt();
        this.pageSize = in.readInt();
        this.totalPage = in.readInt();
        this.articlesList = in.createTypedArrayList(ArticlesListBean.CREATOR);
    }

    public static final Parcelable.Creator<ArticlesTitlesBean> CREATOR = new Parcelable.Creator<ArticlesTitlesBean>() {
        @Override
        public ArticlesTitlesBean createFromParcel(Parcel source) {
            return new ArticlesTitlesBean(source);
        }

        @Override
        public ArticlesTitlesBean[] newArray(int size) {
            return new ArticlesTitlesBean[size];
        }
    };
}
