package com.bokesoft.thirdparty.weixin.example.flow.location;

import com.bokesoft.thirdparty.weixin.bean.WeixinMessageFlow;
import com.bokesoft.thirdparty.weixin.flow.WeixinMessageFlowHandle;

public class LocationFindFlowTest implements WeixinMessageFlow {

	private static final long serialVersionUID = -3213092745849670717L;
	private WeixinMessageFlowHandle[] flowHandles = new WeixinMessageFlowHandle[]{new LBSFindStep1(),new LBSFindStep2()};
	
	public WeixinMessageFlowHandle[] getFlowHandles() {
		return flowHandles;
	}

	public String getName() {
		return "附近";
	}
	
}
