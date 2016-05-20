package com.bokesoft.thirdparty.weixin.example.flow;

import com.bokesoft.thirdparty.weixin.bean.WeixinMessageFlow;
import com.bokesoft.thirdparty.weixin.flow.WeixinMessageFlowHandle;

public class DefaultFlowTest implements WeixinMessageFlow{

	private static final long serialVersionUID = 5324135949755633658L;
	private WeixinMessageFlowHandle[] flowHandles = new WeixinMessageFlowHandle[]{new Step1(),new Step2(),new Step3()};
	
	public WeixinMessageFlowHandle[] getFlowHandles() {
		return flowHandles;
	}

	public String getName() {
		return "本地测试";
	}

	
}
