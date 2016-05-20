package test.thirdparty.weixin;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.zaofans.weixin.common.PayUtils;


public class TestMain {
	public static void main(String[] args) throws UnsupportedEncodingException {
		String str = "transport_fee=0,trade_mode=1, trade_state=0, sign_type=MD5, input_charset=UTF-8, fee_type=1, out_trade_no=ZF1000151126001303, transaction_id=1219005701201511261722583572, discount=0, total_fee=1, time_end=20151126200000, partner=1219005701, notify_id=qrplCy0P5V1vb1XbuC_at1b-wzGjESjJCVCysk88itd7Wwt8RQrrYpHJc9Ju2tiew3J5jYabs3rGQdi6ZU4Df5h12GRBG1Ee, bank_type=CFT, product_fee=1";
		String[] strs = str.trim().split(",");
		Map<String,String> map = new HashMap<String,String>();
		for(int i = 0; i<strs.length ;i++){
			String[] temp = strs[i].split("=");
			map.put(temp[0], temp[1]);
		}
		
		System.out.println("2d9f1a2b8d991eb706767f59653684206dfbbab3");
		
		Map<String, String> map2 = new HashMap<String,String>();
		map2.put("appid", "wxf346c8e83586af4c");
		map2.put("timestamp", "1448539215");
		map2.put("noncestr", "1SJNICGZiwTlcFi2");
		map2.put("issubscribe", "1");
		map2.put("openid", "ooBCYjmGL3X7Qpoi5pK-_k24MyuM");
		map2.put("appkey", "Bdquuqf5ZmiiHE6HbztOLIMT86GwQJ33Nj9uygJyeairb7Crpv5xxeofBg1xYS9uD3r7cIDyjXHCm7uCuKSqJejAhik28H2cbMEuF4m9zJWFWvamNOVnQxEa86WSPiqr");
		String end = PayUtils.paySignSHA1(map2, null);

		System.out.println(end);
	}
}
