package com.jfixby.r3.api;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.components.ComponentInstaller;

public class RedTriplane {
	static private ComponentInstaller<RedTriplaneComponent> componentInstaller = new ComponentInstaller<RedTriplaneComponent>(
			"RedTriplane");

	public static final void installComponent(
			RedTriplaneComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final RedTriplaneComponent invoke() {
		return componentInstaller.invokeComponent();
	}

	public static final RedTriplaneComponent component() {
		return componentInstaller.getComponent();
	}

	public static void setExecutionMode(ExecutionMode developer) {
		componentInstaller.invokeComponent().setExecutionMode(developer);
	}

	public static void setFlag(String flag_name, boolean flag_value) {
		componentInstaller.invokeComponent().setFlag(flag_name,
				flag_value);
	}

	public static boolean getFlag(String flag_name) {
		return componentInstaller.invokeComponent().getFlag(flag_name);
	}

	public static String getStringParameter(String parameter_name) {
		return componentInstaller.invokeComponent().getStringParameter(
				parameter_name);
	}

	public static void setStringParameter(String parameter_name,
			String parameter_value) {
		componentInstaller.invokeComponent().setStringParameter(parameter_name,
				parameter_value);
	}

	public static void setSystemAssetID(String parameter_name,
			AssetID parameter_value) {
		componentInstaller.invokeComponent().setSystemAssetID(parameter_name,
				parameter_value);
	}

	public static AssetID getSystemAssetID(String parameter_name) {
		return componentInstaller.invokeComponent().getSystemAssetID(
				parameter_name);
	}


}
