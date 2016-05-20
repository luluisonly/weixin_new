package yigo.soa.weixin.mid.dispatch;

import com.bokesoft.myerp.common.intf.IContext;

/**
 * @author zhouxw
 * 
 * 中间层处理句柄
 */
public interface MidHandler {

	/**
	 * 根据服务端上下文进行相关处理
	 * 
	 * @param context
	 *            服务端上下文
	 * @return byte[] 返回处理结果
	 * @throws Throwable
	 */
	public byte[] doAction(IContext context) throws Throwable;
	

	/**
	 * 根据服务端上下文进行相关处理
	 * 
	 * @param context
	 *            服务端上下文
	 * @return byte[] 返回处理结果
	 * @throws Throwable
	 */
	public byte[] doAction(byte[] bytes) throws Throwable;

}
