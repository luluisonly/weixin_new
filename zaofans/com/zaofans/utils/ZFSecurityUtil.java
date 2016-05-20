package com.zaofans.utils;

import com.bokesoft.myerp.common.intf.IContext;
import com.zhouxw.utils.security.EncryptUtil;

public class ZFSecurityUtil {
	// 原始数据指纹(TODO 需要使用非对称加密算法:各SOA集群内部保留自己的私钥，用对方SOA公钥加密传到对方)
	public static String sign(String datas) throws Throwable {
		String sign = EncryptUtil.toHexString(EncryptUtil.security(datas, null), "", "");
		return sign;
	}
	public static void checkSign(String sign, String datas) throws Throwable {
		String sign1 = EncryptUtil.toHexString(EncryptUtil.security(datas, null), "", "");
		if (!sign.equals(sign1)) // 指纹比对
			throw new Throwable("[bad request sign]");
	}
	public static void checkSign(IContext context, String datas) throws Throwable {
		String sign = (String)context.getAttribute("sign"); // getParameter("encryption")
		String sign1 = EncryptUtil.toHexString(EncryptUtil.security(datas, null), "", "");
		if (!sign.equals(sign1)) // 指纹比对
			throw new Throwable("[bad request sign]");
	}
}
