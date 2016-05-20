package com.zaofans.utils;

import com.bokesoft.myerp.common.StringUtil;

public class ZFConfigUtil {
	private ZFConfigUtil() {
	}
	public static String getUserCenterUserRefundURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.usercenter.refund",null);
	}
	public static String getUserCenterUserPayURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.usercenter.pay",null);
	}
	public static String getUserCenterUserInfoURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.usercenter.userinfo",null);
	}
	public static String getUserCenterUserUpdate2URL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.usercenter.userupdate2",null);
	}
	public static String getUserCenterSelectUserBySceneIDURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.usercenter.selectUserBySceneId",null);
	}
	public static String getUserCenterSelectUserByWXIdURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.usercenter.selectUserByWXId",null);
	}
	public static String getUserCenterSelectUserByIdURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.usercenter.selectUserById",null);
	}
	public static String getUserCenterUpdateRoleLevelURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.usercenter.updateByActivity",null);
	}
	public static String getUserCenterRegisterForWebChatURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.usercenter.registerForWeChat",null);
	}
	public static String getUserCenterUserConfirmRechargeURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.usercenter.confirmRecharge",null);
	}
	// ===================================================================== //
	public static String getOrderFormOrderUpdateDispAddressURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.orderform.order.updatedispaddress",null);
	}
	public static String getOrderFormOrderUpdateURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.orderform.order.update",null);
	}
	public static String getOrderFormOrderRefundURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.orderform.order.refund",null);
	}
	public static String getOrderFormOrderCancelURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.orderform.order.orderCancel",null);
	}
	public static String getOrderFormFaHuoURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.orderform.order.faHuo",null);
	}
	public static String getOrderFormQianShouURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.orderform.order.qianShou",null);
	}
	public static String getOrderFormCityAddURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.orderform.city.add",null);
	}
	public static String getOrderFormCityDeleteURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.orderform.city.delete",null);
	}
	public static String getOrderFormAreaAddURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.orderform.area.add",null);
	}
	public static String getOrderFormAreaDeleteURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.orderform.area.delete",null);
	}
	public static String getOrderFormDundianAddURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.orderform.dundian.add",null);
	}
	public static String getOrderFormDundianDeleteURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.orderform.dundian.delete",null);
	}
	public static String getOrderFormBuildingAddURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.orderform.building.add",null);
	}
	public static String getOrderFormBuildingDeleteURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.orderform.building.delete",null);
	}
	// ===================================================================== //
	public static String getProductMealAddURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.product.meal.add",null);
	}
	public static String getProductMealUpdateURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.product.meal.update",null);
	}
	public static String getProductMealDeleteURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.product.meal.delete",null);
	}
	public static String getProductMealURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.product.meal",null);
	}
	public static String getProductMealsURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.product.meals",null);
	}
	public static String getProductTypeAdd(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.product.type.add",null);
	}
	public static String getDeliveryTime(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.deliverytime.add",null);
	}
	// ===================================================================== //
	public static String getSCMOrderPushURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.scm.order.push",null);
	}
	public static String getSCMRewardPushURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.scm.reward.push",null);
	}
	public static String getSCMUserPushURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.scm.user.push",null);
	}
	// ===================================================================== //
	public static String getActivityDPCXRulesSaveURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.activity.dpcxrules.save",null);
	}
	public static String getActivityBYCXRulesSaveURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.activity.bycxrules.save",null);
	}
	public static String getActivityCZRulesSaveURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.activity.czrules.save",null);
	}
	public static String getActivityCZRulesCheckURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.activity.czrules.check",null);  // 订单或其他SOA调用充值活动
	}
	public static String getActivityUserPushSceneIDURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.activity.user.pushSceneID",null);
	}
	public static String getActivityCityIdShanghai(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"activity.cityId.shanghai",null);
	}
	public static String getActivityCreateQRCodeActivityURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.activity.qrcode.createActivity",null);
	}
	public static String getActivityUpdateQRCodeActivityURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.activity.qrcode.updateActivity",null);
	}
	public static String getActivityCreateBrandQRCodeActivityURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.activity.qrcode.createBrandActivity",null);
	}
	public static String getActivityUpdateBrandQRCodeActivityURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.activity.qrcode.updateBrandActivity",null);
	}
	public static String getActivityCreateBrandURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.activity.qrcode.createBrand",null);
	}
	public static String getActivityCreateQRCodeUserURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.activity.qrcode.createQRCodeUser",null);
	}
	public static String getPromotionCreateURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.activity.promotion.create",null);
	}
	// ===================================================================== //
	public static String getWechatCodeURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.wechat.code.url",null);
	}
	public static String getWechatLongLink2ShortLinkURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.wechat.longLink2shortLink",null);
	}
	public static String getWechatParseOrderXml2JsonURL(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.wechat.parseOrderXml2Json",null);
	}
	public static String getWechatPayKey(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.weixin.pay.key",null);
	}
	public static Object getWechatStoreName(String DSN) {
		DSN = getDSN(DSN);
		return PropUtil.getProperty(DSN,"server.weixin.pay.storename",null);
	}
	// ===================================================================== //
	private static String getDSN(String DSN) {
//		if (ZFHttpRequestImpl.ZF_CHANGEDSN_COOKIE != null) { // 胖客户端
//			DSN = "SOA@" + ZFHttpRequestImpl.ZF_CHANGEDSN_COOKIE.toUpperCase();
//			return DSN;
//		}
		DSN = StringUtil.isEmpty(DSN) ? "SOA" : "SOA@" + DSN;
		return DSN;
	}
	// ===================================================================== //
}
