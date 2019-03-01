package cn.xyyg.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.TrustManager;

import cn.xyyg.pojo.Token;
import cn.xyyg.pojo.WxMssVo;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;


public class CommonUtil {    

    public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

//获取小程序token

    public static Token getToken(String appid, String appsecret) {

        Token token = null;

        String requestUrl = token_url.replace("APPID", appid).replace("APPSECRET", appsecret);

        // 发起GET请求获取凭证

        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
 

        if (null != jsonObject) {

            try {

                token = new Token();

                token.setAccessToken(jsonObject.getString("access_token"));

                token.setExpiresIn(jsonObject.getInt("expires_in"));

            } catch (JSONException e) {

                token = null;

                // 获取token失败

//                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));

            }

        }

        return token;

    }

//发送模板消息

    public static String sendTemplateMessage(WxMssVo wxMssVo) {

        String info = "";

        try {

            //创建连接

            URL url = new URL(wxMssVo.getRequest_url());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoOutput(true);

            connection.setDoInput(true);

            connection.setRequestMethod("POST");

            connection.setUseCaches(false);

            connection.setInstanceFollowRedirects(true);

            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Type", "utf-8");

            connection.connect();

            //POST请求

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());

            JSONObject obj = new JSONObject();

 

            obj.put("access_token", wxMssVo.getAccess_token());

            obj.put("touser", wxMssVo.getTouser());

            obj.put("template_id", wxMssVo.getTemplate_id());

            obj.put("form_id", wxMssVo.getForm_id());

            obj.put("page", wxMssVo.getPage());

 

            JSONObject jsonObject = new JSONObject();

 

            for (int i = 0; i < wxMssVo.getParams().size(); i++) {

                JSONObject dataInfo = new JSONObject();

                dataInfo.put("value", wxMssVo.getParams().get(i).getValue());

                dataInfo.put("color", wxMssVo.getParams().get(i).getColor());

                jsonObject.put("keyword" + (i + 1), dataInfo);

            }

 

            obj.put("data", jsonObject);

            out.write(obj.toString().getBytes());

            out.flush();

            out.close();

 

            //读取响应

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String lines;

            StringBuffer sb = new StringBuffer("");

            while ((lines = reader.readLine()) != null) {

                lines = new String(lines.getBytes(), "utf-8");

                sb.append(lines);

            }

            info = sb.toString();

            System.out.println(sb);

            reader.close();

            // 断开连接

            connection.disconnect();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return info;

    }
    
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
    	JSONObject jsonObject = null;        
    	StringBuffer buffer = new StringBuffer();          
    	try {              
    		// 创建SSLContext对象，并使用我们指定的信任管理器初始化              
    		TrustManager[] tm = { new MyX509TrustManager() };              
    		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");              
    		sslContext.init(null, tm, new java.security.SecureRandom());              
    		// 从上述SSLContext对象中得到SSLSocketFactory对象              
    		SSLSocketFactory ssf = sslContext.getSocketFactory();                
    		URL url = new URL(requestUrl);              
    		HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();              
    		httpUrlConn.setSSLSocketFactory(ssf);                
    		httpUrlConn.setDoOutput(true);              
    		httpUrlConn.setDoInput(true);              
    		httpUrlConn.setUseCaches(false);              
    		// 设置请求方式（GET/POST）             
    		httpUrlConn.setRequestMethod(requestMethod);                
    		if ("GET".equalsIgnoreCase(requestMethod)) { 
    			
    			httpUrlConn.connect();              }                              
    		// 当有数据需要提交时              
    		if (null != outputStr) {                 
    			OutputStream outputStream = httpUrlConn.getOutputStream();                 
    			// 注意编码格式，防止中文乱码                  
    			outputStream.write(outputStr.getBytes("UTF-8"));                  
    			outputStream.close();             
    			}                
    		// 将返回的输入流转换成字符串             
    		InputStream inputStream = httpUrlConn.getInputStream();              
    		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");              
    		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);                
    		String str = null;              
    		while ((str = bufferedReader.readLine()) != null) {                 
    			buffer.append(str);             
    			}              
    		bufferedReader.close();              
    		inputStreamReader.close();              
    		// 释放资源              
    		inputStream.close();              
    		inputStream = null;              
    		httpUrlConn.disconnect();              
    		jsonObject = JSONObject.fromObject(buffer.toString());          
    		} 
    	catch (ConnectException ce) {             
    			ce.printStackTrace();        
    			} 
    	catch (Exception e) {              
    		e.printStackTrace();        
    		}          
    	return jsonObject;      
    	}
    		
    	
    
    	

 

 

}
