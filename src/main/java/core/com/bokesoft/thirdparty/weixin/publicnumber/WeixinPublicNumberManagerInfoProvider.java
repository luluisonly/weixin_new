package com.bokesoft.thirdparty.weixin.publicnumber;

import com.bokesoft.thirdparty.weixin.Initable;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumberManagerInfo;

public interface WeixinPublicNumberManagerInfoProvider extends Initable {

	public abstract WeixinPublicNumberManagerInfo getWeixinPublicNumberManagerInfo(WeixinContext context) throws Exception;
	
	public abstract void updateWeixinPublicNumberManagerInfo(WeixinContext context,WeixinPublicNumberManagerInfo publicNumberManagerInfo) throws Exception;
	
}
