package org.gz.common.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;

@Slf4j
public class HttpUtils {
	
	private static CloseableHttpClient httpclient = HttpClients.createDefault();
	
	/**
	 * POST CALL
	 * @param url
	 * 			URL
	 * @param params
	 * 			params
	 * @return
	 * @throws IllegalStateException
	 */
	public static String httpPostCall(String url,Map<String, String> params) throws IllegalStateException {
		CloseableHttpResponse response = null;
		try {
			List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
			Iterator<String> parameIterator = params.keySet().iterator();
			while(parameIterator.hasNext()) {
				String parameName = parameIterator.next();
//				nvps.add(new BasicNameValuePair(parameName, URLEncoder.encode((String)params.get(parameName),"UTF-8")));
				nvps.add(new BasicNameValuePair(parameName, (String)params.get(parameName)));
			}
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			
			log.info("HttpUtils httpPostCall method invoke url is :"+httpPost);
			response = httpclient.execute(httpPost);
			log.info("=====>> resp1: {}", response.getStatusLine().getStatusCode());
			log.info("=====>> resp2: {}", responseContext(response.getEntity().getContent()));
			
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY){
				HttpEntity entity = response.getEntity();
			    JSONObject resultJsonObject = JSONObject.parseObject(responseContext(entity.getContent()));
		        String location = resultJsonObject.getString("location");
		        if(location == null) {
		        	 log.error("Redirect URL no find");
		        	 throw new IllegalStateException("Redirect URL no find");
		        }
		        return null;
			} else if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity entity = response.getEntity();
				return responseContext(entity.getContent());
			} else if(response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND){
				log.error("Page: " + url + " no find");
				throw new IllegalStateException("404 Page no find");
			} else {
				log.error(response.getStatusLine().getStatusCode()+" Business is not supported");
				throw new IllegalStateException(response.getStatusLine().getStatusCode()+" Business is not supported");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					log.error(e.toString());
				}
			}
		}
		return null;
	}
	
	/**
	 * GET CALL
	 * @param url
	 * 			URL
	 * @param params
	 * 			params
	 * @return
	 * @throws IllegalStateException
	 */
	public static String httpGetCall(String url, Map<String, String> params) throws IllegalStateException {
		CloseableHttpResponse response = null;
		try {
			StringBuffer paramParts = new StringBuffer();
			Iterator<String> parameIterator = params.keySet().iterator();
			while(parameIterator.hasNext()) {
				String parameName = parameIterator.next();
				paramParts.append("&").append(parameName).append("=").append(params.get(parameName));
			}
			
			if(paramParts.length() > 0) {
				paramParts.replace(0, 1, "?");
			}
			
			String callUrl = url + paramParts.toString();
			
			log.info("HttpUtils httpGetCall method invoke url is :" + callUrl);
			HttpGet httpGet = new HttpGet(callUrl);
			response = httpclient.execute(httpGet);
			
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY){
				HttpEntity entity = response.getEntity();
			    JSONObject resultJsonObject = JSONObject.parseObject(responseContext(entity.getContent()));
		        String location = resultJsonObject.getString("location");
		        if(location == null) {
		        	 log.error("Redirect URL no find");
		        	 throw new IllegalStateException("Redirect URL no find");
		        }
			} else if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity entity = response.getEntity();
				return responseContext(entity.getContent());
			} else if(response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND){
				log.error("Page: " + url + " no find");
				throw new IllegalStateException("404 Page no find");
			} else {
				log.error(response.getStatusLine().getStatusCode()+" Business is not supported");
				throw new IllegalStateException(response.getStatusLine().getStatusCode()+" Business is not supported");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					log.error(e.toString());
				}
			}
		}
		return null;
	}
	
	private static String responseContext(InputStream input) {
    	try {
			BufferedReader readContent = new BufferedReader(new InputStreamReader(input, "UTF-8"));
			String outStr = readContent.readLine();
			StringBuilder sb = new StringBuilder();
			while (outStr != null) {
				if(log.isDebugEnabled()) {
					log.debug(outStr);
				}
				sb.append(outStr);
				outStr = readContent.readLine();
			}
			return sb.toString();
    	} catch(Exception e) {
    		e.printStackTrace();
    	} finally {
    		if(input != null) {
    			try {
					input.close();
				} catch (IOException e) {
					log.error(e.toString());
				}
    		}
    	}
    	return null;
	}
	
	public static String post(String url, String param){
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.connect();
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "GBK"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        } finally{
            try{
                if(out!=null) out.close();
                if(in!=null) in.close();
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
        
        return result;
    }
}
