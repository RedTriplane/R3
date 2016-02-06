package com.jfixby.red.triplane.fokker.assembler;

import com.jfixby.cmns.api.file.File;
import com.jfixby.tool.eclipse.dep.EclipseProjectInfo;

public class Transaction {

	private String source_folder_name;
	private EclipseProjectInfo project_info;
	private AbstractAssembley master;
	private String transaction_name;
	private File output_folder;

	public Transaction(EclipseProjectInfo dep, String source_folder, AbstractAssembley abstractAssembley) {
		this.master = abstractAssembley;
		this.source_folder_name = source_folder;
		this.project_info = dep;
		transaction_name = project_info.getProjectName() + "#" + source_folder_name;
		output_folder = master.getOutputFolder().child(transaction_name);
	}

	@Override
	public String toString() {
		return "" + transaction_name + " -> " + output_folder;
	}

}
