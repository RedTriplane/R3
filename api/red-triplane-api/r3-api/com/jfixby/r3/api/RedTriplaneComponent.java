package com.jfixby.r3.api;

import com.jfixby.cmns.api.assets.AssetID;

public interface RedTriplaneComponent {

	void setExecutionMode(ExecutionMode execution_mode);

	void setFlag(String flag_name, boolean flag_value);

	boolean getFlag(String flag_name);

	String getStringParameter(String parameter_name);

	void setStringParameter(String parameter_name, String parameter_value);

	void setSystemAssetID(String parameter_name, AssetID parameter_value);

	AssetID getSystemAssetID(String parameter_name);

	

}
