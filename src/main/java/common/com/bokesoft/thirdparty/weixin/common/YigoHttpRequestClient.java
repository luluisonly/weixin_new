package com.bokesoft.thirdparty.weixin.common;

import com.bokesoft.myerp.common.midproxy.Env;
import com.bokesoft.myerp.common.midproxy.Request;
import com.bokesoft.myerp.common.midproxy.RequestImplFactory;

/**
 * HTTP方式远程调用YIGO
 *
 */
public class YigoHttpRequestClient {
	
	private static Request createRequest(String midClassName,
			String midMethodName, String dispatchName, Env env, boolean returnEnv,final String server) {
		return new Request(midClassName, midMethodName, dispatchName, env, returnEnv) {
			protected void createImpl() {
//				if (this.requestImpl == null) {
//					synchronized(RequestImpl.class) {
//						if (this.requestImpl == null) {
							this.requestImpl = RequestImplFactory.createRequestImpl(server);
//						}
//					}
//				}
			}
		};
	}
	
	public static Object request(YigoHttpRequestHeader header,final String server,Object []params) throws Exception {
		Request request = createRequest(header.getMidClassName(), header.getMidMethodName(), header.getDispatchName()
				, header.getEnv(), header.isReturnEnv(),server);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				request.addPara(i, params[i]);
			}
		}
//		try {
//			ContextUtil.getContext().setAttribute(DataConstant.STR_AUTOCOMMITUI, true);
//		} catch (Throwable e) {
//			throw new Exception(e);
//		}
		request.addReturnString();
		request.request();
		return request.getReturnValue();
	}
	

	
}
