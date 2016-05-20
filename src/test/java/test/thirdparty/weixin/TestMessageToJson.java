package test.thirdparty.weixin;

import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;

public class TestMessageToJson {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SOAResponseMessage message = new SOAResponseMessage(0, "test");
		System.out.println(message.toString());
		System.out.println("{\"code\":"+message.getCode()+",\"message\":\""+message.getMessage()+"\"}");
	}

}
