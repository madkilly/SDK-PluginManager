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
	private static final Logger logger = LoggerFactory.getLogger(PluginManager.class);
	private static PluginExtensionItf[] plugins;


	static {
		logger.info("初始化微服务插件管理器PluginManager");
		ServiceLoader<PluginExtensionItf> serviceLoader = ServiceLoader.load(PluginExtensionItf.class);
		Iterator<PluginExtensionItf> it = serviceLoader.iterator();
		List<PluginExtensionItf> list = new ArrayList<PluginExtensionItf>();
		if (it.hasNext()) {

			PluginExtensionItf itf = it.next();
			list.add(itf);
			logger.info("加载插件" + itf.getPluginName());
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
		plugins = list.toArray(plugins);
		logger.info("初始化微服务插件管理器PluginManager 成功 已加载插件" + plugins.length + "个");
	}

	public static void runPlugins(ExtensionContext context) {
		logger.info("执行插件链条");
	    PluginInvokerChain chain = new PluginInvokerChain();
		for (int i=0;i<plugins.length;i++) {
			chain.run(plugins, context);
		}
	}

}