package com.stats.daqing.service;

import android.os.Handler;
import android.os.Message;

import org.xutils.common.util.LogUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadThread extends Thread {

	private File saveFile;
	private String downUrl;
	private DownloadDBUtil downloadDBUtil;

	// 已经下载的长度
	private long downLength;

	private boolean isRun;
	private Handler mHandler;

	/**
	 * @param saveFile 文件地址
	 * @param downurl  下载地址
	 */
	public DownloadThread(File saveFile, String downurl, DownloadDBUtil downloadDBUtil, Handler handler) {
		this.saveFile = saveFile;
		this.downUrl = downurl;
		this.downloadDBUtil = downloadDBUtil;
		this.mHandler = handler;
		// 获取以前的下载状态
		downLength = downloadDBUtil.getDownLength(downurl);
		isRun = true;
	}

	public void pause() {
		isRun = false;
	}

	private long fileSize;

	@Override
	public void run() {
		LogUtil.e("线程中run -- url=" + downUrl);
		// 下载开始
		LogUtil.e("线程中run -- url=" + downUrl + " start ");
		Message msgStart = Message.obtain();
		msgStart.what = DownloadHandler.MSG_START;
		msgStart.obj = downUrl;
		mHandler.sendMessage(msgStart);
		// 获取要下载的文件的大小
		try {
			URL url = new URL(this.downUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(30 * 1000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept",
					"image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			conn.setRequestProperty("Accept-Language", "zh-CN");
			conn.setRequestProperty("Referer", downUrl);
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.connect();

			if (conn.getResponseCode() == 200) {
				this.fileSize = conn.getContentLength();//根据响应获取文件大小
				if (this.fileSize <= 0) {
					throw new RuntimeException("Unkown file size ");
				}
			} else {
				throw new Exception("server no response ");
			}
		} catch (Exception e) {
			// 下载失败 -- 获取文件长度失败
			Message msg = Message.obtain();
			msg.what = DownloadHandler.MSG_FAILURE;//下载失败
			msg.obj = downUrl;
			mHandler.sendMessage(msg);
			e.printStackTrace();
			return;
		}

		if (!saveFile.exists()) {
			// 要下载的文件不存在，即从来没有下载过
			try {
				if (fileSize > 0) {
					RandomAccessFile randOut = new RandomAccessFile(this.saveFile, "rw");
					randOut.setLength(this.fileSize);
					randOut.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		LogUtil.e("线程中run -- url=" + downUrl + " -- 已下载长度downLength = " + downLength + " 总长度fileSize = " + fileSize);
		RandomAccessFile threadfile = null;
		InputStream inStream = null;
		if (downLength < fileSize) { //未下载完成
			try {
				//使用Get方式下载
				URL url = new URL(this.downUrl);
				HttpURLConnection http = (HttpURLConnection) url.openConnection();
				http.setConnectTimeout(30 * 1000);
				http.setRequestMethod("GET");
				http.setRequestProperty("Accept",
						"image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
				http.setRequestProperty("Accept-Language", "zh-CN");
				http.setRequestProperty("Referer", url.toString());
				http.setRequestProperty("Charset", "UTF-8");
				http.setRequestProperty("Range", "bytes=" + downLength + "-" + fileSize);//设置获取实体数据的范围
				http.setRequestProperty("User-Agent",
						"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
				http.setRequestProperty("Connection", "Keep-Alive");

				inStream = http.getInputStream();
				byte[] buffer = new byte[1024 * 10];
				int offset;
				threadfile = new RandomAccessFile(this.saveFile, "rwd");
				threadfile.seek(downLength);

				//是否读到末尾并且下载器属于运行状态
				while (this.isRun && ((offset = inStream.read(buffer)) != -1)) {
					threadfile.write(buffer, 0, offset);
					downLength += offset;
					//实时更新（速度太慢了）
					downloadDBUtil.update(downUrl, downLength);
					// 下载进度刷新
					Message msgProgress = Message.obtain();
					msgProgress.what = DownloadHandler.MSG_PROGRESS;
					msgProgress.obj = downUrl;
					msgProgress.arg1 = (int) (downLength * 100 / fileSize);
					mHandler.sendMessage(msgProgress);
					LogUtil.e("线程(" + this.getId() + ")中run -- downLength =  " + downLength + " -- p = "
							+ (downLength * 100 / fileSize));

				}
				if (downLength >= fileSize) {
					// 下载完成
					Message msgFinish = Message.obtain();
					msgFinish.what = DownloadHandler.MSG_FINISH;
					msgFinish.obj = downUrl;
					mHandler.sendMessage(msgFinish);
					// 删除对应的数据库信息
					downloadDBUtil.update(downUrl, 0l);
				}
			} catch (Exception e) { //线程下载过程中被中断

				Message msg = Message.obtain();
				msg.what = DownloadHandler.MSG_FAILURE;//下载失败
				msg.obj = downUrl;
				mHandler.sendMessage(msg);

				//暂停下载
				pause();
			} finally {
				if (inStream != null) {
					try {
						inStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}

				if (threadfile != null) {
					try {
						threadfile.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			// 完成下载
			Message msgFinish = Message.obtain();
			msgFinish.what = DownloadHandler.MSG_FINISH;
			msgFinish.obj = downUrl;
			mHandler.sendMessage(msgFinish);
		}

		super.run();
	}
}
