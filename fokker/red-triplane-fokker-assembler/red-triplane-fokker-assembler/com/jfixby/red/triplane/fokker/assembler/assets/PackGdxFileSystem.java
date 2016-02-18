package com.jfixby.red.triplane.fokker.assembler.assets;

import java.io.IOException;

import com.jfixby.cmns.adopted.gdx.json.GdxJson;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.red.desktop.DesktopAssembler;
import com.jfixby.redtriplane.fokker.fs.AssetsInfo;
import com.jfixby.redtriplane.fokker.fs.GdxAssetsFileSystemIndex;

public class PackGdxFileSystem {

	public static void main(String[] args) throws IOException {
		if (args != null) {
			DesktopAssembler.setup();
			Json.installComponent(new GdxJson());
		}

		File assets_folder = LocalFileSystem.newFile("D:\\[DATA]\\[RED-ASSETS]\\Art-Private\\tinto-assets")
				.child("content");

		String gradle_path_string = "D:\\[DEV]\\[CODE]\\[GDX]\\tinto";
		File gradle_path = LocalFileSystem.newFile(gradle_path_string);
		// D:\[DEV]\[CODE]\[GDX]\tinto\android\assets

		File output_folder = gradle_path.child("android").child("assets");
		output_folder.makeFolder();
		output_folder.clearFolder();

		AssetsInfo info = getCurrent();

		GdxAssetsFileSystemIndex.index(assets_folder, output_folder);

		String data = Json.serializeToString(info);
		output_folder.child(AssetsInfo.FILE_NAME).writeString(data);
		// os.close();
		info.print();

	}

	private static AssetsInfo getCurrent() throws IOException {
		File info_file = LocalFileSystem.ApplicationHome().child(AssetsInfo.FILE_NAME);
		AssetsInfo info = null;
		if (info_file.exists()) {
			try {
				String data = info_file.readToString();
				info = Json.deserializeFromString(AssetsInfo.class, data);
			} catch (IOException e) {
				e.printStackTrace();
				info = new AssetsInfo();
			}
		} else {
			info = new AssetsInfo();
		}

		info.next();
		String data = Json.serializeToString(info);
		info_file.writeString(data);
		return info;
	}
}
