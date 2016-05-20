package test.thirdparty.weixin;

import org.apache.log4j.Logger;


public class TestNull {
	private static final Logger LOGGER = Logger.getLogger(TestNull.class);
	public static void main(String[] args) {
		String str = null;
		LOGGER.info("GetWeixinUserInfo openid:"+str);

//		 str = "null";
//		LOGGER.info("GetWeixinUserInfo openid1:"+str);
	}
}
