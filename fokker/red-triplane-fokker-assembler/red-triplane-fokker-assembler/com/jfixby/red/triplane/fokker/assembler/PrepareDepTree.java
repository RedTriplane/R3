package com.jfixby.red.triplane.fokker.assembler;

import java.io.IOException;
import java.net.URISyntaxException;

import com.jfixby.cmns.api.collections.Set;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.sys.Sys;
import com.jfixby.cmns.api.util.JUtils;
import com.jfixby.cmns.desktop.DesktopAssembler;
import com.jfixby.tool.eclipse.dep.EclipseProjectDependencies;
import com.jfixby.tool.eclipse.dep.EclipseWorkSpaceSettings;

public class PrepareDepTree {

	public static void main(String[] args) throws IOException, URISyntaxException {

		DesktopAssembler.setup();

		File workspace_folder = LocalFileSystem.newFile("D:\\[DEV]\\[CODE]\\[WS-19]");

		EclipseWorkSpaceSettings workspace_settings = EclipseWorkSpaceSettings.readWorkspaceSettings(workspace_folder);
		Sys.exit();

		File desktop_project_folder = LocalFileSystem.newFile("D:\\[DEV]\\[GIT]\\Tinto\\tinto-run-fokker-desktop");
		File android_project_folder = LocalFileSystem.newFile("D:\\[DEV]\\[GIT]\\Tinto\\tinto-run-fokker-android");
		File ios_project_folder = LocalFileSystem.newFile("D:\\[DEV]\\[GIT]\\Tinto\\tinto-run-fokker-ios");
		File gwt_project_folder = LocalFileSystem.newFile("D:\\[DEV]\\[GIT]\\Tinto\\tinto-run-fokker-gwt");

		String gradle_path_string = "D:\\[DEV]\\[CODE]\\[GDX]\\tinto";
		File gradle_path = LocalFileSystem.newFile(gradle_path_string);

		EclipseProjectDependencies desktop = getDependencies(desktop_project_folder);
		EclipseProjectDependencies android = getDependencies(android_project_folder);
		EclipseProjectDependencies ios = getDependencies(ios_project_folder);
		EclipseProjectDependencies gwt = getDependencies(gwt_project_folder);

		Set<String> intersection = JUtils.intersectCollection(desktop.getProjectsList(), gwt.getProjectsList());
		intersection = JUtils.intersectCollection(intersection, android.getProjectsList());
		intersection = JUtils.intersectCollection(intersection, ios.getProjectsList());
		intersection.print("desktop & gwt & android & ios : common projetcs");

	}

	private static EclipseProjectDependencies getDependencies(File project_folder) throws IOException {
		EclipseProjectDependencies project_dependencies = EclipseProjectDependencies
				.extractFromClassPathFile(project_folder);

		project_dependencies.print();
		return project_dependencies;
	}

}
