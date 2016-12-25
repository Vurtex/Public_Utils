package com.puhua.crm.volley;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;


public class MySSLSocketFactory extends SSLSocketFactory{  
    
	   private SSLSocketFactory internalSSLSocketFactory;

	   private SSLContext context;
	    public MySSLSocketFactory(KeyManager[] km, TrustManager[] tm, SecureRandom sr) throws KeyManagementException, NoSuchAlgorithmException {
	    	context = SSLContext.getInstance("TLSv1.2");
	    	context.init(km, tm, sr);
	        internalSSLSocketFactory = context.getSocketFactory();
	    }

	    @Override
	    public String[] getDefaultCipherSuites() {
	        return internalSSLSocketFactory.getDefaultCipherSuites();
	    }

	    @Override
	    public String[] getSupportedCipherSuites() {
	        return internalSSLSocketFactory.getSupportedCipherSuites();
	    }

	    @Override
	    public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
	    	 SSLSocket sslSocket=(SSLSocket) context.getSocketFactory().createSocket(s, host, port, autoClose);  
	         sslSocket.setEnabledProtocols(new String[]{"TLSv1.2"});  
	         return sslSocket;  
//	        return enableTLSOnSocket(internalSSLSocketFactory.createSocket(s, host, port, autoClose));
	    }

	    @Override
	    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
	    	 SSLSocket sslSocket=(SSLSocket) context.getSocketFactory().createSocket(host,port);  
	         sslSocket.setEnabledProtocols(new String[]{"TLSv1.2"});  
	         return sslSocket;  
//	        return enableTLSOnSocket(internalSSLSocketFactory.createSocket(host, port));
	    }

	    @Override
	    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
	    	 SSLSocket sslSocket=(SSLSocket) context.getSocketFactory().createSocket(host, port, localHost, localPort);  
	         sslSocket.setEnabledProtocols(new String[]{"TLSv1.2"});  
	         return sslSocket;  
//	    	return enableTLSOnSocket(internalSSLSocketFactory.createSocket(host, port, localHost, localPort));
	    }

	    @Override
	    public Socket createSocket(InetAddress host, int port) throws IOException {
	    	 SSLSocket sslSocket=(SSLSocket) context.getSocketFactory().createSocket(host, port);  
	         sslSocket.setEnabledProtocols(new String[]{"TLSv1.2"});  
	         return sslSocket;  
//	        return enableTLSOnSocket(internalSSLSocketFactory.createSocket(host, port));
	    }

	    @Override
	    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
	    	 SSLSocket sslSocket=(SSLSocket) context.getSocketFactory().createSocket(address, port, localAddress, localPort);  
	         sslSocket.setEnabledProtocols(new String[]{"TLSv1.2"});  
	         return sslSocket;  
//	    	return enableTLSOnSocket(internalSSLSocketFactory.createSocket(address, port, localAddress, localPort));
	    }

	    private Socket enableTLSOnSocket(Socket socket) {
	        if(socket instanceof SSLSocket) {
	            ArrayList<String> supportedProtocols =
	                    new ArrayList<>(Arrays.asList(((SSLSocket) socket).getSupportedProtocols()));
	            supportedProtocols.retainAll(Arrays.asList("TLSv1.2"));

	            ((SSLSocket)socket).setEnabledProtocols(supportedProtocols.toArray(
	                    new String[supportedProtocols.size()]));
	        }
	        return socket;
	    }
}