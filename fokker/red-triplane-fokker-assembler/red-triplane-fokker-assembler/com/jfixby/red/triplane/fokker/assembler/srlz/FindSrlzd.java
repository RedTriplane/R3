package com.jfixby.red.triplane.fokker.assembler.srlz;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URISyntaxException;

import com.jfixby.cmns.adopted.gdx.json.GdxJson;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.CollectionFilter;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.collections.Set;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.sys.Sys;
import com.jfixby.red.desktop.DesktopAssembler;
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
		// result.print("result");
		// Sys.exit();

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

		Set<Class<?>> serializable_classes = Collections.newSet();

		for (ClassReference class_ref : classes) {
			String string_name = class_ref.getClassName();
			Class<?> clazz;
			try {
				clazz = Class.forName(string_name);
				serializable_classes.add(clazz);
			} catch (ClassNotFoundException e) {
				L.d(class_ref);
				JavaFileHandler H = class_ref.getHandler();
				L.d(H);
				e.printStackTrace();

				Sys.exit();
			}

		}

		serializable_classes.print("serializable_classes");

		Collection<Class<?>> complete_list = completeList(serializable_classes);

		complete_list.print("complete_list classes");

		String CLASS_NAME = "#CLASS_NAME#";
		String import_template = "<extend-configuration-property name=\"gdx.reflect.include\" value=\"" + CLASS_NAME
				+ "\" />";

		L.d("producing import");

		for (Class<?> clazz : complete_list) {
			String class_name = clazz.getName();
			String import_xml = import_template.replaceAll(CLASS_NAME, class_name);
			L.d(import_xml);

		}
	}

	private static Collection<Class<?>> completeList(Set<Class<?>> start) {

		Set<Class<?>> processing = Collections.newSet();
		Set<Class<?>> processed = Collections.newSet();
		Set<Class<?>> to_process = Collections.newSet();

		to_process.addAll(start);

		while (to_process.size() > 0) {
			processing.addAll(to_process);
			to_process.clear();
			for (Class<?> clazz : processing) {
				if (processed.contains(clazz)) {
					continue;
				}

				List<Field> fields = Collections.newList(clazz.getDeclaredFields());
				fields = Collections.filter(fields, field -> !(Modifier.isStatic(field.getModifiers())));
				fields = Collections.filter(fields, new CollectionFilter<Field>() {

					@Override
					public boolean fits(Field element) {
						Class<?> type = element.getType();
						return !type.isPrimitive();
					}

				});
				// fields = Collections.filter(fields, new
				// CollectionFilter<Field>() {
				//
				// @Override
				// public boolean fits(Field element) {
				// Class<?> type = element.getType();
				// String full_name = type.getName();
				// return !full_name.startsWith("java.") || (type ==
				// Vector.class);
				// }
				//
				// });
				// fields.print("fields");
				for (Field field : fields) {
					Type generic_type = field.getGenericType();
					Class<?> type = field.getType();
					if (type == generic_type) {
						// L.d("MA|TCH");
						String full_name = type.getName();
						if (full_name.startsWith("java.")) {
							// L.d("skip", full_name);
							continue;
						} else {
							if (!processed.contains(type)) {
								to_process.add(type);
							}
							continue;
						}
					}
					// L.d("clazz", clazz);
					// L.d("type", type);
					// L.d("generic_type", generic_type);

					ParameterizedType subTypeList = (ParameterizedType) generic_type;
					Type[] type_ars = subTypeList.getActualTypeArguments();
					Set<Type> subtypes = Collections.newSet(type_ars);
					List<Class<?>> tmp = Collections.castCollection(subtypes);
					// subtypes.print("subtypes");
					tmp = Collections.filter(tmp, type_i -> !processed.contains(type_i));
					// tmp = Collections.filter(tmp, type_i ->
					// !processed.contains(type_i));
					// tmp.print("tmp");
					to_process.addAll(tmp);

				}

			}
			processed.addAll(processing);
			processing.clear();
		}

		List<Class<?>> result = Collections.filter(processed, new CollectionFilter<Class<?>>() {

			@Override
			public boolean fits(Class<?> clazz) {
				if (clazz == String.class) {
					return false;
				}
				if (clazz.getName().startsWith("[C")) {
					return false;
				}

				return true;
			}

		});
		return result;
	}

}
