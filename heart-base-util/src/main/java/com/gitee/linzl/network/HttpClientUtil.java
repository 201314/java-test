package com.gitee.linzl.network;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * httpclient 传输数据工具
 * 
 * @author liny
 * 
 *         2016年11月11日
 */
@Slf4j
public class HttpClientUtil {
	// 设置连接超时时间,单位毫秒
	private static final int connectionTimeout = 60000;
	// 设置读取超时\套接字超时时间,单位毫秒
	private static final int socketTimeout = 10000;
	// 设置从连接池获取连接实例的超时
	private static final int connectionRequestTimeout = 10000;
	// 设置每个主机的最大连接数
	private static final int maxConnectionsPerHost = 5;
	// 设置总共的最大连接数
	private static final int maxTotalConnections = 50;
	// 是否允许重连
	private static final boolean requestSentRetryEnabled = false;
	// 连接重试次数
	private static final int retryCount = 5;

	private static CloseableHttpClient httpClient = null;
	private static CloseableHttpResponse httpResponse = null;
	private static HttpPost httpPost = null;
	private static HttpGet httpGet = null;
	private static RequestConfig requestConfig = null;

	private static PoolingHttpClientConnectionManager connMgr;

	private static final ContentType TEXT_PLAIN_UTF8 = ContentType.create(ContentType.TEXT_PLAIN.getMimeType(),
			Consts.UTF_8);

	// 默认配置
	static {
		// 设置连接池
		connMgr = new PoolingHttpClientConnectionManager();
		// 设置连接池总共大小
		connMgr.setMaxTotal(200);
		// 第一个连接最大只能是200，所有连接的和加起来不能超过200
		connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
		// 关闭空闲两分钟的连接
		connMgr.closeIdleConnections(120, TimeUnit.SECONDS);

		RequestConfig.Builder builder = RequestConfig.custom();
		// 设置连接超时
		builder.setConnectTimeout(connectionTimeout);
		// 设置读取超时
		builder.setSocketTimeout(socketTimeout);
		// 设置从连接池获取连接实例的超时
		builder.setConnectionRequestTimeout(connectionRequestTimeout);
		// 在提交请求之前 测试连接是否可用
		// builder.setStaleConnectionCheckEnabled(true);
		requestConfig = builder.build();
	}

	/**
	 * 自定义http配置
	 * 
	 * @return
	 */
	public static void setRequestConfig(int connectionTimeOut, int socketTimeout) {
		// 初始化客户端连接参数
		Builder builder = RequestConfig.custom();
		// 设置请求超时时间
		builder.setConnectTimeout(connectionTimeOut);
		// 设置传输超时时间
		builder.setSocketTimeout(socketTimeout);
		requestConfig = builder.build();
	}

	private static HttpEntity getEntityResult() {
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity entity = httpResponse.getEntity();
			return entity;
		}
		return null;
	}

	private static String getResult() {
		String result = "";
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity entity = httpResponse.getEntity();
			try {
				if (entity != null) {
					result = EntityUtils.toString(entity, Consts.UTF_8);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 关闭http请求
	 */
	private static void closeHttp() {
		try {
			if (null != httpGet) {
				httpGet.releaseConnection();
			}
			if (null != httpPost) {
				httpPost.releaseConnection();
			}
			if (null != httpResponse) {
				httpResponse.close();
				if (null != httpResponse) {
					EntityUtils.consumeQuietly(httpResponse.getEntity());
				}
			}
			if (null != httpClient) {
				httpClient.close();
			}
		} catch (Exception e) {
			log.error("HttpClient 关闭资源出错 ", e);
		}
	}

	/**
	 * 使用get方法提交参数
	 * 
	 * @param url
	 *            提交到对应的url,url带参数
	 * @return
	 */
	public static String httpGet(String url) {
		String result = "";
		try {
			httpClient = HttpClients.createDefault();
			httpGet = new HttpGet(url);
			// httpGet.setConfig(requestConfig); 待解开
			httpResponse = httpClient.execute(httpGet);
			result = getResult();
		} catch (Exception e) {
			log.error("HttpClient get服务出错", e);
		} finally {
			closeHttp();
		}
		return result;
	}

	/**
	 * 使用get方法提交参数
	 * 
	 * @param url
	 *            提交到对应的url,url带参数
	 * @return
	 */
	public static String httpGet(String url, Map<String, String> params) {
		String result = "";
		try {
			httpClient = HttpClients.createDefault();
			StringBuilder builder = new StringBuilder(url);

			int wenhaoParam = builder.indexOf("?");
			if (params != null && params.size() > 0) {
				if (wenhaoParam < 0) {// 表示已经存在url参数
					builder.append("?");
				}
				for (String pKey : params.keySet()) {
					builder.append("&").append(pKey).append("=").append(params.get(pKey));
				}
			}
			url = builder.toString();
			url = url.replaceAll("\\?&", "?");
			url = url.replaceAll("&&", "&");

			httpGet = new HttpGet(url);
			// httpGet.setConfig(requestConfig);待解开

			httpResponse = httpClient.execute(httpGet);
			result = getResult();
		} catch (Exception e) {
			log.error("HttpClient get服务出错", e);
		} finally {
			closeHttp();
		}
		return result;
	}

	/**
	 * 下载文件
	 * 
	 * @param url
	 *            文件所在的url
	 * @param target
	 *            文件下载后存储位置
	 */
	public static void downLoad(String url, File target) {
		downLoad(url, target, null);
	}

	/**
	 * 下载文件
	 * 
	 * @param url
	 *            文件所在的url
	 * @param target
	 *            文件下载后存储位置
	 * @param headerParams
	 *            放在header的参数信息
	 */
	public static void downLoad(String url, File target, Map<String, String> headerParams) {
		httpClient = HttpClients.createDefault();

		OutputStream output = null;
		InputStream input = null;

		try {
			httpGet = new HttpGet(url);

			if (null != headerParams && headerParams.size() > 0) {
				Set<String> set = headerParams.keySet();
				Iterator<String> iter = set.iterator();
				while (iter.hasNext()) {
					String key = iter.next();
					httpGet.addHeader(key, headerParams.get(key));
				}
			}

			httpResponse = httpClient.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			input = entity.getContent();

			long length = entity.getContentLength();
			if (length <= 0) {
				log.error("下载文件不存在！");
				return;
			}

			output = new FileOutputStream(target);
			entity.writeTo(output);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(output);
			IOUtils.closeQuietly(input);
			closeHttp();
		}
	}

	/**
	 * 表单post提交
	 * 
	 * @param url
	 *            提交到对应的url
	 * @param params
	 *            表单参数
	 * @return
	 */
	public static String postForm(String url, Map<String, Object> params) {
		List<NameValuePair> pairList = new ArrayList<>(params.size());
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			NameValuePair pair = new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
			pairList.add(pair);
		}
		return postForm(url, pairList);
	}

	/**
	 * 表单post提交
	 * 
	 * @param url
	 *            提交到对应的url
	 * @param pairList
	 *            表单参数
	 * @param isSSL
	 *            true表示https提交
	 * @return
	 */
	public static String postForm(String url, List<NameValuePair> pairList) {
		httpClient = HttpClients.createDefault();

		String result = "";
		httpPost = new HttpPost(url);
		try {
			// httpPost.setConfig(requestConfig);待解开

			UrlEncodedFormEntity pramEntity = new UrlEncodedFormEntity(pairList, Consts.UTF_8);
			httpPost.setEntity(pramEntity);
			httpPost.setHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
			httpResponse = httpClient.execute(httpPost);

			result = getResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeHttp();
		}
		return result;
	}

	/**
	 * post发送json 请求
	 * 
	 * @param url
	 *            提交到对应的url
	 * @param json
	 *            需要post的json格式数据
	 * @return
	 */
	public static String postJson(String url, String json) {
		httpClient = HttpClients.createDefault();

		String result = "";
		try {
			httpPost = new HttpPost(url);
			// httpPost.setConfig(requestConfig);待解开

			StringEntity entity = new StringEntity(json, Consts.UTF_8);// 解决中文乱码问题
			httpPost.setEntity(entity);
			httpPost.setHeader("Content-type", ContentType.APPLICATION_JSON.toString());
			httpResponse = httpClient.execute(httpPost);

			result = getResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpResponse != null) {
				EntityUtils.consumeQuietly(httpResponse.getEntity());
			}
		}
		return result;
	}

	/**
	 * 上传文件
	 * 
	 * @param url
	 *            上传地址
	 * @param srcFiles
	 *            上传的文件
	 */
	public static String upload(String url, File... srcFiles) {
		return upload(url, srcFiles, null);
	}

	/**
	 * 上传文件
	 * 
	 * @param url
	 *            上传地址
	 * @param srcFiles
	 *            上传的文件
	 * @param params
	 *            上传所需要的参数
	 */
	public static String upload(String url, File[] srcFiles, Map<String, String> params) {
		String result = "";
		try {
			httpClient = HttpClients.createDefault();

			// 把一个普通参数和文件上传给下面这个地址 是一个servlet
			httpPost = new HttpPost(url);

			MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
			for (int index = 0, length = (srcFiles != null ? srcFiles.length : 0); index < length; index++) {
				entityBuilder.addBinaryBody("file" + index, srcFiles[index], ContentType.APPLICATION_FORM_URLENCODED,
						srcFiles[index] != null ? srcFiles[index].getName() : null);
			}

			if (null != params && params.size() > 0) {
				Set<String> set = params.keySet();
				Iterator<String> iter = set.iterator();
				while (iter.hasNext()) {
					String key = iter.next();
					entityBuilder.addTextBody(key, params.get(key), TEXT_PLAIN_UTF8);
				}
			}

			httpPost.setEntity(entityBuilder.build());

			// 发起请求 并返回请求的响应
			httpResponse = httpClient.execute(httpPost);

			// 获取响应对象
			result = getResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeHttp();
		}
		return result;
	}

	public static void main(String[] args) {
		// downLoad("https://www.baidu.com/img/bd_logo1.png", new
		// File("D://testDir//baidu.png"));
		String result = upload("http://localhost:28082/shop/res/upload", new File("D://testDir//baidu.png"));
		System.out.println("result==>" + result);
	}
}
