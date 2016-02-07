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
		for (int i = 0; i < this.transactions.size(); i++) {
			Transaction transaction = transactions.getElementAt(i);
			transaction.execute(transaction_info);
		}
	}

	public void printTransactions(String tag) {
		// L.d();
		this.transactions.print("Assembley[" + tag + "]");

	}

	public File getOutputFolder() {
		return gradle_project_path;
	}

}
