package yigo.soa.weixin.mid.dispatch;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bokesoft.myerp.common.DebugUtil;
import com.bokesoft.myerp.common.StringUtil;
import com.bokesoft.myerp.exception.BKException;

/**
 * httpservlet context wrapper
 * 
 * @author zhouxw
 */
public abstract class HttpServletContext extends CommonContext {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	protected static final String CONTENT_TYPE = "text/html;charset=UTF-8";

	/**
	 * 
	 */
	protected static final String CHARSET = "UTF-8";

	/**
	 * 
	 */
	protected transient ServletRequest srvReq;

	/**
	 * 
	 */
	protected transient ServletResponse srvRes;

	/**
	 * @param req
	 * @param res
	 */
	public HttpServletContext(ServletRequest req, ServletResponse res) {
		super();
		this.srvReq = req;
		this.srvRes = res;
		// this.setPageEncoding(CHARSET);
		if (this.srvRes != null)
			this.setContentType(CONTENT_TYPE);
		// if (this.isMultipartFormData()) {
		// this.mfdReq = this.instanceMultipartFormDataRequest();
		// this.mtwReq = this.instanceMultipartRequestWrapper();
		// }
	}

	@Override
	public void setPageEncoding(String charset) {
		this.srvRes.setCharacterEncoding(charset==null?CHARSET:charset);
	}

	@Override
	public String getURLParamString(String paramName, String paramValue) {
		String queryStr = "";
		Enumeration<?> enu = srvReq.getParameterNames();
		boolean hasSetValue = false;// 是否有外部设置的参数
		while (enu.hasMoreElements()) {
			String tempParamName = (String) enu.nextElement();
			String[] values = srvReq.getParameterValues(tempParamName);
			for (int i = 0; i < values.length; i++) {
				// if (!StringUtil.isBlankOrNull(queryStr))
				if (queryStr != null && !"".equals(queryStr))
					queryStr += "&";
				if (tempParamName.equals(paramName)) {
					queryStr += paramName + "=" + paramValue;
					hasSetValue = true;
				} else {
					queryStr += tempParamName + "=" + values[i];
				}
			}
		}
		if (hasSetValue == false) {
			if (queryStr != null && !"".equals(queryStr))
				queryStr += "&";
			queryStr += paramName + "=" + paramValue;
		}
		return queryStr;
	}

	public void setHttpHeader(String fileName, boolean isAttachment) {
		if (fileName != null && !"".equals(fileName))
			return;
		try {
			// srvRes.setHeader("Content-Length", length);
			if (isAttachment) // 是否有http附件（过度封装了，header可以在外面又编码人员自己设置）
				((HttpServletResponse) srvRes).setHeader(
					"Content-Disposition", //
					"attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
			else {
				((HttpServletResponse) srvRes).setHeader(
					"Content-Disposition", //
					"inline;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
				String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
				if (extName == null || "".equals(extName))
					return;
				// String contentType = "";
				if ("jpg".equalsIgnoreCase(extName)) {
					srvRes.setContentType("image/jpeg");
				} else if ("gif".equalsIgnoreCase(extName)) {
					srvRes.setContentType("image/gif");
				} else if ("tif".equalsIgnoreCase(extName) || "tiff".equalsIgnoreCase(extName)) {
					srvRes.setContentType("image/tiff");
				} else if ("bmp".equalsIgnoreCase(extName)) {
					srvRes.setContentType("image/bmp");
				} else if ("png".equalsIgnoreCase(extName)) {
					srvRes.setContentType("image/png");
				} else if ("txt".equalsIgnoreCase(extName)) {
					srvRes.setContentType("text/plain");
				} else if ("mpeg".equalsIgnoreCase(extName)) {
					srvRes.setContentType("video/mpeg");
				} else if ("mp3".equalsIgnoreCase(extName)) {
					srvRes.setContentType("audio/x-mpeg");
				} else if ("pdf".equalsIgnoreCase(extName)) {
					srvRes.setContentType("application/pdf");
				} else if ("doc".equalsIgnoreCase(extName)) {
					srvRes.setContentType("application/msword");
				} else if ("xls".equalsIgnoreCase(extName)) {
					srvRes.setContentType("application/vnd.ms-excel");
				} else if ("ppt".equalsIgnoreCase(extName)) {
					srvRes.setContentType("application/vnd.ms-powerpoint");
				} else if ("vsd".equalsIgnoreCase(extName)) {
					srvRes.setContentType("application/x-visio");
				} else if ("swf".equalsIgnoreCase(extName)) {
					srvRes.setContentType("application/x-shockwave-flash");
				} else if ("rm".equalsIgnoreCase(extName)) {
					srvRes.setContentType("application/vnd.rn-realmedia");
				} else
					srvRes.setContentType("application/octet-stream");
				// srvRes.setContentType("file/" + extName);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace(); // ignore
		}
	}

	@Override
	public Enumeration<?> getParameterNames() {
		return srvReq.getParameterNames();
	}

	@Override
	public Object[] getParameterValues(String name) {
		return srvReq.getParameterValues(name);
	}

	/**
	 * 在srvReq为null的时候，使用这个集合保存设置的属性或参数值
	 */
	private transient HashMap<String, Object> attri = new HashMap<String, Object>();
	
	@Override
	public Object getAttribute(String name) {
		if ( srvReq != null ) {
			return srvReq.getAttribute(name);
		}
		return attri.get(name);
	}

	@Override
	public Object getParameter(String name) {
		Object obj = this.getAttribute(name);
		if (obj != null && !"".equals(obj))
			return obj;
		if ( srvReq != null ) {
			return srvReq.getParameter(name);
		}
		return attri.get(name);
	}
	
	@Override
	public String getURLParamsByIgnored(String[] ignored) {
		HashMap<String, String> map = new HashMap<String, String>();
		if (ignored != null)
			for (int i = 0; i < ignored.length; i++) {
				map.put(ignored[i], ignored[i]);
			}
		String urlParams = "";// url参数
		int count = 0;
		Enumeration<?> names = srvReq.getParameterNames();
		for (; names.hasMoreElements();) {
			String key = (String) names.nextElement();
			if (map.get(key) != null)
				continue;
			String[] values = srvReq.getParameterValues(key);
			for (int i = 0; i < values.length; i++) {
				if (count > 0)
					urlParams += "&";
				urlParams += key + "=" + values[i];
				count++;
			}
		}
		return urlParams;
	}

	@Override
	public int getMaxInactiveInterval() {
		return ((HttpServletRequest) srvReq).getSession().getMaxInactiveInterval();
		// return srvReq.getSession().getMaxInactiveInterval();
	}

	@Override
	public String getRemoteAddr() {
		return srvReq.getRemoteAddr();
	}

	@Override
	public String getRemoteHost() {
		return srvReq.getRemoteHost();
	}

	@Override
	public boolean isCommitted() {
		return srvRes.isCommitted();
	}

	@Override
	public String getCharacterEncoding() {
		return srvReq.getCharacterEncoding();
	}

	@Override
	public String getServletPath() {
		// return srvReq.getServletPath();
		return ((HttpServletRequest) srvReq).getServletPath();
	}

	@Override
	public String getServiceName() {
		String path = ((HttpServletRequest) srvReq).getContextPath();
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		return path;
	}

	@Override
	public String getServerName() {
		return srvReq.getServerName();
	}

	@Override
	public int getServerPort() {
		return srvReq.getServerPort();
	}

	@Override
	public int getLocalPort() {
		return srvReq.getLocalPort();
	}

	@Override
	public void setCookie(String cookieName, String cookieVal) {
		setCookie(((HttpServletRequest) srvReq), ((HttpServletResponse) srvRes), cookieName, cookieVal);
	}

	/**
	 * 设置cookie
	 * 
	 * @param srvRes
	 * @param cookieVal
	 * @return Cookie
	 */
	public final Cookie setCookie(HttpServletRequest srvReq,
			HttpServletResponse srvRes, String cookieName, String cookieVal) {
		cookieName = this.getServiceName() + "." + cookieName;

		Cookie c = null;
		if (srvReq == null)
			return null;

		Cookie[] cks = srvReq.getCookies();
		if (cks != null) {
			for (Cookie ck : cks) {
				if (ck.getName().equalsIgnoreCase(cookieName)) {
					c = ck;
					break;
				}
			}
		}
		// String contextpath = ((HttpServletRequest) srvReq).getContextPath();
		if (c == null) {
			c = new Cookie(cookieName, cookieVal);
			c.setMaxAge(60 * 60 * 24 * 365);
			c.setPath("/");
			// c.setPath(contextpath); // 默认用二级域名例如/map3 /zswl
			// 尽量不要对2级域名保存cookie
		} else {
			c.setMaxAge(60 * 60 * 24 * 365);
			c.setPath("/");
			// c.setPath(contextpath); // 默认用二级域名例如/map3 /zswl
			// 尽量不要对2级域名保存cookie
			c.setValue(cookieVal);
		}
		srvRes.addCookie(c);
		DebugUtil.asert(cookieVal!=null&&cookieVal.indexOf("||")>=0);
		return c;
	}

//	@Override
//	public Cookie[] getCookies() {
//		return ((HttpServletRequest) srvReq).getCookies();
//	}

	@Override
	public String getCookie(String cookieName) {
		return getCookie(((HttpServletRequest) srvReq), cookieName);
	}

	/**
	 * 从req里获取cookie
	 * 
	 * @param srvReq
	 * @param cookieName
	 *            cookie名
	 * @return String
	 */
	public final String getCookie(HttpServletRequest srvReq, String cookieName) {
		cookieName = this.getServiceName() + "." + cookieName;

		String result = "";
		Cookie[] c = srvReq.getCookies();
		if (c != null)
			for (int i = 0; i < c.length; i++) {
				if (c[i].getName().equals(cookieName)) {
					result = c[i].getValue();
				}
			}
		return result;
	}

	@Override
	public OutputStream getOutputStream() {
		try {
			return srvRes.getOutputStream();
		} catch (Throwable e) {
			throw BKException.dealEx(e);
		}
	}

	@Override
	public InputStream getInputStream() {
		try {
			return srvReq.getInputStream();
		} catch (Throwable e) {
			throw BKException.dealEx(e);
		}
	}

	@Override
	public void forward(String addr) throws Throwable {
		// try {
		RequestDispatcher dispatcher = srvReq.getRequestDispatcher(addr);
		// DebugUtil.debug(">" + dispatcher);
		dispatcher.forward(srvReq, srvRes);
		// } catch (Throwable ex) {
		// throw ex;
		// }
	}

	// public String getBaseURL() {
	// return new StringBuffer(srvReq.getScheme()).append("://").append(
	// srvReq.getServerName()).append(":").append(
	// srvReq.getServerPort()).append(
	// ((HttpServletRequest) srvReq).getContextPath()).toString();
	// }

	@Override
	public Object getRequest() {
		return this.srvReq;
	}

	@Override
	public Object getResponse() {
		return this.srvRes;
	}

	@Override
	public String getContextName() {
		String dsnContextName = getCookie("MAP_C_SERVER_URL");
		if (!StringUtil.isBlankOrNull(dsnContextName)) {
			try {
				return java.net.URLDecoder.decode(dsnContextName, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				DebugUtil.error(e);
			}
		}
		if (srvReq.getScheme()==null)
			return null;
		//String contextpath = ((HttpServletRequest) srvReq).getContextPath();
		String contextName = srvReq.getScheme() + "://"
			+ getServerName() + ":"
			+ getServerPort() + "/"
			+ getServiceName(); // contextpath;
		return contextName;
	}

	@Override
	public void setAttribute(String name, Object v) {
		if ( srvReq != null ) {
			srvReq.setAttribute(name, v);
		} else {
			attri.put(name, v);
		}
	}

	@Override
	public void removeAttribute(String name) {
		if ( srvReq != null ) {
			srvReq.removeAttribute(name);
		} else {
			attri.remove(name);
		}
	}

	@Override
	public void setContentType(String contentType) {
		srvRes.setContentType(contentType);
	}

	@Override
	public void setHeader(String headName, String headValue) {
		((HttpServletResponse) srvRes).setHeader(headName, headValue);
	}

	public void setDateHeader(String string, int i) {
		((HttpServletResponse) srvRes).setDateHeader(string, i);
	}

	@Override
	public Writer getWriter() {
		try {
			// if (!srvRes.isCommitted())
			return srvRes.getWriter();
		} catch (Throwable e) {
			throw BKException.dealEx(e);
		}
	}

}