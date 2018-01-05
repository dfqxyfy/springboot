package com.ccs.blockchain.demo;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@Controller
public class BaiduSemController {

    static final String DATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @RequestMapping("test")
    public String dividends(String addr){
        final SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String utcTime = sdf.format(new Date());
        System.out.println(utcTime);
        sampleOfNormalInterface(utcTime);
        return "success;";
    }

    private static Map<String, String> createHeaders(String utcTime) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("x-bce-date", utcTime);
        headers.put("host", "sem.baidubce.com");
        return headers;
    }

    private static void sampleOfNormalInterface(String utcTime) {
        Signer signer = new Signer();
        signer.withAccessKey("57a63219e2c14c60990af9304ee80ccb")
                .withSecretKey("2825a8cfcf4f44c29744b718dcb08117")
                .withTimestamp(utcTime)
                .withMethod("POST")
                .withVersion("1")
                .withExpire(1800)
                .withUri("/v1/cloud/PreviewService/getPreview")
                .withSignHeaders(createHeaders(utcTime)); // 只传递需要签名的header
        String authorization = "";
        try {
            String signature = signer.genSignature();
            System.out.println(signature);
        } catch (SignerException e) {
            System.out.println(e.getMessage());
        }
        try {
            authorization = signer.genAuthorization();
            System.out.println(authorization);
        } catch (SignerException e) {
            System.out.println(e.getMessage());
        }



        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("http://sem.baidubce.com/v1/feed/cloud/AccountFeedService/getAccountFeed");
        httppost.addHeader("Host", "sem.baidubce.com");
        httppost.addHeader("Content-Type", "application/json");
        httppost.addHeader("x-bce-date", utcTime);
        httppost.addHeader("authorization", authorization);
        StringEntity strEntity = new StringEntity(
                "{\"body\":{\"accountFeedFields\":[\"userId\"]},\"header\":{\"opUsername\":\"尚德机构2016新\",\"opPassword\":\"17170shangDJ\", \"tgSubname\": \"尚德126\", \"bceUser\": \"02f4d1734fd979881a009568da2a504c\"}}",
                Consts.UTF_8);
        httppost.setEntity(strEntity);

        String result = null;
        try {
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity, "UTF-8");
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

        System.out.println(result);


        /*
        CloseableHttpClient httpClient =  new DefaultHttpClient();
        try {

            // Creating an instance of HttpPost.
            HttpPost httpost = new HttpPost("http://sem.baidubce.com/v1/cloud/PreviewService/getPreview");

            httpost.addHeader("Host", "sem.baidubce.com");
            httpost.addHeader("Content-Type", "application/json");
            httpost.addHeader("x-bce-date", utcTime);
            httpost.addHeader("authorization", authorization);
            StringEntity strEntity = new StringEntity(
                    "{\"body\":{\"keyWords\":[\"鲜花\"],\"device\":0,\"region\":1000},\"header\":{\"opUsername\":\"尚德机构2016新\",\"opPassword\":\"17170shangDJ\", \"tgUsername\": \"尚德机构2016新\", \"tgPassword\": \"17170shangDJ\"}}",
                    Consts.UTF_8);
            httpost.setEntity(strEntity);

            // Executing the request.
            HttpResponse response = httpClient.execute(httpost);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = null;
            StringBuffer result = new StringBuffer();
            try {
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(result);


        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        */
    }

}
