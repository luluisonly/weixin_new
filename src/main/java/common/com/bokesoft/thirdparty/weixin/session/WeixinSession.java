package com.bokesoft.thirdparty.weixin.session;

import java.io.Serializable;
import java.util.Set;

import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.flow.WeixinMessageFlowLocalService;
import com.bokesoft.thirdparty.weixin.flow.WeixinMessageFlowService;
import com.bokesoft.thirdparty.weixin.remote.extend.WeixinRmoteSession;

/**
 * 
 * 微信session 存储会话信息的地方
 *
 */
public interface WeixinSession extends WeixinRmoteSession,Serializable{

//	/**
//	 * 激活session最后使用时间为当前时间
//	 */
//	public abstract void active();

	/**
	 * 获取ApplicationContext实例
	 * @return
	 */
	public abstract WeixinContext getWeixinContext();

//	/**
//	 * 获取session创建时间
//	 * @return
//	 */
//	public abstract long getCreationTime();

	/**
	 * 获取sessionid
	 * @return
	 */
	public abstract String getId();
	
//	/**
//	 * 获取上次使用时间
//	 * @return
//	 */
//	public abstract long getLastAccessedTime();

//	/**
//	 * 获取session存活时长
//	 * @return
//	 */
//	public abstract long getMaxInactiveInterval();

	/**
	 * 获取WeixinMessageFlow，检查是否处在FLow当中
	 * @return
	 */
	public abstract WeixinMessageFlowLocalService getWeixinMessageFlowLocalService();
	
	/**
	 * 获取微信公众号
	 * @return
	 * @throws Exception
	 */
	public abstract WeixinPublicNumber getWeixinPublicNumber()throws Exception;

//	/**
//	 * 验证session是否已过期
//	 * @return
//	 */
//	public abstract boolean isValid();
	
//	/**
//	 * 设置session存活时长
//	 * @param millisecond
//	 */
//	public abstract void setMaxInactiveInterval(long millisecond);
	
	
	public abstract void setWeixinMessageFlowLocalService(WeixinMessageFlowLocalService weixinMessageFlowLocalService);
	
	public abstract Object getAttribute(String key);

	public abstract Set<String> getAttributeNames();
	
	public abstract String getOpenid() ;
	
	public abstract String getPublicName() ;

	public abstract WeixinMessageFlowService getWeixinMessageFlowService();
	
	public abstract void setAttribute(String key, Object value);
	
	public abstract void setOpenid(String openid) ;
	
	public abstract void setPublicName(String uname) ;
	
	public abstract void removeAttribute(String key);
	
	public abstract void setWeixinMessageFlowService(WeixinMessageFlowService weixinMessageFlowService);
	
}
