package com.zaofans.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AllPermission;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy; 

/**
 * 
 */
public class AppClassLoader extends URLClassLoader {
	// NOTICE 另外的classloader
	public static ClassLoader getClassLoader(URL[] urls) throws Throwable {
		final ClassLoader cl = new URLClassLoader(urls, AppClassLoader.class.getClassLoader()) { 
			@Override
			protected Class<?> findClass(String name)
					throws ClassNotFoundException { 
				try {
					return super.findClass(name);
				} catch (ClassNotFoundException e) { 
					throw e;
				}
			}
			@Override
			protected String findLibrary(String libname) {
				System.out.println("loading:" + libname);
				return super.findLibrary(libname);
			}
		};
		// new File("E:\\eclipse_workspace_etc\\test\\rxtx\\TTT.jar").toURI().toURL(), //
		// new File("E:\\eclipse_workspace_etc\\test\\rxtx\\RXTXcomm.jar").toURI().toURL() //
		Thread.currentThread().setContextClassLoader(cl);
		return cl;
	}
	// 解决问题: native lib not found in java.lib.library
	// 如果是 unlikedexception 多数是没有去load
	public static void initShareNative(String dir) throws Throwable {
		System.out.println(System.getProperty("java.library.path"));
		System.out.println(System.getProperty("java.io.tmpdir"));
		{
			Policy.setPolicy(new Policy() {
				@Override
				public PermissionCollection getPermissions(CodeSource codesource) {
					Permissions perms = new Permissions();
					perms.add(new AllPermission());
					return (perms);
				}
				@Override
				public void refresh() {
				}
			});
			System.setSecurityManager(null);
			String runningURL = dir;
			// System.out.println(runningURL);
			String runningPath = new File(runningURL).getCanonicalPath();
			// System.out.println(runningPath);
			String nativeLibPath = runningPath;
			// System.out.println(nativeLibPath);
			String newLibPath = nativeLibPath + File.pathSeparator
					+ System.getProperty("java.library.path");
			System.out.println(newLibPath);
			System.setProperty("java.library.path", newLibPath);
			// System.out.println(System.class.getClassLoader());
			Field fieldSysPath = null;
			fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
			fieldSysPath.setAccessible(true); // 反射强制设置该Attribute
			if (fieldSysPath != null) {
				// String[] arr = ((String[]) fieldSysPath.get(null));
				// for (String v : arr)
				// System.out.println("sys_paths:" + v);
				fieldSysPath.set(System.class.getClassLoader(), null);
			}
		}
	}
	private static AppClassLoader cl = null;
	private AppClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);
	}
	/**
	 * 初始化对象
	 * 
	 * @param parent
	 */
	public static AppClassLoader getInstance(ClassLoader parent) {
		// getJarURL("commons-trd.jar")
		URL[] urls = new URL[] {};
		if (cl == null) // 单例
			cl = new AppClassLoader(urls, parent);
		return cl;
	}
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		try {
			return super.findClass(name);
		} catch (ClassNotFoundException e) {
			// testInfo("findClass:\t" + name);
			if (!loadJar(name)) {
				// 判断是否为扩展UIExpFunction
				// if (name.contains(DataConstant.UI_CLASS_PREFIX)) {
				// String jarname = name.replaceAll(
				// DataConstant.UI_CLASS_PREFIX, "");
				// jarname = jarname.substring(0, jarname.indexOf("."))
				// + ".jar";
				// URL extJar = getJarURL(jarname);
				// System.out.println(">add extJar :" + extJar);
				// ClzLoader.super.addURL(extJar);
				// } else {
				loadJar(name);
				// }
			}
			return super.findClass(name);
		}
	}
	/**
	 * 根据类名,装载相关的组件
	 * 
	 * @param clzName
	 * @return boolean
	 */
	private boolean loadJar(String clzName) {
		for (int i = 0; i < userJars.length; ++i) {
			if (userJars[i].checkAndLoad(clzName)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 所有需要加载的类
	 */
	public UserJar[] userJars = new UserJar[] {
			new UserJar("com.haiyan.genmis.castorgen.", "commons-trc.jar"),
			// new UserJar("java_cup.", "java_cup.jar"),
			// new UserJar("org.jdesktop.jdic.", new String[] { "IeEmbed.exe",
			// "MozEmbed.exe", "jdic.dll", "tray.dll", "jdic_new.jar" }),
			// new UserJar("javax.jnlp.", "jnlp.jar"),
			new UserJar("org.jdom.", "jdom.jar"),
			new UserJar(new String[] { "org.castor.", "org.exolab.castor." },
					new String[] { "castor-1.2-commons.jar",
							"castor-1.2-xml.jar" }),
			new UserJar(new String[] { "netscape.", "sun.plugin." },
					"plugin.jar"),
			new UserJar("org.apache.commons.logging.",
					"commons-logging-1.0.4.jar"),
			new UserJar("org.apache.log4j.", "log4j-1.2.9.jar"),
			new UserJar("javax.servlet.", "servlet-api.jar"),
			new UserJar("org.mortbay.jetty.", new String[] { "jetty-6.1.3.jar",
					"jetty-util-6.1.3.jar" }),
			new UserJar("javax.wsdl.", "wsdl4j-1.5.1.jar"),
			// 这个组件打印要使用
			new UserJar("org.apache.commons.collections.",
					"commons-collections-3.2.jar") };
	class UserJar {
		/**
		 * 类的前缀,往往是包名
		 */
		private String[] prefix;
		/**
		 * 包名
		 */
		private String[] name;
		/**
		 * 这一堆变量表示包是否已经加载,因为有些包会载入一些不存在的类,比如castor-1.2-xml.jar
		 */
		private boolean isLoad;
		public UserJar(String[] prefix, String[] name) {
			this.prefix = prefix;
			this.name = name;
		}
		public UserJar(String prefix, String[] name) {
			this.prefix = new String[] { prefix };
			this.name = name;
		}
		public UserJar(String[] prefix, String name) {
			this.prefix = prefix;
			this.name = new String[] { name };
		}
		public UserJar(String prefix, String name) {
			this.prefix = new String[] { prefix };
			this.name = new String[] { name };
		}
		/**
		 * 检查类名,如果类名符合,那载入包
		 * 
		 * @param clzName
		 * @return boolean
		 */
		public boolean checkAndLoad(String clzName) {
			for (int i = 0; i < prefix.length; ++i) {
				if (clzName.startsWith(prefix[i])) {
					load();
					return true;
				}
			}
			return false;
		}
		/**
		 * 载入包
		 */
		public void load() {
			if (isLoad) {
				return;
			}
			for (int i = 0; i < name.length; ++i) {
				if (name[i].endsWith(".jar") || name[i].endsWith(".zip")) {
					AppClassLoader.super.addURL(getJarURL(name[i]));
				} else {
					getJarURL(name[i]); // 只下载不加载
				}
			}
			isLoad = true;
		}
	}
	/**
	 * 取本机组件,可能要下载,返回本机的URL
	 * 
	 * @param jarName
	 * @return URL
	 */
	private static URL getJarURL(String jarName) {
		try {
			return getLocalJarFile(jarName).toURI().toURL();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
	// NOTICE for test
	private static String getServerPath() {
		return System.getProperty("user.dir") + File.separator + "bin";
	}
	// NOTICE for test
	private static String getCachePath() {
		return System.getProperty("user.dir") + File.separator + "bin";
	}
	private static void fileDownload(String sServerPath, String sCachePath) {
		throw new RuntimeException("服务器文件不存在:" + sServerPath);
	}
	private static String getCacheFileID(String path) {
		if (!new File(path).exists())
			return null;
		return "" + new File(path).getUsableSpace(); // 是否和服務器相等
	}
	private static File getCacheFile(String path) {
		return new File(path);
	}
	private static File getLocalJarFile(String jarName) {
		String serverPath = getServerPath();
		String serverFilePath = serverPath + File.separator + jarName;
		String cachePath = getCachePath();
		String cacheFilePath = cachePath + File.separator + jarName;
		File file;
		try {
			file = getFileDownloadPath(serverFilePath, cacheFilePath);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
		return file;
	}
	private static File getFileDownloadPath(String sServerPath,
			String sCachePath) throws Throwable {
		// TODO 如果FileID更新，但是文件没有下载成功会有漏洞
		String clientFileID = getCacheFileID(sCachePath);
		if (clientFileID != null && clientFileID.length() > 0) {
			File cacheFile = getCacheFile(sCachePath);
			if (cacheFile.exists()) {
				System.out.println(">使用本地缓存:" + sCachePath);
				return cacheFile;
			}
		}
		fileDownload(sServerPath, sCachePath);
		return getCacheFile(sCachePath);
	}
}