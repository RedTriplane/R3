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

		EclipseWorkSpaceSettings workspace_settings = EclipseWorkSpaceSettings.readWorkspaceSettings(workspace_folder);
		// workspace_settings.print();
		// Sys.exit();

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

		Set<String> core_project_names = JUtils.intersectCollection(desktop.getProjectsList(), gwt.getProjectsList());
		core_project_names = JUtils.intersectCollection(core_project_names, android.getProjectsList());
		core_project_names = JUtils.intersectCollection(core_project_names, ios.getProjectsList());
//		core_project_names.print("desktop & gwt & android & ios : common projetcs");

		List<EclipseProjectInfo> core_complete_dependency_list = buildCompleteList(workspace_settings,
				core_project_names);
		core_complete_dependency_list.print("core complete_dependency_list");

		List<EclipseProjectInfo> desktop_complete_dependency_list = buildAdditionalDependenciesList(workspace_settings,
				desktop, core_complete_dependency_list);
		desktop_complete_dependency_list.print("desktop_complete_dependency_list");

		List<EclipseProjectInfo> android_complete_dependency_list = buildAdditionalDependenciesList(workspace_settings,
				android, core_complete_dependency_list);
		android_complete_dependency_list.print("android_complete_dependency_list");

		List<EclipseProjectInfo> ios_complete_dependency_list = buildAdditionalDependenciesList(workspace_settings, ios,
				core_complete_dependency_list);
		ios_complete_dependency_list.print("ios_complete_dependency_list");

		List<EclipseProjectInfo> gwt_complete_dependency_list = buildAdditionalDependenciesList(workspace_settings, gwt,
				core_complete_dependency_list);
		gwt_complete_dependency_list.print("gwt_complete_dependency_list");

	}

	private static List<EclipseProjectInfo> buildAdditionalDependenciesList(EclipseWorkSpaceSettings workspace_settings,
			EclipseProjectDependencies begin_dependencies, List<EclipseProjectInfo> core_complete_dependency_list) {
		List<EclipseProjectInfo> result = buildCompleteList(workspace_settings, begin_dependencies.getProjectsList());
		result.removeAll(core_complete_dependency_list);
		// result.print("desktop_complete_dependency_list");
		return result;
	}

	private static List<EclipseProjectInfo> buildCompleteList(EclipseWorkSpaceSettings workspace_settings,
			Collection<String> begin_set) {

		Set<String> processing = Collections.newSet();
		List<EclipseProjectInfo> processed_projects = Collections.newList();
		Set<String> unprocessed_projects = Collections.newSet();
		unprocessed_projects.addAll(begin_set);

		while (unprocessed_projects.size() > 0) {
			processing.addAll(unprocessed_projects);
			unprocessed_projects.clear();
			for (int i = 0; i < processing.size(); i++) {
				String core_project_name = processing.getElementAt(i);
				EclipseProjectInfo info = workspace_settings.getProjectInfo(core_project_name);
				processed_projects.add(info);
				EclipseProjectDependencies project_dependencies = info.getDependencies();
				Collection<String> projects = project_dependencies.getProjectsList();
				unprocessed_projects.addAll(projects);
			}
			processing.clear();
		}
		return processed_projects;
	}

	private static EclipseProjectDependencies getDependencies(File project_folder) throws IOException {
		EclipseProjectDependencies project_dependencies = EclipseProjectDependencies
				.extractFromClassPathFile(project_folder);

//		project_dependencies.print();
		return project_dependencies;
	}

}
