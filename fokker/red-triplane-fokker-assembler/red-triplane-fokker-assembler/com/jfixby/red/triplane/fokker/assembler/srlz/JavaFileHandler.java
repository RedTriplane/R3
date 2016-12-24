package com.jfixby.red.triplane.fokker.assembler.srlz;

import java.io.IOException;

import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.util.JUtils;

public class JavaFileHandler {

	private File src;
	private String data;
	private List<JavaString> lines = Collections.newList();
	private List<JavaString> imports = Collections.newList();
	private String package_string;

	public JavaFileHandler(File src) throws IOException {
		this.src = src;
		data = src.readToString();
	}

	public boolean containsString(String expression) {
		return data.contains(expression);
	}

	@Override
	public String toString() {
		return "File: " + src + "";
	}

	public void mine() {
		imports.clear();
		lines.clear();
		List<String> str_lines = JUtils.split(data, "\n");
		for (String line : str_lines) {
			JavaString j_line = new JavaString(line, this);
			lines.add(j_line);
			if (line.startsWith("import")) {
				imports.add(j_line);
			}
		}
		String prefix = "package ";
		JavaString package_str = lines.getElementAt(0);
		String data_line = package_str.getString();
		int begin = data_line.indexOf(prefix);
		if (begin >= 0) {
			begin = begin + prefix.length();
			int end = data_line.indexOf(";");
			package_string = data_line.substring(begin, end);
		}
	}

	public Collection<JavaString> listLines() {
		return this.lines;
	}

	public Collection<JavaString> listImports() {
		return this.imports;
	}

	public String getPackageDeclaration() {
		return package_string;
	}

}
