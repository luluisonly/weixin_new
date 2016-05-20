package yigo.soa.weixin.mid.dispatch;

import java.io.Closeable;
import java.io.OutputStream;
import java.io.Writer;
import java.sql.Connection;

import com.bokesoft.myerp.common.intf.IContext;
import com.bokesoft.myerp.common.io.CloseUtil;

/**
 * 中间层proxy通用上下文
 * 
 * @author zhouxw
 */
abstract class CommonContext implements IContext, Closeable {

	private static final long serialVersionUID = 1L;

	// /**
	// * context临时变量
	// *
	// * 其实catalina的request的getParameter存储结构也是hashmap servlet退出后清空
	// */
	// protected transient HashMap<String, Object> paraData = new
	// HashMap<String, Object>();
	/**
	 * 中间层翻译
	 * 
	 * @param text
	 * @return String
	 */
	@Override
	public String T(String text) {
		return text;
	}
	// 对象清理器
	protected transient Clear clear = new Clear();
	@Override
	public void addConn(Connection conn) {
		this.clear.addConn(conn);
	}
	@Override
	public void close() {
		try {
			this.clear.clean();
		} catch (Throwable ignore) {
			// ignore.printStackTrace();
		}
	}
	@Override
	public void cleanState() throws Throwable {
		this.clear.cleanState();
	}
	@Override
	public String getURLParams() {
		return getURLParamsByIgnored(null);
	}

	// paraData.put(name, value);
	@Override
	public void setParameter(String name, Object value) {
		this.setAttribute(name, value);
	}
	@Override
	public void write(byte[] bytes) throws Throwable {
		OutputStream out = null;
		try {
			if(!isCommitted()){
				out = getOutputStream();
				out.write(bytes);
				out.flush();
			}
		}
		// catch (Throwable ex) {
		// throw ex;
		// }
		finally {
			CloseUtil.close(out);
		}
	}
	@Override
	public void write(Object obj) throws Throwable {
		Writer out = null;
		try {
			if(!isCommitted()){
				out = getWriter();
				out.write(String.valueOf(obj));
				out.flush();
			}
		}
		// catch (Throwable ex) {
		// throw ex;
		// }
		finally {
			CloseUtil.close(out);
		}
	}

	public boolean isCloseImmd() {
		return false;
	}

}