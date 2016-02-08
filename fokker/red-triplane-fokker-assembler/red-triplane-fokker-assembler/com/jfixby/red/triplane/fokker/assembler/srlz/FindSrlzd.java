package com.jfixby.red.triplane.fokker.assembler.srlz;

import java.io.IOException;
import java.net.URISyntaxException;

import com.jfixby.cmns.adopted.gdx.json.GdxJson;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.cmns.desktop.DesktopAssembler;
import com.jfixby.red.triplane.fokker.assembler.AndroidProjectSettings;
import com.jfixby.red.triplane.fokker.assembler.DesktopProjectSettings;
import com.jfixby.red.triplane.fokker.assembler.GwtProjectSettings;
import com.jfixby.red.triplane.fokker.assembler.iOSProjectSettings;

public class FindSrlzd {

	public static void main(String[] args) throws IOException, URISyntaxException {

		DesktopAssembler.setup();
		Json.installComponent(new GdxJson());

		File workspace_folder = LocalFileSystem.newFile("D:\\[DEV]\\[CODE]\\[WS-19]");

		// workspace_settings.print();
		// Sys.exit();

		File desktop_project_folder = LocalFileSystem.newFile("D:\\[DEV]\\[GIT]\\Tinto\\tinto-run-fokker-desktop");
		File android_project_folder = LocalFileSystem.newFile("D:\\[DEV]\\[GIT]\\Tinto\\tinto-run-fokker-android");
		File ios_project_folder = LocalFileSystem.newFile("D:\\[DEV]\\[GIT]\\Tinto\\tinto-run-fokker-ios");
		File gwt_project_folder = LocalFileSystem.newFile("D:\\[DEV]\\[GIT]\\Tinto\\tinto-run-fokker-gwt");

		String gradle_path_string = "D:\\[DEV]\\[CODE]\\[GDX]\\tinto";
		File gradle_path = LocalFileSystem.newFile(gradle_path_string);

		DesktopProjectSettings desktop = new DesktopProjectSettings(desktop_project_folder);
		AndroidProjectSettings android = new AndroidProjectSettings(android_project_folder);
		iOSProjectSettings ios = new iOSProjectSettings(ios_project_folder);
		GwtProjectSettings gwt = new GwtProjectSettings(gwt_project_folder);

		SrlzSearcherSpecs specs = new SrlzSearcherSpecs();
		specs.setGradleProjectPath(gradle_path);
		specs.setDesktopProject(desktop);
		specs.setAndroidProject(android);
		specs.setiOSProject(ios);
		specs.setGWTProject(gwt);
		specs.setWorkSpaceFolder(workspace_folder);

		SrlzSearcher searcher = new SrlzSearcher(specs);

		String filter_string = ".class";
		Collection<JavaFileHandler> result = searcher.filterFiles(handler -> handler.containsString(filter_string));
		// result.print("H");
		List<JavaString> good_lines = Collections.newList();
		for (JavaFileHandler H : result) {
			H.mine();
			Collection<JavaString> lines = H.listLines();
			lines = Collections.filter(lines, element -> element.containsExpressin(filter_string));
			good_lines.addAll(lines);
			// lines.print("");
			// return
			// Collection<JavaString> class_line =
			// H.getLineThatContains(".class");
			// L.d("", class_line);
		}

		// good_lines.print("good_lines");
		List<ClassReference> classes = Collections.newList();
		for (JavaString H : good_lines) {
			String data = H.getString();
			int begin = data.indexOf('(') + 1;
			int end = data.indexOf(".class");
			String class_name = data.substring(begin, end);
			ClassReference ref = new ClassReference(class_name, H);
			classes.add(ref);
		}

		// classes.print("serializable classes");
	}

}
