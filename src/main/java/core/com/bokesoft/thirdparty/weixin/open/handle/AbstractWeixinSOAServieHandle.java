package com.bokesoft.thirdparty.weixin.open.handle;

import com.bokesoft.thirdparty.weixin.open.WeixinSOAServieHandle;

/**
 * 用于远程调用第三方组件处理用户发来的指令
 *
 */
public abstract class AbstractWeixinSOAServieHandle implements WeixinSOAServieHandle {

	private boolean needValidate = true;

	public boolean needValidate() {
		return this.needValidate;
	}

	public void setNeedValidate(boolean needValidate) {
		this.needValidate = needValidate;
	}

}
