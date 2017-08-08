package com.stats.daqing.service;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import org.xutils.common.util.LogUtil;

import java.io.File;
import java.util.HashMap;

public class DownloadHandler {
	private DownloadInterface downloadInterface;
	//    private Context mContext;
	private Handler mHandler;
	private DownloadDBUtil downloadDBUtil;

	public static final int MSG_START = 101;
	public static final int MSG_PROGRESS = 102;
	public static final int MSG_FINISH = 103;
	public static final int MSG_FAILURE = 104;


	private HashMap<String, DownloadThread> downloadThreads;

	public DownloadHandler(Context mContext, DownloadInterface downloadInterface) {
		this.downloadInterface = downloadInterface;
		//        this.mContext = mContext;
		downloadDBUtil = new DownloadDBUtil(mContext);
		downloadThreads = new HashMap<String, DownloadThread>();

		if (Looper.myLooper() != null) {
			mHandler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					DownloadHandler.this.handleMessage(msg);
				}
			};
		}
	}

	private void handleMessage(Message msg) {
		String url = (String) msg.obj;
		switch (msg.what) {
		case MSG_START:
			downloadInterface.onStart(url);
			break;
		case MSG_PROGRESS:
			int p = msg.arg1;
			downloadInterface.onProgress(url, p);
			break;
		case MSG_FINISH:
			downloadInterface.onFinish(url);
			break;
		case MSG_FAILURE:
			downloadInterface.onFailure(url);
			break;
		}
	}

	/**
	 * 下载文件
	 *
	 * @param downloadUrl 下载地址
	 * @param fileDir     文件保存路径
	 * @param fileName    文件名字
	 */
	public void download(String downloadUrl, File fileDir, String fileName) {
		// 启动下载线程，传递本类中的handler。
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		if (TextUtils.isEmpty(downloadUrl)) {
			downloadInterface.onFailure(downloadUrl);
			return;
		}
		LogUtil.e("启动一条下载线程 -- url = " + downloadUrl);
		downloadDBUtil.addDownload(downloadUrl);
		DownloadThread downloadThread = new DownloadThread(new File(fileDir, fileName), downloadUrl, downloadDBUtil,
				mHandler);
		downloadThreads.put(downloadUrl, downloadThread);
		downloadThread.start();
	}

	/**
	 * 暂停下载
	 *
	 * @param downloadUrl 下载地址
	 */
	public void pause(String downloadUrl) {
		if (downloadThreads.containsKey(downloadUrl)) {
			downloadThreads.get(downloadUrl).pause();
		}
	}
}
