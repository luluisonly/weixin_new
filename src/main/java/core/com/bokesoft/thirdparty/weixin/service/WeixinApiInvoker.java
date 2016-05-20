package com.bokesoft.thirdparty.weixin.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bokesoft.thirdparty.weixin.bean.WeixinApiResult;
import com.bokesoft.thirdparty.weixin.common.APIURL;
import com.bokesoft.thirdparty.weixin.common.SimpleHttpClient;

/**
 * 调用微信提供的API
 *
 */
public class WeixinApiInvoker {

	/**
	 * 创建菜单
	 * @param publicNumber
	 * @return
	 * @throws IOException
	 */
	public WeixinApiResult createWeixinMenu(String token,String menu) throws IOException{
		String create_weixin_menu_url = APIURL.CREATE_WEIXIN_MENU_URL+token;
		String result = SimpleHttpClient.invokePostWithBody4String(create_weixin_menu_url,menu,3000);
		return new WeixinApiResult(result);
		
	}
	/**
	 * 发送客服消息
	 * @param access_token
	 * @param message
	 * @return
	 * @throws IOException
	 */
	public WeixinApiResult sendMessage(String access_token,String message) throws IOException{
		String send_weixin_message_url  = APIURL.SEND_WEIXIN_MESSAGE_URL+access_token;
		String result = SimpleHttpClient.invokePostWithBody4String(send_weixin_message_url,message,3000);
		return new WeixinApiResult(result);
	}
	/**
	 * 获取用户信息
	 * @param access_token
	 * @param openid
	 * @return
	 * @throws IOException
	 */
	public String getUserInfo(String access_token,String openid) throws IOException{
		String get_userinfo_url = APIURL.GET_WEIXIN_USERINFO_URL+access_token+"&openid="+openid;
		String result = SimpleHttpClient.invokeGet4String(get_userinfo_url,3000);
		return result;
	}
	/**
	 * 批量获取用户信息
	 * @param access_token
	 * @param openid
	 * @return
	 * @throws IOException
	 */
	public String batchGetUserInfo(String access_token,String[] openidList) throws IOException{
		JSONArray user_list = new JSONArray();
		for(String openid : openidList){
			JSONObject userJson = new JSONObject();
			userJson.put("lang", "zh-CN");
			userJson.put("openid", openid);
			user_list.add(userJson);
		}
		JSONObject json = new JSONObject();
		json.put("user_list", user_list);
		System.out.println(openidList.length+"----"+json);
		String batch_get_userinfo_url = APIURL.Batch_GET_WEIXIN_USERINFO_URL+access_token;
		String result = SimpleHttpClient.invokePostWithBody4String(batch_get_userinfo_url,json.toJSONString(),10000);
		return result;
	}
	/**
	 * 网页授权后的用户信息获取
	 * 
	 * @param access_token
	 * @param openid
	 * @return
	 * @throws IOException
	 */
	public String getUserInfoByAccesstoken(String access_token,String openid) throws IOException{
		String get_userinfo_url = APIURL.GET_WEIXIN_USERINFO_BY_ACCESSTOKEN_URL+access_token+"&opneid="+openid+"&lang=zh_CN";
		String result = SimpleHttpClient.invokeGet4String(get_userinfo_url,3000);
		return result;
	}
	/**
	 * 上传多媒体文件
	 * @param access_token
	 * @param type
	 * @param array
	 * @param filename
	 * @param fileLength
	 * @param contentType
	 * @return
	 * @throws IOException
	 */
	public String uploadMedia(String access_token,String type,byte[] array,String filename,String fileLength,String contentType) throws IOException{
		String upload_weixin_media_url = APIURL.UPLOAD_WEIXIN_MEDIA_URL+access_token+"&type="+type;
		Map<String,String> media = new HashMap<String, String>();
		media.put("filename", filename);
		media.put("fileLength", fileLength);
		media.put("contentType", contentType);
		String result = SimpleHttpClient.invokePostWithBody4String(upload_weixin_media_url, new ByteArrayInputStream(array),media,3000);
		return result;
	}
	/**
	 * 下载多媒体文件
	 * @param access_token
	 * @param media_id
	 * @return
	 * @throws Exception 
	 */
	public InputStream downloadMedia(String access_token,String media_id) throws Exception{
		String download_weixin_media_url = APIURL.DOWNLOAD_WEIXIN_MEDIA_URL+access_token+"&media_id="+media_id;
		InputStream result = SimpleHttpClient.invokeGet4Stream(download_weixin_media_url,3000);
		return result;
	}
	/**
	 * 根据code获取openid
	 * @param appid
	 * @param appsecret
	 * @param code
	 * @return
	 * @throws IOException
	 */
	public String getOpenid(String appid,String appsecret,String code) throws IOException{
		String get_openid_url = APIURL.GET_OPENID_URL+"appid="+appid+"&secret="+appsecret+"&grant_type=authorization_code&code="+code;
		String result = SimpleHttpClient.invokeGet4String(get_openid_url, 3000);
		return result;
	}
	/**
	 * 获取临时二维码
	 */
	public String getTemporaryQRCode(String access_token,String param) throws IOException{
		String  get_temporary_qrcode = APIURL.GET_QRCODE+access_token;
		String scene_id = "";
		String expire_seconds = "604800";
		if(param.indexOf(":")!=-1){
			String[] params = param.split(":");
			expire_seconds = params[0];
			scene_id = params[1];
		}else{
			scene_id=param;
		}
		String json = "{\"expire_seconds\": "+expire_seconds+", \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": "+scene_id+"}}}";
		String result = SimpleHttpClient.invokePostWithBody4String(get_temporary_qrcode, json, 3000);
		return result;
	}
	/**
	 * 获取永久二维码
	 */
	public String getPermanentQRCode(String access_token,String scene_info) throws IOException{
		String  get_temporary_qrcode = APIURL.GET_QRCODE+access_token;
		String param = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\":{${scene_info}}}}";
		if(scene_info.indexOf("scene_str")!=-1){
			param = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\":{${scene_info}}}}";
		}
		param = param.replace("${scene_info}", scene_info);
		String result = SimpleHttpClient.invokePostWithBody4String(get_temporary_qrcode, param, 3000);
		return result;
	}
	/**
	 * 发送模板消息
	 */
	public String sendTemplateMessage(String access_token,String json) throws IOException{
		String send_template_message = APIURL.SEND_TEMPLATE+access_token;
		//TODO 具体的模板消息发送需要认证后在进行实际的处理,此处应该不进行验证，只进行转发
		String result = SimpleHttpClient.invokePostWithBody4String(send_template_message,json,3000);
		return result;
	}
	/**
	 * 调用微信统一下单api
	 * @param orderInfo
	 * @return
	 * @throws IOException
	 */
	public String callWeixinOrderInterface(String orderInfo) throws IOException{
		String send_orderInfo = APIURL.SEND_ORDERINFO;
		String result = SimpleHttpClient.invokePostWithBody4String(send_orderInfo, orderInfo, 3000);
		return result;
	}
	/**
	 * 查询订单api
	 * @param queryInfo
	 * @return
	 * @throws IOException
	 */
	public String callWeixinOrderqueryInterface(String queryInfo) throws IOException{
		String orderquery_url = APIURL.ORDERQUERY_URL;
		String result = SimpleHttpClient.invokePostWithBody4String(orderquery_url, queryInfo, 3000);
		return result;
	}
	/**
	 * 关闭订单api
	 * @param orderInfo
	 * @return
	 * @throws IOException
	 */
	public String callWeixinCloseorderInterface(String orderInfo) throws IOException{
		String closeorder_url = APIURL.CLOSEORDER_URL;
		String result = SimpleHttpClient.invokePostWithBody4String(closeorder_url, orderInfo, 7200);
		return result;
	}
	/**
	 * 退款api
	 */
	public String callWeixinRefundInterface(String mchId,String cer,String refundInfo) throws Exception{
		String refundquery_url = APIURL.REFUND_URL;
		String result = SimpleHttpClient.invokePostSSLWithBody4String(refundquery_url,mchId,cer,refundInfo, 7200);
		return result;
	}
	/**
	 * 查询退款api
	 * @param refundQueryInfo
	 * @return
	 * @throws IOException
	 */
	public String callWeixinRefundqueryInterface(String refundQueryInfo) throws IOException{
		String refundquery_url = APIURL.REFUNDQUERY_URL;
		String result = SimpleHttpClient.invokePostWithBody4String(refundquery_url, refundQueryInfo, 7200);
		return result;
	}
	/**
	 * 下载对账单api
	 * @param downloadbillInfo
	 * @return
	 * @throws IOException
	 */
	public String callWeixinDownloadbillInterface(String downloadbillInfo) throws IOException{
		String downloadbill_url = APIURL.DOWNLOADBILL_URL;
		String result = SimpleHttpClient.invokePostWithBody4String(downloadbill_url,downloadbillInfo,7200);
		return result;
	}
	
	/**
	 * 长链接转换成短链接api
	 * @param access_token
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public String long2ShortURL(String access_token,String url) throws IOException{
		String long2Short_url = APIURL.LONG2SHORT_URL+access_token;
		JSONObject params = new JSONObject();
		params.put("action", "long2short");
		params.put("long_url",url);
		String result = SimpleHttpClient.invokePostWithBody4String(long2Short_url,params.toJSONString(),7200);
		return result;
	}
	
	/**
	 * 获取jssdk凭证
	 * 
	 * @param genAccess_token
	 * @return
	 * @throws IOException 
	 */
	public String getJsapiTicket(String access_token) throws IOException {
		String jsapiticket_url = APIURL.JSAPITICKET_URL+access_token;
		String result = SimpleHttpClient.invokeGet4String(jsapiticket_url, 3000);
		JSONObject resJson = JSONObject.parseObject(result);
		return resJson.getString("ticket");
	}

	/**
	 * 获取关注者列表
	 * 
	 * @param access_token
	 * @param nextOpenid
	 * @return
	 * @throws IOException
	 */
	public String batchGetOpenidList(String access_token, String nextOpenid) throws IOException {
		String url = APIURL.Batch_GET_OPENID_LIST_URL+access_token;
		if(!StringUtils.isBlank(nextOpenid)){
			url= url + "&next_openid=" + nextOpenid;
		}
		String result = SimpleHttpClient.invokeGet4String(url, 100000);
		JSONObject resJson = JSONObject.parseObject(result);
		System.out.println("----------------------next_openid--------------------------"+resJson.getString("next_openid"));
		return resJson.getString("data");
	}
	
	/**
	 * 批量移动用户分组
	 * 
	 * @param access_token
	 * @param openidList
	 * @param toGroupid
	 * @return
	 * @throws IOException
	 */
	public String batchMoveUser(String access_token, String openidList, int toGroupid) throws IOException {
		String url = APIURL.BATCHMOVEUSER_URL + access_token;
		JSONObject json = new JSONObject();
		String[] openids = openidList.replace("[", "").replace(" ", "").replace("]", "").split(",");
		json.put("openid_list", openids);
		json.put("to_groupid", toGroupid);
		String result = SimpleHttpClient.invokePostWithBody4String(url, json.toString(), 100000);
		return result;
	}
	/**
	 * 
	 * 获取微信数据统计接口
	 * 
	 * @param access_token
	 * @param type
	 * @param dateInfo
	 * @return
	 * @throws IOException
	 */
	public String getStatisticsData(String access_token, String type, String dateInfo) throws IOException {
		String url = "";
		if("getUserSummary".equals(type)){
			url = APIURL.GET_USER_SUMMARY_URL;
		}else if("getUserCumulate".equals(type)){
			url = APIURL.GET_USER_CUMULATE_URL;
		}else if("getArticleSummary".equals(type)){
			url = APIURL.GET_ARTICLE_SUMMARY_URL;
		}else if("getArticleTotal".equals(type)){
			url = APIURL.GET_ARTICLE_TOTAL_URL;
		}else if("getUserRead".equals(type)){
			url = APIURL.GET_USER_READ_URL;
		}else if("getUserReadHour".equals(type)){
			url = APIURL.GET_USER_READHOUR_URL;
		}else if("getUserShare".equals(type)){
			url = APIURL.GET_USER_SHARE_URL;
		}else if("getUserShareHour".equals(type)){
			url = APIURL.GET_USER_SHAREHOUR_URL;
		}
		String result = SimpleHttpClient.invokePostWithBody4String(url+access_token, dateInfo, 3000);
		return result;
	}
}