package com.bokesoft.thirdparty.weixin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.myerp.common.SharedBundle;
import com.bokesoft.thirdparty.weixin.common.SimpleHttpClient;
import com.zaofans.weixin.common.WXPaySecurityUtils;

/**
 * 
 * 微信支付完成的通知
 * 
 * @author liuxin
 *
 */

public class WeixinPayNotifyServletV2 extends HttpServlet {
	
	private static final long serialVersionUID = 7591116739179025543L;
	// 微信返回  fail 失败，success 成功
	private static final String STATUC_SUCCESS = "SUCCESS";
	private static final String STATUC_FAIL    = "FAIL";
	//微信商户的key 用于校验
	private static final String key = SharedBundle.getProperties("server.weixin.pay.key");
	private static final String paySignKey = SharedBundle.getProperties("server.weixin.pay.signkey");
	// 微信支付回调通知url
	private static final String zfNotifyUrl = SharedBundle.getProperties("server.soa.orderform.paynotify");
//	private static final String dsNotifyUrl = SharedBundle.getProperties("server.soa.activity.paynotify");
	private static final String czNotifyUrl = SharedBundle.getProperties("server.soa.usercenter.paynotify");
	private static Logger LOGGER = Logger.getLogger(WeixinPayNotifyServletV2.class);
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		String responseStr = "";
		try {
			JSONObject result = WXPaySecurityUtils.vaildateWXPaySign4V2(request, key, paySignKey);
			LOGGER.info("WeixinPayNotify :" + result.toString());
			if(STATUC_FAIL.equals(result.get("return_code"))){
				String return_msg = result.getString("return_msg");
				LOGGER.warn("Weixin pay failed,cause by "+ return_msg);
			}else if(STATUC_SUCCESS.equals(result.get("return_code"))){
				String result_code = result.getString("result_code");
				//只有成功的时候才通知
				if(STATUC_SUCCESS.equals(result_code)){
					String out_trade_no = result.getString("out_trade_no");
					//TODO 根据商户订单号规则进行相应转发
					String resStr = "";
					if(out_trade_no.startsWith("ZF")){
						resStr = sendPayInfo2UserCenter(result,TradeType.MARK,zfNotifyUrl);
					}else if(out_trade_no.startsWith("DS")){
						
					}else if(out_trade_no.startsWith("CZ")){	
						resStr = sendPayInfo2UserCenter(result,TradeType.RECHARGE,czNotifyUrl);
					}else{
					}
					System.out.println(resStr);
					JSONObject resJson = JSONObject.parseObject(resStr);
					if(resJson != null && resJson.getBoolean("success")){
						responseStr ="success";
					}
				}
			}
		} catch (Throwable e) {
			LOGGER.error(e.getMessage());
		}
		try {
			Map<String, String> responseMap = WXPaySecurityUtils.parseXml(responseStr);
			responseStr = responseMap.get("return_code").toLowerCase();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter writer = response.getWriter();
		writer.write(responseStr);
		writer.flush();
	}
	
	private static String sendPayInfo2UserCenter(JSONObject result,TradeType mark,String url) throws Throwable {
		JSONObject datas = new JSONObject();
		datas.put("openid", result.get("openid"));
		datas.put("time_end", result.get("time_end"));
		String outTradeNo= result.getString("out_trade_no");
		outTradeNo = outTradeNo.substring(2, outTradeNo.length());
		datas.put("out_trade_no", outTradeNo);
		datas.put("total_fee", result.get("total_fee"));
		datas.put("transaction_id", result.get("transaction_id"));
		datas.put("trade_type", mark.toString());
		String encryption = WXPaySecurityUtils.sign4zaofans(datas.toString());
		Map<String, String> params = new HashMap<String, String>();
		params.put("datas", datas.toString());
		params.put("sign",encryption);
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("__sessionid", result.getString("openid"));
		String resStr = SimpleHttpClient.invokePost4String(url, params,headers, 10000);
		return resStr;
	}
	
	public static void main(String[] args) throws Throwable {
		String content = "<xml><appid><![CDATA[wx2421b1c4370ec43b]]></appid><attach><![CDATA[支付测试]]></attach><bank_type><![CDATA[CFT]]></bank_type><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[10000100]]></mch_id><nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str><openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid><out_trade_no><![CDATA[1409811653]]></out_trade_no><sign><![CDATA[B552ED6B279343CB493C5DD0D78AB241]]></sign><sub_mch_id><![CDATA[10000100]]></sub_mch_id><time_end><![CDATA[20140903131540]]></time_end><total_fee>105</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code></xml>";
		Map<String, String> result = WXPaySecurityUtils.parseXml(content);
//		String resStr = sendZFToOrderForm(result);
//		String resStr = sendPayInfo2UserCenter(result,TradeType.MARK,czNotifyUrl);
//		System.out.println(resStr);
//		JSONObject resJson = JSONObject.parseObject(resStr);
//		if(resJson != null && resJson.getBoolean("success")){
//			String responseStr ="<xml><return_code><![CDATA[%s]]></return_code><return_msg><![CDATA[%s]]></return_msg></xml>";
//			System.out.println(responseStr);
//		}
	}
	
}