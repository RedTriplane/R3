
package com.jfixby.red.triplane.fokker.depsearch;

import java.io.IOException;

import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.desktop.DesktopSetup;
import com.jfixby.scarabei.api.file.ChildrenList;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.FileFilter;
import com.jfixby.scarabei.api.file.LocalFileSystem;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.util.JUtils;

public class ScanClassPathCollectJars {

	public static void main (final String[] args) throws IOException {
		DesktopSetup.deploy();
		final String gradle_path_string = "D:\\[DEV]\\[CODE]\\[GDX]\\tinto";
		final File gradle_path = LocalFileSystem.newFile(gradle_path_string);
		final File jars = LocalFileSystem.ApplicationHome().child("jars");
		jars.clearFolder();
		final FileFilter filter = file -> {
			try {
				return file.isFolder();
			} catch (final IOException e) {
				e.printStackTrace();
			}
			return false;
		};
		final ChildrenList folders = gradle_path.listDirectChildren().filterFiles(filter);
		final File core = folders.findChild("core");
		final List<String> core_jars = collectJars(core, jars, Collections.newList());
		core_jars.print("core_jars");
		for (int i = 0; i < folders.size(); i++) {
			final File subfolder = folders.getElementAt(i);
			if (subfolder == core) {
				continue;
			}
			collectJars(subfolder, jars, core_jars);
		}

	}

	private static List<String> collectJars (final File subfolder, final File jars, final List<String> ignore_jars)
		throws IOException {
		final File class_path_file = subfolder.listDirectChildren().findChild(".classpath");
		if (class_path_file == null) {
			return null;
		}
		final String child_name = subfolder.getName();
		final File jar_child_folder = jars.child(child_name);

		final String data = class_path_file.readToString();
		// data = data.replaceAll("/r", " ");
		final List<String> deps_list = JUtils.split(data, "<classpathentry");
		// L.d(subfolder + "", data);
		// deps_list.print(subfolder + "");
		jar_child_folder.makeFolder();

		L.d(subfolder);

		final List<String> processed_jars = Collections.newList();

		for (int i = 0; i < deps_list.size(); i++) {
			final String element = deps_list.getElementAt(i);
			final String jar_path = getJarPath(element);

			if (jar_path == null) {
				continue;
			}
			if (ignore_jars.contains(jar_path)) {
				continue;
			}
			L.d();
			L.d("jar_path", jar_path);
			processed_jars.add(jar_path);

			final File jar = LocalFileSystem.newFile(jar_path);
			LocalFileSystem.copyFileToFolder(jar, jar_child_folder);

			final String src_path = getSrcPath(element);
			if (src_path != null) {
				L.d("src_path", src_path);
				final File src = LocalFileSystem.newFile(src_path);
				LocalFileSystem.copyFileToFolder(src, jar_child_folder.child("src"));
			}
		}
		return processed_jars;

	}

	private static String getJarPath (final String element) {
		final String prefix = "kind=\"lib\" path=\"";
		int begin_index = element.indexOf(prefix, 0);
		if (begin_index < 0) {
			return null;
		}

		begin_index = begin_index + prefix.length();
		final int end_index = element.indexOf("\"", begin_index);
		if (end_index < 0) {
			return null;
		}
		final String path = element.substring(begin_index, end_index);
		return path;
	}

	private static String getSrcPath (final String element) {
		final String prefix = "sourcepath=\"";
		int begin_index = element.indexOf(prefix, 0);
		if (begin_index < 0) {
			return null;
		}

		begin_index = begin_index + prefix.length();
		final int end_index = element.indexOf("\"", begin_index);
		if (end_index < 0) {
			return null;
		}
		final String path = element.substring(begin_index, end_index);
		return path;
	}

}
