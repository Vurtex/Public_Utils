package com.puhua.crm.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.puhua.crm.R;
import com.puhua.crm.server.HomeReceiver;
import com.puhua.crm.view.CircleTextImageView.CircleTextImageView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by xiayong on 2015/6/27.
 */
public class Utils {

	/**
	 * Map a value within a given range to another range.
	 * 
	 * @param value
	 *            the value to map
	 * @param fromLow
	 *            the low end of the range the value is within
	 * @param fromHigh
	 *            the high end of the range the value is within
	 * @param toLow
	 *            the low end of the range to map to
	 * @param toHigh
	 *            the high end of the range to map to
	 * @return the mapped value
	 */
	public static double mapValueFromRangeToRange(double value, double fromLow,
			double fromHigh, double toLow, double toHigh) {
		double fromRangeSize = fromHigh - fromLow;
		double toRangeSize = toHigh - toLow;
		double valueScale = (value - fromLow) / fromRangeSize;
		return toLow + (valueScale * toRangeSize);
	}

	public static void DismissInputSoft(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0); // 强制隐藏键盘
	}

	public static String getYesterday() {
		Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
		ca.setTime(new Date()); // 设置时间为当前时间
		ca.add(Calendar.DATE, -1); //
		Date lastMonth = ca.getTime(); // 结果
		System.out.println(lastMonth);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(sf.format(lastMonth));
		return sf.format(lastMonth);

	}

	public static String getDate(String format) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat(format);
		String date = sDateFormat.format(new Date());
		// Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
		// // int year = t.year;
		// // int month = t.month;
		// int date = t.monthDay;
		// String dates = "";
		// if (date<10) {
		// dates = "0"+date;
		// }else {
		// dates = ""+date;
		// }
		return date;
	}

	/**
	 * 比较日期 DATE1开始时间 DATE2结束时间
	 */
	public static int compare_date(String DATE1, String DATE2) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		// DateFormat df = new SimpleDateFormat(format);
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt2.getTime() > dt1.getTime()) {
				return 1;
			} else if (dt2.getTime() < dt1.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 判断时间跨度是否为三个月 DATE1开始时间 DATE2结束时间
	 */
	public static boolean timeSpan(String DATE1, String DATE2) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		// DateFormat df = new SimpleDateFormat(format);
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt2.getTime() > dt1.getTime()) {
				float span = (dt2.getTime() - dt1.getTime()) / 1000 / 60 / 60
						/ 24;
				if (span > 91) {
					return false;
				} else {
					return true;
				}
			} else if (dt2.getTime() < dt1.getTime()) {
				return false;
			} else {
				return false;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return false;
	}

	/**
	 * set margins of the specific view
	 * 
	 * @param target
	 * @param l
	 * @param t
	 * @param r
	 * @param b
	 */
	public static void setMargin(View target, int l, int t, int r, int b) {
		if (target.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
			ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) target
					.getLayoutParams();
			p.setMargins(l, t, r, b);
			target.requestLayout();
		}
	}

	/**
	 * convert drawable to bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
				.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;

	}

	/** 获取屏幕的宽度 */
	public final static int getWindowsWidth(Context activity) {
//		DisplayMetrics dm = new DisplayMetrics();
		Resources re=activity.getResources();
		DisplayMetrics dm=re.getDisplayMetrics();
		int width=dm.widthPixels;
//		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return width;
	}

	/**
	 * 初始化ImageLoader
	 * 
	 * @param context
	 */
	public static void initImageLoader(Context context) {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.default_avatar)
				.showImageForEmptyUri(R.drawable.default_avatar)
				.showImageOnFail(R.drawable.default_avatar).cacheInMemory(true)
				.considerExifParams(true)
				.displayer(new FadeInBitmapDisplayer(300, true, true, true))
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
				context).defaultDisplayImageOptions(defaultOptions)
				.memoryCache(new WeakMemoryCache());
		ImageLoaderConfiguration config = builder.build();
		ImageLoader.getInstance().init(config);
	}

	@SuppressWarnings("deprecation")
	public static ArrayList<String> getGalleryPhotos(Activity act) {
		ArrayList<String> galleryList = new ArrayList<String>();
		try {
			final String[] columns = { MediaStore.Images.Media.DATA,
					MediaStore.Images.Media._ID };
			final String orderBy = MediaStore.Images.Media._ID;
			Cursor imagecursor = act.managedQuery(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
					null, null, orderBy);
			if (imagecursor != null && imagecursor.getCount() > 0) {
				while (imagecursor.moveToNext()) {
					String item = new String();
					int dataColumnIndex = imagecursor
							.getColumnIndex(MediaStore.Images.Media.DATA);
					item = imagecursor.getString(dataColumnIndex);
					galleryList.add(item);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Collections.reverse(galleryList);
		return galleryList;
	}

	public static Bitmap convertViewToBitmap(View view) {
		Bitmap bitmap = null;
		try {
			int width = view.getWidth();
			int height = view.getHeight();
			if (width != 0 && height != 0) {
				bitmap = Bitmap.createBitmap(width, height,
						Bitmap.Config.ARGB_8888);
				Canvas canvas = new Canvas(bitmap);
				view.layout(0, 0, width, height);
				view.setBackgroundColor(Color.WHITE);
				view.draw(canvas);
			}
		} catch (Exception e) {
			bitmap = null;
			e.getStackTrace();
		}
		return bitmap;

	}

	public static boolean saveImageToGallery(Context context, Bitmap bmp,
			boolean isPng) {
		if (bmp == null) {
			return false;
		}
		File appDir = new File(Environment.getExternalStorageDirectory(),
				context.getString(R.string.app_name));
		if (!appDir.exists()) {
			if (!appDir.mkdir()) {
				return false;
			}
		}
		String fileName;
		if (isPng) {
			fileName = System.currentTimeMillis() + ".png";
		} else {
			fileName = System.currentTimeMillis() + ".jpg";
		}
		File file = new File(appDir, fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			if (isPng) {
				bmp.compress(CompressFormat.PNG, 100, fos);
			} else {
				bmp.compress(CompressFormat.JPEG, 100, fos);
			}
			bmp.recycle();
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		try {
			MediaStore.Images.Media.insertImage(context.getContentResolver(),
					file.getAbsolutePath(), fileName, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
				Uri.fromFile(appDir)));
		return true;
	}

	/**
	 * 获取IMEI码
	 * 
	 * @return String
	 */
	public static String getIMEI(Context mContext) {
		if (mContext == null) {
			return "";
		}
		TelephonyManager tm = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = tm.getDeviceId();
		return imei;
	}

	@SuppressLint("SimpleDateFormat")
	public static String getCurrtentTime(int flag) {
		SimpleDateFormat formatter;
		if (flag == 1) {
			formatter = new SimpleDateFormat("yyyy-MM-dd");
		} else if (flag == 2) {
			formatter = new SimpleDateFormat("yyyy-MMssssss");
		} else {
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}

		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String time = formatter.format(curDate);
		return time;

	}

	@SuppressWarnings("deprecation")
	public static void refresh_praise_img(Context context,
			RelativeLayout layout_givelike, Object Tag) {
		Drawable img = null;
		if (Tag.equals("0"))// 没有点
		{
			img = context.getResources().getDrawable(
					R.drawable.btn_givelike_img_nor);
		} else {
			img = context.getResources().getDrawable(
					R.drawable.btn_givelike_img_h);
		}

		// 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
		img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
		((TextView) layout_givelike.findViewById(R.id.tv_bravo))
				.setCompoundDrawables(img, null, null, null); // 设置左图标
	}

	public static final int dpToPx(float dp, Resources res) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				res.getDisplayMetrics());
	}

	public static final FrameLayout.LayoutParams createLayoutParams(int width,
			int height) {
		return new FrameLayout.LayoutParams(width, height);
	}

	public static final FrameLayout.LayoutParams createMatchParams() {
		return createLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
	}

	public static final FrameLayout.LayoutParams createWrapParams() {
		return createLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
	}

	public static final FrameLayout.LayoutParams createWrapMatchParams() {
		return createLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
	}

	public static final FrameLayout.LayoutParams createMatchWrapParams() {
		return createLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
	}

	// A method to find height of the status bar
	public static int getStatusBarHeight(Context c) {
		int result = 0;
		int resourceId = c.getResources().getIdentifier("status_bar_height",
				"dimen", "android");
		if (resourceId > 0) {
			result = c.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	/**
	 * 获取版本号信息
	 * 
	 * @param context
	 * @return PackageInfo
	 */
	public static PackageInfo getVersion(Context context) {
		PackageManager pm = context.getPackageManager();
		PackageInfo pi = null;
		try {
			pi = pm.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return pi;
	}

	/**
	 * 带自个名的头像
	 * 
	 * @param mContext
	 * @param view
	 * @param name
	 */
	@SuppressWarnings("deprecation")
	public static void HeadPic(Context mContext, CircleTextImageView view,
			String name, String color) {

		view.setFillColor(Color.parseColor(color));

		if (name.length() > 2&&name.length()<=4) {
			String name2 = name.substring(name.length() - 2, name.length());
			view.setText(name2);
		} else if(name.length() > 4){
			String name3 = name.substring(1,3);
			view.setText(name3);
		}else {
			view.setText(name);
		}
	}

	/**
	 * 验证手机格式
	 */
	public static boolean isMobileNO(String mobiles) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
		String telRegex = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (mobiles.isEmpty())
			return false;
		else
			return mobiles.matches(telRegex);
	}

	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * 检测String是否全是中文
	 * 
	 * @param name
	 * @return
	 */
	public static boolean checkChinese(String name) {
		boolean res = true;
		char[] cTemp = name.toCharArray();
		for (int i = 0; i < name.length(); i++) {
			if (!isChinese(cTemp[i])) {
				res = false;
				break;
			}
		}
		return res;
	}

	/**
	 * 限制特殊字符
	 * 
	 * @param c
	 * @return 特殊字符:false
	 */
	public static boolean ExChar(char c) {
		// String digits =
		// "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		String digits = "-_+={}[]~!@#$%&*()':;',.?~！@#%……&*（）‘；：”“’。，、？   ";
		if (digits.indexOf(c) < 0)
			return false;
		else
			return true;
	}

	/**
	 * 只能输入数字和字母
	 * 
	 * @param c
	 * @return 数字或者字母:true
	 */
	public static boolean ExNumAndLetter(char c) {
		String digits = "0123456789abcdefghijgklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		if (digits.indexOf(c) < 0)
			return false;
		else
			return true;
	}

	/**
	 * @param c
	 * @return 数字:true
	 */
	public static boolean ExNum(char c) {
		String digits = "0123456789";
		if (digits.indexOf(c) < 0)
			return false;
		else
			return true;
	}

	/**
	 * @param textView
	 * @param flag
	 *            :0:只限制特殊字符;1:只能输入数字;2:只能输入数字加字母
	 * @param context
	 * @param tl
	 */
	public static void CheckExAndChinese(final EditText textView,
			final int flag, final toastListener tl) {

		textView.addTextChangedListener(new TextWatcher() {
			String tmp = "";

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				textView.setSelection(s.length());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				tmp = s.toString();
			}

			@Override
			public void afterTextChanged(Editable s) {
				String str = s.toString();
				if (str.equals(tmp)) {
					return;
				}
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < str.length(); i++) {
					char charAt = str.charAt(i);
					if (flag == 0) {// 限制特殊字符
						if (Utils.ExNumAndLetter(charAt)
								|| Utils.isChinese(charAt)
								|| Utils.ExChar(charAt))
							sb.append(charAt);
						else
							tl.showDefineToast(flag);
					} else if (flag == 1)// 只能输入数字
					{
						if (Utils.ExNum(charAt))
							sb.append(charAt);
						else
							tl.showDefineToast(flag);
					} else if (flag == 2)// 只能输入数字+字母
					{
						if (Utils.ExNumAndLetter(charAt))
							sb.append(charAt);
						else
							tl.showDefineToast(flag);
					} else {
						tl.showDefineToast(flag);
					}
				}
				tmp = sb.toString();
				textView.setText(tmp);
			}

		});
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/** 获取手机的密度 */
	public static float getDensity(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.density;
	}

	private static HomeReceiver mHomeKeyReceiver = null;

	public static void registerHomeKeyReceiver(Context context) {

		mHomeKeyReceiver = new HomeReceiver();
		final IntentFilter homeFilter = new IntentFilter(
				Intent.ACTION_CLOSE_SYSTEM_DIALOGS);

		context.registerReceiver(mHomeKeyReceiver, homeFilter);
	}

	public static void unregisterHomeKeyReceiver(Context context) {
		if (null != mHomeKeyReceiver) {
			context.unregisterReceiver(mHomeKeyReceiver);
		}
	}

}
