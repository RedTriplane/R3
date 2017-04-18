
package com.jfixby.red.triplane.fokker.assembler;

import java.io.IOException;
import java.util.ArrayList;

import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.LocalFileSystem;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.util.JUtils;
import com.jfixby.tool.eclipse.dep.EclipseProjectDependencies;
import com.jfixby.tool.eclipse.dep.EclipseProjectInfo;
import com.jfixby.tool.eclipse.dep.EclipseWorkSpaceSettings;

public class FokkerAssembley {

	private final CoreAssembley core_assembley;
	private DesktopAssembley desktop_assembley;
	private AndroidAssembley android_assembley;
	private iOSAssembley ios_assembley;
	private GWTAssembley gwt_assembley;

	public FokkerAssembley (final FokkerAssembleySpecs specs) throws IOException {

		final DesktopProjectSettings desktop = specs.getDesktopProjectSettings();
		final GwtProjectSettings gwt = specs.getGwtProjectSettings();
		final iOSProjectSettings ios = specs.getiOSProjectSettings();
		final AndroidProjectSettings android = specs.getAndroidProjectSettings();

		final File gradle_path = specs.getGradlePath();

		final File workspace_folder = specs.getWorkSpaceFolder();
		final EclipseWorkSpaceSettings workspace_settings = EclipseWorkSpaceSettings.readWorkspaceSettings(workspace_folder);

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
		// core_project_names.print("desktop & gwt & android & ios : common
		// projetcs");

		final Set<EclipseProjectInfo> core_complete_dependency_list = buildCompleteList(workspace_settings, core_project_names);
		// core_complete_dependency_list.print("core complete_dependency_list");
		this.core_assembley = new CoreAssembley(this, core_complete_dependency_list, gradle_path.child("core"));
		{
			final Set<EclipseProjectInfo> desktop_complete_dependency_list = buildAdditionalDependenciesList(workspace_settings,
				desktop.getEclipseProjectInfo(), core_complete_dependency_list);
			// desktop_complete_dependency_list.print("desktop_complete_dependency_list");
			this.desktop_assembley = new DesktopAssembley(this, desktop_complete_dependency_list, gradle_path.child("desktop"));
		}
		{
			final Set<EclipseProjectInfo> android_complete_dependency_list = buildAdditionalDependenciesList(workspace_settings,
				android.getEclipseProjectInfo(), core_complete_dependency_list);
			// android_complete_dependency_list.print("android_complete_dependency_list");
			this.android_assembley = new AndroidAssembley(this, android_complete_dependency_list, gradle_path.child("android"));

		}
		{
			final Set<EclipseProjectInfo> ios_complete_dependency_list = buildAdditionalDependenciesList(workspace_settings,
				ios.getEclipseProjectInfo(), core_complete_dependency_list);
			// ios_complete_dependency_list.print("ios_complete_dependency_list");
			this.ios_assembley = new iOSAssembley(this, ios_complete_dependency_list, gradle_path.child("ios"));

		}
		{
			final Set<EclipseProjectInfo> gwt_complete_dependency_list = buildAdditionalDependenciesList(workspace_settings,
				gwt.getEclipseProjectInfo(), core_complete_dependency_list);
			// gwt_complete_dependency_list.print("gwt_complete_dependency_list");
			this.gwt_assembley = new GWTAssembley(this, gwt_complete_dependency_list, gradle_path.child("html"));

		}
	}

	private static Set<EclipseProjectInfo> buildAdditionalDependenciesList (final EclipseWorkSpaceSettings workspace_settings,
		final EclipseProjectInfo begin_dependencies, final Collection<EclipseProjectInfo> core_complete_dependency_list) {
		final Set<EclipseProjectInfo> result = buildCompleteList(workspace_settings,
			begin_dependencies.getDependencies().getProjectsList());

		result.removeAll(core_complete_dependency_list);
		result.add(begin_dependencies);
		// result.print("desktop_complete_dependency_list");
		return result;
	}

	private static Set<EclipseProjectInfo> buildCompleteList (final EclipseWorkSpaceSettings workspace_settings,
		final Collection<String> begin_set) {

		final Set<String> processing = Collections.newSet();
		final Set<EclipseProjectInfo> processed_projects = Collections.newSet();
		final Set<String> unprocessed_projects = Collections.newSet();
		unprocessed_projects.addAll(begin_set);

		while (unprocessed_projects.size() > 0) {
			processing.addAll(unprocessed_projects);
			unprocessed_projects.clear();
			for (int i = 0; i < processing.size(); i++) {
				final String core_project_name = processing.getElementAt(i);
				final EclipseProjectInfo info = workspace_settings.getProjectInfo(core_project_name);
				processed_projects.add(info);
				// L.d("info", info);

				final EclipseProjectDependencies project_dependencies = info.getDependencies();
				final Collection<String> projects = project_dependencies.getProjectsList();
				// projects.print("expand");
				unprocessed_projects.addAll(projects);
			}
			processing.clear();
		}
		return processed_projects;
	}

	public void printTransactions () {
		this.core_assembley.printTransactions("core");
		this.desktop_assembley.printTransactions("desktop");
		this.android_assembley.printTransactions("android");
		this.gwt_assembley.printTransactions("gwt");
		this.ios_assembley.printTransactions("iOS");

	}

	public void deletePreviousTransactionsIfPresent () throws IOException {
		final TransactionsInfo previous_transaction = TransactionsInfo.loadLast();
		if (previous_transaction != null) {
			final ArrayList<ExecutedTransaction> executed_transactions = previous_transaction.listTransactions();
			for (int k = 0; k < executed_transactions.size(); k++) {
				final ExecutedTransaction exec = executed_transactions.get(k);
				final String native_folder_path = exec.getAbsolutePathString();
				final File source_folder = LocalFileSystem.newFile(native_folder_path);
				final boolean success = source_folder.delete();
				L.d("deleting", source_folder + " " + success);

			}
		}
	}

	public void executeCodeTransfer () throws IOException {
		final TransactionsInfo transaction = new TransactionsInfo();
		this.core_assembley.executeCodeTransfer(transaction);
		this.desktop_assembley.executeCodeTransfer(transaction);
		this.android_assembley.executeCodeTransfer(transaction);
		this.gwt_assembley.executeCodeTransfer(transaction);
		this.ios_assembley.executeCodeTransfer(transaction);

		TransactionsInfo.save(transaction);
	}

	public void printDependencies () {
		this.core_assembley.printDependencies();
		this.desktop_assembley.printDependencies();
		this.android_assembley.printDependencies();
		this.gwt_assembley.printDependencies();
		this.ios_assembley.printDependencies();
	}

}
