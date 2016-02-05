package com.jfixby.red.triplane.fokker.depsearch;

import java.io.IOException;

import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.file.ChildrenList;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.util.JUtils;
import com.jfixby.cmns.desktop.DesktopAssembler;

public class ScanClassPath {

	public static void main(String[] args) throws IOException {
		DesktopAssembler.setup();
		String gradle_path_string = "D:\\[DEV]\\[CODE]\\[GDX]\\tinto";
		File gradle_path = LocalFileSystem.newFile(gradle_path_string);
		File jars = LocalFileSystem.ApplicationHome().child("jars");
		jars.clearFolder();
		ChildrenList folders = gradle_path.listChildren().filter(file -> file.isFolder());
		for (int i = 0; i < folders.size(); i++) {
			File subfolder = folders.getElementAt(i);
			collectJars(subfolder, jars);
		}

	}

	private static void collectJars(File subfolder, File jars) throws IOException {
		File class_path_file = subfolder.listChildren().findChild(".classpath");
		if (class_path_file == null) {
			return;
		}
		String child_name = subfolder.getName();
		File jar_child_folder = jars.child(child_name);

		String data = class_path_file.readToString();
		// data = data.replaceAll("/r", " ");
		List<String> deps_list = JUtils.split(data, "<classpathentry");
		// L.d(subfolder + "", data);
		deps_list.print(subfolder + "");
		jar_child_folder.makeFolder();
	}

}
