package com.puhua.crm.base;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
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
import com.puhua.crm.volley.VolleyDataRequester;

/**
 * @author Vurtex Fragment基类
 */
public class BaseFragment extends Fragment implements toastListener {
	
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
						Recevied("", js, null);
					} else {
						onMyResponse2(js);

					}
				} else
					showToast("返回数据验签失败！");

				break;

			default:
				break;
			}
			
			
		};
	};

	private void stringRequestPostHttpExample(String code, final String json, final int i) {
		VolleyDataRequester.withSelfCertifiedHttps(context)
				.setUrl(getString(R.string.IP_HTTP) + Constants.WEB_SERVER_URL)
				// .setBody( getPostParamsCF(code) )
				.setMethod(VolleyDataRequester.Method.POST)
				.setStringResponseListener(new VolleyDataRequester.StringResponseListener() {
					@Override
					public void onResponse(final String response) {
						// callback
					       //执行方法
					        
					       Log.e("返回：", response + "");
						boolean doCheck = false;
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

						} else {
							onMyResponse2(js);

						}
						}
						
//						if (Constants.isEncrypt) {
//							// 客户端私钥解密过程
//							try {
//								
//								String restr = RSAEncrypt.decrypt( RSAEncrypt.loadPrivateKeyByStr(CrmApplication.CLIENT_PRIVATE_KEY),
//										response.toString());
//								js = new JSONObject(restr);
//								String rtnMsg = js.get("rtnCode") + "";
//								if (rtnMsg.equals("2001")) {
//									// 用户身份失效，重登
//									showToast("用户身份失效，请重新登录！");
//									startActivity(new Intent(context, LoginActivity.class));// EMLoginActivity
//								}
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
//							doCheck = RSASignature.doCheck(json, sign, CrmApplication.SERVER_PUBLIC_KEY);
//						} else {
//							doCheck = true;
//						}
//						if (doCheck) {
//							if (i == 0) {
//								onMyResponse(js);
//								Recevied("", js, null);
//							} else {
//								onMyResponse2(js);
//
//							}
//						} else
//							showToast("返回数据验签失败！");
//
//						Log.e("response------", response);
//						long endTime=System.currentTimeMillis();
//					       float excTime=(float)(endTime-startTime)/1000;
//					       System.out.println("执行时间："+excTime+"s");
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
	/**
	 * https.
	 * 
	 * @param code
	 * @param data
	 * @return
	 */
	public String httpsPostString(String code, Map<String, String> data, final int i) {
		// final VolleyMethods volleyMethods = new VolleyMethods(context);
		HashMap<String, Object> params = ((BaseActivity) context).getPostParams(code);
		params.put("data", data);
		Gson gson = new Gson();
		final String json = gson.toJson(params);
		stringRequestPostHttpExample(code, json, i);
		return json;
	}

	protected boolean progressShow;
	protected ProgressDialog pdd;
	public Activity context;
	public PreferencesUtils preferencesUtils;
//	public CustomProgressDialog dialog;
	protected AQuery aq;
	private static Toast mToast;
	private static Handler mHandler = new Handler();
	private static Runnable r = new Runnable() {
		public void run() {
			mToast.cancel();
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this.getActivity();
		preferencesUtils = new PreferencesUtils(context, Preferences.CONFIG_FILE);
//		dialog = new CustomProgressDialog(context);
		pdd = new ProgressDialog(context);
		pdd.setMessage(getString(R.string.Is_loading));
		pdd.setCanceledOnTouchOutside(false);
		pdd.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				progressShow = false;
			}
		});
		super.onCreate(savedInstanceState);
	}

	public void Recevied(String url, JSONObject object, AjaxStatus status) {

	};

	public void onMyResponse(JSONObject json) {
	};

	public void onMyResponse2(JSONObject json) {
	};

	public void setAquery(View view) {
		aq = new AQuery(view);
	}

	public void showToast(String message) {
		mHandler.removeCallbacks(r);
		if (mToast != null)
			mToast.setText(message);
		else
			mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		mHandler.postDelayed(r, 1000);
		mToast.show();
		// Toast toast = Toast.makeText(context, (!StringUtil.isEmpty(message))
		// ? message : Constants.ERROR_MESSAGE,
		// Toast.LENGTH_SHORT);
		// toast.setGravity(Gravity.CENTER, 0, 0);
		// toast.show();
	}

	protected void showToast(String message, int length) {
		Toast toast = Toast.makeText(context, (!StringUtil.isEmpty(message)) ? message : Constants.ERROR_MESSAGE,
				length);
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

	public void httpPost(String serviceCode, Map<String, ?> data, String callBackMethodName) {
		HashMap<String, Object> params = getPostParams(serviceCode);
		params.put("data", data);
		Gson gson = new Gson();
		String json = gson.toJson(params);
		HashMap<String, Object> param = new HashMap<String, Object>();
		if (Constants.isEncrypt) {
			// 公钥加密过程
			String cipher = null;
			try {
				cipher = RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(Boot.CLIENT_PUBLIC_KEY), json);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("原文：" + json);
			System.out.println("加密：" + cipher);
			String encode = "";
			try {
				encode = URLEncoder.encode(cipher, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			param.put("param", cipher);
		} else {
			param.put("param", json);
		}
		aq.ajax(context.getString(R.string.IP_HTTP) + Constants.WEB_SERVER_URL, param, JSONObject.class, this,
				callBackMethodName);
		// aq.ajax( Constants.WEB_SERVER_URL2+Constants.CRM_ADMINCLIENT_CODE,
		// param, JSONObject.class,this, callBackMethodName);
	}

	/**
	 * POST方式
	 * 
	 * @param contex
	 * @param serviceCode
	 * @param data
	 * @param callBack
	 */
	public String httpPost(String serviceCode, Map<String, ?> data, AjaxCallback<JSONObject> callback) {
		HashMap<String, Object> params = getPostParams(serviceCode);
		params.put("data", data);
		Gson gson = new Gson();
		String json = gson.toJson(params);
		HashMap<String, Object> param = new HashMap<String, Object>();
		if (Constants.isEncrypt) {
			// 公钥加密过程
			String cipher = null;
			try {
				cipher = RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(Boot.CLIENT_PUBLIC_KEY), json);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("原文：" + json);
			System.out.println("加密：" + cipher);
			String encode = "";
			try {
				encode = URLEncoder.encode(cipher, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			param.put("param", cipher); // json);
		} else {
			param.put("param", json);
		}
		aq.ajax(context.getString(R.string.IP_HTTP) + Constants.WEB_SERVER_URL, param, JSONObject.class, callback);
		return json;
	}

	/**
	 * POST方式
	 * 
	 * @param context
	 * @param serviceCode
	 * @param data
	 * @param callBack
	 */
	public void httpPost(String serviceCode, Map<String, ?> data) {
		httpPost(serviceCode, data, "callback");
	}

	/**
	 * GET方式
	 * 
	 * @param context
	 * @param url
	 */
	public void httpGet(Activity context, String url) {
		aq.ajax(url, String.class, new AjaxCallback<String>() {
			@Override
			public void callback(String url, String html, AjaxStatus status) {
				showToast("" + html);// http://192.168.62.52:8887/DISPATCH/Customer/rest/common/getApp?cusManagerNo=ycjadmin&passWord=111111
				System.out.println(html);
			}

		});
	}

	/**
	 * @param errorcode
	 *            ：status.getCode()
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
	}
}
