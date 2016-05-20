package test.thirdparty.weixin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.bokesoft.thirdparty.weixin.common.SimpleHttpClient;

public class TestLong2Short {
	public static void main(String[] args) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("type","long2shortUrl");
		params.put("uname", "zaofans");
		params.put("param","weixin://wxpay/bizpayurl?sign=83BBF2221EDCA6399E0F942764E0FFBB&appid=wx7318a883737ed334&mch_id=10065813&product_id=dashang_1&time_stamp=1438752827&nonce_str=gRGD1v5cOuYV");
		try {
			String response = SimpleHttpClient.invokePost4String("http://www.zaofans.com/weixin-debug/open-api", params, 7200);
			System.out.println(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
