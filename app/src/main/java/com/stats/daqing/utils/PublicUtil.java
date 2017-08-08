package com.stats.daqing.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.stats.daqing.R;
import com.stats.daqing.common.DaQingApplication;
import com.stats.daqing.service.AppUpdateService;

import org.xutils.common.util.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PublicUtil {
	/*public static void runInMain(Runnable task) {
		if (Thread.currentThread().getId() != MApplication.mainThreadId) {
			MApplication.mainThreadHandler.post(task);
		} else {
			task.run();
		}
	}*/

	/**
	 * 获得MetaData数据<br/>
	 * <meta-data android:name="SZICITY_CHANNEL" android:value="CHANNEL108"/>
	 *
	 * @param ctx
	 *            context
	 * @param key
	 *            android:name
	 * @return android:value
	 */
	public static String getMetaData(Context ctx, String key) {
		String CHANNELID = "itotem";
		if (TextUtils.isEmpty(key)) {
			key = "UMENG_CHANNEL";
		}
		try {
			ApplicationInfo ai;
			ai = ctx.getPackageManager().getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
			Object value = ai.metaData.get(key);
			if (value != null) {
				CHANNELID = value.toString();
			}
		} catch (Exception e) {
			//
		}
		return CHANNELID;
	}

	/**
	 * 将图片转化为二进制的方法
	 *
	 * @param bitMap
	 *            要转换的图片
	 * @return byte数组的字符串表示
	 */
	public static byte[] changeBitmapToByte(Bitmap bitMap) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitMap.compress(Bitmap.CompressFormat.PNG, 100, bos);
		return bos.toByteArray();
	}

	/**
	 * 将二进制转化为图片的方法
	 *
	 * @param b
	 * @return
	 */
	public static Bitmap changeByteToBitmap(byte[] b) {
		Bitmap bitmap = null;
		if (b.length != 0) {
			bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
		}
		return bitmap;
	}

	/**
	 * 判断参数是否为全部为数字<br>
	 * </br> 减少字符串转数字时的错误<br>
	 * </br> 此方法可使用于直接进行转换时
	 *
	 * @param s
	 *            要判断的参数
	 * @return 如果全部为数字，返回原有字符串，否则返回"0"，参数为null，依然返回"0"
	 */
	public static String isNum(String s) {
		if (TextUtils.isEmpty(s)) {
			return "0";
		}
		return isNumForBoolean(s) ? s : "0";
	}

	/**
	 * 判断参数是否为全部为数字，全数字，意味着不能存在小数点，及不能判断double和float<br>
	 * </br> 减少字符串转数字时的错误<br>
	 * </br> 此方法可使用于判断是否为全是数字的字符串
	 *
	 * @param s
	 *            要判断的参数
	 * @return true 全部为数字，否则返回false
	 */
	public static boolean isNumForBoolean(String s) {
		// 为空，不是数字，返回false
		return !TextUtils.isEmpty(s) && s.matches("\\d+");
	}

	/**
	 * 判断当前网络是否连接
	 *
	 * @param context
	 *            ctx
	 * @return true-->已连接 ;false -->未连接
	 */
	public static boolean isNetworkAvailable(Context context) {

		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm == null)
			return false;
		NetworkInfo netinfo = cm.getActiveNetworkInfo();
		return netinfo != null && netinfo.isConnected();
	}

	/**
	 * 显示软键盘
	 *
	 */
	public static void showMethod(Activity activity) {

		InputMethodManager imm = (InputMethodManager) activity.getSystemService(FragmentActivity.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
	}

	/**
	 * 隐藏软键盘
	 *
	 * @param activity
	 */
	public static void hideSoftMethod(Activity activity) {

		try {
			// 隐藏软键盘
			((InputMethodManager) (activity.getSystemService(Context.INPUT_METHOD_SERVICE))).hideSoftInputFromWindow(
					activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断是否存在SD卡
	 *
	 * @return true，有SD卡。false，无SD卡
	 */
	public static boolean isHaveSDCard() {
		String SDState = android.os.Environment.getExternalStorageState();
		return SDState.equals(android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * 验证邮箱是否合法
	 *
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		// final String str =
		// "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		// final String str = "\\w+@(\\w+.)+[a-z]{2,3}";
		final String str = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		// final String str =
		// "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		// final Pattern p = Pattern.compile(str);
		// final Matcher m = p.matcher(email);
		// LogUtil.i(m.matches() + "---");
		return email.matches(str);
	}

	/**
	 * 验证手机号是否正确
	 *
	 * @param phoneNum
	 * @return
	 */
	public static boolean isCellphone(String phoneNum) {
		// final String parrern1 = "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
		// final Pattern pattern = Pattern.compile(parrern1);
		// final Matcher matcher = pattern.matcher(str);
		// if (matcher.matches()) {
		// return true;
		// }
		return phoneNum.matches("^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
	}

	/**
	 * 验证身份证是否合法
	 *
	 * @param IDCard
	 * @return
	 */
	public static boolean isIDcard(String IDCard) {
		// final String pattern =
		// "^[1-9][0-7]\\d{4}((19\\d{2}(0[13-9]|1[012])(0[1-9]|[12]\\d|30))|(19\\d{2}(0[13578]|1[02])31)|(19\\d{2}02(0[1-9]|1\\d|2[0-8]))|(19([13579][26]|[2468][048]|0[48])0229))\\d{3}(\\d|X|x)?$";
		// final Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		// final Matcher matcher = p.matcher(idStr);
		// if (matcher.matches()) {
		// return true;
		// }
		return IDCard.matches(
				"^[1-9][0-7]\\d{4}((19\\d{2}(0[13-9]|1[012])(0[1-9]|[12]\\d|30))|(19\\d{2}(0[13578]|1[02])31)|(19\\d{2}02(0[1-9]|1\\d|2[0-8]))|(19([13579][26]|[2468][048]|0[48])0229))\\d{3}(\\d|X|x)?$");
	}

	/**
	 * 将文件存到sd卡目录
	 * @param data
	 * @param filePath
	 */
	public static void saveDatatoLocalFile(String data, String filePath) {
		if (!TextUtils.isEmpty(data) && !TextUtils.isEmpty(filePath)) {
			File file = new File(filePath);
			if (file != null && file.exists()) {
				file.delete();
			}
			FileOutputStream outStr = null;
			try {
				if (file.createNewFile()) {
					outStr = new FileOutputStream(file);
					if (outStr != null) {
						outStr.write(data.getBytes());
						outStr.flush();
					}
					Log.i("file path", " file path" + filePath);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (outStr != null) {
					try {
						outStr.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						outStr = null;
					}
				}
			}
		}
	}

	/**{"message":"主要更新内容：<br/>1. 优化部分功能","url":"http:www.haozishi.cn","version":"1.0.0"}
	 * 获取当前的版本
	 * 
	 * @return
	 */
	public static int getCurVersion(Context mContext) {
		try {
			PackageInfo pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);

			return pInfo.versionCode;// 系统自带的功能;
		} catch (NameNotFoundException e) {
//			LogUtils.e(e.getMessage());
		}
		return 1;
	}

	// 检查更新
	public static void checkVervisonSuccess(final String apkUrl, final Context mContext) {
		 /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
		final AlertDialog.Builder normalDialog =
				new AlertDialog.Builder(DaQingApplication.getContext());
		LogUtil.e("进入谈对话框逻辑");
		normalDialog.setIcon(R.drawable.iconfont_downgrey);
		normalDialog.setTitle("我是一个普通Dialog");
		normalDialog.setMessage("你要点击哪一个按钮呢?");
		normalDialog.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//...To-do
						// 点击更新，后台service下载程序，完成后提示安装即可。
						Toast.makeText(mContext, "开始下载", Toast.LENGTH_SHORT).show();
						dialog.dismiss();
						Intent intent = new Intent();
						intent.setClass(mContext, AppUpdateService.class);
						intent.putExtra("updateBean", apkUrl);
						DaQingApplication.getContext().startService(intent);
					}
				});
		normalDialog.setNegativeButton("关闭",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//...To-do
					}
				});
		// 显示
		normalDialog.show();
	}

}
