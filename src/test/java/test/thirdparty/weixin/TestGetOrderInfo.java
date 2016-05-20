package test.thirdparty.weixin;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.thirdparty.weixin.common.SimpleHttpClient;

public class TestGetOrderInfo {
	public static void main(String[] args) throws IOException {
//		String result = SimpleHttpClient.invokeGet4String("http://192.168.46.95:8080/zaofans/orderform/orderForm/aaafK3", 3000);
//		JSONObject resultJson = JSONObject.parseObject(result);
		JSONObject json = new JSONObject();
		json.put("123", null);
		System.out.println(json.toJSONString());
	}
}
