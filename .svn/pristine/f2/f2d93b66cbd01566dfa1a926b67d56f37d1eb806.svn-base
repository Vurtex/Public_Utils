package com.puhua.crm.common;

import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

import android.app.Application;
import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.puhua.crm.entity.ClientList;
import com.puhua.crm.entity.ClientList.consContent;
import com.puhua.crm.util.RSAUtil;
import com.puhua.crm.volley.SSLCertificateValidation;
import com.puhua.crm.volley.VolleySSLSocketFactory;

public class Boot {
	private Context context;
	private static Boot thiscontext;
	public static String CLIENT_PUBLIC_KEY;
	public static String CLIENT_PRIVATE_KEY;
	public static String SERVER_PUBLIC_KEY;
	public static ArrayList<consContent> arrayList;
	public static ArrayList<consContent> arrayList1;
	public static ArrayList<consContent> arrayList2;
	public static ArrayList<consContent> arrayList3;
	public Boot() {
		
	}
	public Boot(Context context) {
		arrayList = new ArrayList<ClientList.consContent>();
		arrayList1 = new ArrayList<ClientList.consContent>();
		arrayList2 = new ArrayList<ClientList.consContent>();
		arrayList3 = new ArrayList<ClientList.consContent>();
		this.context=context;
		thiscontext=this;
		RSAUtil rsautil=new RSAUtil(context);
		CLIENT_PRIVATE_KEY=rsautil.getPrivateKeyInfo("client.key.p12");
		SERVER_PUBLIC_KEY=rsautil.getPublicKeyInfo("server.cer");
		CLIENT_PUBLIC_KEY=RSAUtil.CLIENT_PUBLIC_KEY;
	}
	public static Boot getInstance() {
		if (null==thiscontext) {
			thiscontext=new Boot();
		}
		return thiscontext;
	}
	/**
	 * Global request queue for Volley
	 */
	private RequestQueue mRequestQueueWithSelfCertifiedSsl;

	private RequestQueue mRequestQueueWithDefaultSsl;

	private RequestQueue mRequestQueueWithHttp;

	public RequestQueue getRequestQueueWithHttp() {
		if (mRequestQueueWithHttp == null) {
			mRequestQueueWithHttp = Volley.newRequestQueue(context);
		}

		return mRequestQueueWithHttp;
	}

	public RequestQueue getRequestQueueWithDefaultSsl() {
		// lazy initialize the request queue, the queue instance will be
		// created when it is accessed for the first time
		if (mRequestQueueWithDefaultSsl == null) {

			Network network = new BasicNetwork(new HurlStack());

			Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);

			RequestQueue queue = new RequestQueue(cache, network);
			queue.start();

			mRequestQueueWithDefaultSsl = queue; // Volley.newRequestQueue(getApplicationContext());

			SSLCertificateValidation.trustAllCertificate();
		}

		return mRequestQueueWithDefaultSsl;
	}

	/**
	 * @return The Volley Request queue, the queue will be created if it is null
	 */
	public RequestQueue getRequestQueueWithSelfCertifiedSsl() {

		// if(mRequestQueueWithSelfCertifiedSsl == null){
		// SSLSocketFactory sslSocketFactory =
		// SelfSSLSocketFactory.getSSLSocketFactory(getApplicationContext());
		// Network network = new BasicNetwork(new
		// HurlStack(null,sslSocketFactory));
		// Cache cache = new DiskBasedCache(getCacheDir(),1024 * 1024) ;
		// mRequestQueueWithSelfCertifiedSsl = new RequestQueue(cache,network) ;
		// mRequestQueueWithSelfCertifiedSsl.start();
		//
		// HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier()
		// {
		// @Override
		// public boolean verify(String hostname, SSLSession session) {
		// // 当URL的主机名和服务器的标识主机名不匹配默认返回true
		// return true;
		// }
		// });
		// }

		// lazy initialize the request queue, the queue instance will be
		// created when it is accessed for the first time
		if (mRequestQueueWithSelfCertifiedSsl == null) {
			try {
				SSLSocketFactory sslSocketFactory = VolleySSLSocketFactory.getSSLSocketFactory(context);

				Network network = new BasicNetwork(new HurlStack(null, sslSocketFactory));

				Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);

				RequestQueue queue = new RequestQueue(cache, network);
				queue.start();

				mRequestQueueWithSelfCertifiedSsl = queue; // Volley.newRequestQueue(getApplicationContext());

			} catch (Exception e) {
				e.printStackTrace();
			}

			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String hostName, SSLSession ssls) {
					return true;
				}
			});
		}

		return mRequestQueueWithSelfCertifiedSsl;
	}
	
	
}
