package test.thirdparty.weixin;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.thirdparty.weixin.common.SimpleHttpClient;

public class TestSendTemplateMessage {
	public static void main(String[] args) {
		 try {
			 Map<String, String> params = new HashMap<String, String>();
			 params.put("type","sendTemplateMessage");
			 params.put("uname", "zaofans");
//		 	 params.put("param", "{\"touser\":\"onbRUsyIXOMPNETJ1IkjJGSVAu4A\",\"template_id\":\"jsSZ2JzCOPgWihHW-nCEbmX-DvPtjhAjbZ9_s99VEuM\",\"url\":\"http://www.baidu.com\",\"data\":{\"first\": {\"value\":\"haha\",\"color\":\"#FF00FF\"},\"keyword1\":{ \"value\":\"haha\",\"color\":\"#FF00FF\"},\"keyword2\": { \"value\":\"15.00\",\"color\":\"#FF00FF\"},\"keyword4\":{\"value\":\"2014\",\"color\":\"#FF00FF\"},\"remark\":{\"value\":\"haha \\nhehe\",\"color\":\"#173177\"}}}");//要传入的code
//			 JSONObject jsonObject = new JSONObject();
////			 jsonObject.put("templateType", "ordercommitsuccess");
//			 jsonObject.put("templateType", "orderstatuschange");
//			 jsonObject.put("openid", "onbRUsyIXOMPNETJ1IkjJGSVAu4A");
//			 jsonObject.put("url", "");
//			 jsonObject.put("title", "中文不乱码");
//			 jsonObject.put("time", "中文不乱码");
//			 jsonObject.put("amount", "中文不乱码");
//			 jsonObject.put("orderId", "中文不乱码");
//			 jsonObject.put("status", "中文不乱码");
//			 jsonObject.put("remark", "中文不乱码");
			 JSONObject datas = new JSONObject();
			 datas.put("templateType", "rechargesuccess");
			 datas.put("openid", "onbRUsyIXOMPNETJ1IkjJGSVAu4A");
			 datas.put("url", "");
		  	 datas.put("title","尊敬的用户:为了吃方便面,您已成功充值！" );
			 datas.put("time", new SimpleDateFormat("MM月dd日 HH时mm分").format(new Date())+"");
		 	 datas.put("amount", "100");
			 datas.put("remark", "账户余额:"+"10000");
			 String temp = URLEncoder.encode(datas.toString(), "UTF-8");
			 params.put("param", temp);
			 System.out.println(URLDecoder.decode(temp, "UTF-8"));
			String response = SimpleHttpClient.invokePost4String("http://localhost:8080/weixin-debug/open-api", params, 7200);
			System.out.println(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
