package com.jfixby.r3.fokker.api.assets;

import com.jfixby.cmns.api.ComponentInstaller;
import com.jfixby.cmns.api.assets.AssetID;

public class FokkerRasterDataRegister {

	static private ComponentInstaller<FokkerRasterDataRegisterComponent> componentInstaller = new ComponentInstaller<FokkerRasterDataRegisterComponent>(
			"FokkerRasterDataRegister");

	public static final void installComponent(
			FokkerRasterDataRegisterComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final FokkerRasterDataRegisterComponent invoke() {
		return componentInstaller.invokeComponent();
	}

	public static final FokkerRasterDataRegisterComponent component() {
		return componentInstaller.getComponent();
	}

	static public void registerRaster(AssetID rester_id, FokkerRasterData data) {
		invoke().registerRaster(rester_id, data);
	}

	public static FokkerRasterData getGdxRasterData(AssetID asset_id) {
		return invoke().getGdxRasterData(asset_id);
	}

	public static void printAll(String string) {
		invoke().printAll(string);
	}

}
