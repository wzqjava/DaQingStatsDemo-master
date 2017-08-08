package com.stats.daqing.common;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;



/**
 * @author
 */
public class ToastAlone {

    /**
     * 唯一的toast
     */
    private static Toast mToast;
    private static ToastAlone instance;

    public static ToastAlone getInstance() {

        if (instance == null) {
            synchronized (ToastAlone.class) {
                instance = new ToastAlone();
            }
        }
        return instance;
    }

    /**
     * @param ctx
     * @param tips
     * @param lastTime
     *
     * @return
     */
    public static void showToast(Context ctx, String tips, int lastTime) {
        showShortToast(tips);
    }


    public static void showShortToast(String message) {

        if (!TextUtils.isEmpty(message)) {

            mToast.setText(message);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.show();
        }
    }

    public static void showShortToast(int resId) {
        mToast.setText(resId);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static void showLongToast(String message) {

        if (!TextUtils.isEmpty(message)) {
            mToast.setText(message);
            mToast.setDuration(Toast.LENGTH_LONG);
            mToast.show();
        }
    }

    public static void showLongToast(int resId) {

        mToast.setText(resId);
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.show();
    }

    //程序启动就完成初始化，引用全局的context
    public void init(Context context) {
        if (mToast == null) {
            synchronized (ToastAlone.class) {
                if (mToast == null) {
                    mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
                    //                    mToast.setGravity(Gravity.CENTER, 0, 0);
                }
            }
        }
    }
}