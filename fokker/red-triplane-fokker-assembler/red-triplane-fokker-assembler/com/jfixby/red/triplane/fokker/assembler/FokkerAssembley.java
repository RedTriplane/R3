package com.jfixby.red.triplane.fokker.assembler;

import java.io.IOException;
import java.util.Vector;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.collections.Set;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.util.JUtils;
import com.jfixby.tool.eclipse.dep.EclipseProjectDependencies;
import com.jfixby.tool.eclipse.dep.EclipseProjectInfo;
import com.jfixby.tool.eclipse.dep.EclipseWorkSpaceSettings;

public class FokkerAssembley {

	private CoreAssembley core_assembley;
	private DesktopAssembley desktop_assembley;
	private AndroidAssembley android_assembley;
	private iOSAssembley ios_assembley;
	private GWTAssembley gwt_assembley;

	public FokkerAssembley(FokkerAssembleySpecs specs) throws IOException {

		DesktopProjectSettings desktop = specs.getDesktopProjectSettings();
		GwtProjectSettings gwt = specs.getGwtProjectSettings();
		iOSProjectSettings ios = specs.getiOSProjectSettings();
		AndroidProjectSettings android = specs.getAndroidProjectSettings();

		File gradle_path = specs.getGradlePath();

		File workspace_folder = specs.getWorkSpaceFolder();
		EclipseWorkSpaceSettings workspace_settings = EclipseWorkSpaceSettings.readWorkspaceSettings(workspace_folder);

		Set<String> core_project_names = JUtils.intersectCollection(
				desktop//
						.getEclipseProjectInfo()//
						.getDependencies()//
						.getProjectsList(),
				gwt//
						.getEclipseProjectInfo()//
						.getDependencies()//
						.getProjectsList());
		core_project_names = JUtils.intersectCollection(core_project_names,
				android.getEclipseProjectInfo().getDependencies().getProjectsList());
		core_project_names = JUtils.intersectCollection(core_project_names,
				ios.getEclipseProjectInfo().getDependencies().getProjectsList());
		core_project_names.print("desktop & gwt & android & ios : common projetcs");

		List<EclipseProjectInfo> core_complete_dependency_list = buildCompleteList(workspace_settings,
				core_project_names);
		core_complete_dependency_list.print("core complete_dependency_list");
		core_assembley = new CoreAssembley(this, core_complete_dependency_list, gradle_path.child("core"));
		{
			List<EclipseProjectInfo> desktop_complete_dependency_list = buildAdditionalDependenciesList(
					workspace_settings, desktop.getEclipseProjectInfo(), core_complete_dependency_list);
			// desktop_complete_dependency_list.print("desktop_complete_dependency_list");
			desktop_assembley = new DesktopAssembley(this, desktop_complete_dependency_list,
					gradle_path.child("desktop"));
		}
		{
			List<EclipseProjectInfo> android_complete_dependency_list = buildAdditionalDependenciesList(
					workspace_settings, android.getEclipseProjectInfo(), core_complete_dependency_list);
			// android_complete_dependency_list.print("android_complete_dependency_list");
			android_assembley = new AndroidAssembley(this, android_complete_dependency_list,
					gradle_path.child("android"));

		}
		{
			List<EclipseProjectInfo> ios_complete_dependency_list = buildAdditionalDependenciesList(workspace_settings,
					ios.getEclipseProjectInfo(), core_complete_dependency_list);
			// ios_complete_dependency_list.print("ios_complete_dependency_list");
			ios_assembley = new iOSAssembley(this, ios_complete_dependency_list, gradle_path.child("ios"));

		}
		{
			List<EclipseProjectInfo> gwt_complete_dependency_list = buildAdditionalDependenciesList(workspace_settings,
					gwt.getEclipseProjectInfo(), core_complete_dependency_list);
			// gwt_complete_dependency_list.print("gwt_complete_dependency_list");
			gwt_assembley = new GWTAssembley(this, gwt_complete_dependency_list, gradle_path.child("html"));

		}
	}

	private static List<EclipseProjectInfo> buildAdditionalDependenciesList(EclipseWorkSpaceSettings workspace_settings,
			EclipseProjectInfo begin_dependencies, List<EclipseProjectInfo> core_complete_dependency_list) {
		List<EclipseProjectInfo> result = buildCompleteList(workspace_settings,
				begin_dependencies.getDependencies().getProjectsList());

		result.removeAll(core_complete_dependency_list);
		// result.add(begin_dependencies);
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
				L.d("info", info);

				EclipseProjectDependencies project_dependencies = info.getDependencies();
				Collection<String> projects = project_dependencies.getProjectsList();
				projects.print("expand");
				unprocessed_projects.addAll(projects);
			}
			processing.clear();
		}
		return processed_projects;
	}

	public void printTransactions() {
		this.core_assembley.printTransactions("core");
		this.desktop_assembley.printTransactions("desktop");
		this.android_assembley.printTransactions("android");
		this.gwt_assembley.printTransactions("gwt");
		this.ios_assembley.printTransactions("iOS");

	}

	public void deletePreviousTransactionsIfPresent() throws IOException {
		TransactionsInfo previous_transaction = TransactionsInfo.loadLast();
		if (previous_transaction != null) {
			Vector<ExecutedTransaction> executed_transactions = previous_transaction.listTransactions();
			for (int k = 0; k < executed_transactions.size(); k++) {
				ExecutedTransaction exec = executed_transactions.get(k);
				String native_folder_path = exec.getAbsolutePathString();
				File source_folder = LocalFileSystem.newFile(native_folder_path);
				boolean success = source_folder.delete();
				L.d("deleting", source_folder + " " + success);

			}
		}
	}

	public void executeCodeTransfer() throws IOException {
		TransactionsInfo transaction = new TransactionsInfo();
		this.core_assembley.executeCodeTransfer(transaction);
		this.desktop_assembley.executeCodeTransfer(transaction);
		this.android_assembley.executeCodeTransfer(transaction);
		this.gwt_assembley.executeCodeTransfer(transaction);
		this.ios_assembley.executeCodeTransfer(transaction);
		TransactionsInfo.save(transaction);
	}

	public void printDependencies() {
		this.core_assembley.printDependencies();
		this.desktop_assembley.printDependencies();
		this.android_assembley.printDependencies();
		this.gwt_assembley.printDependencies();
		this.ios_assembley.printDependencies();
	}

}
