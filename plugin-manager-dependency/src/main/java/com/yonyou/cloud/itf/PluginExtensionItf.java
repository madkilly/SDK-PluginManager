package com.yonyou.cloud.itf;

import com.yonyou.cloud.ExtensionContext;

public interface PluginExtensionItf {
	
	public boolean run(ExtensionContext context);
	
	public int order();
	
	public String getPluginName();
}
