package com.puhua.crm.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;

public class PropertiesUtils {
	private Properties p;
	private Context context;
	public PropertiesUtils(Context context){
		this.context=context;
	} 
	// 读
	public String get(String key) {
		p = new Properties();
		try {
			InputStream in = context.getAssets().open("keys.properties"); 
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p.getProperty(key);
	}

//	// 写
//	public void put(String key, String value) {
//		p = new Properties();
//		try {
//			InputStream in = context.getAssets().open("key.properties"); 
//			p.load(in);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		p.setProperty(key, value);
//		OutputStream fos;
//		try {
//			fos = new FileOutputStream(Constants.PROP_PATH + "/login.properties");
//			p.store(fos, null);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
}
