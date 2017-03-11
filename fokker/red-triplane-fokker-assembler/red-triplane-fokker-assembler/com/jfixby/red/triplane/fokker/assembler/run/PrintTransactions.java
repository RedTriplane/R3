
package com.jfixby.red.triplane.fokker.assembler.run;

import java.io.IOException;
import java.net.URISyntaxException;

import com.jfixby.red.triplane.fokker.assembler.AndroidProjectSettings;
import com.jfixby.red.triplane.fokker.assembler.DesktopProjectSettings;
import com.jfixby.red.triplane.fokker.assembler.FokkerAssembley;
import com.jfixby.red.triplane.fokker.assembler.FokkerAssembleySpecs;
import com.jfixby.red.triplane.fokker.assembler.GwtProjectSettings;
import com.jfixby.red.triplane.fokker.assembler.iOSProjectSettings;
import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.LocalFileSystem;
import com.jfixby.scarabei.api.json.Json;
import com.jfixby.scarabei.gson.GoogleGson;

public class PrintTransactions {

	public static void main (final String[] args) throws IOException, URISyntaxException {

		ScarabeiDesktop.deploy();
		Json.installComponent(new GoogleGson());

		final File workspace_folder = LocalFileSystem.newFile("D:\\[DEV]\\[CODE]\\[WS-19]");

		// workspace_settings.print();
		// Sys.exit();

		final File desktop_project_folder = LocalFileSystem.newFile("D:\\[DEV]\\[GIT]\\Tinto\\tinto-run-fokker-desktop");
		final File android_project_folder = LocalFileSystem.newFile("D:\\[DEV]\\[GIT]\\Tinto\\tinto-run-fokker-android");
		final File ios_project_folder = LocalFileSystem.newFile("D:\\[DEV]\\[GIT]\\Tinto\\tinto-run-fokker-ios");
		final File gwt_project_folder = LocalFileSystem.newFile("D:\\[DEV]\\[GIT]\\Tinto\\tinto-run-fokker-gwt");

		final String gradle_path_string = "D:\\[DEV]\\[CODE]\\[GDX]\\tinto";
		final File gradle_path = LocalFileSystem.newFile(gradle_path_string);

		final DesktopProjectSettings desktop = new DesktopProjectSettings(desktop_project_folder);
		final AndroidProjectSettings android = new AndroidProjectSettings(android_project_folder);
		final iOSProjectSettings ios = new iOSProjectSettings(ios_project_folder);
		final GwtProjectSettings gwt = new GwtProjectSettings(gwt_project_folder);

		final FokkerAssembleySpecs specs = new FokkerAssembleySpecs();
		specs.setGradleProjectPath(gradle_path);
		specs.setDesktopProject(desktop);
		specs.setAndroidProject(android);
		specs.setiOSProject(ios);
		specs.setGWTProject(gwt);
		specs.setWorkSpaceFolder(workspace_folder);

		final FokkerAssembley assembley = new FokkerAssembley(specs);

		// assembley.deletePreviousTransactionsIfPresent();
		assembley.printDependencies();
		// assembley.executeCodeTransfer();

	}

}
