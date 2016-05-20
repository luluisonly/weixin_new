package com.bokesoft.thirdparty.weixin.open;

import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOARequestMessage;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;

/**
 * 控制服务处理的实例
 *
 */
public class WeixinSOAServiceImpl implements WeixinSOAService {
	
	private static final Logger LOGGER = Logger.getLogger(WeixinSOAServiceImpl.class);
	
	private static SOAResponseMessage NULL_PARAM = new SOAResponseMessage(1000,"null param");

	public SOAResponseMessage doService(WeixinContext context, SOARequestMessage requestMessage)
			throws Exception {
		WeixinSOAServieHandle servieHandle = context.getWeixinSOAServieHandle(requestMessage
				.getType());
		if (servieHandle == null) {
			LOGGER.warn("no such service:" + requestMessage.getType());
			Map<String, WeixinSOAServieHandle> weixinSOAServieHandleMap = context.getWeixinSOAServieHandles();
			Set<String>  names = weixinSOAServieHandleMap.keySet();
			StringBuffer buffer = new StringBuffer("Did not found service :");
			buffer.append(requestMessage.getType());
			buffer.append(",there are services");
			for(String name:names){
				buffer.append(",");
				buffer.append(name);
			}
			return new SOAResponseMessage(1000,buffer.toString());
		}
		if (servieHandle.needValidate() && !validate(requestMessage.getUsername(), requestMessage.getPassword())) {
			LOGGER.warn("request forbidden");
			return SOAResponseMessage.REQUEST_FORBIDDEN;
		}
		if (requestMessage.getObjectParam() == null) {
			LOGGER.warn("null param");
			return NULL_PARAM;
		}
		LOGGER.info("invoke soa service:" +requestMessage.getType());
		SOAResponseMessage responseMessage = servieHandle.handle(context, requestMessage);
		LOGGER.info("response for "+requestMessage.getType()+" >>: "+responseMessage.toString());
		return responseMessage;
	}

	private boolean validate(String username, String password) {
		//TODO 后期会加上身份认证
		
		return true;
	}

}
