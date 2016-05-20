package test.thirdparty.weixin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.thirdparty.weixin.common.SimpleHttpClient;

public class TestCases {
	
	public void test1(){
		System.out.println("#####################");
		
	}
	
	public void testUpdateWeixinPublicNumber() throws IOException{
		new SimpleHttpClient();
		System.out.println("更新微信公众号 start request");
		Map<String,String> map = new HashMap<String,String>();
		map.put("uname", "test1");
		map.put("param", "{grant_type: 'client_credential',app_id : 'wx73d1d82341c7e1ff', secret : '0b08317dbe143291862e162f0c6fb08e', public_Type : 'SERVIC',action:'update'}");
		map.put("type", "updateWeixinPublicNumber");
		String result = SimpleHttpClient.invokePost4String("http://wangkai-pc:8300/yigo/open-api", map,3000);
		
		JSONObject json = JSONObject.parseObject(result);
		boolean success = json.getBoolean("success");
		
		if (!success) {
			System.err.println("更新微信公众号 result:"+result);
		}else{
			System.out.println("更新微信公众号 result:"+result);
		}
		
	}
	
	
	public void testUpdateWeixinPublicNumber4Delete() throws IOException{
		new SimpleHttpClient();
		System.out.println("删除微信公众号 start request");
		Map<String,String> map = new HashMap<String,String>();
		map.put("uname", "test1");
		map.put("param", "{action:'delete'}");
		map.put("type", "updateWeixinPublicNumber");
		String result = SimpleHttpClient.invokePost4String("http://wangkai-pc:8300/yigo/open-api", map,3000);
		
		JSONObject json = JSONObject.parseObject(result);
		boolean success = json.getBoolean("success");
		
		if (!success) {
			System.err.println("删除微信公众号 result:"+result);
		}else{
			System.out.println("删除微信公众号 result:"+result);
		}
		
	}
	
	
	public void testUpdateSubscribeReply() throws IOException{
		new SimpleHttpClient();
		System.out.println("更新关注回复 start request");
		Map<String,String> map = new HashMap<String,String>();
		map.put("uname", "test1");
		//文本消息
		map.put("param", "{msgType: 'text',content : '欢迎使用文本测试'}");
		//图文消息
//		map.put("param", "{msgType: 'news',articles : "
//				+ "[{title:'图文测试',description:'图文测试描述',url:'http://wk1.skypiea.info/yigo/web2/BillUIFormUI.html?k=subsys_SCM_mobile',picUrl:'http://wk1.skypiea.info/yigo/weixin.png'"
//				+ "},{title:'图文测试',description:'图文测试描述',url:'http://wk1.skypiea.info/yigo/web2/BillUIFormUI.html?k=subsys_SCM_mobile',picUrl:'http://wk1.skypiea.info/yigo/weixin.png'}]}");
		map.put("type", "updateSubscribeReply");
		String result = SimpleHttpClient.invokePost4String("http://wangkai-pc:8300/yigo/open-api", map,3000);
		
		JSONObject json = JSONObject.parseObject(result);
		boolean success = json.getBoolean("success");
		
		if (!success) {
			System.err.println("更新关注回复 result:"+result);
		}else{
			System.out.println("更新关注回复 result:"+result);
		}
		
	}
	
	
	public void testUpdateKeywordsReply() throws IOException{
		new SimpleHttpClient();
		System.out.println("更新关键词回复 start request");
		Map<String,String> map = new HashMap<String,String>();
		map.put("uname", "test1");
		//文本消息
		map.put("param", "{serviceKey:'文本测试',msgType: 'text',action:'update',content : '欢迎使用文本测试'}");
		//图文消息
//		map.put("param", "{serviceKey:'图文测试',msgType: 'news',articles : "
//				+ "[{title:'图文测试',description:'图文测试描述',url:'http://wk1.skypiea.info/yigo/web2/BillUIFormUI.html?k=subsys_SCM_mobile',picUrl:'http://wk1.skypiea.info/yigo/weixin.png'"
//				+ "},{title:'图文测试',description:'图文测试描述',url:'http://wk1.skypiea.info/yigo/web2/BillUIFormUI.html?k=subsys_SCM_mobile',picUrl:'http://wk1.skypiea.info/yigo/weixin.png'}]}");
		map.put("type", "updateKeywordsReply");
		String result = SimpleHttpClient.invokePost4String("http://wangkai-pc:8300/yigo/open-api", map,3000);
		
		JSONObject json = JSONObject.parseObject(result);
		boolean success = json.getBoolean("success");
		
		if (!success) {
			System.err.println("更新关键词回复 result:"+result);
		}else{
			System.out.println("更新关键词回复 result:"+result);
		}
		
	}
	
	
	public void testUpdateKeywordsReply4Delete() throws IOException{
		new SimpleHttpClient();
		System.out.println("删除关键词回复 start request");
		Map<String,String> map = new HashMap<String,String>();
		map.put("uname", "test1");
		//文本消息
		map.put("param", "{serviceKey:'文本测试',action:'delete'}");
		//图文消息
//		map.put("param", "{serviceKey:'图文测试',msgType: 'news',articles : "
//				+ "[{title:'图文测试',description:'图文测试描述',url:'http://wk1.skypiea.info/yigo/web2/BillUIFormUI.html?k=subsys_SCM_mobile',picUrl:'http://wk1.skypiea.info/yigo/weixin.png'"
//				+ "},{title:'图文测试',description:'图文测试描述',url:'http://wk1.skypiea.info/yigo/web2/BillUIFormUI.html?k=subsys_SCM_mobile',picUrl:'http://wk1.skypiea.info/yigo/weixin.png'}]}");
		map.put("type", "updateKeywordsReply");
		String result = SimpleHttpClient.invokePost4String("http://wangkai-pc:8300/yigo/open-api", map,3000);
		
		JSONObject json = JSONObject.parseObject(result);
		boolean success = json.getBoolean("success");
		
		if (!success) {
			System.err.println("删除关键词回复 result:"+result);
		}else{
			System.out.println("删除关键词回复 result:"+result);
		}
		
	}
	
	
	public void testUpdateDefaultReply() throws IOException{
		new SimpleHttpClient();
		System.out.println("更新默认回复 start request");
		Map<String,String> map = new HashMap<String,String>();
		map.put("uname", "test1");
		//文本消息
		map.put("param", "{msgType: 'text',content : '欢迎使用文本测试'}");
		//图文消息
//		map.put("param", "{msgType: 'news',articles : "
//				+ "[{title:'图文测试',description:'图文测试描述',url:'http://wk1.skypiea.info/yigo/web2/BillUIFormUI.html?k=subsys_SCM_mobile',picUrl:'http://wk1.skypiea.info/yigo/weixin.png'"
//				+ "},{title:'图文测试',description:'图文测试描述',url:'http://wk1.skypiea.info/yigo/web2/BillUIFormUI.html?k=subsys_SCM_mobile',picUrl:'http://wk1.skypiea.info/yigo/weixin.png'}]}");
		map.put("type", "updateDefaultReply");
		String result = SimpleHttpClient.invokePost4String("http://wangkai-pc:8300/yigo/open-api", map,3000);
		
		JSONObject json = JSONObject.parseObject(result);
		boolean success = json.getBoolean("success");
		
		if (!success) {
			System.err.println("更新默认回复 result:"+result);
		}else{
			System.out.println("更新默认回复 result:"+result);
		}
		
	}
	
	public void testUpdateWeixinMenu() throws IOException{
		System.out.println("更新微信自定义菜单 start request");
		Map<String,String> map = new HashMap<String,String>();
		map.put("uname", "53312");
		//文本消息
		map.put("param", "{'button': [{'type': 'click','name': '街道服务','key': '街道服务'},{'type': 'click','name': '网上商城','key': '网上商城'},{'name': '问卷调查','sub_button': [{'type': 'click','name': '小区问卷调查','key': '小区问卷调查'}]}]}");
		map.put("param", "{'button':[{'name':'问卷调查','msgType':'text','sub_button':[{'name':'小区问卷调查','msgType':'text','type':'click','key':'小区问卷调查'},{'name':'绑定','msgType':'text','type':'click','key':'绑定'},{'name':'待办','msgType':'text','type':'click','key':'待办'},{'name':'已办','msgType':'text','type':'click','key':'已办'}]},{'name':'关于YIGO','msgType':'text','sub_button':[{'name':'3D打印','msgType':'text','type':'click','key':'3DDY'}]},{'name':'智慧社区','msgType':'text','sub_button':[{'name':'街道服务','msgType':'text','type':'click','key':'街道服务'},{'name':'网上商城','msgType':'text','type':'click','key':'网上商城'}]}]}");
		map.put("type", "updateWeixinMenu");
		String result = SimpleHttpClient.invokePost4String("http://localhost:8300/yigo/open-api", map,3000);
		
		JSONObject json = JSONObject.parseObject(result);
		int code = json.getIntValue("code");
		
		if (code != 0) {
			System.err.println("更新微信自定义菜单 result:"+result);
		}else{
			System.out.println("更新微信自定义菜单 result:"+result);
		}
		
	}
	
	
	public void testSendMessage() {
		//http://localhost:8300/yigo/open-api?type=sendWeixinMessage&param={%20%22touser%22:%22oVwitjvhu-EZJ3s5ofrIHk8RL4YQ%22,%20%22msgtype%22:%22text%22,%20%22text%22:%20{%20%22content%22:%22Hello%20World%22%20}%20}&uname=53312
	}
	
	
	public void testGetUserinfo() {
		//http://localhost:8300/yigo/open-api?type=getWeixinUserInfo&param=oVwitjvhu-EZJ3s5ofrIHk8RL4YQ&uname=53312

	}
	
	
	public static void main(String[] args) throws IOException {
		TestCases testCases = new TestCases();
		testCases.testUpdateWeixinPublicNumber();
		testCases.testUpdateSubscribeReply();
		testCases.testUpdateKeywordsReply();
		testCases.testUpdateDefaultReply();
		testCases.testUpdateWeixinMenu();
		testCases.testUpdateKeywordsReply4Delete();
	}
}
