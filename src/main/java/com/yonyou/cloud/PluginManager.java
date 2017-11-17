package com.yonyou.cloud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import com.yonyou.cloud.itf.PluginExtensionItf;

public class PluginManager {
	private static PluginExtensionItf[] plugins;
	private static PluginInvokerChain chain;
	
	static{
		ServiceLoader<PluginExtensionItf> serviceLoader = ServiceLoader.load(PluginExtensionItf.class); 
		 Iterator<PluginExtensionItf> it = serviceLoader.iterator();
		 List<PluginExtensionItf> list = new ArrayList<PluginExtensionItf>();
		 if(it.hasNext()) {
			 list.add(it.next());
		 }
		 Collections.sort(list, new Comparator<PluginExtensionItf>() {

			public int compare(PluginExtensionItf o1, PluginExtensionItf o2) {
				if(o1.order()<o2.order()) {
					return 1;
				}
				if(o1.order()>o2.order()) {
					return -1;
				}
				return 0;
			}
			 
		 });
		 plugins=list.toArray(plugins);
		 chain = new PluginInvokerChain();
	}
	
	public static void runPlugins(ExtensionContext context) {
		chain.run(plugins, context);
	}

}
