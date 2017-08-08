package com.stats.daqing.bean;

import java.io.Serializable;

public class VersionDetialInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * "msg": "有升级版,  主要更新内容 :1.优化ui  2.修复若干bug
	 * 
	 *  "url": "http://10.1.16.229:8080/_assets/apk/mt.apk",
	 *  
	 *   "version": 1.0
	 */
	private String msg;
	private String url;
	private String version;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
