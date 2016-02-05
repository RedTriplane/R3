package com.jfixby.red.triplane.fokker.depsearch;

import java.io.IOException;

import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.file.ChildrenList;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.util.JUtils;
import com.jfixby.cmns.desktop.DesktopAssembler;

public class ScanClassPathCollectJars {

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
		// deps_list.print(subfolder + "");
		jar_child_folder.makeFolder();

		L.d(subfolder);

		for (int i = 0; i < deps_list.size(); i++) {
			String element = deps_list.getElementAt(i);
			String jar_path = getJarPath(element);
			if (jar_path == null) {
				continue;
			}
			L.d();
			L.d("jar_path", jar_path);

			File jar = LocalFileSystem.newFile(jar_path);
			LocalFileSystem.copyFileToFolder(jar, jar_child_folder);

			String src_path = getSrcPath(element);
			if (src_path != null) {
				L.d("src_path", src_path);
				File src = LocalFileSystem.newFile(src_path);
				LocalFileSystem.copyFileToFolder(src, jar_child_folder.child("src"));
			}
		}

	}

	private static String getJarPath(String element) {
		String prefix = "kind=\"lib\" path=\"";
		int begin_index = element.indexOf(prefix, 0);
		if (begin_index < 0) {
			return null;
		}

		begin_index = begin_index + prefix.length();
		int end_index = element.indexOf("\"", begin_index);
		if (end_index < 0) {
			return null;
		}
		String path = element.substring(begin_index, end_index);
		return path;
	}

	private static String getSrcPath(String element) {
		String prefix = "sourcepath=\"";
		int begin_index = element.indexOf(prefix, 0);
		if (begin_index < 0) {
			return null;
		}

		begin_index = begin_index + prefix.length();
		int end_index = element.indexOf("\"", begin_index);
		if (end_index < 0) {
			return null;
		}
		String path = element.substring(begin_index, end_index);
		return path;
	}

}
