package com.stats.daqing.feature.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bilibili.magicasakura.widgets.TintToolbar;
import com.stats.daqing.R;
import com.stats.daqing.feature.download.DownloadInfo;
import com.stats.daqing.feature.download.DownloadManager;
import com.stats.daqing.feature.download.DownloadState;
import com.stats.daqing.feature.download.DownloadViewHolder;

import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

/**
 * 下载管理界面
 */
@ContentView(R.layout.activity_download)
public class DownloadActivity extends AppCompatActivity {

    @ViewInject(R.id.lv_download)
    private ListView downloadList;
    @ViewInject(R.id.toolbar)
    private TintToolbar mToolBar;

    private DownloadManager downloadManager;
    private DownloadListAdapter downloadListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        setSupportActionBar(mToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(null);
            // 设置返回按钮
            supportActionBar.setHomeButtonEnabled(true);
        }
        downloadManager = DownloadManager.getInstance();
        downloadListAdapter = new DownloadListAdapter();
        downloadList.setAdapter(downloadListAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class DownloadListAdapter extends BaseAdapter {

        private Context mContext;
        private final LayoutInflater mInflater;

        private DownloadListAdapter() {
            mContext = getBaseContext();
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            if (downloadManager == null) return 0;
            return downloadManager.getDownloadListCount();
        }

        @Override
        public Object getItem(int i) {
            return downloadManager.getDownloadInfo(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            DownloadItemViewHolder holder;
            DownloadInfo downloadInfo = downloadManager.getDownloadInfo(i);
            if (view == null) {
                view = mInflater.inflate(R.layout.download_item, null);
                holder = new DownloadItemViewHolder(view, downloadInfo);
                view.setTag(holder);
                holder.refresh();
            } else {
                holder = (DownloadItemViewHolder) view.getTag();
                holder.update(downloadInfo);
            }

            if (downloadInfo.getState().value() < DownloadState.FINISHED.value()) {
                try {
                    downloadManager.startDownload(
                            downloadInfo.getUrl(),
                            downloadInfo.getLabel(),
                            downloadInfo.getFileSavePath(),
                            downloadInfo.isAutoResume(),
                            downloadInfo.isAutoRename(),
                            holder,null);
                } catch (DbException ex) {
                    Toast.makeText(x.app(), "添加下载失败", Toast.LENGTH_LONG).show();
                }
            }

            return view;
        }
    }

    public class DownloadItemViewHolder extends DownloadViewHolder {
        @ViewInject(R.id.download_label)
        TextView label;
        @ViewInject(R.id.download_state)
        TextView state;
        @ViewInject(R.id.download_pb)
        ProgressBar progressBar;
        @ViewInject(R.id.download_stop_btn)
        Button stopBtn;

        public DownloadItemViewHolder(View view, DownloadInfo downloadInfo) {
            super(view, downloadInfo);
            refresh();
        }

        @Event(R.id.download_stop_btn)
        private void toggleEvent(View view) {
            DownloadState state = downloadInfo.getState();
            switch (state) {
                case WAITING:
                case STARTED:
                    downloadManager.stopDownload(downloadInfo);
                    break;
                case ERROR:
                case STOPPED:
                    try {
                        downloadManager.startDownload(
                                downloadInfo.getUrl(),
                                downloadInfo.getLabel(),
                                downloadInfo.getFileSavePath(),
                                downloadInfo.isAutoResume(),
                                downloadInfo.isAutoRename(),
                                this,null);
                    } catch (DbException ex) {
                        Toast.makeText(x.app(), "添加下载失败", Toast.LENGTH_LONG).show();
                    }
                    break;
                case FINISHED:
                    Toast.makeText(x.app(), "已经下载完成", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }

        @Event(R.id.download_remove_btn)
        private void removeEvent(View view) {
            try {
                downloadManager.removeDownload(downloadInfo);
                downloadListAdapter.notifyDataSetChanged();
            } catch (DbException e) {
                Toast.makeText(x.app(), "移除任务失败", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void update(DownloadInfo downloadInfo) {
            super.update(downloadInfo);
            refresh();
        }

        @Override
        public void onWaiting() {
            refresh();
        }

        @Override
        public void onStarted() {
            refresh();
        }

        @Override
        public void onLoading(long total, long current) {
            refresh();
        }

        @Override
        public void onSuccess(File result) {
            refresh();
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            refresh();
        }

        @Override
        public void onCancelled(Callback.CancelledException cex) {
            refresh();
        }

        public void refresh() {
            label.setText(downloadInfo.getLabel());
            // state.setText(downloadInfo.getState().toString());
            String stateStr = getState(downloadInfo.getState());
            state.setText(stateStr);


            progressBar.setProgress(downloadInfo.getProgress());

            stopBtn.setVisibility(View.VISIBLE);
            stopBtn.setText(x.app().getString(R.string.stop));
            DownloadState state = downloadInfo.getState();
            switch (state) {
                case WAITING:
                case STARTED:
                    stopBtn.setText(x.app().getString(R.string.stop));
                    break;
                case ERROR:
                case STOPPED:
                    stopBtn.setText(x.app().getString(R.string.start));
                    break;
                case FINISHED:
                    stopBtn.setVisibility(View.INVISIBLE);
                    break;
                default:
                    stopBtn.setText(x.app().getString(R.string.start));
                    break;
            }
        }
    }

    private String getState(DownloadState state) {
        String stateStr = "";
        switch(state){
            case WAITING:
                stateStr = "等待下载";
                break;
            case STARTED:
                stateStr = "开始下载";
                break;
            case ERROR:
                stateStr = "下载出错";
                break;
            case STOPPED:
                stateStr = "停止下载";
                break;
            case FINISHED:
                stateStr = "下载完成";
                break;
        }
        return stateStr;
    }
}
