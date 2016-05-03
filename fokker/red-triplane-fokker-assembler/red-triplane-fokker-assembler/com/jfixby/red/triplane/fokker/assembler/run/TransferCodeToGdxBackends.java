package com.jfixby.red.triplane.fokker.assembler.run;

import java.io.IOException;
import java.net.URISyntaxException;

import com.jfixby.cmns.adopted.gdx.json.RedJson;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.cmns.api.log.L;
import com.jfixby.red.desktop.DesktopSetup;
import com.jfixby.red.triplane.fokker.assembler.AndroidProjectSettings;
import com.jfixby.red.triplane.fokker.assembler.DesktopProjectSettings;
import com.jfixby.red.triplane.fokker.assembler.FokkerAssembley;
import com.jfixby.red.triplane.fokker.assembler.FokkerAssembleySpecs;
import com.jfixby.red.triplane.fokker.assembler.GwtProjectSettings;
import com.jfixby.red.triplane.fokker.assembler.iOSProjectSettings;

public class TransferCodeToGdxBackends {

	public static void main(String[] args) throws IOException, URISyntaxException {

		DesktopSetup.deploy();
		Json.installComponent(new RedJson());

		File workspace_folder = LocalFileSystem.newFile("D:\\[DEV]\\[CODE]\\[WS-19]");

		// workspace_settings.print();
		// Sys.exit();

		File desktop_project_folder = LocalFileSystem.newFile("D:\\[DEV]\\[GIT]\\Tinto\\tinto-run-fokker-desktop");
		File android_project_folder = LocalFileSystem.newFile("D:\\[DEV]\\[GIT]\\Tinto\\tinto-run-fokker-android");
		File ios_project_folder = LocalFileSystem.newFile("D:\\[DEV]\\[GIT]\\Tinto\\tinto-run-fokker-ios");
		File gwt_project_folder = LocalFileSystem.newFile("D:\\[DEV]\\[GIT]\\Tinto\\tinto-run-fokker-gwt");

		String gradle_path_string = "D:\\[DEV]\\[CODE]\\[GDX]\\tinto";
		File gradle_path = LocalFileSystem.newFile(gradle_path_string);

		DesktopProjectSettings desktop = new DesktopProjectSettings(desktop_project_folder);
		AndroidProjectSettings android = new AndroidProjectSettings(android_project_folder);
		iOSProjectSettings ios = new iOSProjectSettings(ios_project_folder);
		GwtProjectSettings gwt = new GwtProjectSettings(gwt_project_folder);

		FokkerAssembleySpecs specs = new FokkerAssembleySpecs();
		specs.setGradleProjectPath(gradle_path);
		specs.setDesktopProject(desktop);
		specs.setAndroidProject(android);
		specs.setiOSProject(ios);
		specs.setGWTProject(gwt);
		specs.setWorkSpaceFolder(workspace_folder);

		FokkerAssembley assembley = new FokkerAssembley(specs);

		assembley.deletePreviousTransactionsIfPresent();
		// assembley.printTransactions();
		assembley.executeCodeTransfer();

		L.d("DONE");

	}

}
