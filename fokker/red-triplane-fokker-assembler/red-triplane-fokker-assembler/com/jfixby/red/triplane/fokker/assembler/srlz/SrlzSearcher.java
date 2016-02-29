package com.jfixby.red.triplane.fokker.assembler.srlz;

import java.io.IOException;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.collections.Set;
import com.jfixby.cmns.api.file.ChildrenList;
import com.jfixby.cmns.api.file.File;
import com.jfixby.red.triplane.fokker.assembler.AndroidProjectSettings;
import com.jfixby.red.triplane.fokker.assembler.DesktopProjectSettings;
import com.jfixby.red.triplane.fokker.assembler.GwtProjectSettings;
import com.jfixby.red.triplane.fokker.assembler.iOSProjectSettings;
import com.jfixby.tool.eclipse.dep.EclipseProjectDependencies;
import com.jfixby.tool.eclipse.dep.EclipseProjectInfo;
import com.jfixby.tool.eclipse.dep.EclipseWorkSpaceSettings;

public class SrlzSearcher {

	final private List<File> sources_list = Collections.newList();;
	final private List<JavaFileHandler> java_files_list = Collections.newList();;

	public SrlzSearcher(SrlzSearcherSpecs specs) throws IOException {

		DesktopProjectSettings desktop = specs.getDesktopProjectSettings();
		GwtProjectSettings gwt = specs.getGwtProjectSettings();
		iOSProjectSettings ios = specs.getiOSProjectSettings();
		AndroidProjectSettings android = specs.getAndroidProjectSettings();

		File gradle_path = specs.getGradlePath();

		File workspace_folder = specs.getWorkSpaceFolder();
		EclipseWorkSpaceSettings workspace_settings = EclipseWorkSpaceSettings.readWorkspaceSettings(workspace_folder);

		Set<String> projects = Collections.newSet();
		projects.addAll(desktop.getEclipseProjectInfo().getDependencies().getProjectsList());
		projects.add(desktop.getEclipseProjectInfo().getProjectName());
		
//		projects.addAll(ios.getEclipseProjectInfo().getDependencies().getProjectsList());
//		projects.add(ios.getEclipseProjectInfo().getProjectName());
		
		projects.addAll(gwt.getEclipseProjectInfo().getDependencies().getProjectsList());
		projects.add(gwt.getEclipseProjectInfo().getProjectName());
		
		projects.addAll(android.getEclipseProjectInfo().getDependencies().getProjectsList());
		projects.add(android.getEclipseProjectInfo().getProjectName());

		// projects.print("projects");

		Set<EclipseProjectInfo> complete_dependency_list = buildCompleteList(workspace_settings, projects);

		// complete_dependency_list.print("all");

		for (int i = 0; i < complete_dependency_list.size(); i++) {
			EclipseProjectInfo project = complete_dependency_list.getElementAt(i);
			File path = project.getProjectPath();
			Collection<String> sources = project.getDependencies().getSourceFoldersList();
			for (int k = 0; k < sources.size(); k++) {
				String child_name = sources.getElementAt(k);
				File src_path = path.child(child_name);
				// L.d("source folder", src_path);
				sources_list.add(src_path);
			}
		}
		java_files_list.clear();
		for (int i = 0; i < this.sources_list.size(); i++) {
			File sources = sources_list.getElementAt(i);
			indexJavaFiles(sources);
		}
//		java_files_list.print(".java");
//		Sys.exit();
	}

	private void indexJavaFiles(File sources) throws IOException {
		if (sources.extensionIs(".java")) {
			JavaFileHandler handler = new JavaFileHandler(sources);
			this.java_files_list.add(handler);
		} else if (sources.isFolder()) {
			ChildrenList java_files = sources.listChildren().filterByExtension(".java");
			for (File src : java_files) {
				indexJavaFiles(src);
			}
			ChildrenList subfoldes = sources.listSubFolders();
			for (File folder : subfoldes) {
				indexJavaFiles(folder);
			}
		}
	}

	private static Set<EclipseProjectInfo> buildCompleteList(EclipseWorkSpaceSettings workspace_settings,
			Collection<String> begin_set) {

//		begin_set.print("begin_set");
//		Sys.exit();

		Set<String> processing = Collections.newSet();
		Set<EclipseProjectInfo> processed_projects = Collections.newSet();
		Set<String> unprocessed_projects = Collections.newSet();
		unprocessed_projects.addAll(begin_set);

		while (unprocessed_projects.size() > 0) {
			processing.addAll(unprocessed_projects);
			unprocessed_projects.clear();
			for (int i = 0; i < processing.size(); i++) {
				String core_project_name = processing.getElementAt(i);
				EclipseProjectInfo info = workspace_settings.getProjectInfo(core_project_name);
				processed_projects.add(info);
				// L.d("info", info);

				EclipseProjectDependencies project_dependencies = info.getDependencies();
				Collection<String> projects = project_dependencies.getProjectsList();
				// projects.print("expand");
				unprocessed_projects.addAll(projects);
			}
			processing.clear();
		}
		return processed_projects;
	}

	public Collection<JavaFileHandler> filterFiles(JavaFileFilter filter) {
		return filterFiles(this.java_files_list, filter);
	}

	public static Collection<JavaFileHandler> filterFiles(Collection<JavaFileHandler> files_list,
			JavaFileFilter filter) {
		List<JavaFileHandler> result = Collections.newList();
		for (JavaFileHandler file : files_list) {
			if (filter.fits(file)) {
				result.add(file);
			}
		}
		return result;
	}

}
