package com.stats.daqing.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/8.
 */

public class TimeUtil {

    /**
     * 毫秒转换日期
     * @param millisecond   毫秒
     * @return
     */
    public static String millisecond2DateStr(long millisecond){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = dateFormat.format(new Date(millisecond));
        return format;
    }


    /**
     * 毫秒转换日期
     * @param millisecond   毫秒
     * @param formatDate    如:"yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String millisecond2DateStr(long millisecond,String formatDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatDate);
        String format = dateFormat.format(new Date(millisecond));
        return format;
    }



}
