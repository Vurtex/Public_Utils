package com.puhua.crm.base;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.google.gson.Gson;
import com.ihep.RSAEncrypt;
import com.ihep.RSASignature;
import com.puhua.crm.R;
import com.puhua.crm.common.Boot;
import com.puhua.crm.common.Constants;
import com.puhua.crm.common.Preferences;
import com.puhua.crm.common.PreferencesUtils;
import com.puhua.crm.util.StringUtil;
import com.puhua.crm.util.Utils;
import com.puhua.crm.util.toastListener;
import com.puhua.crm.view.pulltorefresh.PullToRefreshLayout;
import com.puhua.crm.volley.VolleyDataRequester;

/**
 * @author Vurtex Activity基类
 */
@SuppressLint("ShowToast")
public class BaseActivity extends AppCompatActivity implements toastListener {
	protected boolean progressShow;
	protected ProgressDialog pdd;
	protected Activity context;
	protected InputMethodManager inputMethodManager;
	protected PreferencesUtils preferencesUtils;
	protected AQuery aq;
	protected final String TAG = "BaseActivity";
	public PopupWindow popupWindow;
	private static Toast mToast;
	public ProgressDialog mdialog;
	private static Handler mHandler = new Handler();
	private static Runnable r = new Runnable() {
		public void run() {
			mToast.cancel();
		}
	};
	private Handler mhandler=new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				boolean doCheck = false;
				JSONObject js = null;
				String restr=(String)msg.obj;
				Bundle b = msg.getData();
				 int i = b.getInt("i");
				try {
					js = new JSONObject(restr);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (null!=js) {
					
				
				try {
					String rtnMsg = js.get("rtnCode") + "";
					if (rtnMsg.equals("2001")) {
						// 用户身份失效，重登
						showToast("用户身份失效，请重新登录！");
						Intent intent=new Intent(Constants.BACK_LOGIN_ACTION);
						context.sendBroadcast(intent);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String json  = b.getString("json");
				String sign = null;
				if (null != js && js.has("sign")) {
					try {
						sign = js.getString("sign");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					showToast("请求数据异常");
					return;
				}
				doCheck = RSASignature.doCheck(json, sign, Boot.SERVER_PUBLIC_KEY);
				if (doCheck) {
					if (i == 0) {
						onMyResponse(js);
					}else if (i == 1000) {
						onMyResponse3(js);
					}else if (i==1004) {
						onMyResponse4(js);
					}else if (i==1005) {
						onMyResponse5(js);
					} else {
						onMyResponse2(js);

					}
				} else
					showToast("返回数据验签失败！");
				}
				break;

			default:
				break;
			}
			
			
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		context = this;
		// 透明状态栏
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		// //透明导航栏
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

		aq = new AQuery(context);
		pdd = new ProgressDialog(context);
		pdd.setMessage(getString(R.string.Is_loading));
		pdd.setCanceledOnTouchOutside(false);
		pdd.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				progressShow = false;
			}
		});
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        
		preferencesUtils = new PreferencesUtils(context, Preferences.CONFIG_FILE);
//		dialog = new CustomProgressDialog(context);
		// 透明状态栏
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//			Window window = getWindow();
//			// Translucent status bar
//			window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//		}
		super.onCreate(savedInstanceState);
	}

	protected void onResume() {
		super.onResume();
		Utils.registerHomeKeyReceiver(context);
	}

	/**
	 * https.
	 * 
	 * @param code
	 * @param data
	 * @return
	 */
	public String httpsPostStringForMapObject(String code, Map<String, Object> data, final int i) {
		HashMap<String, Object> params = ((BaseActivity) context).getPostParams(code);
		params.put("data", data);
		Gson gson = new Gson();
		final String json = gson.toJson(params);
		stringRequestPostHttpExample(code, json, i);
		return json;
	}

	/**
	 * @param code
	 * @param data
	 * @param i
	 * @param pullToRefreshLayout
	 * @return
	 */
	public String httpsPostStringForRefresh(String code, Map<String, String> data, final int i,
			final PullToRefreshLayout pullToRefreshLayout) {

		// final VolleyMethods volleyMethods = new VolleyMethods(context);
		HashMap<String, Object> params = ((BaseActivity) context).getPostParams(code);
		params.put("data", data);
		Gson gson = new Gson();
		final String json = gson.toJson(params);
		stringRequestPostHttpExamplem(code, json, i, pullToRefreshLayout);
		return json;
	}

	private void stringRequestPostHttpExample(String code, final String json, final int i) {
		VolleyDataRequester.withSelfCertifiedHttps(this).setUrl(getString(R.string.IP_HTTP) + Constants.WEB_SERVER_URL)
				// .setBody( getPostParamsCF(code) )
				.setMethod(VolleyDataRequester.Method.POST)
				.setStringResponseListener(new VolleyDataRequester.StringResponseListener() {
					@Override
					public void onResponse(String response) {

						// callback
						Log.e("返回：", response + "");

						JSONObject js = null;
						if (Constants.isEncrypt) {
							showdata(response,json,i);
						}else{
							
							try {
								js = new JSONObject(response);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						if (i == 0) {
							onMyResponse(js);

						}else if (i == 1000) {
							onMyResponse3(js);
						} else {
							onMyResponse2(js);

						}
						}
//						if (Constants.isEncrypt) {
//							// 客户端的私钥解密过程
//							try {
//								String restr = RSAEncrypt.decrypt(
//										RSAEncrypt.loadPrivateKeyByStr(Boot.CLIENT_PRIVATE_KEY),
//										response.toString());
//								js = new JSONObject(restr);
//							} catch (JSONException e) {
//								e.printStackTrace();
//							} catch (Exception e1) {
//								e1.printStackTrace();
//							}
//							String sign = null;
//							if (null != js && js.has("sign")) {
//								try {
//									sign = js.getString("sign");
//								} catch (JSONException e) {
//									e.printStackTrace();
//								}
//							} else {
//								showToast("请求数据异常");
//								return;
//							}
//							doCheck = RSASignature.doCheck(json, sign, Boot.SERVER_PUBLIC_KEY);
//						} else {
//							doCheck = true;
//							try {
//								js = new JSONObject(response);
//							} catch (JSONException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						}
//						if (doCheck) {
//							if (i == 0) {
//								onMyResponse(js);
//
//							}
//							else if (i == 1000) {
//								onMyResponse3(js);
//							}else {
//								onMyResponse2(js);
//
//							}
//						} else
//							showToast("返回数据验签失败！");
//
//						Log.e("js------", js + "");
					}

				}).requestString(json);
	}
	
	private void showdata(final String response,final String json,final int i){
		Thread thread=new Thread(){
			@Override
			public void run() {
				super.run();
				try {
					String restr = RSAEncrypt.decrypt( RSAEncrypt.loadPrivateKeyByStr(Boot.CLIENT_PRIVATE_KEY),
							response.toString());
					Message msg = new Message();
					Bundle b = new Bundle(); 
					b.putString("json", json); 
					 b.putInt("i", i); 
					msg.setData(b); 
					msg.what = 0;
					msg.obj=restr;
					mhandler.sendMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();
		thread=null;
		System.gc();
		
		
	};

	private void stringRequestPostHttpExamplem(String code, final String json, final int i,
			final PullToRefreshLayout pullToRefreshLayout) {
		VolleyDataRequester.withSelfCertifiedHttps(this).setUrl(getString(R.string.IP_HTTP) + Constants.WEB_SERVER_URL)
				// .setBody( getPostParamsCF(code) )
				.setMethod(VolleyDataRequester.Method.POST)
				.setStringResponseListener(new VolleyDataRequester.StringResponseListener() {
					@Override
					public void onResponse(String response) {

						// callback
						Log.e("返回：", response + "");

						boolean doCheck = false;
						JSONObject js = null;
						if (Constants.isEncrypt) {
							// 客户端私钥解密过程
							try {
								String restr = RSAEncrypt.decrypt(
										RSAEncrypt.loadPrivateKeyByStr(Boot.CLIENT_PRIVATE_KEY),
										response.toString());
								js = new JSONObject(restr);
							} catch (JSONException e) {
								e.printStackTrace();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							String sign = null;
							if (null != js && js.has("sign")) {
								try {
									sign = js.getString("sign");
								} catch (JSONException e) {
									e.printStackTrace();
								}
							} else {
								showToast("请求数据异常");
								return;
							}
							doCheck = RSASignature.doCheck(json, sign, Boot.SERVER_PUBLIC_KEY);
						} else {
							doCheck = true;
						}
						if (doCheck) {
							if (i == 0) {
								onMyResponsem(js, pullToRefreshLayout);

							} else {
								onMyResponse2(js);

							}
						} else
							showToast("返回数据验签失败！");

						Log.e("js------", js + "");
					}

				}).requestString(json);
	}

	/**
	 * https.
	 * 
	 * @param code
	 * @param data
	 * @return
	 */
	public String httpsPostString(String code, Map<String, String> data, final int i) {
		HashMap<String, Object> params = ((BaseActivity) context).getPostParams(code);
		params.put("data", data);
		Gson gson = new Gson();
		final String json = gson.toJson(params);

		stringRequestPostHttpExample(code, json, i);
		return json;
	}

	/**
	 * @param code
	 *            String
	 * @param data
	 *            Map<String, String>
	 * @return
	 */
	public String httpsPostString(String code, Map<String, String> data) {
		return this.httpsPostString(code, data, 0);
	}

	public void showToast(String message) {
		if (message.length() > 5) {
			if (message.substring(0, 5).equals("Error:")) {
				Error(Integer.parseInt(message.substring(6)));
			}
		}
		mHandler.removeCallbacks(r);
		if (mToast != null)
			mToast.setText(message);
		else
			mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		mHandler.postDelayed(r, 1000);
		mToast.show();
	}

	protected void showToast(String message, int length) {
		Toast toast = Toast.makeText(this, (!StringUtil.isEmpty(message)) ? message : Constants.ERROR_MESSAGE, length);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public HashMap<String, Object> getPostParams(String serviceCode) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("servicecode", serviceCode);
		params.put("source", "04");
		params.put("target", "11102");
		params.put("phoneImei", Utils.getIMEI(context));
		return params;
	}

	public HashMap<String, String> getPostParamsCF(String serviceCode) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("servicecode", serviceCode);
		params.put("source", "04");
		params.put("target", "11102");
		params.put("phoneImei", Utils.getIMEI(context));
		return params;
	}

	public void onMyResponse(JSONObject json) {
	};
	public void onMyResponse3(JSONObject json) {
	};
	public void onMyResponse4(JSONObject json) {
	};
	public void onMyResponse5(JSONObject json) {
	};

	public void onMyResponsem(JSONObject json, final PullToRefreshLayout pullToRefreshLayout) {
	};

	public void onMyResponse2(JSONObject json) {
	};

	/**
	 * 弹出日期选择器对话框
	 */
	public void showDatePickerDialog(final TextView txt) {
		// 普通按钮事件
		Calendar d = Calendar.getInstance(Locale.CHINA);
		// 创建一个日历引用d，通过静态方法getInstance() 从指定时区 Locale.CHINA 获得一个日期实例
		Date myDate = new Date();
		// 创建一个Date实例
		d.setTime(myDate);
		// 设置日历的时间，把一个新建Date实例myDate传入
		int year = d.get(Calendar.YEAR);
		int month = d.get(Calendar.MONTH);
		int day = d.get(Calendar.DAY_OF_MONTH);
		// 获得日历中的 year month day
		DatePickerDialog dlg = new DatePickerDialog(context, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				// DatePickerDialog 中按钮Set按下时自动调用
				// 通过id获得TextView对象
				txt.setText(Integer.toString(year) + "-" + Integer.toString(monthOfYear) + "-"
						+ Integer.toString(dayOfMonth));
				// 设置text

			}
		}, year, month, day);
		// 新建一个DatePickerDialog 构造方法中
		// （设备上下文，OnDateSetListener时间设置监听器，默认年，默认月，默认日）
		dlg.show();
		// 让DatePickerDialog显示出来

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//		if (dialog.isShowing()) {
//			dialog.dismiss();
//		}
		if (mdialog != null && mdialog.isShowing()) {
			mdialog.dismiss();
			mdialog = null;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (popupWindow != null && popupWindow.isShowing()) {
			popupWindow.dismiss();
		}
		Utils.unregisterHomeKeyReceiver(context);
	}

	/**
	 * @param errorcode
	 *            ：status.getCode()
	 * 
	 */
	public void Error(int errorcode) {
		if (errorcode == -101) {
			showToast("无法连接服务器,请检查网络！");
		} else if (errorcode == 404) {
			showToast("无法连接到指定接口！");
		} else {
			showToast("错误码:" + errorcode);
		}
	}

	public void showDialog(String message, boolean CanceledOnTouchOutside) {
		if (mdialog == null)
			mdialog = new ProgressDialog(context);
		mdialog.setMessage(message);
		mdialog.setCanceledOnTouchOutside(CanceledOnTouchOutside);
		mdialog.show();

	}
	/**
	* 判断是否是wifi连接
	*/
	public boolean isWifi(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null)
			return false;
		return connectivity.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

	}
/**
	* 判断网络是否连接
	*
	*/
	public boolean isConnected(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (null != connectivity) {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (null != info && info.isConnected()) {
				if (info.getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}
	@Override
	public void showDefineToast(int flag) {

		if (flag == 0) {
			showToast("暂不支持部分特殊字符");
		} else if (flag == 1) {
			showToast("请输入数字");
		} else if (flag == 2) {
			showToast("请输入字母和数字");
		} else {
			showToast("请重新输入");
		}
	};
	 protected void hideSoftKeyboard() {
	        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
	            if (getCurrentFocus() != null)
	                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
	                        InputMethodManager.HIDE_NOT_ALWAYS);
	        }
	    }
}
