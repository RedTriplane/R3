package com.jfixby.red.triplane.fokker.assembler.srlz;

import com.jfixby.cmns.api.log.L;

public class JavaString {

	private JavaFileHandler javaFileHandler;
	private String line;

	@Override
	public String toString() {
		return "JavaString>" + line + "";
	}

	public JavaString(String line, JavaFileHandler javaFileHandler) {
		this.javaFileHandler = javaFileHandler;
//		L.d("     line", line + "<");
		this.line = line.replaceAll("\r", "");
//		L.d("this.line", this.line + "<");
	}

	public boolean containsExpressin(String expr) {
		return line.contains(expr);
	}

	public String getString() {
		return line;
	}

	public JavaFileHandler getFileHandler() {
		return javaFileHandler;
	}

}
