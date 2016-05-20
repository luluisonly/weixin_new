package com.bokesoft.thirdparty.weixin.publicnumber;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.common.FileUtil;
import com.bokesoft.thirdparty.weixin.common.TConstant;

/**
 * 本地的微信公众号提供者
 */
public class LocalWeixinPublicNumberProvider implements WeixinPublicNumberProvider {

	private String config_subfix = ".conf";
	private static final Logger LOGGER = Logger.getLogger(LocalWeixinPublicNumberProvider.class);
	@Override
	public WeixinPublicNumber getWeixinPublicNumber(WeixinContext context,String uname) throws Exception{
		return context.getWeixinPublicNumberFromLocal(uname);
	}
	@Override
	public void initialize(WeixinContext context) throws Exception {
		File root = new File(TConstant.WEIXIN_CONF_PATH);
		if (!root.exists()) {
			LOGGER.warn("Floder not found! Path:"+root.getAbsolutePath()+",Program will try to create it!");
			FileUtil.forceMkdir(root);
			LOGGER.info("Floder:"+root.getAbsolutePath()+" was created!");
		}
		LOGGER.info("Publicnumber-config-path:"+root.getAbsolutePath());
		File[] configs = root.listFiles();
		if (configs != null) {
			for (File config : configs) {
				try {
					loadPublicNumberConfig(context, config);
				} catch (Exception e) {
					e.printStackTrace();
					String fileName = config.getName();
					String uname = fileName.substring(0, fileName.lastIndexOf("."));
					context.getWeixinPublicNumbers().remove(uname);
				}
			}
		}
	}

	private void loadPublicNumberConfig(WeixinContext context, File file)throws Exception {
		String content = FileUtil.readFileToString(file, "UTF-8");
		JSONObject jsonObject = JSONObject.parseObject(content);
		loadPublicNumberConfig(context, jsonObject);
	}

	private void loadPublicNumberConfig(WeixinContext context, JSONObject jsonObject)throws Exception {
		WeixinPublicNumberUpdater weixinPublicNumberUpdater = context.getWeixinPublicNumberUpdater();
		weixinPublicNumberUpdater.updateAllProperties(context,jsonObject);
	}
	@Override
	public void updateWeixinPublicNumber(WeixinContext context, WeixinPublicNumber publicNumber) throws IOException {
		String uname = publicNumber.getUname();
		FileUtil.writeStringToFile(
					new File(TConstant.WEIXIN_CONF_PATH + uname + config_subfix),
					publicNumber.toString(true),
					"UTF-8",
					false);
	}
	@Override
	public void removeWeixinPublicNumber(WeixinContext context, String uname)throws Exception {
		FileUtil.deleteDirectoryOrFileByCls(TConstant.WEIXIN_CONF_PATH+uname+config_subfix);
	}
	@Override
	public void shoutdown(WeixinContext context) throws Exception {
	}
	@Override
	public void addWeixinPublicNumber(WeixinContext context,WeixinPublicNumber publicNumber) throws Exception {
		updateWeixinPublicNumber(context, publicNumber);
	}
	

}
