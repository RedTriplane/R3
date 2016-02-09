package com.jfixby.red.triplane.fokker.assembler.srlz;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.err.Err;

public class ClassReference {

	@Override
	public String toString() {
		return "ClassReference [" + full_class_name + "]";
	}

	private String class_name;
	private JavaString java_stringjava_string;
	private JavaFileHandler javaFileHandler;
	private String full_class_name;

	public ClassReference(String class_name, JavaString java_string) {
		this.class_name = class_name;
		this.java_stringjava_string = java_string;
		this.javaFileHandler = java_string.getFileHandler();
		Collection<JavaString> imports = this.javaFileHandler.listImports();
		imports = Collections.filter(imports, element -> element.containsExpressin(class_name + ";"));
		// imports.print("imports: " + class_name);
		if (imports.size() == 1) {
			JavaString the_import = imports.getLast();
			String data_line = the_import.getString();
			String prefix = "import ";
			int begin = data_line.indexOf(prefix);
			if (begin >= 0) {
				begin = begin + prefix.length();
				int end = data_line.indexOf(";");
				full_class_name = data_line.substring(begin, end);
			}
		} else if (imports.size() == 0) {
			String package_declaration = this.javaFileHandler.getPackageDeclaration();
			full_class_name = package_declaration + "." + class_name;
			// L.d(package_declaration);
			// imports.print(class_name + " ? " + java_string.getFileHandler());
			// javaFileHandler.listLines().print("");
			// Sys.exit();
			// "package "

		} else {
			Err.reportError("? " + this);
		}

	}

	public String getClassName() {
		return full_class_name;
	}

	public JavaFileHandler getHandler() {
		return this.javaFileHandler;
	}

}
