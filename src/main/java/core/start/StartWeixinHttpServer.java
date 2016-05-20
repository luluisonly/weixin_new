package start;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;

import com.bokesoft.myerp.common.SharedBundle;
import com.bokesoft.myerp.common.StringUtil;

public class StartWeixinHttpServer {
	
	private static final Logger LOGGER  = Logger.getLogger(StartWeixinHttpServer.class);

	public static void main(String[] args) throws Exception {
//		if (args.length>0) {
//			System.setProperty("YIGO.CONFIG_BUNDLE_NAME", args[0]);
////			SharedBundle.CONFIG_BUNDLE_NAME = args[1];
////			SharedBundle.clearPropertiesCache();
//		}
		ClassLoader cp = ClassLoader.getSystemClassLoader();
		Class<?> cls = cp.loadClass(StartWeixinHttpServer.class.getName());
		cls.getMethod("start").invoke(null);
		//Object o = cls.newInstance();
		//Method m = cls.getMethod("startServer", String.class);
		//m.invoke(o, docPath);
	}
	
	public static void start() throws Exception {
		String path = new File(".").getAbsolutePath();
		path = path.substring(0, path.length() - 1);
		String deployPath = path+"WEB-INF";
		if (!new File(deployPath).exists()) {
			path = path + "src\\main\\webapp";
		}
		Server server = new Server();//  
		// 
		Connector connector = new SelectChannelConnector();
		String app_server = SharedBundle.getProperties("APP_SERVER");
		String port = null;
		if (StringUtil.isBlankOrNull(app_server)) {
			port = "8300";
		}else{
			port = app_server.substring(app_server.indexOf(":")+1, app_server.length());
		}
		connector.setPort(Integer.parseInt(port));
		// 
		server.setConnectors(new Connector[] { connector });
		// 
		WebAppContext webapp = new WebAppContext();
		// 
		String app_service = SharedBundle.getProperties("APP_SERVICE");
		if (StringUtil.isBlankOrNull(app_service)) {
			app_service = "weixin";
		}
		webapp.setContextPath("/"+app_service);
//		LOGGER.info(">> server path:"+path);
//		LOGGER.info(">> server port:"+port);
//		LOGGER.info(">> server service:/"+app_service);
		webapp.setWar(path);
		// 
		// webapp.setDefaultsDescriptor("etc/webdefault.xml");
		server.setStopAtShutdown(true);
		server.setHandler(webapp);
		// 
		System.out.println("  SERVER PATH     "+path);
		System.out.println("  SERVER PORT     "+port);
		System.out.println("  SERVER SERVICE  /"+app_service+"\n");
		server.start();
		LOGGER.info(">> server started");
	}
	
}
