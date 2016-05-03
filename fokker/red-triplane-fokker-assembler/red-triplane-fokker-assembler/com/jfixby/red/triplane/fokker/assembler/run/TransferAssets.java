package com.jfixby.red.triplane.fokker.assembler.run;

import java.io.IOException;
import java.net.URISyntaxException;

import com.jfixby.cmns.adopted.gdx.json.RedJson;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.red.desktop.DesktopSetup;

public class TransferAssets {

	public static void main(String[] args) throws IOException, URISyntaxException {

		DesktopSetup.deploy();
		Json.installComponent(new RedJson());

		File assets_folder = LocalFileSystem.newFile("D:\\[DATA]\\[RED-ASSETS]\\Art-Private\\tinto-assets");

		String gradle_path_string = "D:\\[DEV]\\[CODE]\\[GDX]\\tinto";
		File gradle_path = LocalFileSystem.newFile(gradle_path_string);

	}

}
