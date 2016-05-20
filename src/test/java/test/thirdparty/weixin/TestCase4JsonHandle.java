package test.thirdparty.weixin;

import com.bokesoft.thirdparty.weixin.service.WeixinReplyMessageJsonBuilderImpl;

public class TestCase4JsonHandle {
	public static void main(String[] args) throws Exception {
		String content =  "{ToUserName:'toUser',FromUserName:'FromUser',CreateTime:'123456',MsgType:'text',Content:'欢迎使用文本测试',MsgId:'1234567890123456'}";
		new WeixinReplyMessageJsonBuilderImpl().buildMessage(content);
	}
}
