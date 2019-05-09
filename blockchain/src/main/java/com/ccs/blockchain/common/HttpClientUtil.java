//package com.ccs.blockchain.common;
//
//import java.util.*;
//import java.util.Map.Entry;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
///*
// * 利用HttpClient进行post请求的工具类
// */
//public class HttpClientUtil {
//    public String doPost(String url,Map<String,String> map,String charset){
//		HttpClient httpClient = null;
//		HttpPost httpPost = null;
//		String result = null;
//		try{
//			httpClient = new SSLClient();
//			httpPost = new HttpPost(url);
//			//设置参数
//			List<NameValuePair> list = new ArrayList<NameValuePair>();
//			Iterator iterator = map.entrySet().iterator();
//			while(iterator.hasNext()){
//				Entry<String,String> elem = (Entry<String, String>) iterator.next();
//				list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
//			}
//			if(list.size() > 0){
//				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
//				httpPost.setEntity(entity);
//			}
//			HttpResponse response = httpClient.execute(httpPost);
//			if(response != null){
//				HttpEntity resEntity = response.getEntity();
//				if(resEntity != null){
//					result = EntityUtils.toString(resEntity,charset);
//				}
//			}
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
//		return result;
//	}
//
//	public static String doGet(String url,Map<String,String> map,String charset){
//		HttpClient httpClient = null;
//		HttpGet httpGet = null;
//		String result = null;
//		try{
//			httpClient = new SSLClient();
//			httpGet = new HttpGet(url);
//			//设置参数
//			List<NameValuePair> list = new ArrayList<NameValuePair>();
//			Iterator iterator = map.entrySet().iterator();
//			while(iterator.hasNext()){
//				Entry<String,String> elem = (Entry<String, String>) iterator.next();
//				list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
//			}
//			if(list.size() > 0){
//				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
//			}
//			HttpResponse response = httpClient.execute(httpGet);
//			if(response != null){
//				HttpEntity resEntity = response.getEntity();
//				if(resEntity != null){
//					result = EntityUtils.toString(resEntity,charset);
//				}
//			}
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
//		return result;
//	}
//
//
//	public static void main(String[] args) {
//		String str = HttpClientUtil.doGet("https://btcdiv.com/getDividends.php?addr=1H6ZZpRmMnrw8ytepV3BYwMjYYnEkWDqVP",new HashMap<>(),"utf-8");
//		System.out.println(str);
//	}
//}