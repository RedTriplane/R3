
package com.jfixby.red.triplane.fokker.assembler.run;

import java.io.IOException;
import java.net.URISyntaxException;

import com.jfixby.cmns.api.desktop.DesktopSetup;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.json.Json;

public class TransferAssets {

	public static void main (final String[] args) throws IOException, URISyntaxException {

		DesktopSetup.deploy();
		Json.installComponent("com.jfixby.cmns.adopted.gdx.json.RedJson");

		final File assets_folder = LocalFileSystem.newFile("D:\\[DATA]\\[RED-ASSETS]\\Art-Private\\tinto-assets");

		final String gradle_path_string = "D:\\[DEV]\\[CODE]\\[GDX]\\tinto";
		final File gradle_path = LocalFileSystem.newFile(gradle_path_string);

	}

}
