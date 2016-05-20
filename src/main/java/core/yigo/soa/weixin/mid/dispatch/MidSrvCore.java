package yigo.soa.weixin.mid.dispatch;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bokesoft.myerp.billmeta.BillDocument;
import com.bokesoft.myerp.billmeta.BillDocumentArray;
import com.bokesoft.myerp.billmeta.BillMetaData;
import com.bokesoft.myerp.billmeta.BillMetaLinkedTable;
import com.bokesoft.myerp.billmeta.EnumDBType;
import com.bokesoft.myerp.common.ContextUtil;
import com.bokesoft.myerp.common.DebugUtil;
import com.bokesoft.myerp.common.IExpEvaluate;
import com.bokesoft.myerp.common.intf.IContext;
import com.bokesoft.myerp.common.intf.IDBManager;
import com.bokesoft.myerp.common.io.CloseUtil;
import com.bokesoft.myerp.common.midproxy.Env;
import com.bokesoft.myerp.common.midproxy.Request;
import com.bokesoft.myerp.common.rowset.BKRowSet;

/**
 * Applet客户端调用中间层的主入口
 */
public class MidSrvCore extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public MidSrvCore() {
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		perform(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		perform(request, response);
	}

	private static class MidContext extends HttpServletContext implements IContext {

		private static final long serialVersionUID = 1L;
		public MidContext(HttpServletRequest req, HttpServletResponse res) {super(req, res);}

		public BillDocument getBillDoc() {
			return null;
		}

		public BillDocumentArray getBillDocArray() {
			return null;
		}

		public BillMetaData getBillMetaData() throws Throwable {
			return null;
		}

		public BillMetaLinkedTable getMetaTable(String arg0) throws Throwable {
			return null;
		}

		public BillDocument setBillDoc(BillDocument arg0) {
			return null;
		}

		public BillDocumentArray setBillDocArray(BillDocumentArray arg0) {
			return null;
		}

		public Object evaluate(String arg0, String arg1) throws Throwable {
			return null;
		}

		public int executePrepareUpdate(String arg0, Object[] arg1) throws Throwable {
			return 0;
		}

		public BKRowSet executeSQL(String arg0) throws Throwable {
			return null;
		}

		public BKRowSet getPrepareResultSet(String arg0, Object[] arg1) throws Throwable {
			return null;
		}

		public void setAutoCommit(boolean arg0) throws Throwable {}

		public void setComplete() throws Throwable {}

		public void setFail() throws Throwable {}

		private Env env = null;

		public void setEnv(Env env) {	this.env = env;	}

		public Env getEnv() {	return env;	}

		public Integer getDBType() {	return env.getDBType();	}

		public EnumDBType getEnumDBType() {	return env.getEnumDBType();	}

		public void registerMidFunction(Serializable midFunction) {	}

		public boolean evaluateBoolean(String exp, String des) throws Throwable {	return false;	}

		public IContext createSubContext() throws Throwable {return null;}

		public IDBManager getDBM() throws Throwable {return null;}

		public IExpEvaluate getMidExp() throws Throwable {return null;}

		public void setDateHeader(String arg0, long arg1) {
		}

		public void setFileHttpContentType(String arg0, boolean arg1) {
		}

		public void setStatus(int arg0) throws Throwable {
		}

	}

	private MidHandler midHandler = new MidSrvHandler();
	
	// myerp中间层和webservice容器的交互入口
	protected void perform(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		IContext context = null;
		try {
			context = new MidContext(req, res);
			ContextUtil.set(context);
			byte[] bytes = midHandler.doAction(context);
			if (bytes != null)
				context.write(bytes);
		} catch (Throwable ex) {
			try {
				DebugUtil.error(ex);
				Request clientReq = getRequset(context, ex);
				context.write(clientReq.buildClient());
			} catch (Throwable e) {
				e.printStackTrace();
			}
		} finally {
			CloseUtil.close(context);
			ContextUtil.set(null);
			context = null;
		}

	}

	private static Request getRequset(IContext context, Throwable ex) {
		// 不要用context构造
		Request clientReq = new Request();
		clientReq.setEnv(context.getEnv());
		clientReq.setReturnEnv(false);
		clientReq.setErr(ex);
		return clientReq;
	}

}