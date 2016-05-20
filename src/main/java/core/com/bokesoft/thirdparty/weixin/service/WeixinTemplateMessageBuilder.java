package com.bokesoft.thirdparty.weixin.service;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class WeixinTemplateMessageBuilder {
	
	private final static String RECHARGESUCCESS = "rechargesuccess";
	private final static String ORDERRECEIVED = "orderreceived";
	private final static String ORDERSTATUSCHANGE = "orderstatuschange";
	private final static String ORDERCOMMITSUCCESS = "ordercommitsuccess";
	private final static String REFUNDSUCCESS = "refundsuccess";
	

	private interface $WeixinTemplateMessageHandle{
		public abstract String buildTemplateMessage(JSONObject jsonObject);
	}
	
	private static Map<String, $WeixinTemplateMessageHandle> weixinTemplateMessageHandles = new HashMap<String, $WeixinTemplateMessageHandle>(){
		
		private static final long serialVersionUID = 4806617745974329921L;
		
		private final String CZCGTEMPLATE = "{\"touser\":\"%s\","
											+"\"template_id\":\"kOJN5XYWdYltqaP7bzhFp81iLKFclWH6tNDyybPM8kI\","
											+"\"url\":\"%s\","
											+"\"data\":"
											+"{\"first\": {\"value\":\"%s\",\"color\":\"#000000\"},"
											+"\"keynote1\":{\"value\":\"%s\",\"color\":\"#000000\"},"
											+"\"keynote2\": {\"value\":\"%s\",\"color\":\"#000000\"},"
											+"\"remark\":{\"value\":\"%s\",\"color\":\"#000000\"}}}";
		
		private final String DDSLTEMPLATE = "{\"touser\":\"%s\","
											+"\"template_id\":\"H61NyvqM40Ro1BzlphLbie6jGjAX0JXOQrNSuxW1BIs\","
											+"\"url\":\"%s\","
											+"\"data\":{"
											+"\"first\": {\"value\":\"%s\",\"color\":\"#000000\"},"
											+"\"storeName\": {\"value\":\"%s\",\"color\":\"#000000\"},"
											+"\"orderId\":{\"value\":\"%s\",\"color\":\"#000000\"},"
											+"\"orderType\": {\"value\":\"%s\",\"color\":\"#000000\"},"
											+"\"remark\":{\"value\":\"%s\",\"color\":\"#000000\"}}}";
		private final String DDZTTXTEMPLATE = "{\"touser\":\"%s\","
											+"\"template_id\":\"yBrX9-OXf_msNMldi5C1osjCK3899AbwY6b-wp9UlPU\","
											+"\"url\":\"%s\","
											+"\"data\":{"
											+"\"first\": {\"value\":\"%s\",\"color\":\"#000000\"},"
											+"\"keyword1\": {\"value\":\"%s\",\"color\":\"#000000\"},"
											+"\"keyword2\":{\"value\":\"%s\",\"color\":\"#000000\"},"
											+"\"keyword3\": {\"value\":\"%s\",\"color\":\"#000000\"},"
											+"\"remark\":{\"value\":\"%s\",\"color\":\"#000000\"}}}";
		private final String DDTJCGTEMPLATE = "{\"touser\":\"%s\","
											+"\"template_id\":\"vZY2LGZsfhjsGPs2vQIG9HXDeCMgFAzq7dmfcCABJtQ\","
											+"\"url\":\"%s\","
											+"\"data\":{"
											+"\"first\": {\"value\":\"%s\",\"color\":\"#000000\"},"
											+"\"keyword1\": {\"value\":\"%s\",\"color\":\"#000000\"},"
											+"\"keyword2\":{\"value\":\"%s\",\"color\":\"#000000\"},"
											+"\"keyword3\": {\"value\":\"%s\",\"color\":\"#000000\"},"
											+"\"keyword4\": {\"value\":\"%s\",\"color\":\"#000000\"},"
											+"\"remark\":{\"value\":\"%s\",\"color\":\"#000000\"}}}";
		private final String TKCGTEMPLATE = "{\"touser\":\"%s\","
											+"\"template_id\":\"n2-tRr4ykIbU9lZtbyMKt0Ki-1VbmZTxvDyj_DYXHf8\","
											+"\"url\":\"%s\","
											+"\"data\":{"
											+"\"first\": {\"value\":\"%s\",\"color\":\"#000000\"},"
											+"\"keyword1\": {\"value\":\"%s\",\"color\":\"#000000\"},"
											+"\"keyword2\":{\"value\":\"%s\",\"color\":\"#000000\"},"
											+"\"keyword3\": {\"value\":\"%s\",\"color\":\"#000000\"},"
											+"\"keyword4\": {\"value\":\"%s\",\"color\":\"#000000\"},"
											+"\"keyword5\": {\"value\":\"%s\",\"color\":\"#000000\"},"
											+"\"remark\":{\"value\":\"%s\",\"color\":\"#000000\"}}}";
		
		{
			put(RECHARGESUCCESS, new $WeixinTemplateMessageHandle(){
				@Override
				public String buildTemplateMessage(JSONObject jsonObject) {
					Object[] params = new Object[]{
						jsonObject.getString("openid"),
						jsonObject.getString("url"),
						jsonObject.getString("title"),
						jsonObject.getString("time"),
						jsonObject.getString("amount"),
						jsonObject.getString("remark")
					};
					return String.format(CZCGTEMPLATE, params);
				}
			});
			
			put(ORDERRECEIVED, new $WeixinTemplateMessageHandle(){
				@Override
				public String buildTemplateMessage(JSONObject jsonObject) {
					Object[] params = new Object[]{
						jsonObject.getString("openid"),
						jsonObject.getString("url"),
						jsonObject.getString("title"),
						jsonObject.getString("storeName"),
						jsonObject.getString("orderId"),
						jsonObject.getString("orderType"),
						jsonObject.getString("remark")
					};
					return String.format(DDSLTEMPLATE, params);
				}
			});
			
			put(ORDERSTATUSCHANGE, new $WeixinTemplateMessageHandle(){
				@Override
				public String buildTemplateMessage(JSONObject jsonObject) {
					Object[] params = new Object[]{
						jsonObject.getString("openid"),
						jsonObject.getString("url"),
						jsonObject.getString("title"),
						jsonObject.getString("orderId"),
						jsonObject.getString("status"),
						jsonObject.getString("time"),
						jsonObject.getString("remark")
					};
					return String.format(DDZTTXTEMPLATE, params);
				}
			});
			put(ORDERCOMMITSUCCESS, new $WeixinTemplateMessageHandle(){
				@Override
				public String buildTemplateMessage(JSONObject jsonObject) {
					Object[] params = new Object[]{
							jsonObject.getString("openid"),
							jsonObject.getString("url"),
							jsonObject.getString("title"),
							jsonObject.getString("time"),
							jsonObject.getString("amount"),
							jsonObject.getString("detail"),
							jsonObject.getString("address"),
							jsonObject.getString("remark")
						};
					return String.format(DDTJCGTEMPLATE, params);
				}
				
			});
			put(REFUNDSUCCESS, new $WeixinTemplateMessageHandle(){
				@Override
				public String buildTemplateMessage(JSONObject jsonObject) {
					Object[] params = new Object[]{
							jsonObject.getString("openid"),
							jsonObject.getString("url"),
							jsonObject.getString("title"),
							jsonObject.getString("info"),
							jsonObject.getString("time"),
							jsonObject.getString("storeName"),
							jsonObject.getString("orderId"),
							jsonObject.getString("refundWay"),
							jsonObject.getString("remark")
						};
					return String.format(TKCGTEMPLATE, params);
				}
				
			});
		}
		
	};
	
	public static String getTemplateStr(String param){
		JSONObject jsonObject = JSONObject.parseObject(param);
		String templateType = jsonObject.getString("templateType");
		$WeixinTemplateMessageHandle handle = weixinTemplateMessageHandles.get(templateType);
		if(handle == null){
			return "UnsupportTemplateType";
		}
		return handle.buildTemplateMessage(jsonObject);
	}
	
	public static void main(String[] args) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("templateType", "ordercommitsuccess");
		jsonObject.put("openid", "openid");
		jsonObject.put("url", "url");
		jsonObject.put("title", "title");
		jsonObject.put("time", "time");
		jsonObject.put("amount", "amount");
		jsonObject.put("detail", "detail");
		jsonObject.put("address", "address");
		jsonObject.put("remark", "remark");
		String result = getTemplateStr(jsonObject.toString());
		System.out.println(result);
	}
}
