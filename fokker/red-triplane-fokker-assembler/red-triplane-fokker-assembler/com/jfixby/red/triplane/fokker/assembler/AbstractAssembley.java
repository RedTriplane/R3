package com.jfixby.red.triplane.fokker.assembler;

import java.io.IOException;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.log.L;
import com.jfixby.tool.eclipse.dep.EclipseProjectInfo;

public abstract class AbstractAssembley {
	private FokkerAssembley master;
	private Collection<EclipseProjectInfo> dependencies;
	private List<EclipseProjectInfo> api = Collections.newList();;
	private List<EclipseProjectInfo> impl = Collections.newList();;
	private List<Transaction> transactions = Collections.newList();
	private File gradle_project_path;
	private String project_name;

	public AbstractAssembley(FokkerAssembley fokkerAssembley, Collection<EclipseProjectInfo> dependency_list,
			File gradle_project_path) {
		this.master = fokkerAssembley;
		this.gradle_project_path = gradle_project_path;
		this.dependencies = dependency_list;

		project_name = gradle_project_path.getName();
		for (int i = 0; i < this.dependencies.size(); i++) {
			EclipseProjectInfo dep = this.dependencies.getElementAt(i);
			if (dep.getProjectName().contains("api")) {
				api.add(dep);
			} else {
				impl.add(dep);
			}
			Collection<String> sources = dep.getDependencies().getSourceFoldersList();
			for (int k = 0; k < sources.size(); k++) {
				String source_folder = sources.getElementAt(k);
				Transaction transact = new Transaction(dep, source_folder, this);
				this.transactions.add(transact);
			}
		}
	}

	public void printDependencies() {
		L.d("Dependencies " + project_name);
		api.print("api");
		impl.print("code");
	}

	private Collection<EclipseProjectInfo> getDependencies() {
		return dependencies;
	}

	public void executeCodeTransfer(TransactionsInfo transaction_info) throws IOException {
		List<String> gradle_links = Collections.newList();
		for (int i = 0; i < this.transactions.size(); i++) {
			Transaction transaction = transactions.getElementAt(i);
			transaction.execute(transaction_info);
			gradle_links.add(transaction.getName());
		}
		updateGradleBuildConfig(gradle_links);
	}

	private static final String SOURCE_FOLDERS_PREFIX = "sourceSets.main.java.srcDirs = ";

	private void updateGradleBuildConfig(List<String> gradle_links) throws IOException {
		File build_gradle = gradle_project_path.child("build.gradle");
		String N = "\n";
		String data = build_gradle.readToString();
		File build_gradle_old = gradle_project_path.child("build.gradle.old");
		build_gradle_old.writeString(data);
		List<String> lines = Collections.newList(data.split(N));
		String build_gradle_output_string = "";
		for (int i = 0; i < lines.size(); i++) {
			String line_i = lines.getElementAt(i);
			if (line_i.startsWith(SOURCE_FOLDERS_PREFIX)) {
				line_i = formGradleConfigLine(gradle_links);
			}
			build_gradle_output_string = build_gradle_output_string + line_i + N;
		}
		build_gradle.writeString(build_gradle_output_string);
	}

	private static String formGradleConfigLine(List<String> folder_names) {
		String line = SOURCE_FOLDERS_PREFIX + "[ \"src/\"";
		for (int i = 0; i < folder_names.size(); i++) {
			line = line + ", \"" + folder_names.getElementAt(i) + "/\"";
		}
		line = line + " ]";
		return line;
	}

	public void printTransactions(String tag) {
		// L.d();
		this.transactions.print("Assembley[" + tag + "]");

	}

	public File getOutputFolder() {
		return gradle_project_path;
	}

}
