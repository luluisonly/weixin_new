package com.zaofans.utils;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

import com.bokesoft.myerp.common.StringUtil;

public class PropUtil {
	static class MyPropertyResourceBundle extends PropertyResourceBundle {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public MyPropertyResourceBundle(ResourceBundle rs) throws IOException {
			super(new InputStream(){
				@Override
				public int read() throws IOException {
					return -1;
				}});
			lookup = new HashMap(System.getProperties()); // 优先级最低
			lookup.putAll(System.getenv()); // OS Environment Variables
			this.read(rs);
		}
		public void read(ResourceBundle rs) {
			Iterator<String> keys = rs.keySet().iterator();
			while(keys.hasNext()) {
				String k = keys.next();
				lookup.put(k, rs.getObject(k));
			}
		}
		@Override
		public Object handleGetObject(String key) {
			if (key == null) {
				throw new NullPointerException();
			}
			return lookup.get(key);
		}
		@Override
		public Enumeration<String> getKeys() {
			ResourceBundle parent = this.parent;
			return new sun.util.ResourceBundleEnumeration(lookup.keySet(),
					(parent != null) ? parent.getKeys() : null);
		}
		@Override
		protected Set<String> handleKeySet() {
			return lookup.keySet();
		}
		// ==================privates==================== //
		private Map<String, Object> lookup;
	}
	private static String APP_BUNDLE_NAME = "core";
	private static Map<String,ResourceBundle> BUNDLE = new HashMap<String,ResourceBundle>();
	/**
	 * @return ResourceBundle(PropertyResourceBundle) 
	 */
	public final static ResourceBundle getPropertyBundle(String bundleName) {
		String key = System.getProperty("APP_BUNDLE_NAME");
		if (!StringUtil.isEmpty(bundleName))
			key = bundleName;
		if (StringUtil.isEmpty(key))
			key = APP_BUNDLE_NAME;
		if (!BUNDLE.containsKey(key))
			synchronized(PropUtil.class) {
				if (!BUNDLE.containsKey(key))
					try {
						File rootFile = PathUtil.getConfigRootFile(null);
						if (rootFile!=null && rootFile.exists()) {
							File file = new File(rootFile.getAbsolutePath() + File.separator + key + ".properties");
							if (file.exists()) {
								InputStream ins = new FileInputStream(file);
								PropertyResourceBundle rsp = new PropertyResourceBundle(ins);
								CloseUtil.close(ins);
								BUNDLE.put(key, rsp);
							}
						} 
						if (!BUNDLE.containsKey(key)) {
							ResourceBundle rso = ResourceBundle.getBundle(key, 
									Locale.getDefault(), Thread.currentThread().getContextClassLoader());
							MyPropertyResourceBundle rsb = new MyPropertyResourceBundle(rso); // 从环境变量获取配置
							BUNDLE.put(key, rsb);
						}
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
			}
		return BUNDLE.get(key);
	}
	public final static ResourceBundle getPropertyBundle() {
		return getPropertyBundle(null);
	}
	public final static ResourceBundle getLocalPropertyBundle(String bundleName) {
		String s = bundleName;
		if (StringUtil.isEmpty(s))
			s = APP_BUNDLE_NAME;
		ResourceBundle rsl = ResourceBundle.getBundle(s, 
				Locale.getDefault(), Thread.currentThread().getContextClassLoader());
		return rsl;
	}
	public final static ResourceBundle getLocalPropertyBundle() {
		return getLocalPropertyBundle(null);
	}
    /**
     * @param bundName
     * @param key
     * @return
     */
    public static String getProperty(String bundName, String key, String def) {
        try {
        	ResourceBundle b;
        	if (!StringUtil.isEmpty(bundName))
        		b = getPropertyBundle(bundName);
        	else
	        	b = getPropertyBundle();
            if (!b.containsKey(key)) {
            	return def;
            }
            return b.getString(key);
        } catch (Throwable ignore) {
        	ignore.printStackTrace();
        	return def;
        }
    }
    /**
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        return getProperty(null, key, "");
    }
    /**
     * @param bundName
     * @param key
     * @return
     */
    public static String getProperty(String key, String def) {
        return getProperty(null, key, def);
    }
    // ============================================================= //
	public static String INVOKE_BUNDLE_NAME = "AppInvokes";
    /**
     * @return PropertyResourceBundle
     * @throws Throwable
     */
    public final static ResourceBundle getInvokePropertyBundle()
            throws Throwable {
        // ResourceBundle.getBundle(bundName)
        // ResourceBundle.getBundle(bundName,Locale.getDefault())
        // ResourceBundle.getBundle(bundName,Locale.getDefault(),getLoader()) (PropertyResourceBundle)
		String s = System.getProperty("INVOKE_BUNDLE_NAME");
		if (StringUtil.isEmpty(s))
			s = INVOKE_BUNDLE_NAME;
        return ResourceBundle.getBundle(s, Locale.getDefault(), Thread.currentThread().getContextClassLoader());
    }
    /**
     * @param key
     * @return
     */
    public static String getInvokeProperty(String key) {
        try {
            ResourceBundle b = getInvokePropertyBundle();
            if (!b.containsKey(key))
                return "";
            return b.getString(key);
        } catch (Throwable ignore) {
        	ignore.printStackTrace();
            return "";
        }
    }
    
}