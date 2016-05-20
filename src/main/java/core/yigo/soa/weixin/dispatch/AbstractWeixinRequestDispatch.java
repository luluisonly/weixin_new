package yigo.soa.weixin.dispatch;

import com.bokesoft.myerp.common.DebugUtil;
import com.bokesoft.myerp.common.intf.IContext;
import com.bokesoft.myerp.common.intf.IDispatchImpl;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteMessage;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRemoteSessionImpl;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRmoteSession;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinMessageCacheStorage;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRemoteMessageService;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRemoteMessageServiceImpl;
import com.bokesoft.thirdparty.weixin.service.WeixinReplyMessageJsonBuilderImpl;

/**
 * 微信调用YIGO在YIGO端的Dispatch
 */
@Deprecated
public abstract class AbstractWeixinRequestDispatch implements IDispatchImpl {
	
	private WeixinReplyMessageJsonBuilderImpl builderImpl = new WeixinReplyMessageJsonBuilderImpl();
	
	private WeixinRemoteMessageService weixinRemoteMessageService = new WeixinRemoteMessageServiceImpl();
	
	private static boolean inited = false;
	
	public Object dispatch(IContext context, String className,String methodName, Object[] params) throws Throwable {
		if (!inited) {
			synchronized (AbstractWeixinRequestDispatch.class) {
				if (!inited) {
					this.initWeixinConfig();
					inited = true;
				}
			}
		}
		if (params.length < 3) {
			DebugUtil.debug("param length less than 3");
			return SOAResponseMessage.NULL;
		}
		String username = String.valueOf(params[1]);
		String password = String.valueOf(params[2]);
		if (!validate(username, password)) {
			DebugUtil.debug("invoke unauthorized");
			return SOAResponseMessage.NULL;
		}
		try {
			SOAResponseMessage message = SOAResponseMessage.NULL;
			WeixinRemoteMessage remoteMessage = null;
				remoteMessage = (WeixinRemoteMessage) builderImpl.buildMessage(String.valueOf(params[0]));
				WeixinRmoteSession cache = WeixinMessageCacheStorage.getWeixinMessageCache(remoteMessage.getFromUserName());
				if (cache == null) {
					cache = new WeixinRemoteSessionImpl(remoteMessage.getFromUserName(),remoteMessage.getToUserName());
					WeixinMessageCacheStorage.putWeixinMessageCache(remoteMessage.getFromUserName(), cache);
				}
				message = weixinRemoteMessageService.doService(cache, remoteMessage);
				return message.toString();
		} catch (Throwable e) {
			e.printStackTrace();
			return SOAResponseMessage.NULL;
		}
	}
	
	private boolean validate(String username,String password){
		//TODO
		return true;
	}

	abstract void initWeixinConfig();
	
}
