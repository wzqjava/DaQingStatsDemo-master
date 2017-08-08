package com.stats.daqing.base;

import android.content.Context;
import android.view.View;

public abstract class BasePager {
	
	public Context mContext;
	public View view;

	public BasePager(Context context) {
		mContext = context;
		view = initView();
	}
	
	public abstract View initView();
	
	public abstract void initData();
	
	// 返回当前类对应界面效果的方法
	public View getRootView(){
		return view;
	}

}
