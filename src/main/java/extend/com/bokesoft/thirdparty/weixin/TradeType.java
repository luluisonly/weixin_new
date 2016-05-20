package com.bokesoft.thirdparty.weixin;

public enum TradeType {
	RECHARGE("C") // 充值余额
 	,MARK("M"); //微信支付，只是记录不用扣余额
	private String type;
	private TradeType(String type){
		this.type = type;
	}
	@Override
	public String toString(){
		return this.type;
	}
	public static TradeType parse(String type) throws Exception {
		if ("C".equalsIgnoreCase(type))
			return RECHARGE;
		else if("M".equalsIgnoreCase(type))
			return MARK;
		throw new Exception("unkown tradetype");
	}
}
