
package com.jfixby.red.triplane.fokker.assembler.run;

import java.io.IOException;
import java.net.URISyntaxException;

import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.LocalFileSystem;
import com.jfixby.scarabei.api.json.Json;

public class TransferAssets {

	public static void main (final String[] args) throws IOException, URISyntaxException {

		ScarabeiDesktop.deploy();
		Json.installComponent("com.jfixby.cmns.adopted.gdx.json.RedJson");

		final File assets_folder = LocalFileSystem.newFile("D:\\[DATA]\\[RED-ASSETS]\\Art-Private\\tinto-assets");

		final String gradle_path_string = "D:\\[DEV]\\[CODE]\\[GDX]\\tinto";
		final File gradle_path = LocalFileSystem.newFile(gradle_path_string);

	}

}
