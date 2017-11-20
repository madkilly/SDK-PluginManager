package com.yonyou.cloud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.cloud.itf.PluginExtensionItf;

public class PluginManager {
	private static Logger logger;
	private static boolean logflag =true;
	private static PluginExtensionItf[] plugins;


	static {
		try {
			logger = LoggerFactory.getLogger(PluginManager.class);
		} catch (Throwable e) {
			logger =null;
			logflag=false;
		}
		if(logflag) {
			logger.info("初始化微服务插件管理器PluginManager");
		}
		ServiceLoader<PluginExtensionItf> serviceLoader = ServiceLoader.load(PluginExtensionItf.class);
		Iterator<PluginExtensionItf> it = serviceLoader.iterator();
		List<PluginExtensionItf> list = new ArrayList<PluginExtensionItf>();
		while (it.hasNext()) {

			PluginExtensionItf itf = it.next();
			list.add(itf);
			if(logflag) {
				logger.info("加载插件" + itf.getPluginName());
			}
		}
		Collections.sort(list, new Comparator<PluginExtensionItf>() {

			public int compare(PluginExtensionItf o1, PluginExtensionItf o2) {
				if (o1.order() < o2.order()) {
					return 1;
				}
				if (o1.order() > o2.order()) {
					return -1;
				}
				return 0;
			}

		});
		plugins = list.toArray(new PluginExtensionItf[1]);
		if(logflag) {
			logger.info("初始化微服务插件管理器PluginManager 成功 已加载插件" + plugins.length + "个");
		}
	}

	public static void runPlugins(ExtensionContext context) {
		if(logflag) {
			StringBuilder sb = new StringBuilder();
			logger.info("执行插件链条");
			for(PluginExtensionItf itf:plugins){
				sb.append(itf.getPluginName()).append(",");
			}
			if(sb.length()>0){
				sb.deleteCharAt(sb.length()-1);
			}
			
			logger.info("链条执行顺序:"+sb.toString());
		} 
	    PluginInvokerChain chain = new PluginInvokerChain();
		for (int i=0;i<plugins.length;i++) {
			chain.run(plugins, context);
		}
	}

}
