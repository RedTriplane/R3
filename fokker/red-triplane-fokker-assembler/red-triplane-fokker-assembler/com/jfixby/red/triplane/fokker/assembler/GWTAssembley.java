package com.jfixby.red.triplane.fokker.assembler;

import java.io.IOException;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.log.L;
import com.jfixby.tool.eclipse.dep.EclipseProjectInfo;

public class GWTAssembley extends AbstractAssembley {

	public GWTAssembley(FokkerAssembley fokkerAssembley, Collection<EclipseProjectInfo> dependency_list,
			File gradle_output_project_folder) {
		super(fokkerAssembley, dependency_list, gradle_output_project_folder);
	}

	public void executeCodeTransfer(TransactionsInfo transaction_info) throws IOException {
		List<String> gradle_links = Collections.newList();
		gradle_links.add("src");
		
		ExecutedTransaction exec = new ExecutedTransaction();
		transaction_info.transactions.add(exec);
		exec.failed = true;
		for (int i = 0; i < this.transactions.size(); i++) {
			Transaction transaction = transactions.getElementAt(i);
			File output_folder = transaction.getOutputFolder();
			if (i == 0) {
				File src = output_folder.parent().child("src");
				exec.index = transaction_info.transactions.indexOf(exec);
				exec.native_folder_path = src.toJavaFile().getAbsolutePath();
				src.clearFolder();
			}

			File input_folder = transaction.getInputFolder();
			input_folder.getFileSystem().copyFolderContentsToFolder(input_folder, output_folder);
			L.d("transaction executed", this);
		}
		exec.failed = false;
		updateGradleBuildConfig(gradle_links);
	}
}
