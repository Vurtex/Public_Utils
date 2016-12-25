package com.puhua.crm.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;
import com.ihep.RSAEncrypt;
import com.ihep.RSASignature;
import com.puhua.crm.R;
import com.puhua.crm.analytic.IDataRequestListener;
import com.puhua.crm.base.BaseActivity;
import com.puhua.crm.common.Boot;
import com.puhua.crm.common.Constants;
import com.puhua.crm.volley.VolleyDataRequester;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class HttpHelper {

	public AQuery aq;
	private Context context;

	public HttpHelper(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		aq = new AQuery(context);
	}
	 private void stringRequestPostHttpExample(String code,final String json,final int i,final IDataRequestListener listener){
	        VolleyDataRequester.withSelfCertifiedHttps( context )
	                .setUrl(context.getString(R.string.IP_HTTP)+ Constants.WEB_SERVER_URL)
//	                .setBody( getPostParamsCF(code) )
	                .setMethod( VolleyDataRequester.Method.POST )
	                .setStringResponseListener( new VolleyDataRequester.StringResponseListener() {
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
								//	showToast("请求数据异常");
									return;
								}
								doCheck = RSASignature.doCheck(json, sign,
										Boot.SERVER_PUBLIC_KEY);
							} else {
								doCheck = true;
							}
							if (doCheck) {
								if (i == 0) {
									if (js != null) {
										try {
											if (js.get("rtnCode").equals("1000")) {
												listener.loadSuccess(js);
											}
										} catch (JSONException e) {
											e.printStackTrace();
										}
									} 	
								} else {
									onMyResponse2(js);

								}
							} else{}
								//showToast("返回数据验签失败！");
						
	                     //   Toast.makeText( context, "HTTP/POST,StringRequest successfully.", Toast.LENGTH_SHORT ).show();
	                        Log.e("response------", response);
	                    }
	                    
	                } )
	                .requestString(json);
	    }

	/**
	 * https.
	 * 
	 * @param code
	 * @param data
	 * @return
	 */
	public String httpsPostString(String code, Map<String, String> data,
			final int i, final IDataRequestListener listener) {
		HashMap<String, Object> params = ((BaseActivity) context)
				.getPostParams(code);
		params.put("data", data);
		Gson gson = new Gson();
		final String json = gson.toJson(params);
		stringRequestPostHttpExample(code, json, i,listener);
		return json;
	}

	public void onMyResponse2(JSONObject json) {
	};


}
