package com.stats.daqing.service;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.stats.daqing.utils.PublicUtil;

import org.xutils.common.util.LogUtil;

/**
 * 下载时用的数据库
 */
public class DownloadDBUtil {

	private DBHelper dbHelper;
	private SQLiteDatabase db;

	private static final String DB_NAME = "down.db";
	private static final String TABLE_NAME = "filedownlog";
	private static final String DOWN_PATH = "downpath";
	private static final String DOWN_LENGTH = "downlength";

	public DownloadDBUtil(Context ctx) {
		dbHelper = new DBHelper(ctx);
	}

	private void openDB() {
		db = dbHelper.getWritableDatabase();
	}

	private void closeDB() {
		db.close();
	}

	/**
	 * 得到对应的url已经下载的长度
	 *
	 * @param downurl 下载地址
	 * @return 0 未开始下载，(0,x)下载的长度
	 */
	public synchronized long getDownLength(String downurl) {
		long temp = 0;
		openDB();
		//        Cursor c = db.rawQuery("select * from filedownlog where downpath=?", new String[]{downurl});
		Cursor c = db.query(TABLE_NAME, new String[] { "_id", DOWN_PATH, DOWN_LENGTH }, DOWN_PATH + " = ?",
				new String[] { downurl }, null, null, null);
		if (c.moveToFirst()) {
			LogUtil.e("moveToFirst -- true");
			temp = Long.parseLong(PublicUtil.isNum(c.getString(c.getColumnIndex(DOWN_LENGTH))));
			LogUtil.e("moveToFirst -- true temp = " + temp);
		}
		c.close();
		closeDB();
		return temp;
	}

	/**
	 * 更新url对应的下载长度
	 *
	 * @param url        下载地址
	 * @param downLength 下载长度
	 */
	public synchronized void update(String url, long downLength) {
		openDB();
		ContentValues cv = new ContentValues();
		cv.put(DOWN_PATH, url);
		cv.put(DOWN_LENGTH, String.valueOf(downLength));
		int i = db.update(TABLE_NAME, cv, DOWN_PATH + " = ?", new String[] { url });
		LogUtil.e("下载表更新 downLength = " + downLength + " -- i = " + i);
		closeDB();
	}

	public synchronized void addDownload(String downloadUrl) {

		openDB();
		Cursor c = db.query(TABLE_NAME, new String[] { "_id", DOWN_PATH, DOWN_LENGTH }, DOWN_PATH + " = ?",
				new String[] { downloadUrl }, null, null, null);
		if (c.moveToFirst()) {
			// 非空，表示已有，
			return;
		}
		c.close();
		ContentValues cv = new ContentValues();
		cv.put(DOWN_PATH, downloadUrl);
		cv.put(DOWN_LENGTH, "0");
		db.insert(TABLE_NAME, null, cv);
		closeDB();

	}

	private class DBHelper extends SQLiteOpenHelper {

		//数据库名
		private static final int VERSION = 1;

		public DBHelper(Context context) {
			super(context, DB_NAME, null, VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (_id integer primary key autoincrement, "
					+ DOWN_PATH + " text, " + DOWN_LENGTH + " text)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
	}
}
