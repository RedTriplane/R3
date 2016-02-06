package com.jfixby.red.triplane.fokker.assembler;

import java.io.IOException;
import java.net.URISyntaxException;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.collections.Set;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.util.JUtils;
import com.jfixby.cmns.desktop.DesktopAssembler;
import com.jfixby.tool.eclipse.dep.EclipseProjectDependencies;
import com.jfixby.tool.eclipse.dep.EclipseProjectInfo;
import com.jfixby.tool.eclipse.dep.EclipseWorkSpaceSettings;

public class PrepareDepTree {

	public static void main(String[] args) throws IOException, URISyntaxException {

		DesktopAssembler.setup();

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
		assembley.printTransactions();

	}

}
