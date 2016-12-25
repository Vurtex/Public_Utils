package com.puhua.crm.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.ihep.RSAEncrypt;
import com.puhua.crm.common.Boot;
import com.puhua.crm.common.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class VolleyDataRequester {

    public static final String TAG = "VolleyDataRequester";

    private enum Type {
        HTTP,
        HTTPS_DEFALT,
        HTTPS_SELF_CERTIFIED
    }

    public enum Method {
        GET,
        POST
    }

    public interface StringResponseListener extends Response.Listener<String> {

    }

    public interface ResponseErrorListener extends Response.ErrorListener {

    }

    public interface JsonResponseListener extends Response.Listener<JSONObject> {

    }

    public interface JsonArrayResponseListener extends Response.Listener<JSONArray> {

    }

    private StringResponseListener mStringResponseListener;

    private JsonResponseListener mJsonResponseListener;

    private JsonArrayResponseListener mJsonArrayResponseListener;

    private ResponseErrorListener mResponseErrorListener;

    private RequestQueue mRequestQueue;

    private Context mContext;

    private String url;

    private Method method;

    private JSONObject jsonBody;

    private Map<String,String> mapBody;

    private Map<String,String> headers;

    private String strBody;

    public VolleyDataRequester(Context ctx, Type type) {
        if (type == Type.HTTP) {
            mRequestQueue = ((Boot) Boot.getInstance()).getRequestQueueWithHttp();
        }

        if (type == Type.HTTPS_DEFALT) {
            mRequestQueue = ((Boot) Boot.getInstance()).getRequestQueueWithDefaultSsl();
        }

        if (type == Type.HTTPS_SELF_CERTIFIED) {
            mRequestQueue =((Boot) Boot.getInstance()).getRequestQueueWithSelfCertifiedSsl();
        }

        mContext = ctx;
    }

    public static VolleyDataRequester withHttp(Context ctx) {
        return new VolleyDataRequester( ctx, Type.HTTP );
    }

    public static VolleyDataRequester withDefaultHttps(Context ctx) {
        return new VolleyDataRequester( ctx, Type.HTTPS_DEFALT );
    }

    public static VolleyDataRequester withSelfCertifiedHttps(Context ctx) {
        return new VolleyDataRequester( ctx, Type.HTTPS_SELF_CERTIFIED );
    }

    public VolleyDataRequester setMethod(Method method) {
        this.method = method;
        return this;
    }

    public VolleyDataRequester setUrl(String url) {
        this.url = url;
        return this;
    }

    public VolleyDataRequester setBody(JSONObject body) {
        this.jsonBody = body;
        return this;
    }

    public VolleyDataRequester setBody(String body) {
        this.strBody = body;
        return this;
    }

    public VolleyDataRequester setBody (Map<String,String> stringRequestParam){
        this.mapBody = stringRequestParam;
        return this;
    }

    public VolleyDataRequester setStringResponseListener(StringResponseListener mStringResponseListener) {
        this.mStringResponseListener = mStringResponseListener;
        return this;
    }

    public VolleyDataRequester setJsonResponseListener(JsonResponseListener mJsonResponseListener) {
        this.mJsonResponseListener = mJsonResponseListener;
        return this;
    }

    public VolleyDataRequester setResponseErrorListener(ResponseErrorListener mResponseErrorListener) {
        this.mResponseErrorListener = mResponseErrorListener;
        return this;
    }

    public VolleyDataRequester setJsonArrayResponseListener (JsonArrayResponseListener mJsonArrayResponseListener){
        this.mJsonArrayResponseListener = mJsonArrayResponseListener;
        return this;
    }

    public void requestString(final String json) {
        StringRequest request = null;
        if (Method.GET == method) {
            request = new StringRequest(
                    Request.Method.GET,
                    url,
                    mStringResponseListener,
                    mResponseErrorListener );
            request.setRetryPolicy(new DefaultRetryPolicy(30*1000, 1, 1.0f));

        }

        if (Method.POST == method) {
            request = new StringRequest(
                    Request.Method.POST,
                    url,
                    mStringResponseListener,
                    mResponseErrorListener ) {
                @Override
                protected Map<String, String> getParams(){

    				Map<String, String> param = new HashMap<String, String>();
    				System.out
    						.println("--------------公钥加密私钥解密过程-------------------");
    				if (Constants.isEncrypt) {
    					// 公钥加密过程
    					String cipher = null;
    					try {
    						cipher = RSAEncrypt.encrypt(RSAEncrypt
    								.loadPublicKeyByStr(Boot.SERVER_PUBLIC_KEY),
    								json);
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
    					param.put("param", cipher + ""); // json);
    				} else {
    					param.put("param", json + "");
    				}
    				return param;
    			
//                    return mapBody;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
//Headers like below.
//                    Map<String,String> params = new HashMap<String, String>();
//                    params.put("Content-Type","application/x-www-form-urlencoded");
                    if (headers != null){
                        return headers;
                    }else {
                        return super.getHeaders();
                    }
                }
            };
        }
        request.setRetryPolicy(new DefaultRetryPolicy(30*1000, 1, 1.0f));
        mRequestQueue.add( request );
    }

    public void requestJson() {
        JsonObjectRequest request = new JsonObjectRequest( url,
                jsonBody,
                mJsonResponseListener,
                mResponseErrorListener );

        if (jsonBody != null) {
            Log.d( TAG, request.getBody().toString() );
        }

        mRequestQueue.add( request );
    }

    public void requestJsonArray() {
        JsonArrayRequest request = new JsonArrayRequest (url,
                mJsonArrayResponseListener,
                mResponseErrorListener );

        mRequestQueue.add( request );
    }
}
