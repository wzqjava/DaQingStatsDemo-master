package com.stats.daqing.service;

public interface DownloadInterface {

	public void onStart(String url);

	public void onProgress(String url, int p);

	public void onFinish(String url);

	public void onFailure(String url);

}
