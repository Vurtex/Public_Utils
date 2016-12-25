package com.puhua.crm.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * 屏幕工具 Created by nereo on 15/11/19. Updated by nereo on 2016/1/19.
 */
@SuppressLint("NewApi")
public class ScreenUtils {

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	@SuppressLint("NewApi")
	public static Point getScreenSize(Activity context) {
		/*
		 * WindowManager wm = (WindowManager)
		 * context.getSystemService(Context.WINDOW_SERVICE); Display display =
		 * wm.getDefaultDisplay(); Point out = new Point(); if
		 * (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB_MR2) {
		 * display.getSize(out); } else { int width = display.getWidth(); int
		 * height = display.getHeight(); out.set(width, height); } return out;
		 */
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);

		Point out = new Point();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			out.x = dm.widthPixels;
			out.y = dm.heightPixels;
		} else {
			int width = dm.widthPixels;
			int height = dm.heightPixels;
			out.set(width, height);
		}
		return out;
	}
}
