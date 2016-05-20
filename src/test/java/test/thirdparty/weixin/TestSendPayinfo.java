package test.thirdparty.weixin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.bokesoft.thirdparty.weixin.common.SimpleHttpClient;
import com.zaofans.weixin.common.PayUtils;
import com.zaofans.weixin.common.XmlUtils;

public class TestSendPayinfo {

	public static void main(String[] args) throws IOException {

		Map<String,String> map = new HashMap<String, String>();
//		String temp = System.currentTimeMillis()+"";
//		map.put("result_code","SUCCESS");
//		map.put("return_code","SUCCESS");
//		map.put("openid","oZTTgsolauLUXfjXx74asiGWupfI");//
//		map.put("time_end", temp);
//		map.put("out_trade_no", "ZF20150830aaab0X");
//		map.put("total_fee","3700");
//		map.put("trade_type","JSAPI");
//		map.put("transaction_id","1006950917201508300748662844");
//		String signValue = PayUtils.paySign(map, "9150ec9565808b2f3e914de3d0257270");
//		map.put("sign", signValue);
//		String xml = XmlUtils.maptoXml(map);
//		String resStr = SimpleHttpClient.invokePostWithBody4String("http://www.joyseed.com/weixin/pay-notify", xml, 10000);
//		System.out.println(resStr);
//		
	}
	
}
