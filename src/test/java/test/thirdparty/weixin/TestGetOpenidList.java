package test.thirdparty.weixin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.myerp.common.SharedBundle;
import com.bokesoft.thirdparty.weixin.common.SimpleHttpClient;

public class TestGetOpenidList {
	public static void main(String[] args) {
		 Map<String, String> params = new HashMap<String, String>();
		 params.put("type","batchGetWeixinOpenidList");
		 params.put("uname", "joyseed");
//		 params.put("param", "{\"touser\":\"oChe9viLngahOIcQaJUTWGMRHS-I\",\"template_id\":\"ifPfNci2hQBgWXZc8FBmHEpltnPPPy28ObXA-uU5zm4\",\"url\":\"http://www.zaofans.com\",\"data\":{\"first\": {\"value\":\"恭喜你购买成功！\",\"color\":\"#FF00FF\"},\"product\":{ \"value\":\"美味早餐\",\"color\":\"#FF00FF\"},\"price\": { \"value\":\"15.00元\",\"color\":\"#FF00FF\"},\"time\":{\"value\":\"2014年9月15日\",\"color\":\"#FF00FF\"},\"remark\":{\"value\":\"欢迎再次购买！\",\"color\":\"#173177\"}}}");//要传入的code
		 params.put("param","");
		 try {
			String response = SimpleHttpClient.invokePost4String("http://www.zaofans.com/weixin-debug/open-api", params, 7200);
			System.out.println(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
