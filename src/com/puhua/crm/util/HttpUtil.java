package com.puhua.crm.util;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


public class HttpUtil {

    private HashMap<String, Object> map;
    private String strUrl;
    private String strMethod;
    private HttpGet httpGet;
    private HttpClient client;
    private HttpPost post;
    private Handler handler;
    public HttpUtil(HashMap<String, Object> param,String strUrl, Handler handler)
    {
        this.map = param;
        this.strUrl = strUrl;
        this.handler = handler;
    }
    
    
    public void ExecuteGetRequest()
    {
        
        Thread thread = new Thread(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    String result = "";
                    String params = "";
                    Iterator iterator = map.entrySet().iterator();
                    while(iterator.hasNext())
                    {
                        Map.Entry entry = (Map.Entry)iterator.next();
                        String key = entry.getKey().toString();
                        String value = entry.getValue().toString();
                        String patam = key+"="+value+"&";
                        params += patam;
                    }
                    params= params.substring(0, params.length() - 1);
//            		// 公钥加密过程
//            		String cipher = "";
//            		try {
//            			cipher = RSAUtils.encryptByPublicKey(params, RSAEncrypt.loadPublicKeyByStr(CrmApplication.publicKey));
//            		} catch (Exception e) {
//            			// TODO Auto-generated catch block
//            			e.printStackTrace();
//            		}
                    strUrl +=params;//cipher;
                    httpGet = new HttpGet(strUrl);
                    client = new DefaultHttpClient();
                    HttpResponse response = client.execute(httpGet);
                    if(response.getStatusLine().getStatusCode()==200){
                        result = EntityUtils.toString(response.getEntity());
                        } 
                    Message msg = new Message();
                    msg.obj = result;
                    handler.sendMessage(msg);
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
    
    public void ExecutePostRequest()
    {
        Thread thread = new Thread(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                String result = "";
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                try {
                    post = new HttpPost(strUrl);
                    client = new DefaultHttpClient();
                    Iterator iterator = map.entrySet().iterator();
                    while(iterator.hasNext())
                    {
                        Map.Entry entry = (Map.Entry)iterator.next();
                        String key = entry.getKey().toString();
                        String value = entry.getValue().toString();
                        NameValuePair nv = new BasicNameValuePair(key, value);
                        list.add(nv);
                    }
                    post.setEntity(new UrlEncodedFormEntity(list,HTTP.UTF_8));
                    HttpResponse response = client.execute(post);
                    if(response.getStatusLine().getStatusCode() == 200)
                    {
                        result = EntityUtils.toString(response.getEntity());
                    }
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("result", result);
					msg.setData(bundle);
                    
                    handler.sendMessage(msg);
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        thread.start();
        
    }
}