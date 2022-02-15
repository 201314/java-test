package com.gitee.linzl.network;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

/**
 * httpclient 传输数据工具
 */
@Slf4j
public class HttpClientUtil {
    public static final int POOL_SIZE = 100;

    // 设置连接超时时间,单位毫秒
    public static final int CONNECT_TIMEOUT = 5000;

    // 设置读取超时\套接字超时时间,单位毫秒
    public static final int SOCKET_TIMEOUT = 30000;

    // 设置从连接池获取连接实例的超时
    public static final int CONNECTION_REQUEST_TIMEOUT = 3000;

    private final CloseableHttpClient httpClient;

    public static HttpClientUtil getInstance() {
        return new HttpClientUtil();
    }

    public HttpClientUtil() {
        this(POOL_SIZE, POOL_SIZE,
            CONNECT_TIMEOUT,
            SOCKET_TIMEOUT,
            CONNECTION_REQUEST_TIMEOUT);
    }

    public HttpClientUtil(Integer maxTotal, Integer defaultMaxPerRoute) {
        this(maxTotal, defaultMaxPerRoute,
            CONNECT_TIMEOUT,
            SOCKET_TIMEOUT,
            CONNECTION_REQUEST_TIMEOUT);
    }

    public HttpClientUtil(Integer maxTotal, Integer defaultMaxPerRoute, Integer connectTimeout,
                          Integer socketTimeout, Integer connectionRequestTimeout) {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext =
                (new SSLContextBuilder()).loadTrustMaterial(null, (chain, authType) -> true).build();
            // 信任所有
            sslsf = new SSLConnectionSocketFactory(sslContext, (s, sslSession) -> true);
        } catch (Exception e) {
        }

        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(
            RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslsf)
                .build()
        );
        // 设置连接池总共大小
        connMgr.setMaxTotal(maxTotal);
        // 第一个连接最大只能是defaultMaxPerRoute，所有连接的和加起来不能超过defaultMaxPerRoute
        connMgr.setDefaultMaxPerRoute(defaultMaxPerRoute);

        RequestConfig.Builder cfgBuilder = RequestConfig.custom();
        // 设置连接超时
        cfgBuilder.setConnectTimeout(connectTimeout);
        // 设置读取超时
        cfgBuilder.setSocketTimeout(socketTimeout);
        // 设置从连接池获取连接实例的超时
        cfgBuilder.setConnectionRequestTimeout(connectionRequestTimeout);

        RequestConfig requestConfig = cfgBuilder.build();

        httpClient = HttpClients.custom()
            .setConnectionManager(connMgr)
            .setDefaultRequestConfig(requestConfig)
            .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
            .build();
    }

    /**
     * 使用get方法提交参数
     *
     * @param url 提交到对应的url,url带参数
     * @return
     */
    public String httpGet(String url) {
        return httpGet(url, Collections.emptyMap());
    }

    /**
     * 使用get方法提交参数
     *
     * @param url 提交到对应的url,url带参数
     * @return
     */
    public String httpGet(String url, Map<String, String> params) {
        StringBuilder builder = new StringBuilder(url);

        if (MapUtils.isNotEmpty(params)) {
            int wenhaoParam = builder.indexOf("?");
            if (wenhaoParam < 0) {
                builder.append("?");
            }
            StringBuffer content = new StringBuffer();
            for (String pKey : params.keySet()) {
                content.append(pKey).append("=").append(params.get(pKey)).append("&");
            }
            url = builder.append(content).toString();
            url = url.replaceAll("\\?&", "?");
            url = url.replaceAll("&&", "&");
        }

        HttpGet httpGet = new HttpGet(url);
        // 自定义配置，如以上static{}中的参数
        // httpGet.setConfig(requestConfig);待解开

        try (CloseableHttpResponse clsResp = httpClient.execute(httpGet)) {
            if (clsResp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new RuntimeException(clsResp.getStatusLine().getStatusCode() + ":" +
                    clsResp.getStatusLine().getReasonPhrase());
            }
            return EntityUtils.toString(clsResp.getEntity(), Consts.UTF_8);
        } catch (Exception e) {
            log.error("HttpClient get服务出错", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 下载文件
     *
     * @param url    文件所在的url
     * @param target 文件下载后存储位置
     */
    public void downLoad(String url, File target) {
        downLoad(url, target, Collections.emptyMap());
    }

    /**
     * 下载文件
     *
     * @param url          文件所在的url
     * @param target       文件下载后存储位置
     * @param headerParams 放在header的参数信息
     */
    public void downLoad(String url, File target, Map<String, String> headerParams) {
        OutputStream output = null;
        InputStream input = null;

        HttpGet httpGet = new HttpGet(url);
        if (MapUtils.isNotEmpty(headerParams)) {
            headerParams.forEach((key, val) -> httpGet.addHeader(key, val));
        }

        try (CloseableHttpResponse clsResp = httpClient.execute(httpGet);) {
            HttpEntity entity = clsResp.getEntity();
            input = entity.getContent();

            long length = entity.getContentLength();
            if (length <= 0) {
                log.error("下载文件不存在！");
                return;
            }

            output = new FileOutputStream(target);
            entity.writeTo(output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(output);
            IOUtils.closeQuietly(input);
        }
    }

    /**
     * 表单post提交
     *
     * @param url    提交到对应的url
     * @param params 表单参数
     * @return
     */
    public String postForm(String url, Map<String, Object> params) {
        List<NameValuePair> pairList = new ArrayList<>(params.size());
        if (MapUtils.isNotEmpty(params)) {
            params.forEach((key, val) -> {
                NameValuePair pair = new BasicNameValuePair(key, String.valueOf(val));
                pairList.add(pair);
            });
        }
        return postForm(url, pairList);
    }

    /**
     * 表单post提交
     *
     * @param url      提交到对应的url
     * @param pairList 表单参数
     * @return
     */
    public String postForm(String url, List<NameValuePair> pairList) {
        HttpPost httpPost = new HttpPost(url);
        // httpPost.setConfig(requestConfig);待解开
        // 解决中文乱码问题
        HttpEntity pramEntity = EntityBuilder.create()
            .setContentType(ContentType.APPLICATION_FORM_URLENCODED.withCharset(Consts.UTF_8))
            .setParameters(pairList)
            .build();

        httpPost.setEntity(pramEntity);

        try (CloseableHttpResponse clsResp = httpClient.execute(httpPost)) {
            if (clsResp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new RuntimeException(clsResp.getStatusLine().getStatusCode() + ":" +
                    clsResp.getStatusLine().getReasonPhrase());
            }
            return EntityUtils.toString(clsResp.getEntity(), Consts.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * post发送json 请求
     *
     * @param url  提交到对应的url
     * @param json 需要post的json格式数据
     * @return
     */
    public String postJson(String url, String json) {
        HttpPost httpPost = new HttpPost(url);
        // httpPost.setConfig(requestConfig);待解开

        // 解决中文乱码问题
        HttpEntity strEntity = EntityBuilder.create()
            .setContentType(ContentType.APPLICATION_JSON)
            .setText(json)
            .build();
        httpPost.setEntity(strEntity);
        try (CloseableHttpResponse clsResp = httpClient.execute(httpPost)) {
            if (clsResp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new RuntimeException(clsResp.getStatusLine().getStatusCode() + ":" +
                    clsResp.getStatusLine().getReasonPhrase());
            }
            return EntityUtils.toString(clsResp.getEntity(), Consts.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 上传文件
     *
     * @param url      上传地址
     * @param srcFiles 上传的文件
     */
    public String upload(String url, File... srcFiles) {
        return upload(url, srcFiles, null);
    }

    /**
     * 上传文件
     *
     * @param url      上传地址
     * @param srcFiles 上传的文件
     * @param params   上传所需要的参数
     */
    public String upload(String url, File[] srcFiles, Map<String, String> params) {
        // 把一个普通参数和文件上传给下面这个地址 是一个servlet
        HttpPost httpPost = new HttpPost(url);

        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        for (int index = 0, length = (srcFiles != null ? srcFiles.length : 0); index < length; index++) {
            entityBuilder.addBinaryBody("file" + index, srcFiles[index], ContentType.APPLICATION_FORM_URLENCODED,
                srcFiles[index] != null ? srcFiles[index].getName() : null);
        }

        if (MapUtils.isNotEmpty(params)) {
            params.forEach((key, val) -> entityBuilder.addTextBody(key, val, ContentType.TEXT_PLAIN.withCharset(Consts.UTF_8)));
        }

        httpPost.setEntity(entityBuilder.build());

        try (CloseableHttpResponse clsResp = httpClient.execute(httpPost)) {
            if (clsResp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new RuntimeException(clsResp.getStatusLine().getStatusCode() + ":" +
                    clsResp.getStatusLine().getReasonPhrase());
            }
            return EntityUtils.toString(clsResp.getEntity(), Consts.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
