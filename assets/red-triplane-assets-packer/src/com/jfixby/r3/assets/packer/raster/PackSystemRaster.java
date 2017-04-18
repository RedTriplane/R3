
package com.jfixby.r3.assets.packer.raster;

import java.io.IOException;

import com.jfixby.r3.assets.packer.ConfigLoader;
import com.jfixby.r3.assets.packer.cfg.R3AssetsPackerConfig;
import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.LocalFileSystem;
import com.jfixby.scarabei.api.json.Json;
import com.jfixby.scarabei.gson.GoogleGson;

public class PackSystemRaster {

	public static void main (final String[] args) throws IOException {
		ScarabeiDesktop.deploy();
		Json.installComponent(new GoogleGson());

		final R3AssetsPackerConfig cfg = ConfigLoader.load("r3-assets-packer-config.json");

		final File outputBankFolder = LocalFileSystem.newFile(cfg.outputBankFolderPath);
		final File tank = outputBankFolder.child(cfg.targetTank);
		tank.listDirectChildren().print("tank");

		SystemRasterPacker.pack(tank);

	}

}
