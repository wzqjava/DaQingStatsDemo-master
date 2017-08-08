package com.stats.daqing.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/14.
 */

public class NewArticlesBean {


    /**
     * articlesList : [{"colCell":"colcell","columnId":"1","columnName":"数据发布","content":"content","createTime":1499996293000,"createUser":"赵乙军","id":113,"imageSmallList":"","importType":0,"introductionBase":"","isFistTopFlip":1,"publicCell":"public","publishFlag":0,"publishTime":1499961600000,"remark":"1.绝对额按现价计算，增长速度按不变价计算","rowCell":"rowcell","title":"1-4  全省各市（地）地区生产总值","typeId":36,"typeName":"全省各市（地）地区生产总值"}]
     * counts : 1
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

    public static class ArticlesListBean {
        /**
         * colCell : colcell
         * columnId : 1
         * columnName : 数据发布
         * content : content
         * createTime : 1499996293000
         * createUser : 赵乙军
         * id : 113
         * imageSmallList :
         * importType : 0
         * introductionBase :
         * isFistTopFlip : 1
         * publicCell : public
         * publishFlag : 0
         * publishTime : 1499961600000
         * remark : 1.绝对额按现价计算，增长速度按不变价计算
         * rowCell : rowcell
         * title : 1-4  全省各市（地）地区生产总值
         * typeId : 36
         * typeName : 全省各市（地）地区生产总值
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
    }
}
