package com.jfixby.red.triplane.fokker.assembler.ds;

import com.jfixby.scarabei.api.log.L;

public class AssemblerConfig {

	private String gradle_output_folder;
	private String assets_input;
	private String input_workspace;

	final public ProjectAssemblerContainer core = new ProjectAssemblerContainer();
	final public ProjectAssemblerContainer ios = new ProjectAssemblerContainer();
	final public ProjectAssemblerContainer android = new ProjectAssemblerContainer();
	final public ProjectAssemblerContainer desktop = new ProjectAssemblerContainer();
	final public ProjectAssemblerContainer html = new ProjectAssemblerContainer();

	public void print() {
		L.d("---Foker-Assembler-Config------");
		L.d(" Input:");
		L.d("   code workspace", input_workspace);
		L.d("           assets", assets_input);
		L.d(" Output:");
		L.d("   gradle project", gradle_output_folder);
		L.d();
		L.d(" Assets:");
		// for (int i = 0; i < assets_transactions.size(); i++) {
		// AssetTransaction element = assets_transactions.get(i);
		// L.d("   >>> " + element.output);
		// }
		L.d();
		L.d(" Core:");
		this.core.print("   ");

		L.d(" Android:");
		this.android.print("   ");

		L.d(" Desktop:");
		this.desktop.print("   ");

		L.d(" Html5:");
		this.html.print("   ");

	}

	public void setGradleOutputFolder(String gradle_output_folder) {
		this.gradle_output_folder = gradle_output_folder;
	}

	public void setAssetsInput(String assets_input) {
		this.assets_input = assets_input;
	}

	public String getGradleOutputFolder() {
		return this.gradle_output_folder;
	}

	public String getAssetsInput() {
		return this.assets_input;
	}

	public void setInputWorkspace(String input_workspace) {
		this.input_workspace = input_workspace;
	}

	public String getInputWorkspace() {
		return this.input_workspace;
	}
}
