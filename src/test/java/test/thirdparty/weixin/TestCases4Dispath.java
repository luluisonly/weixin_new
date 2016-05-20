package test.thirdparty.weixin;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.myerp.common.datatype.VarUtil;
import com.bokesoft.myerp.common.midproxy.Env;
import com.bokesoft.myerp.common.midproxy.MAP_C_ENV;
import com.bokesoft.myerp.common.midproxy.Request;
import com.bokesoft.myerp.common.midproxy.RequestImpl;
import com.bokesoft.myerp.common.midproxy.RequestImplFactory;

public class TestCases4Dispath {
	
	
	private String testMethod(String type,String uname,String param) {
		String DSN = MAP_C_ENV.getDefaultDSN();
		Env env = MAP_C_ENV.createEnv(DSN);
		Request r = createRequest("", "", "com.bokesoft.thirdparty.weixin.WeixinSOADispatch", env, false);
		r.addPara(0, uname);
		r.addPara(1, "userid");
		r.addPara(2, type);
		r.addPara(3, param);
		r.addPara(4, "username");
		r.addPara(5, "password");
		r.addReturnString();
		r.request();
		return VarUtil.toString(r.getReturnValue());
	}
	
	private Request createRequest(String midClassName,
			String midMethodName, String dispatchName, Env env, boolean returnEnv) {
		final String server = "http://localhost:8300/yigo"; // SharedBundle.getProperties("server.soa.weixin");//"http://1.1.7.47:8080/yigo");
		return new Request(midClassName, midMethodName, dispatchName, env, returnEnv) {
			protected void createImpl() {
				if (this.requestImpl == null) {
					synchronized(RequestImpl.class) {
						if (this.requestImpl == null) {
							this.requestImpl = RequestImplFactory.createRequestImpl(server);
						}
					}
				}
			}
		};
	}
	
	
	public void testUpdateWeixinPublicNumber() throws IOException{
		System.out.println("更新微信公众号 start request");
		String result = testMethod("updateWeixinPublicNumber", "test1", "{grant_type: 'client_credential',app_id : 'wx73d1d82341c7e1ff', secret : '0b08317dbe143291862e162f0c6fb08e', public_Type : 'SERVIC',action:'update'}");
		JSONObject json = JSONObject.parseObject(result);
		boolean success = json.getBoolean("success");
		if (!success) {
			System.err.println("更新微信公众号 result:"+result);
		}else{
			System.out.println("更新微信公众号 result:"+result);
		}
		
	}
	
	
	public void testUpdateWeixinPublicNumber4Delete() throws IOException{
		System.out.println("删除微信公众号 start request");
		String result = testMethod( "updateWeixinPublicNumber","test1", "{action:'delete'}");
		JSONObject json = JSONObject.parseObject(result);
		boolean success = json.getBoolean("success");
		if (!success) {
			System.err.println("删除微信公众号 result:"+result);
		}else{
			System.out.println("删除微信公众号 result:"+result);
		}
		
	}
	
	public void testUpdateSubscribeReply() throws IOException{
		System.out.println("更新关注回复 start request");
		//文本消息
		//图文消息
//		map.put("param", "{msgType: 'news',articles : "
//				+ "[{title:'图文测试',description:'图文测试描述',url:'http://wk1.skypiea.info/yigo/web2/BillUIFormUI.html?k=subsys_SCM_mobile',picURL:'http://wk1.skypiea.info/yigo/weixin.png'"
//				+ "},{title:'图文测试',description:'图文测试描述',url:'http://wk1.skypiea.info/yigo/web2/BillUIFormUI.html?k=subsys_SCM_mobile',picURL:'http://wk1.skypiea.info/yigo/weixin.png'}]}");
		String result = testMethod( "updateSubscribeReply","test1","{msgType: 'text',content : '欢迎使用文本测试'}");
		JSONObject json = JSONObject.parseObject(result);
		boolean success = json.getBoolean("success");
		
		if (!success) {
			System.err.println("更新关注回复 result:"+result);
		}else{
			System.out.println("更新关注回复 result:"+result);
		}
		
	}
	
	
	public void testUpdateKeywordsReply() throws IOException{
		System.out.println("更新关键词回复 start request");
		//图文消息
//		map.put("param", "{serviceKey:'图文测试',msgType: 'news',articles : "
//				+ "[{title:'图文测试',description:'图文测试描述',url:'http://wk1.skypiea.info/yigo/web2/BillUIFormUI.html?k=subsys_SCM_mobile',picURL:'http://wk1.skypiea.info/yigo/weixin.png'"
//				+ "},{title:'图文测试',description:'图文测试描述',url:'http://wk1.skypiea.info/yigo/web2/BillUIFormUI.html?k=subsys_SCM_mobile',picURL:'http://wk1.skypiea.info/yigo/weixin.png'}]}");
		String result = testMethod("updateKeywordsReply", "test1","{serviceKey:'文本测试',msgType: 'text',action:'update',content : '欢迎使用文本测试'}");
		JSONObject json = JSONObject.parseObject(result);
		boolean success = json.getBoolean("success");
		
		if (!success) {
			System.err.println("更新关键词回复 result:"+result);
		}else{
			System.out.println("更新关键词回复 result:"+result);
		}
		
	}
	
	
	public void testUpdateKeywordsReply4Delete() throws IOException{
		System.out.println("删除关键词回复 start request");
		//图文消息
//		map.put("param", "{serviceKey:'图文测试',msgType: 'news',articles : "
//				+ "[{title:'图文测试',description:'图文测试描述',url:'http://wk1.skypiea.info/yigo/web2/BillUIFormUI.html?k=subsys_SCM_mobile',picURL:'http://wk1.skypiea.info/yigo/weixin.png'"
//				+ "},{title:'图文测试',description:'图文测试描述',url:'http://wk1.skypiea.info/yigo/web2/BillUIFormUI.html?k=subsys_SCM_mobile',picURL:'http://wk1.skypiea.info/yigo/weixin.png'}]}");
		String result = testMethod("updateKeywordsReply","test1","{serviceKey:'文本测试',action:'delete'}");
		JSONObject json = JSONObject.parseObject(result);
		boolean success = json.getBoolean("success");
		
		if (!success) {
			System.err.println("删除关键词回复 result:"+result);
		}else{
			System.out.println("删除关键词回复 result:"+result);
		}
		
	}
	
	
	public void testUpdateDefaultReply() throws IOException{
		System.out.println("更新默认回复 start request");
		//图文消息
//		map.put("param", "{msgType: 'news',articles : "
//				+ "[{title:'图文测试',description:'图文测试描述',url:'http://wk1.skypiea.info/yigo/web2/BillUIFormUI.html?k=subsys_SCM_mobile',picURL:'http://wk1.skypiea.info/yigo/weixin.png'"
//				+ "},{title:'图文测试',description:'图文测试描述',url:'http://wk1.skypiea.info/yigo/web2/BillUIFormUI.html?k=subsys_SCM_mobile',picURL:'http://wk1.skypiea.info/yigo/weixin.png'}]}");
		
		String result = testMethod("updateDefaultReply","test1", "{msgType: 'text',content : '欢迎使用文本测试'}");
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
		
		String result = testMethod("updateWeixinMenu","999", "{\"button\":[{\"name\":\"订餐\",\"sub_button\":[{\"type\":\"click\",\"name\":\"单据测试\",\"key\":\"单据测试\"},{\"type\":\"click\",\"name\":\"订餐\",\"key\":\"订餐\"},{\"type\":\"click\",\"name\":\"查看订餐\",\"key\":\"查看订餐\"},{\"type\":\"click\",\"name\":\"编辑我的菜\",\"key\":\"编辑我的菜\"}]},{\"name\":\"我\",\"sub_button\":[{\"type\":\"click\",\"name\":\"我的钱包 \",\"key\":\"我的钱包\"}]},{\"name\":\"关联YIGO\",\"sub_button\":[{\"type\":\"click\",\"name\":\"绑定\",\"key\":\"绑定\"},{\"type\":\"click\",\"name\":\"解除绑定\",\"key\":\"解除绑定\"}]}]}");
		JSONObject json = JSONObject.parseObject(result);
		boolean success = json.getBoolean("success");
		
		if (!success) {
			System.err.println("更新微信自定义菜单 result:"+result);
		}else{
			System.out.println("更新微信自定义菜单 result:"+result);
		}
		
	}
	public static void main(String[] args) throws IOException {
		TestCases4Dispath testCases = new TestCases4Dispath();
		testCases.testUpdateWeixinPublicNumber();
		testCases.testUpdateSubscribeReply();
		testCases.testUpdateKeywordsReply();
		testCases.testUpdateDefaultReply();
		testCases.testUpdateWeixinMenu();
		testCases.testUpdateKeywordsReply4Delete();
		
		
		
		
	}
}
