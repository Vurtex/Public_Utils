package com.puhua.crm.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import android.content.Context;

import com.ihep.Base64;
import com.puhua.crm.volley.CertificateConfig;

public class RSAUtil {
	private Context context;
	public static String CLIENT_PUBLIC_KEY;
	
	public RSAUtil(Context context) {
		this.context=context;
		
	}
	/** 这个方法所用到的文件目录是assets
	 * @param fileName  assets目录下的文件名 若有二级目录带上文件夹名字
	 * @return privateKey
	 */
	public String getPrivateKeyInfo(String fileName) {
		String privateKey = "";
		try {
			// Create a keystore object   
			KeyStore keyStore = KeyStore.getInstance(CertificateConfig.KEY_STORE_TYPE_P12);
			// Load the file into the keystore   
			keyStore.load(context.getAssets().open(fileName), CertificateConfig.keyStorePassword.toCharArray());
			Key key = keyStore.getKey(CertificateConfig.alias, CertificateConfig.keyStorePassword.toCharArray());
			PrivateKey priKey = (PrivateKey) (key);
			if (priKey != null) {
				privateKey = Base64.base64EncodeOrgin(priKey.getEncoded());
				System.out.println("客户端私钥："+privateKey);
			}
  
			// public key   
			Certificate crt = keyStore.getCertificate(CertificateConfig.alias);
			PublicKey publicKey = crt.getPublicKey();
			if (publicKey != null)
				CLIENT_PUBLIC_KEY=Base64.base64EncodeOrgin(publicKey.getEncoded());
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return privateKey;
	}
	
	/** 这个方法所用到的文件目录是assets
	 * @param fileName  assets目录下的文件名 若有二级目录带上文件夹名字
	 * @return publicKey
	 */
	public String getPublicKeyInfo(String fileName) {
		String PUBLIC_KEY = "";
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			Certificate cert = cf.generateCertificate(context.getAssets().open(fileName));
			PublicKey publicKeyFromcrt = cert.getPublicKey();
			if (publicKeyFromcrt != null){
				PUBLIC_KEY=Base64.base64EncodeOrgin(publicKeyFromcrt.getEncoded());
			}
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return PUBLIC_KEY;
	}
}
