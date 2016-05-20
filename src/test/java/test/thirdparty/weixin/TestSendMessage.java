package test.thirdparty.weixin;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.bokesoft.thirdparty.weixin.common.Encryptor;
import com.bokesoft.thirdparty.weixin.common.SimpleHttpClient;

public class TestSendMessage {
	
	private static String H1_1_8_16_8300 = "http://1.1.8.16:8300/yigo/";
	private static String H1_1_8_16_8600 = "http://1.1.8.16:8600/yigo/";
	private static String HLocalhost_8600 = "http://localhost:8600/yigo/";
	private static String HLocalhost_8300 = "http://localhost:8300/yigo/";
	private static String HLocalhost_83001 = "http://1.1.10.20:8300/yigo/";
	private static String uname_tacter = "tacter_qEk3bRbZAT4T";
//	private static String HLocalhost_8300 = "http://dev.bokesoft.com/bokeoa-demo/";
	public static void main(String[] args) throws IOException {
		long createTime = System.currentTimeMillis();
		String url = createURL(HLocalhost_8300, uname_tacter,createTime);
		System.out.println("==================发出消息："+DateFormat.getDateTimeInstance().format(new Date()));
		long old = System.currentTimeMillis();
//		String message = sendTextMessage(url, "怎么去中山公园");
//		String message = sendTextMessage(url, "本地测试","222");
//		String message = sendTextMessage(url, "远程测试");
//		String message = sendTextMessage(url, "1");
//		String message = sendTextMessage(url, "返回");
//		String message = sendTextMessage(url, "退出");
//		String message = sendTextMessage(url, "测试");
//		String message = sendTextMessage(url, "绑定","222");
//		String message = sendTextMessage(url, "设置当前项目","222");
//		String message = sendTextMessage(url, "22222","222");
//		String message = sendTextMessage(url, "查询当前项目","222");
//		String message = sendTextMessage(url, "上班签到","222");
//		String message = sendTextMessage(url, "下班签到","222");
//		String message = sendTextMessage(url, "审批");
//		String message = sendTextMessage(url, "2");
//		String message = sendTextMessage(url, "实例管理","2222");
		String message = sendTextMessage(url, "12","222",createTime);
//		String message = sendTextMessage(url, "考勤记录","222");
//		String message = sendTextMessage(url, "上班签到","222");
//		String message = sendTextMessage(url, "本月异常签到查询","od7B1jiKE7oiyauLA4_-R2i6zcq0");
//		String message = sendTextMessage(url, "本月异常签到查询","od7B1jnPHjFippxK6Rbv3QPfKNNQ");
//		String message = sendLocationEventMessage(url,"222");
		System.out.println("==================收到消息："+DateFormat.getDateTimeInstance().format(new Date()));
		System.out.println("==================消耗时间："+(System.currentTimeMillis() - old));
		System.out.println(message);
	}
	private static String createURL(String host,String uname_token,long createTime){
		String timestamp = String.valueOf(createTime);
		String nonce = "695091264858";
		String []temp = uname_token.split("_");
		List<String> list = new ArrayList<String>();
		list.add(nonce);
		list.add(timestamp);
		list.add(temp[1]);
		Collections.sort(list);
		StringBuffer stringBuffer = new StringBuffer();
		for (String str : list) {
			stringBuffer.append(str);
		}
		String signature =  Encryptor.encodeBYSHA1(stringBuffer.toString());
		return host+"weixin?uname="+temp[0]+"&signature="+signature+"&timestamp="+timestamp+"&nonce=695091264858";
	}
	public static String sendTextMessage(String url,String text,String openid,long createTime) throws IOException{
		String prefix = "<xml><ToUserName><![CDATA[uname]]></ToUserName><FromUserName><![CDATA["+openid+"]]></FromUserName><CreateTime>"
				+createTime
				+"</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[";
		String subfix = "]]></Content><MsgId>1234567890123456</MsgId></xml>";
		String message = prefix+text+subfix;
		return SimpleHttpClient.invokePostWithBody4String(url, message,3000);
	}
	public static String sendImageMessage(String url,String text,String openid,long createTime) throws IOException{
		String message = "<xml><ToUserName><![CDATA[uname]]></ToUserName><FromUserName><![CDATA["+openid+"]]></FromUserName><CreateTime>"
				+createTime
				+"</CreateTime><MsgType><![CDATA[image]]></MsgType><PicUrl><![CDATA[this is a url]]></PicUrl><MediaId><![CDATA[media_id]]></MediaId><MsgId>1234567890123456</MsgId></xml>";
		return SimpleHttpClient.invokePostWithBody4String(url, message,3000);
	}
	public static String sendVoiceMessage(String url,String text,String openid,long createTime) throws IOException{
		String message = "<xml><ToUserName><![CDATA[uname]]></ToUserName><FromUserName><![CDATA["+openid+"]]></FromUserName><CreateTime>"
				+createTime
				+"</CreateTime><MsgType><![CDATA[voice]]></MsgType><MediaId><![CDATA[media_id]]></MediaId><Format><![CDATA[Format]]></Format><MsgId>1234567890123456</MsgId></xml>";
		return SimpleHttpClient.invokePostWithBody4String(url, message,3000);
	}
	public static String sendVideoMessage(String url,String text,String openid,long createTime) throws IOException{
		String message = "<xml><ToUserName><![CDATA[uname]]></ToUserName><FromUserName><![CDATA["+openid+"]]></FromUserName><CreateTime>"
				+createTime
				+"</CreateTime><MsgType><![CDATA[video]]></MsgType><MediaId><![CDATA[media_id]]></MediaId><ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId><MsgId>1234567890123456</MsgId></xml>";
		return SimpleHttpClient.invokePostWithBody4String(url, message,3000);
	}
	public static String sendLocationEventMessage(String url,String openid,long createTime) throws IOException{
		String message = "<xml><ToUserName><![CDATA[uname]]></ToUserName><FromUserName><![CDATA["+openid+"]]></FromUserName><CreateTime>"
				+createTime
				+"</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[LOCATION]]></Event><Latitude>31.223429</Latitude><Longitude>121.389366</Longitude><Precision>86.464020</Precision></xml>";
		return SimpleHttpClient.invokePostWithBody4String(url, message,3000);
	}
	public static String sendLinkMessage(String url,String text,String openid,long createTime) throws IOException{
		String message = "<xml><ToUserName><![CDATA[uname]]></ToUserName><FromUserName><![CDATA["+openid+"]]></FromUserName><CreateTime>"
				+createTime
				+"</CreateTime><MsgType><![CDATA[link]]></MsgType><Title><![CDATA[公众平台官网链接]]></Title><Description><![CDATA[公众平台官网链接]]></Description><Url><![CDATA[url]]></Url><MsgId>1234567890123456</MsgId></xml>";
		return SimpleHttpClient.invokePostWithBody4String(url, message,3000);
	}
}