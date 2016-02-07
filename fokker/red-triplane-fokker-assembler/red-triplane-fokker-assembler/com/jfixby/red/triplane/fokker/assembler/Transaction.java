package com.jfixby.red.triplane.fokker.assembler;

import java.io.IOException;

import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.log.L;
import com.jfixby.tool.eclipse.dep.EclipseProjectInfo;

public class Transaction {

	private String source_folder_name;
	private EclipseProjectInfo project_info;
	private AbstractAssembley master;
	private String transaction_name;
	private File output_folder;
	private File input_folder;

	public Transaction(EclipseProjectInfo dep, String source_folder, AbstractAssembley abstractAssembley) {
		this.master = abstractAssembley;
		this.source_folder_name = source_folder;
		this.project_info = dep;
		transaction_name = "R3." + project_info.getProjectName() + "." + source_folder_name;
		output_folder = master.getOutputFolder().child(transaction_name);
		input_folder = project_info.getProjectPath().child(source_folder);
	}

	@Override
	public String toString() {
		return transaction_name + " -> " + output_folder;
	}

	public void execute(TransactionsInfo transaction_info) throws IOException {
		ExecutedTransaction exec = new ExecutedTransaction();
		exec.failed = true;
		exec.native_folder_path = output_folder.toJavaFile().getAbsolutePath();
		transaction_info.transactions.add(exec);
		exec.index = transaction_info.transactions.indexOf(exec);
		project_info.getProjectPath();
		input_folder.getFileSystem().copyFolderContentsToFolder(input_folder, output_folder);
		L.d("transaction executed", this);
		exec.failed = false;
	}

	public String getName() {
		return transaction_name;
	}

	public File getOutputFolder() {
		return this.output_folder;
	}

	public File getInputFolder() {
		return this.input_folder;
	}

}
