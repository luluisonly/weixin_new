package test.thirdparty.weixin;

@Deprecated
public class TestLoginCenter4Weixin2{// extends TestLoginCenterCache implements ILoginCenter {
	
//	private final static String WEIXIN_SESSIONID = "_WEIXIN_SESSIONID"; 
//	
//	protected boolean toLoginPage(IContext context, int type) {
//		try {
//			if (!App.isWebMid())
//				return true;
//			String url = ((HttpServletRequest)context.getRequest()).getRequestURI();
//			String isDebug = url.indexOf("_src")>=0?"_src":"";
////			String s = String.format("<script>alert('%s');window.top.location.href='Yigo.html';</script>", ErrorMessage.LOGIN_TIMEOUT);
//			String s = "<script>window.top.location.href='Yigo"+isDebug+".html';</script>";
//			context.write(s);
////			// 死循环
////			context.write("javascript:window.top.location.href='Yigo.html';");
//			return true;
//		} catch (Throwable ex) {
//			throw BKException.dealEx(ex);
//		}
//	}
//	
//	protected void setSessionEnv(WeixinSession session, Env env){
//		session.setEnv(env);
//	}
//	
//	protected Env getSessionEnv(WeixinSession session){
//		return session.getEnv();
//	}
//	
//	private Env getCacheEnv(WeixinSession session){
//		try { 
//			Env env = null;
//			IContext ctx = ContextUtil.getContext(); // 请求上下文
//			if (ctx!=null) {
//				env = getSessionEnv(session);
//				if (env!=null)
//					return env;
//			}
//			// 有些情况ctx为空（YigoUI处理中另开的线程）
//			env = (Env)getCloudProvider().getLocalCache(CACHEID, session.getId()); // cluster cache
//			if (env==null) // 超时或者注销
//				return null;
//			
//			if (ctx!=null) { // for AppLocal 重新将云端env设置进session
//				setSessionEnv(getSession(ctx), env); // 不依赖session的超时
//			}
//			return env;
//		} catch (Throwable e) {
//			throw BKException.dealEx(e);
//		}
//	}
//	
//	public void setCacheEnv(WeixinSession session, Env env){
//		try { 
//			if (env==null)
//				getCloudProvider().delLocalCache(CACHEID, session.getId());
//			else
//				getCloudProvider().setLocalCache(CACHEID, session.getId(), env); // cluster cache
////			EnvWrapper envW = new EnvWrapper();
////			envW.env = env;
////			envW.visit = System.currentTimeMillis();
//			IContext ctx = ContextUtil.getContext(); // 请求上下文
//			if (ctx!=null) {
//				setSessionEnv(session, env);
//			}
//		} catch (Throwable e) {
//			throw BKException.dealEx(e);
//		}
//		
//	}
//
//	@Override
//	public void setDSNSessionID(HttpServletRequest req, HttpServletResponse res, String key) {
//		Env env = null;
//		String[] keys = StringUtil.split(key, ";");
//		String SID = keys[0];
//		String serverID = keys[1];
//		String weixinSessionID = getWeixinSessionID(req);
//		if (StringUtil.isEmpty(weixinSessionID)) {
//			if ((env = getCacheEnv(serverID, SID)) != null) {
//				String v = env.getEnvValue(WebContext.SERVERID_PARAM);
//				if (StringUtil.isBlankOrNull(v)) { // 存入最近的serverid
//					setSessionEnv(getSession(req), env); // 登录完一次就放进session做本地缓存
//					setCacheEnv(serverID, SID, env); // 更新集群cache的env
//					setCookie(req,res,WebContext.COOKIE_SID, SID); // 设置浏览器标识
//				} else {
//					// NOTICE 用httpclient模拟的数据只能根据只字片语来模拟package无法拿到有用的信息
//					// 只要防止浏览器的SID篡改就能避免关键数据泄漏
//					if (!req.getSession().getId().equals(v)) { // 防止SID被盗用或者被篡改
//						// toLoginPage(s, 1);
//						UICommand.overSession(-1); // break opts in later
//					}
//				}
//			}
//		}else{
//			WeixinSession session = WeixinContextFactory.getWeixinSession(weixinSessionID);
//			if ((env = getCacheEnv(session)) != null) {
//				String v = env.getEnvValue(WebContext.SERVERID_PARAM);
//				if (StringUtil.isBlankOrNull(v)) { // 存入最近的serverid
//					setSessionEnv(session, env); // 登录完一次就放进session做本地缓存
//					setCacheEnv(session, env); // 更新集群cache的env
//					setCookie(req,res,WebContext.COOKIE_SID, SID); // 设置浏览器标识
//				} else {
//					// NOTICE 用httpclient模拟的数据只能根据只字片语来模拟package无法拿到有用的信息
//					// 只要防止浏览器的SID篡改就能避免关键数据泄漏
//					if (!session.getId().equals(v)) { // 防止SID被盗用或者被篡改
//						// toLoginPage(s, 1);
//						UICommand.overSession(-1); // break opts in later
//					}
//				}
//			}
//		}
//	}
//	
//	private final String CACHEID = "S_LOGIN";
//	
//	@Override
//	public IntfType logout(IContext context, Integer[] sessionIDs) throws Throwable {
//		DebugUtil.debug("logout...");
//		Env env = context.getEnv();
//		env.setSessionID(0);
//		String weixinSessionID = getWeixinSessionID(context);
//		if (StringUtil.isEmpty(weixinSessionID)) {
//			if (context.getAttribute("ISWEIXIN")==Boolean.TRUE) {
//				weixinSessionID = (String)context.getAttribute("WEIXINSID");
//				setCacheEnv(WeixinContextFactory.getWeixinSession(weixinSessionID), null);
//			} else {
//				String serverID = getSession(context).getId();
//				setCacheEnv(serverID, env.getDSNSessionID(), null);
//			}
//		}else{
//			setCacheEnv(WeixinContextFactory.getWeixinSession(weixinSessionID), null);
//		}
//		toLoginPage(context);
//		return IntfType.INTERCEPT;
//	}
//
//	@Override
//	public void doAfterLogin(IContext context, Integer sessionID) throws Throwable {
//		DebugUtil.debug("doAfterLogin...");
//		Env env = context.getEnv();
//		String weixinSessionID = getWeixinSessionID(context);
//		if (StringUtil.isEmpty(weixinSessionID)) {
//			if (context.getAttribute("ISWEIXIN")==Boolean.TRUE) {
//				weixinSessionID = (String)context.getAttribute("WEIXINSID");
//				setCacheEnv(WeixinContextFactory.getWeixinSession(weixinSessionID), env);
//			} else {
//				String serverID = getSession(context).getId();
//				setCacheEnv(serverID, env.getDSNSessionID(), env);
//			}
//		}else{
//			setCacheEnv(WeixinContextFactory.getWeixinSession(weixinSessionID), env);
//		}
//	}
//
//	
//	private String getWeixinSessionID(IContext context){
//		return context.getCookie(WEIXIN_SESSIONID);
//	}
//	
//	private String getWeixinSessionID(HttpServletRequest request){
//		Cookie [] cookies = request.getCookies();
//		for(Cookie cookie:cookies){
//			if (WEIXIN_SESSIONID.equals(cookie.getName())) {
//				return cookie.getValue();
//			}
//		}
//		return null;
//	}
//	
//	public void setContext(IContext context) {
//		if (context == null)
//			return;
//		Env env;
//		String weixinSessionID = getWeixinSessionID(context);
//		if (StringUtil.isEmpty(weixinSessionID)) {
//			String serverID = getSession(context).getId();
//			String SID = MAP_C_ENV.getDSNSessionID(context);
//			if ((env = getCacheEnv(serverID, SID)) != null){ // Env放Cache,一定时间内不用重取
//				context.setEnv(env);
//			} else {
//				if (MAP_C_ENV.isDoLogin(context)) { // login.jsp或logout.jsp会设置
//					String DSN = MAP_C_ENV.getDefaultDSN();
//					context.setEnv(new Env());
//					WebContext.initGuestEnv(context, SID, DSN); // 清除Env并构造为游客信息
//					env = context.getEnv();
//				} else {
//					toLoginPage(context);
//					UICommand.overSession(0); // break opts in later
//				}
//			}
//		}else{
//			if ((env = getCacheEnv(WeixinContextFactory.getWeixinSession(weixinSessionID))) != null){ // Env放Cache,一定时间内不用重取
//				context.setEnv(env);
//			} else {
//				if (MAP_C_ENV.isDoLogin(context)) { // login.jsp或logout.jsp会设置
//					String DSN = MAP_C_ENV.getDefaultDSN();
//					context.setEnv(new Env());
//					WebContext.initGuestEnv(context, weixinSessionID, DSN); // 清除Env并构造为游客信息
//					env = context.getEnv();
//				} else {
//					toLoginPage(context);
//					UICommand.overSession(0); // break opts in later
//				}
//			}
//		}
		
//		// 1 读取当前env里的DSNSessionID
//		// 2 到SessionCluster检查是否已经登录？
//		// 3 如果没有跳转到登录界面
//		// 4 如果有那么检查 当前env信息和SessionCluster里的env信息是否吻合（安全性检查）
//		// 5 如果当前env没有，则直接拿SessionCluster里的env信息设置到当前session里或者context里。
//		
//	}
}