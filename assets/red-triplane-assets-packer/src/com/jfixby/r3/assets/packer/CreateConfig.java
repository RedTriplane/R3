
package com.jfixby.r3.assets.packer;

import java.io.IOException;

import com.jfixby.r3.assets.packer.cfg.R3AssetsPackerConfig;
import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.LocalFileSystem;
import com.jfixby.scarabei.api.json.Json;
import com.jfixby.scarabei.api.json.JsonString;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.gson.GoogleGson;

public class CreateConfig {

	public static void main (final String[] args) throws IOException {

		ScarabeiDesktop.deploy();
		Json.installComponent(new GoogleGson());
		final R3AssetsPackerConfig config = new R3AssetsPackerConfig();

		config.outputBankFolderPath = "D:/POOL/Tinto/tinto-run-fokker-desktop/assets/com.red-triplane.assets.r3.local";

		final File outputFile = LocalFileSystem.ApplicationHome().child(R3AssetsPackerConfig.FILE_NAME);

		final JsonString json = Json.serializeToString(config);
		L.d(json);
		L.d("writing to", outputFile);
		outputFile.writeString(json.toString());
	}

}
