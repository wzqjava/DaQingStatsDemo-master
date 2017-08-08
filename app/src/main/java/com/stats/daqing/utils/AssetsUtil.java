package com.stats.daqing.utils;

import android.content.res.AssetManager;

import com.litesuits.common.io.FileUtils;
import com.litesuits.common.io.IOUtils;
import com.stats.daqing.common.DaQingApplication;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/9.
 */

public class AssetsUtil {

    /**
     * 将assets目录下的指定文件夹内的所有文件拷贝到内存卡指定目录下
     * @param srcDir
     * @param destDir
     */
    public static void copyAssetsDir2StorageDir(String srcDir,String destDir){
        List<File> files = getFiles(srcDir);
        File file;
        String filePath;
        String destFile;

        for (int i = 0; i < files.size(); i++) {
            file = files.get(i);
            filePath = file.getPath();
            destFile = destDir + File.separator + file.getName();
            copyAssetsFile2Storage(filePath, destFile);
        }
    }


    /**
     * 复制assets目录下的文件到指定路径
     * @param srcFile   源文件
     * @param destFile   目标文件绝对路径
     */
    public static void copyAssetsFile2Storage(String srcFile,String destFile){
        InputStream inputStream;
        try {
            inputStream = getAssets().open(srcFile);
            FileUtils.copyInputStreamToFile(inputStream,new File(destFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取assets文件夹下的dirName文件夹下的所有文件名
     * @param dirName   文件夹名称,如果为空将获取assets文件夹下的所有文件
     * @return
     */
    public static List<File> getFiles(String dirName) {
        AssetManager assetManager = getAssets();
        List<File> files = new ArrayList<>();
        String[] fileNames;
        try {
            fileNames = assetManager.list(dirName);
            for (int i = 0; i < fileNames.length; i++) {
                files.add(new File(dirName + File.separator + fileNames[i]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }



    public static String readFile(String fileName){
        AssetManager assets = getAssets();
        String s = null;
        try {
            InputStream inputStream = assets.open(fileName);
             s = IOUtils.toString(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    private static AssetManager getAssets() {
        return DaQingApplication.getContext().getAssets();
    }

}
