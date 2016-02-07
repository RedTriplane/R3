package com.jfixby.red.triplane.fokker.assembler;

public class ExecutedTransaction {

	public String native_folder_path;
	public boolean failed = false;
	public int index = -1;

	public String getAbsolutePathString() {
		return native_folder_path;
	}

}
