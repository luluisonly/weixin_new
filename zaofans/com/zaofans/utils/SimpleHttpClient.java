package com.zaofans.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import java.util.Set;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.bokesoft.myerp.common.DebugUtil;

public class SimpleHttpClient {

	public static InputStream invokeGet4Stream(String url) throws IOException {
		return invokeGet4Stream(url, null);
	}
	public static InputStream invokeGet4Stream(String url,
			Map<String, String> headers) throws IOException {
		DebugUtil.debug("invokeGet4Stream.url="+url);
		HttpClient hc = new HttpClient();
		GetMethod get = new GetMethod(url);
		if (headers != null) {
			Set<Entry<String, String>> entrys = headers.entrySet();
			for (Entry<String, String> entry : entrys) {
				get.addRequestHeader(entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
		try {
			hc.executeMethod(get);
			return get.getResponseBodyAsStream();
		} finally {
			get.releaseConnection();
		}
	}
	public static String getRedirectURL(String url) throws IOException {
		return getRedirectURL(url, null);
	}
	public static String getRedirectURL(String url, Map<String, String> headers)
			throws IOException {
		DebugUtil.debug("getRedirectURL.url="+url);
		HttpClient hc = new HttpClient();
		GetMethod get = new GetMethod(url);
		get.setFollowRedirects(false);
		if (headers != null) {
			Set<Entry<String, String>> entrys = headers.entrySet();
			for (Entry<String, String> entry : entrys) {
				get.addRequestHeader(entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
		try {
			hc.executeMethod(get);
			Header[] resHeaders = get.getResponseHeaders();
			for (Header header : resHeaders) {
				if ("Location".equals(header.getName())) {
					return header.getValue();
				}
			}
			return null;
		} finally {
			get.releaseConnection();
		}
	}
	public static String invokeGet4String(String url) throws IOException {
		return invokeGet4String(url, null);
	}
	public static String invokeGet4String(String url,
			Map<String, String> headers) throws IOException {
		DebugUtil.debug("invokeGet4String.url="+url);
		HttpClient hc = new HttpClient();
		GetMethod get = new GetMethod(url);
		get.getParams().setContentCharset("UTF-8");
		if (headers != null) {
			Set<Entry<String, String>> entrys = headers.entrySet();
			for (Entry<String, String> entry : entrys) {
				get.addRequestHeader(entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
		try {
			hc.executeMethod(get);
			return new String(get.getResponseBody(), "UTF-8");
		} finally {
			get.releaseConnection();
		}
	}
	public static InputStream invokePost4Stream(String url,
			Map<String, String> params) throws IOException {
		return invokePost4Stream(url, params, null);
	}
	public static InputStream invokePost4Stream(String url,
			Map<String, String> params, Map<String, String> headers)
			throws IOException {
		DebugUtil.debug("invokePost4Stream.url="+url);
		HttpClient hc = new HttpClient();
		PostMethod post = new PostMethod(url);
		post.getParams().setContentCharset("UTF-8");
		if (headers != null) {
			Set<Entry<String, String>> entrys = headers.entrySet();
			for (Entry<String, String> entry : entrys) {
				post.addRequestHeader(entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
		if (params != null) {
			Set<Entry<String, String>> entrys = params.entrySet();
			for (Entry<String, String> entry : entrys) {
				post.addParameter(entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
		try {
			hc.executeMethod(post);
			return post.getResponseBodyAsStream();
		} finally {
			post.releaseConnection();
		}
	}
	public static String invokePost4String(String url,
			Map<String, String> params) throws IOException {
		return invokePost4String(url, params, null);
	}
	public static String invokePost4String(String url,
			Map<String, String> params, Map<String, String> headers)
			throws IOException {
		DebugUtil.debug("invokePost4String.url="+url);
		HttpClient hc = new HttpClient();
		PostMethod post = new PostMethod(url);
		post.getParams().setContentCharset("UTF-8");
		if (headers != null) {
			Set<Entry<String, String>> entrys = headers.entrySet();
			for (Entry<String, String> entry : entrys) {
				post.addRequestHeader(entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
		if (params != null) {
			Set<Entry<String, String>> entrys = params.entrySet();
			for (Entry<String, String> entry : entrys) {
				post.addParameter(entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
		try {
			hc.executeMethod(post);
			return new String(post.getResponseBody(), "UTF-8");
		} finally {
			post.releaseConnection();
		}
	}
	public static InputStream invokePostWithBody4Stream(String url,
			InputStream inputStream) throws IOException {
		return invokePostWithBody4Stream(url, inputStream, null);
	}
	public static InputStream invokePostWithBody4Stream(String url,
			InputStream inputStream, Map<String, String> headers)
			throws IOException {
		DebugUtil.debug("invokePostWithBody4Stream.url="+url);
		HttpClient hc = new HttpClient();
		PostMethod post = new PostMethod(url);
		post.getParams().setContentCharset("UTF-8");
		if (headers != null) {
			Set<Entry<String, String>> entrys = headers.entrySet();
			for (Entry<String, String> entry : entrys) {
				post.addRequestHeader(entry.getKey(),
						String.valueOf(entry.getValue()));
			}
		}
		RequestEntity requestEntity = new InputStreamRequestEntity(inputStream);
		post.setRequestEntity(requestEntity);
		try {
			hc.executeMethod(post);
			return post.getResponseBodyAsStream();
		} finally {
			post.releaseConnection();
		}
	}
	public static InputStream invokePostWithBody4Stream(String url, String body)
			throws IOException {
		return invokePostWithBody4Stream(url, body, null);
	}
	public static InputStream invokePostWithBody4Stream(String url,
			String body, Map<String, String> headers) throws IOException {
		DebugUtil.debug("invokePostWithBody4Stream.url="+url);
		HttpClient hc = new HttpClient();
		PostMethod post = new PostMethod(url);
		post.getParams().setContentCharset("UTF-8");
		if (headers != null) {
			Set<Entry<String, String>> entrys = headers.entrySet();
			for (Entry<String, String> entry : entrys) {
				post.addRequestHeader(entry.getKey(),
						String.valueOf(entry.getValue()));
			}
		}
		RequestEntity requestEntity = new ByteArrayRequestEntity(body.getBytes("UTF-8"));
		post.setRequestEntity(requestEntity);
		try {
			hc.executeMethod(post);
			return post.getResponseBodyAsStream();
		} finally {
			post.releaseConnection();
		}
	}
	public static String invokePostWithBody4String(String url,
			InputStream inputStream) throws IOException {
		return invokePostWithBody4String(url, inputStream, null);
	}
	public static String invokePostWithBody4String(String url,
			InputStream inputStream, Map<String, String> headers)
			throws IOException {
		DebugUtil.debug("invokePostWithBody4String.url="+url);
		HttpClient hc = new HttpClient();
		PostMethod post = new PostMethod(url);
		post.getParams().setContentCharset("UTF-8");
		if (headers != null) {
			Set<Entry<String, String>> entrys = headers.entrySet();
			for (Entry<String, String> entry : entrys) {
				post.addRequestHeader(entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
		RequestEntity requestEntity = new InputStreamRequestEntity(inputStream);
		post.setRequestEntity(requestEntity);
		try {
			hc.executeMethod(post);
			return new String(post.getResponseBody(), "UTF-8");
		} finally {
			post.releaseConnection();
		}
	}
	public static String invokePostWithBody4String(String url, String body)
			throws IOException {
		return invokePostWithBody4String(url, body, null);
	}
	public static String invokePostWithBody4String(String url, String body,
			Map<String, String> headers) throws IOException {
		DebugUtil.debug("invokePostWithBody4String.url="+url);
		HttpClient hc = new HttpClient();
		PostMethod post = new PostMethod(url);
		if (headers != null) {
			Set<Entry<String, String>> entrys = headers.entrySet();
			for (Entry<String, String> entry : entrys) {
				post.addRequestHeader(entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
		post.getParams().setContentCharset("UTF-8");
		RequestEntity requestEntity = new ByteArrayRequestEntity(body.getBytes("UTF-8"));
		post.setRequestEntity(requestEntity);
		try {
			hc.executeMethod(post);
			return new String(post.getResponseBody(), "UTF-8");
		} finally {
			post.releaseConnection();
		}
	}
	public static void main(String[] args) throws IOException {
		String response = SimpleHttpClient
				.invokePost4String(
						"Http://localhost:8300/yigo/weixin?signature=www&timestamp=www&nonce=www&echostr=wwww",
						null, null);
		System.out.println(response);

	}
	public static String invokePostSSLWithBody4String(String url,String mchId,String cer,String body,int timeout) throws Exception {
		 KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(cer));
        try {
            keyStore.load(instream, mchId.toCharArray());
        } finally {
            instream.close();
        }
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, mchId.toCharArray())
                .build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
       RequestConfig config = RequestConfig.custom()
       	    .setConnectionRequestTimeout(timeout).setConnectTimeout(timeout)
       	    .setSocketTimeout(timeout).build();
       CloseableHttpResponse response = null;
       HttpPost post = new HttpPost(url);
       HttpEntity requestEntity = new StringEntity(body,"UTF-8");
       post.setEntity(requestEntity);
       post.setConfig(config);
		try {
			response = httpclient.execute(post);
		    return EntityUtils.toString(response.getEntity());
		} finally {
			response.close();
		}
	}
}
