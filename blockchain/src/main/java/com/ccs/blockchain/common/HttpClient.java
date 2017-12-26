package com.ccs.blockchain.common;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClient {

    private static Logger logger = LoggerFactory.getLogger(HttpClient.class);

    private HttpClient(){

    }
    /**
     * HttpClient连接SSL
     */
    public static void ssl() {
        CloseableHttpClient httpclient = null;
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            FileInputStream instream = new FileInputStream(new File("d:\\tomcat.keystore"));
            try {
                // 加载keyStore d:\\tomcat.keystore    
                trustStore.load(instream, "123456".toCharArray());
            } catch (CertificateException e) {
                e.printStackTrace();
            } finally {
                try {
                    instream.close();
                } catch (Exception ignore) {
                }
            }
            // 相信自己的CA和所有自签名的证书  
            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
            // 只允许使用TLSv1协议  
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            // 创建http请求(get方式)  
            HttpGet httpget = new HttpGet("https://localhost:8443/myDemo/Ajax/serivceJ.action");
            logger.debug("executing request" + httpget.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                HttpEntity entity = response.getEntity();
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    logger.debug("Response content length: " + entity.getContentLength());
                    logger.debug(EntityUtils.toString(entity));
                    EntityUtils.consume(entity);
                }
            } finally {
                response.close();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } finally {
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String postForm(Map<String, String> parameterMap, String url) {
        return postForm(parameterMap, url);
    }

    /**
     * post方式提交表单（模拟用户登录请求）
     */
    public static String postForm(Map<String, String> parameterMap, Map<String, String> headerMap, String url) {
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost    
        HttpPost httppost = new HttpPost(url);
        // 创建参数队列    
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        if (parameterMap != null) {
            for (String key : parameterMap.keySet()) {
                formparams.add(new BasicNameValuePair(key, parameterMap.get(key)));
            }
        }
        if (headerMap != null) {
            for (String key : headerMap.keySet()) {
                if (key != null && headerMap.get(key) != null) {
                    httppost.setHeader(new BasicHeader(key, headerMap.get(key)));
                }
            }
        }

        UrlEncodedFormEntity uefEntity;
        String result = null;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            logger.debug("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity, "UTF-8");
                    logger.debug("--------------------------------------");
                    logger.info("Response content: " + result);
                    logger.debug("--------------------------------------");
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源    
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     */
    public static String post(Map<String, String> parameterMap, Map<String, String> headerMap, String url) {
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost    
        HttpPost httppost = new HttpPost(url);
        // 创建参数队列    
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        for (String key : parameterMap.keySet()) {
            formparams.add(new BasicNameValuePair(key, parameterMap.get(key)));
        }
        for (String key : headerMap.keySet()) {
            if (key != null && headerMap.get(key) != null) {
                httppost.setHeader(new BasicHeader(key, headerMap.get(key)));
            }
        }
        String result = null;
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            uefEntity.setContentEncoding("UTF-8");
            uefEntity.setContentType("application/json");
            httppost.setEntity(uefEntity);
            logger.debug("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity, "UTF-8");
                    logger.debug("--------------------------------------");
                    logger.info("Response content: " + EntityUtils.toString(entity, "UTF-8"));
                    logger.debug("--------------------------------------");
                }
            } finally {
                response.close();
            }
        } catch (IOException e) {
            logger.error("连接io失败错误", e);
        } finally {
            // 关闭连接,释放资源    
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 请求webService服务的方法
     * @param xmlStr
     * @param headerMap
     * @param url
     * @return
     */
    public static String postWebService(String xmlStr, Map<String, String> headerMap, String url) {
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        for (String key : headerMap.keySet()) {
            if (key != null && headerMap.get(key) != null) {
                httppost.setHeader(new BasicHeader(key, headerMap.get(key)));
            }
        }
        String result = null;
        try {
            StringEntity xmlEntity = new StringEntity(xmlStr);
            httppost.setEntity(xmlEntity);
            logger.debug("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity, "UTF-8");
                    logger.debug("--------------------------------------");
                    logger.info("Response content: " + result);
                    logger.debug("--------------------------------------");
                }
            } finally {
                response.close();
            }
        } catch (IOException e) {
            logger.error("连接io失败错误", e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void getJsonHttpPost(String requestUrl, String values) {
        //根据URL，生成HttpPost对象：还未添加请求参数
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(requestUrl);

        //给httpPost添加http请求头部：包含Content-Type、Accept、source、COOKIE信息
//        configHeader(httpPost);
        String jsonString = values;
        //将该字符串设置为HttpEntity，并设置编码方式
        StringEntity s = new StringEntity(jsonString, "UTF-8");
        s.setContentEncoding("UTF-8");
        s.setContentType("application/json");
        //发送json数据需要设置contentType
        httpPost.setEntity(s);
        HttpResponse res = null;
        try {
            res = httpclient.execute(httpPost);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                HttpEntity entity = res.getEntity();
                String result = EntityUtils.toString(res.getEntity());
                // 返回json格式
                System.out.println(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 发送 get请求
     */
    public static String get(String url, Map<String, String> parameterMap, Map<String, String> headerMap) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String result = null;
        CloseableHttpResponse response = null;
        //封装请求参数
        List<NameValuePair> params = new ArrayList<>();
        if(parameterMap != null) {
            for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        String paramStr = "";
        try {
            paramStr = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
            // 创建httpget.    
            HttpGet httpget = new HttpGet(url + "?" + paramStr);
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                httpget.setHeader(entry.getKey(), entry.getValue());
            }
            logger.info("request url = {},param = {},headermap = {}", url, parameterMap.toString(), headerMap.toString());
            // 执行get请求.
            response = httpclient.execute(httpget);
            // 获取响应实体
            HttpEntity entity = response.getEntity();
            // 打印响应状态
            logger.info("response status :" + response.getStatusLine());
            if (entity != null) {
                result = EntityUtils.toString(entity);
                // 打印响应内容
                logger.info("Response content: " + result);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源    
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String post(String body,String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader(new BasicHeader("Content-Type","application/json; charset=utf-8"));

        String result = null;
        try {
            //StringEntity stringEntity = new StringEntity("{\"header\":{\"username\":\"北京尚佳崇业教育有限公司\",\"password\":\"Sunland2017\",\"token\":\"b60fe899-1927-440c-b056-7050e35c415a\"},\"body\":{\"requestData\":[\"account_all\"]}}}", Charset.forName("utf-8"));
            StringEntity stringEntity = new StringEntity(body, Charset.forName("utf-8"));
            httppost.setEntity(stringEntity);
            System.out.println(stringEntity.toString());
            System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity, "UTF-8");
                    logger.debug("--------------------------------------");
                    logger.debug("Response content: " + result) ;
                    logger.debug("--------------------------------------");
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}