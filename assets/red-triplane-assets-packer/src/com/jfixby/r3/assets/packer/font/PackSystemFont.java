
package com.jfixby.r3.assets.packer.font;

import java.io.IOException;

import com.jfixby.r3.assets.packer.cfg.ConfigLoader;
import com.jfixby.r3.assets.packer.cfg.R3AssetsPackerConfig;
import com.jfixby.r3.engine.core.unit.raster.FOKKER_SYSTEM_ASSETS;
import com.jfixby.scarabei.api.assets.Names;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.LocalFileSystem;
import com.jfixby.scarabei.api.json.Json;
import com.jfixby.scarabei.gson.GoogleGson;

public class PackSystemFont {

	public static void main (final String[] args) throws IOException {
		ScarabeiDesktop.deploy();
		Json.installComponent(new GoogleGson());

		final R3AssetsPackerConfig cfg = ConfigLoader.load("r3-assets-packer-config.json");

		final File outputBankFolder = LocalFileSystem.newFile(cfg.outputBankFolderPath);
		final File tank = outputBankFolder.child(cfg.targetTank);
		tank.listDirectChildren().print("tank");
		final String fontName = FOKKER_SYSTEM_ASSETS.GENERIC_FONT.toString();
		final File fontFolder = LocalFileSystem.ApplicationHome().child("fonts").child(fontName);
		final List<String> steps = FOKKER_SYSTEM_ASSETS.GENERIC_FONT.steps();
		steps.reverse();
		final String fontFileName = Names.newID(steps).toString();
		SystemFontPacker.packSystemFont(tank, fontFolder, fontFileName);
	}

}
