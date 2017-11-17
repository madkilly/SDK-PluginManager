package com.yonyou.cloud;

import com.yonyou.cloud.itf.PluginExtensionItf;

public class PluginInvokerChain {
	private int cursor = 0;
	
	public PluginInvokerChain() {
	}
	public void run(PluginExtensionItf[] plugins,ExtensionContext context) {
		if (cursor < plugins.length) {
			PluginExtensionItf itf = plugins[cursor++]; // 获取并将游标指向下一个Invoker
			itf.run(context);
		}
		throw new IllegalStateException("The cursor cross-border, cursor: " + cursor + ", invoker size: " + plugins.length);
	
	}

}
