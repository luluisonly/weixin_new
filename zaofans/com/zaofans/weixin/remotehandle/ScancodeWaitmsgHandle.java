package com.zaofans.weixin.remotehandle;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.myerp.common.SharedBundle;
import com.bokesoft.thirdparty.weixin.bean.SOARequestMessage;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinEventMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteTextMessage;
import com.bokesoft.thirdparty.weixin.common.SimpleHttpClient;
import com.bokesoft.thirdparty.weixin.open.WeixinSOALocalDispatch;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRemoteTextMessageCellHandle;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRmoteSession;
import com.zaofans.weixin.common.BFConstant;

public class ScancodeWaitmsgHandle implements WeixinRemoteTextMessageCellHandle {
	
	private static final Logger LOGGER = Logger.getLogger(ScancodeWaitmsgHandle.class);

	@Override
	public SOAResponseMessage handleWeixinRemoteTextMessage(
			WeixinRmoteSession cache, WeixinRemoteTextMessage remoteTextMessage){
		WeixinEventMessage eventMessage = remoteTextMessage.getEventMessage();
		SOAResponseMessage responseMessage = new SOAResponseMessage();
		String result = eventMessage.getScanResult();
		String openid = eventMessage.getFromUserName();
		// TODO 处理扫描二维码的返回，要根据具体的业务进行处理
		if (result != null && result.toLowerCase().startsWith("http")) {
			responseMessage.setMessage("小Fan检测到该二维码是页面，<a href=\""+result+"\">点击打开</a>");
		} else {
			try { 
				//二维码图片应该是json格式的数据
				String orderId = result;
				//Map<String, String> headers = new HashMap<String, String>();
				String orderInfo_url=SharedBundle.getProperties("server.soa.orderform.orderinfo");
				if(StringUtils.isBlank(orderInfo_url)){
					LOGGER.error("core文件中server.soa.orderform.orderinfo必须配置，请检查");
					responseMessage.setMessage("系统错误，请联系管理员");
					return responseMessage;
				}
				if(!orderInfo_url.endsWith("/")){
					orderInfo_url = orderInfo_url+"/";
				}
				orderInfo_url = orderInfo_url + orderId +"?__sessionid=" + openid;
				System.out.println(orderInfo_url);
				String orderInfo = SimpleHttpClient.invokeGet4String(
						orderInfo_url, 7200);
				if(StringUtils.isBlank(orderInfo)){
					responseMessage.setMessage("无此订单信息");
					return responseMessage;
				}
				Map<String,String> map = parseOrderInfo(orderInfo);
				if(map==null){
					responseMessage.setMessage("无此订单明细信息");
					return responseMessage;
				}
				SOARequestMessage requestMessage = new SOARequestMessage();
				requestMessage.setType("sendTemplateMessage");
				requestMessage.setUname(BFConstant.UNAME);
				String template = "{\"touser\": \"${openid}\",  \"template_id\": \"jsSZ2JzCOPgWihHW-nCEbmX-DvPtjhAjbZ9_s99VEuM\",${url}\"topcolor\": \"#FF0000\",    \"data\": {\"first\":{\"color\":\"#173177\",\"value\":\"${firstData}\"},\"keyword1\":{\"color\":\"#173177\",\"value\":\"${keyword1Data}\"},\"keyword2\":{\"color\":\"#173177\",\"value\":\"${keyword2Data}\"},\"keyword3\":{\"color\":\"#173177\",\"value\":\"${keyword3Data}\"},\"keyword4\":{\"color\":\"#173177\",\"value\":\"${keyword4Data}\"},\"remark\":{\"value\":\"${remarkData}\"}}}";
				template = template
						.replace("${openid}", openid)
						.replace("${firstData}", "订单详情").replace("${keyword1Data}", "暂无").replace("${keyword2Data}", map.get("PRICE")).replace("${keyword3Data}", map.get("NAME")+" "+map.get("COUNT")+" "+map.get("CONTENT")).replace("${keyword4Data}", "暂无").replace("${remarkData}", "这是您的订单信息");
				if("false".equalsIgnoreCase(map.get("DILIVERYMAN"))){
					template = template.replace("${url}", "");
				}else{
					String orderConfirm_url = SharedBundle.getProperties("server.soa.orderform.confirm");
					if(StringUtils.isBlank(orderConfirm_url)){
						LOGGER.error("core文件中server.soa.orderform.confirm必须配置，请检查");
						responseMessage.setMessage("系统错误，请联系管理员");
						return responseMessage;
					}
					if(!orderConfirm_url.endsWith("/")){
						orderConfirm_url = orderConfirm_url+"/";
					}
					String realUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6cdf1c4b3e368bcd&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=123456#wechat_redirect";
					orderConfirm_url = URLEncoder.encode(orderConfirm_url+orderId,"utf-8");
					realUrl=realUrl.replace("REDIRECT_URI",orderConfirm_url);
					System.out.println(realUrl);
					template = template.replace("${url}","\"url\":\""+realUrl+"\",");
				}
				requestMessage.setObjectParam(template);
				responseMessage.setMessage("");
				System.out.println("------------------"+template);
				SOAResponseMessage response =WeixinSOALocalDispatch.doDispatch(requestMessage);
				//TODO 根据返回值处理下
				if(response.getData()!=null){
					throw new Exception("查询成功，但是推送消息失败");
				}
				
			} catch (Exception e) {
				LOGGER.error("查询订单消息推送时，出现了错误："+e.getStackTrace());
				responseMessage.setMessage("查询出现错误，请稍后重试");
			}
		}
		return responseMessage;
	}

	private Map<String,String> parseOrderInfo(String orderInfo) {
		Map<String,String> map = new HashMap<String,String>();
		JSONObject jsonObject = JSONObject.parseObject(orderInfo);
		String ORDERDETAIL = jsonObject.getString("ORDERDETAIL");
		if(ORDERDETAIL==null||ORDERDETAIL.length()==0){
			return null;
		}
		JSONObject orderdetailJson = JSONObject.parseObject(ORDERDETAIL.substring(1,ORDERDETAIL.length()-1));
		map.put("NAME", orderdetailJson.getString("NAME"));
		map.put("CONTENT", orderdetailJson.getString("CONTENT"));
		map.put("COUNT",orderdetailJson.getString("COUNT"));
		map.put("PRICE", orderdetailJson.getString("PRICE"));
		map.put("DILIVERYMAN",jsonObject.getString("DILIVERYMAN"));
		return map;
	}

	@Override
	public String getHandleName() {
		return "ScancodeWaitmsg";
	}

}
