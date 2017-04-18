
package com.jfixby.r3.assets.packer;

import java.io.IOException;

import com.jfixby.r3.assets.packer.cfg.R3AssetsPackerConfig;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.LocalFileSystem;
import com.jfixby.scarabei.api.json.Json;

public class ConfigLoader {

	public static R3AssetsPackerConfig load (final String string) throws IOException {
		final File file = LocalFileSystem.ApplicationHome().child(string);
		final String json = file.readToString();
		return Json.deserializeFromString(R3AssetsPackerConfig.class, json);
	}

}
