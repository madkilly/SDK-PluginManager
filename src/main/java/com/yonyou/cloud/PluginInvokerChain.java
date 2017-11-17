package com.yonyou.cloud;

import com.yonyou.cloud.itf.PluginExtensionItf;

public class PluginInvokerChain {
	private int cursor = 0;
	
	public PluginInvokerChain() {
	}
	public void run(PluginExtensionItf[] plugins,ExtensionContext context) {
		if (cursor < plugins.length) {
			PluginExtensionItf itf = plugins[cursor++];
			itf.run(context);
			return;
		}
		throw new IllegalStateException("The cursor cross-border, cursor: " + cursor + ", invoker size: " + plugins.length);
	
	}

}
