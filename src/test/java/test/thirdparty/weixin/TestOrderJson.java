package test.thirdparty.weixin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.thirdparty.weixin.common.SimpleHttpClient;

public class TestOrderJson {

	public static void main(String[] args) throws IOException {
		String json = SimpleHttpClient.invokeGet4String("http://192.168.46.95:8080/zaofans/orderform/orderForm/aaafK3", 3000);
		JSONObject jsonObject = JSONObject.parseObject(json);
		String ORDERDETAIL = jsonObject.getString("ORDERDETAIL");
		JSONObject orderdetailJson = JSONObject.parseObject(ORDERDETAIL.substring(1,ORDERDETAIL.length()-1));
		Map<String,String> map = new HashMap<String,String>();
		map.put("NAME", orderdetailJson.getString("NAME"));
		map.put("CONTENT", orderdetailJson.getString("CONTENT"));
		map.put("COUNT",orderdetailJson.getString("COUNT"));
		map.put("PRICE", orderdetailJson.getString("PRICE"));
		
		JSONObject param = new JSONObject();
//		param.put("touser", openid);
		param.put("msgtype","text");
		param.put("text", new JSONObject().put("content", "套餐名："+map.get("NAME")+"; 套餐内容："+map.get("CONTENT")+"; 数量："+map.get("COUNT")+"; 价格："+map.get("PRICE")));

	}
	
}
