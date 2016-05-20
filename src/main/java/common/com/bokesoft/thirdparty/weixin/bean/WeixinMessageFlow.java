package com.bokesoft.thirdparty.weixin.bean;

import java.io.Serializable;

import com.bokesoft.thirdparty.weixin.flow.WeixinMessageFlowHandle;

public interface WeixinMessageFlow extends Serializable{

	public abstract WeixinMessageFlowHandle[] getFlowHandles();

	public abstract String getName() ;

}
