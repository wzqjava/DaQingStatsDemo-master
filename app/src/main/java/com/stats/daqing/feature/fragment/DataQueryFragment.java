package com.stats.daqing.feature.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.litesuits.common.utils.HandlerUtil;
import com.stats.daqing.R;
import com.stats.daqing.base.BasePager;
import com.stats.daqing.bean.DataReleaseBean;
import com.stats.daqing.bean.MaterialBean;
import com.stats.daqing.common.Constants;
import com.stats.daqing.common.ToastAlone;
import com.stats.daqing.common.Urls;
import com.stats.daqing.feature.adapter.DataQueryAdapter;
import com.stats.daqing.feature.adapter.InterpretationTypeAdapter;
import com.stats.daqing.feature.download.DownloadManager;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据查询
 * Created by Administrator on 2017/5/21.
 */

public class DataQueryFragment extends BasePager implements View.OnClickListener {


    private XRecyclerView mRecyclerView;
    private RecyclerView rvTypes;
    private DataQueryAdapter mAdapter;
    private int times = 0;
    private InterpretationTypeAdapter typeAdapter;
    private List<MaterialBean.MaterialListBean> materialList;
    /** 当前类型id **/
    private int currentTypeId;
    /** 当前页数 **/
    private int currentPage = 1;
    /** 每页返回数据 **/
    private int pageSize = 12;

    public DataQueryFragment(Context context) {
        super(context);
    }

    private void initVariable() {
        materialList = new ArrayList<>();
    }

    @Override
    public View initView() {
        initVariable();
        View inflate = assignViews();
        getTypes();
        return inflate;
    }

    @NonNull
    private View assignViews() {
        View inflate = View.inflate(mContext, R.layout.fragment_data_query, null);
        mRecyclerView = (XRecyclerView) inflate.findViewById(R.id.recyclerview);
        rvTypes = (RecyclerView) inflate.findViewById(R.id.rv_types);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                // 下拉刷新
                currentPage = 1;
                getArticleData(currentTypeId);
                mRecyclerView.refreshComplete();
            }


            @Override
            public void onLoadMore() {
                // 加载更多
                currentPage++;
                getArticleData(currentTypeId);
            }
        });

        mAdapter = new DataQueryAdapter(this,materialList);
        mRecyclerView.setAdapter(mAdapter);
        return inflate;
    }

    @Override
    public void initData() {

    }

    private void getTypes() {
        RequestParams params = new RequestParams(Urls.URL_APP_TYPES);
        params.addParameter("columnId","4");

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                DataReleaseBean bean = gson.fromJson(result, DataReleaseBean.class);
                List<DataReleaseBean.TypesListBean> list = bean.getTypesList();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                rvTypes.setLayoutManager(linearLayoutManager);
                typeAdapter = new InterpretationTypeAdapter(DataQueryFragment.this, list);
                rvTypes.setAdapter(typeAdapter);
                getArticleData(list.get(0).getId());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 获取文章列表
     * @param typeId
     */
    private void getArticleData(int typeId) {
        currentTypeId = typeId;
        RequestParams entity = new RequestParams(Urls.URL_APP_MATERIAL);
        entity.addParameter("typeId",currentTypeId);
        entity.addParameter("currentPage", currentPage);
        entity.addParameter("pageSize", pageSize);
        x.http().get(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("onSuccess: result = " + result);
                Gson gson = new Gson();
                MaterialBean materialBean = gson.fromJson(result, MaterialBean.class);

                if(currentPage > materialBean.getTotalPage()){
                    // 没有更多
                    ToastAlone.showShortToast("没有更多数据");
                    mRecyclerView.setNoMore(true);
                }else if(currentPage == 1){
                    // 下拉刷新
                    materialList = materialBean.getMaterialList();
                    mAdapter.setData(materialList);
                    mRecyclerView.refreshComplete();
                }else{
                    // 加载更多
                    materialList = materialBean.getMaterialList();
                    mAdapter.addData(materialList);
                    mRecyclerView.loadMoreComplete();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastAlone.showShortToast("获取数据失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    @Override
    public void onClick(View v) {
        MaterialBean.MaterialListBean bean;
        switch (v.getId()) {
            case R.id.rl_item:
                File file = (File) v.getTag();
                // File file = StorageUtil.getAppCustomCacheDirectory(Constants.APP_CACHE_DIR + File.separator + "统计年鉴20170613-13.txt");
                openFile(file);
                break;

            case R.id.tv_down:
                // 下载
                bean = (MaterialBean.MaterialListBean) v.getTag();
                String fileName = bean.getName();
                String fileUrl = bean.getFileUrl();
                // fileUrl = "http://pic.hualongxiang.com/app/image/2010/1222/15-45-13-328996514.s.480x360.jpg";
                // fileUrl = "http://dl.bintray.com/wyouflf/maven/org/xutils/xutils/3.5.0/xutils-3.5.0.aar";
                int endIndex = fileUrl.lastIndexOf(".");
                String end = fileUrl.substring(endIndex);
                try {
                    DownloadManager.getInstance().startDownload(fileUrl, fileName, "/sdcard/" + Constants.APP_CACHE_DIR_CRASH + "/" + fileName + end, true, false, null, new Runnable() {
                        @Override
                        public void run() {
                            HandlerUtil.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // 下载完成后,刷新数据
                                    mAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    });
                } catch (DbException e) {
                    e.printStackTrace();
                }
                ToastAlone.showShortToast("下载文件:" + fileName);
                break;

            case R.id.rl_type_item:
                // 点击类型,刷新文章列表
                currentPage = 1;
                int position = (int) v.getTag();
                DataReleaseBean.TypesListBean type = (DataReleaseBean.TypesListBean) v.getTag(R.id.rl_type_item);
                typeAdapter.setCurrentPosition(position);
                getArticleData(type.getId());
                break;
        }
    }







    /**
     * 打开文件
     * @param file
     */
    private void openFile(File file){
        if (file == null) {
            ToastAlone.showShortToast("文件不存在,请先下载");
            return;
        }
        try {
            Uri photoURI = FileProvider.getUriForFile(mContext, "com.stats.daqing.fileprovider", file);

            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //设置intent的Action属性
            intent.setAction(Intent.ACTION_VIEW);
            //获取文件file的MIME类型
            String type = getMIMEType(file);
            //设置intent的data和Type属性。
            intent.setDataAndType(photoURI, type);
            // intent.setDataAndType(/*uri*/Uri.fromFile(file), type);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            ToastAlone.showShortToast("请到应用市场下载 WPS APP");
        }

    }

    /**
     * 根据文件后缀名获得对应的MIME类型。
     * @param file
     */
    private String getMIMEType(File file) {

        String type="*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if(dotIndex < 0){
            return type;
        }
    /* 获取文件的后缀名 */
        String end=fName.substring(dotIndex,fName.length()).toLowerCase();
        if(end.equals(""))return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for(int i=0;i<MIME_MapTable.length;i++){ //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if(end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    private final String[][] MIME_MapTable={
            //{后缀名， MIME类型}
            {".3gp",    "video/3gpp"},
            {".apk",    "application/vnd.android.package-archive"},
            {".asf",    "video/x-ms-asf"},
            {".avi",    "video/x-msvideo"},
            {".bin",    "application/octet-stream"},
            {".bmp",    "image/bmp"},
            {".c",  "text/plain"},
            {".class",  "application/octet-stream"},
            {".conf",   "text/plain"},
            {".cpp",    "text/plain"},
            {".doc",    "application/msword"},
            {".docx",   "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls",    "application/vnd.ms-excel"},
            {".xlsx",   "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe",    "application/octet-stream"},
            {".gif",    "image/gif"},
            {".gtar",   "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h",  "text/plain"},
            {".htm",    "text/html"},
            {".html",   "text/html"},
            {".jar",    "application/java-archive"},
            {".java",   "text/plain"},
            {".jpeg",   "image/jpeg"},
            {".jpg",    "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log",    "text/plain"},
            {".m3u",    "audio/x-mpegurl"},
            {".m4a",    "audio/mp4a-latm"},
            {".m4b",    "audio/mp4a-latm"},
            {".m4p",    "audio/mp4a-latm"},
            {".m4u",    "video/vnd.mpegurl"},
            {".m4v",    "video/x-m4v"},
            {".mov",    "video/quicktime"},
            {".mp2",    "audio/x-mpeg"},
            {".mp3",    "audio/x-mpeg"},
            {".mp4",    "video/mp4"},
            {".mpc",    "application/vnd.mpohun.certificate"},
            {".mpe",    "video/mpeg"},
            {".mpeg",   "video/mpeg"},
            {".mpg",    "video/mpeg"},
            {".mpg4",   "video/mp4"},
            {".mpga",   "audio/mpeg"},
            {".msg",    "application/vnd.ms-outlook"},
            {".ogg",    "audio/ogg"},
            {".pdf",    "application/pdf"},
            {".png",    "image/png"},
            {".pps",    "application/vnd.ms-powerpoint"},
            {".ppt",    "application/vnd.ms-powerpoint"},
            {".pptx",   "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop",   "text/plain"},
            {".rc", "text/plain"},
            {".rmvb",   "audio/x-pn-realaudio"},
            {".rtf",    "application/rtf"},
            {".sh", "text/plain"},
            {".tar",    "application/x-tar"},
            {".tgz",    "application/x-compressed"},
            {".txt",    "text/plain"},
            {".wav",    "audio/x-wav"},
            {".wma",    "audio/x-ms-wma"},
            {".wmv",    "audio/x-ms-wmv"},
            {".wps",    "application/vnd.ms-works"},
            {".xml",    "text/plain"},
            {".z",  "application/x-compress"},
            {".zip",    "application/x-zip-compressed"},
            {"",        "*/*"}
    };

}
