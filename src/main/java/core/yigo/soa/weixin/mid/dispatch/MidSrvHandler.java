package yigo.soa.weixin.mid.dispatch;

import java.io.InputStream;

import com.bokesoft.myerp.common.ByteUtil;
import com.bokesoft.myerp.common.DebugUtil;
import com.bokesoft.myerp.common.MidClassName;
import com.bokesoft.myerp.common.MidMethodName;
import com.bokesoft.myerp.common.RttiUtil;
import com.bokesoft.myerp.common.StringUtil;
import com.bokesoft.myerp.common.intf.IContext;
import com.bokesoft.myerp.common.intf.IDispatchImpl;
import com.bokesoft.myerp.common.midproxy.Env;
import com.bokesoft.myerp.common.midproxy.FunctionPara;
import com.bokesoft.myerp.common.midproxy.Request;
import com.bokesoft.myerp.exception.BKException;

/**
 * 现用的中间层代理句柄
 * 
 * @author ZhouXW
 */
class MidSrvHandler implements MidHandler {

	public byte[] doAction(IContext context) {
		long t = System.currentTimeMillis();
		DebugUtil.debug(">midcontext start.");
		// Class[] paramTypes = null;// mr.getParamTypes();
		Object[] paramValues = null;// mr.getParamValues
		Object returnVal = null;
		Request request = null;
		try {
			request = new Request(); // 解析协议
			DebugUtil.debug(">new Request()");

			request.setEnv(new Env());
			// (-- 1");
			context.setEnv(request.getEnv());
			// (-- 2");
			InputStream is = null;
			try {
				// (-- 3");
				is = context.getInputStream();
				byte[] bytes = ByteUtil.getBytes(is); // TODO 要不要保留bytes?
				// (-- 4");
				request.parserClient(bytes); // 会对this.returnEnv重新计算
				// (-- 5");
			} finally {
				if (is != null)
					is.close();
			}
			MidClassName className = request.getMidClassName();
			// (-- 6");
			MidMethodName staticMethodName = request.getFunctionName();
			// (-- 7");
			String dispatchName = request.getMidDispatchName();
			// (-- 8");
			FunctionPara[] finalFp = request.getFunctionParaList();
			// (-- 9");
			paramValues = new Object[finalFp.length];
			for (int paramCount = 0; paramCount < finalFp.length; paramCount++) {
				if (finalFp[paramCount].isHasValue() || finalFp[paramCount].getMode() != FunctionPara.IN) {
					// paramTypes[paramCount] = finalFp[paramCount]
					// .getParamClass();
					paramValues[paramCount] = finalFp[paramCount].getParamValue();
				}
			}
			// (-- 10");
			returnVal = dispatch(context, className, staticMethodName, dispatchName, paramValues);
			// (-- 11");
			context.setComplete();
			for (int paramIndex = 0; paramIndex < finalFp.length; paramIndex++) {
				if (finalFp[paramIndex].getMode() != FunctionPara.IN) {
					finalFp[paramIndex].setValue(paramValues[paramIndex]);
				}
			}
			request.setFunctionParaList(finalFp);
			if (request.isReturnValue())
				request.setReturnValue(returnVal);
			byte[] bytes = request.buildClient();
			return bytes;
		} catch (Throwable e) {
			// 出错的处理,向客户端返回错误
			DebugUtil.error(e);
			byte[] bytes = null;
			if (request != null) {
				request.setErr(e);
				try {
					bytes = request.buildClient();
				} catch (Throwable e1) {
					e1.printStackTrace(); // ignore
				}
			}
			return bytes;
		} finally {
			DebugUtil.debug(">midcontext end.c=" + (System.currentTimeMillis() - t));
		}
	}
 
	public byte[] doAction(byte[] bytes) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

	public Object dispatch(IContext context, MidClassName midClassName,
			MidMethodName functionName, String dispatchName, Object[] p)
			throws Throwable {
		if (StringUtil.isBlankOrNull(dispatchName)) 
			throw new BKException("DispatchName不能为空");
		IDispatchImpl dispatchImp = (IDispatchImpl) RttiUtil.instanceOnce(dispatchName.trim());
		if (dispatchImp==null)
			throw new Throwable("类没有实现"+dispatchName);
		return dispatchImp.dispatch(context, midClassName.content, functionName.content, p);
		
	} 

}