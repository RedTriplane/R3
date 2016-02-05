package com.jfixby.red.triplane.fokker.assembler;

import java.io.IOException;

import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.collections.Set;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.util.JUtils;
import com.jfixby.cmns.desktop.DesktopAssembler;
import com.jfixby.tool.eclipse.dep.EclipseProjectDependencies;

public class PrepareDepTree {

	public static void main(String[] args) throws IOException {

		DesktopAssembler.setup();

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

		List<String> projectsA = desktop.getProjectsList();
		List<String> projectsB = gwt.getProjectsList();

		Set<String> intersection = JUtils.intersectLists(projectsA, projectsB);

		intersection.print("common-projects");

	}

	private static EclipseProjectDependencies getDependencies(File project_folder) throws IOException {
		EclipseProjectDependencies project_dependencies = EclipseProjectDependencies
				.extractFromClassPathFile(project_folder);

		project_dependencies.print();
		return project_dependencies;
	}

}
