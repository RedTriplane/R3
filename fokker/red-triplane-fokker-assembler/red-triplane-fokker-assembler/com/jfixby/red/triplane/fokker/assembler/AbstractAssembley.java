package com.jfixby.red.triplane.fokker.assembler;

import java.io.IOException;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.file.File;
import com.jfixby.tool.eclipse.dep.EclipseProjectInfo;

public abstract class AbstractAssembley {
	private FokkerAssembley master;
	private List<EclipseProjectInfo> dependencies;
	private List<Transaction> transactions = Collections.newList();
	private File gradle_project_path;

	public AbstractAssembley(FokkerAssembley fokkerAssembley, List<EclipseProjectInfo> dependency_list,
			File gradle_project_path) {
		this.master = fokkerAssembley;
		this.gradle_project_path = gradle_project_path;
		this.dependencies = dependency_list;
		for (int i = 0; i < this.dependencies.size(); i++) {
			EclipseProjectInfo dep = this.dependencies.getElementAt(i);
			Collection<String> sources = dep.getDependencies().getSourceFoldersList();
			for (int k = 0; k < sources.size(); k++) {
				String source_folder = sources.getElementAt(k);
				Transaction transact = new Transaction(dep, source_folder, this);
				this.transactions.add(transact);
			}
		}
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
