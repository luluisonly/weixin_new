package com.bokesoft.thirdparty.weixin.publicnumber;

import com.bokesoft.thirdparty.weixin.Initable;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;

/**
 * 微信公众号获取提供者
 *
 */
public interface WeixinPublicNumberProvider extends Initable {
	
	public abstract WeixinPublicNumber getWeixinPublicNumber(WeixinContext context,String uname) throws Exception;
	
	public abstract void updateWeixinPublicNumber(WeixinContext context,WeixinPublicNumber publicNumber)throws Exception;
	
	public abstract void removeWeixinPublicNumber(WeixinContext context,String uname)throws Exception;
	
	public abstract void addWeixinPublicNumber(WeixinContext context,WeixinPublicNumber publicNumber)throws Exception;
}
