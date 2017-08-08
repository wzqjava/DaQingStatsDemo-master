package com.stats.daqing.service;

import android.content.Context;

/**
 * Email: kenny.li@itotemdeveloper.com<br>
 * 文件下载类
 */
public abstract class FileDownloader implements DownloadInterface {

	protected Context mContext;
	protected DownloadHandler downloadHandler;

	public FileDownloader(Context ctx) {
		this.mContext = ctx;
		downloadHandler = new DownloadHandler(ctx, this);
	}

	@Override
	public abstract void onStart(String url);

	@Override
	public abstract void onProgress(String url, int p);

	@Override
	public abstract void onFinish(String url);

	@Override
	public abstract void onFailure(String url);
}
