package com.stats.daqing.service;

import android.app.Notification;import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.stats.daqing.R;
import com.stats.daqing.bean.VersionDetialInfo;
import com.stats.daqing.common.Constants;
import com.stats.daqing.utils.PublicUtil;

import org.xutils.common.util.LogUtil;

import java.io.File;
import java.util.Locale;



/**
 * 
 */
public class AppUpdateService extends Service {

	private NotificationManager manager;
	private Notification notif;
	private PendingIntent pendingIntent;
	private AppUpdateDownloader appUpdateDownloader;
	private VersionDetialInfo detailInfo;

	public static final String LEGALD_AILY_SD = Environment.getExternalStorageDirectory().getPath() + "/daqing";

	public static final String UPDATE_DIR = LEGALD_AILY_SD + "/download";

	@Override
	public void onCreate() {
		manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notif = new Notification();
		notif.icon = R.drawable.logo2;
		notif.flags = Notification.FLAG_AUTO_CANCEL;
		notif.tickerText = "下载开始";
		pendingIntent = null;
		Intent intent = new Intent();
		pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		appUpdateDownloader = new AppUpdateDownloader(this);
		notif.contentIntent = pendingIntent;
		LogUtil.e("onStartCommand执行了");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		LogUtil.e("service的onCreate执行了");
		if (null != intent) {
			String apkurl  =  intent.getStringExtra(Constants.INTENT_KEY_DONWLOAD_URL);
			LogUtil.e("onStartCommand执行了"+ apkurl);
				appUpdateDownloader.download(apkurl);

		}

		return super.onStartCommand(intent, flags, startId);
	}

	public IBinder onBind(Intent intent) {
		return null;
	}

	private void removeFile() {
		File file = new File(UPDATE_DIR, "temp.apk");
		boolean b = file.exists();
		Log.e("cxm", "b----" + b);
		boolean delete = file.delete();
		Log.e("cxm", "delete----" + delete);
	}

	private class AppUpdateDownloader extends FileDownloader {

		public AppUpdateDownloader(Context ctx) {
			super(ctx);
		}

		@Override
		public void onStart(String url) {
			notif.contentView = new RemoteViews(getPackageName(), R.layout.content_view);
			notif.contentView.setTextViewText(R.id.tvName, "最新安装包");
			notif.contentView.setTextViewText(R.id.tvProgress, "0%");
			notif.contentView.setProgressBar(R.id.content_view_progress, 100, 0, false);
			manager.notify(1, notif);
		}

		@Override
		public void onProgress(String url, int p) {
			notif.contentView = new RemoteViews(getPackageName(), R.layout.content_view);
			notif.contentView.setTextViewText(R.id.tvName, "最新安装包");
			notif.contentView.setTextViewText(R.id.tvProgress, p + "%");
			notif.contentView.setProgressBar(R.id.content_view_progress, 100, p, false);
			manager.notify(1, notif);
		}

		@Override
		public void onFinish(String url) {
			notif.contentView = new RemoteViews(getPackageName(), R.layout.content_view);
			notif.contentView.setTextViewText(R.id.tvName, "最新安装包");
			notif.contentView.setTextViewText(R.id.tvProgress, "100%");
			notif.contentView.setProgressBar(R.id.content_view_progress, 100, 100, false);
			manager.notify(1, notif);
			Toast.makeText(mContext, "下载完成",Toast.LENGTH_SHORT).show();
			// 启动安装程序
			installApk(new File(UPDATE_DIR + "/temp.apk"));
		}

		@Override
		public void onFailure(String url) {
			Toast.makeText(mContext, "下载失败",Toast.LENGTH_SHORT).show();
		}

		private void installApk(File f) {
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setAction(Intent.ACTION_VIEW);
			String type = getMIMEType(f);
			intent.setDataAndType(Uri.fromFile(f), type);
			mContext.startActivity(intent);
		}

		private String getMIMEType(File f) {
			String type = "";
			String fName = f.getName();
			String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase(Locale.getDefault());
			if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("xmf") || end.equals("ogg")
					|| end.equals("wav")) {
				type = "audio";
			} else if (end.equals("3gp") || end.equals("mp4")) {
				type = "video";
			} else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg")
					|| end.equals("bmp")) {
				type = "image";
			} else if (end.equals("apk")) {
				type = "application/vnd.android.package-archive";
			} else {
				type = "*";
			}
			if (end.equals("apk")) {
			} else {
				type += "/*";
			}
			return type;
		}

		/**
		 * 下载此URL文件
		 * 
		 * @param url
		 */
		public void download(String url) {
			if (TextUtils.isEmpty(url)) {
				url = "";
			}
			if (PublicUtil.isHaveSDCard()) {
				removeFile();
				Toast.makeText(mContext, "我开始下载了",Toast.LENGTH_SHORT).show();
				downloadHandler.download(url, new File(UPDATE_DIR), "temp.apk");
			} else {
				Toast.makeText(mContext, "无SD卡，不可执行更新程序",Toast.LENGTH_SHORT).show();
			}
		}
	}
}
