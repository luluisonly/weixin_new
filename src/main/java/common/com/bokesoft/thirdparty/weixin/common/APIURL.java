package com.bokesoft.thirdparty.weixin.common;

public class APIURL {

	public static String API_TYPE = null;

	public static String GEN_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?access_token=";

	public static String CREATE_WEIXIN_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";

	public static String SEND_WEIXIN_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";

	public static String GET_WEIXIN_USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=";
	
	public static String Batch_GET_WEIXIN_USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=";
	
	public static String Batch_GET_OPENID_LIST_URL = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=";
	
	public static String GET_WEIXIN_USERINFO_BY_ACCESSTOKEN_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=";

	public static String GET_USER_SUMMARY_URL = "https://api.weixin.qq.com/datacube/getusersummary?access_token=";
	
	public static String GET_USER_CUMULATE_URL = "https://api.weixin.qq.com/datacube/getusercumulate?access_token=";
	
	public static String GET_ARTICLE_SUMMARY_URL = "https://api.weixin.qq.com/datacube/getarticlesummary?access_token=";
	
	public static String GET_ARTICLE_TOTAL_URL = "https://api.weixin.qq.com/datacube/getarticletotal?access_token=";
	
	public static String GET_USER_READ_URL = "https://api.weixin.qq.com/datacube/getuserread?access_token=";
	
	public static String GET_USER_READHOUR_URL = "https://api.weixin.qq.com/datacube/getuserreadhour?access_token=";
	
	public static String GET_USER_SHARE_URL = "https://api.weixin.qq.com/datacube/getusershare?access_token=";
	
	public static String GET_USER_SHAREHOUR_URL = "https://api.weixin.qq.com/datacube/getusersharehour?access_token=";
		
	public static String UPLOAD_WEIXIN_MEDIA_URL = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=";

	public static String DOWNLOAD_WEIXIN_MEDIA_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=";

	public static String GET_OPENID_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?";

	public static String GET_QRCODE = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";
	
	public static String GET_QRCODE_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";

	public static String SEND_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
	
	public static String SEND_ORDERINFO = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	public static String ORDERQUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	
	public static String CLOSEORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
	
	public static String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	
	public static String REFUNDQUERY_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
	
	public static String DOWNLOADBILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
	
	public static String LONG2SHORT_URL = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=";
	
	public static String JSAPITICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=";
	
	public static String BATCHMOVEUSER_URL = "https://api.weixin.qq.com/cgi-bin/groups/members/batchupdate?access_token=";
}
