package com.stats.daqing.common;

/**
 * Created by Administrator on 2017/6/4.
 */

public class Urls {


    public static final String BASE_URL = "http://202.97.194.240:9080/";


    /**
     *  获取栏目
     *  mainActivity中的GridView的数据
     * **/
    public static final String URL_APP_COLUMN = BASE_URL + "app/column";
    /**关于我们**/
    public static final String ABOUT_US = BASE_URL + "/app/aboutUs";// TODO: 2017-08-05 补全接口
    /** 找回密码 **/
    public static final String URL_RETRIEVE_PASSWORD = BASE_URL + "/app/retrieve/password";
    /** 注册--保存注册 或 修改用户个人信息 id不为空：修改，为空：保存**/
    public static final java.lang.String URL_APP_REGISTER = BASE_URL + "app/register";

    /** 版本升级**/
    public static final String URL_APP_UPDATE_APP = BASE_URL + "/app/versions";

    /**
     * 获取底部分类 ,(统计分析,统计公报,热点专呈);
     *
     * **/
    public static final String URL_APP_TYPES = BASE_URL + "app/types";
    /** 获取文章列表 **/
    public static final String URL_APP_ARTCLES = BASE_URL + "app/articles/lists";

    /** 获取文章详情 **/
    public static final String URL_APP_ARTCLES_DETIAL = BASE_URL + "app/articles";


    /** 数据查询 -> 获取列表 **/
    public static final String URL_APP_MATERIAL = BASE_URL + "app/material";

    /** 设置主题色 **/
    public static final String URL_APP_BACKCOLOR = BASE_URL + "app/backColor";

    /** 注册 - 获取密保问题 **/
    public static final java.lang.String URL_APP_REGISTER_QUESTIONS = BASE_URL + "app/register/questions";


    /** 登录 **/
    public static final java.lang.String URL_APP_LOGIN = BASE_URL + "app/login";

    /** 找回密码 **/
    public static final java.lang.String URL_APP_FIND_PASSWORD = BASE_URL + "app/retrieve/password";
}
