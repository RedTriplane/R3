
package com.jfixby.red.triplane.fokker.assembler.srlz;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URISyntaxException;

import com.jfixby.red.triplane.fokker.assembler.AndroidProjectSettings;
import com.jfixby.red.triplane.fokker.assembler.DesktopProjectSettings;
import com.jfixby.red.triplane.fokker.assembler.GwtProjectSettings;
import com.jfixby.red.triplane.fokker.assembler.iOSProjectSettings;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.CollectionFilter;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.desktop.DesktopSetup;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.LocalFileSystem;
import com.jfixby.scarabei.api.json.Json;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.sys.Sys;

public class FindSrlzd {

	public static void main (final String[] args) throws IOException, URISyntaxException {

		DesktopSetup.deploy();
		Json.installComponent("com.jfixby.cmns.adopted.gdx.json.RedJson");

		final File workspace_folder = LocalFileSystem.newFile("D:\\[DEV]\\[CODE]\\[WS-19]");

		// workspace_settings.print();
		// Sys.exit();

		final File desktop_project_folder = LocalFileSystem.newFile("D:\\[DEV]\\[GIT]\\Tinto\\tinto-run-fokker-desktop");
		final File android_project_folder = LocalFileSystem.newFile("D:\\[DEV]\\[GIT]\\Tinto\\tinto-run-fokker-android");
		final File ios_project_folder = LocalFileSystem.newFile("D:\\[DEV]\\[GIT]\\Tinto\\tinto-run-fokker-ios");
		final File gwt_project_folder = LocalFileSystem.newFile("D:\\[DEV]\\[GIT]\\Tinto\\tinto-run-fokker-gwt");

		final String gradle_path_string = "D:\\[DEV]\\[CODE]\\[GDX]\\tinto";
		final File gradle_path = LocalFileSystem.newFile(gradle_path_string);

		final DesktopProjectSettings desktop = new DesktopProjectSettings(desktop_project_folder);
		final AndroidProjectSettings android = new AndroidProjectSettings(android_project_folder);
		final iOSProjectSettings ios = new iOSProjectSettings(ios_project_folder);
		final GwtProjectSettings gwt = new GwtProjectSettings(gwt_project_folder);

		final SrlzSearcherSpecs specs = new SrlzSearcherSpecs();
		specs.setGradleProjectPath(gradle_path);
		specs.setDesktopProject(desktop);
		specs.setAndroidProject(android);
		specs.setiOSProject(ios);
		specs.setGWTProject(gwt);
		specs.setWorkSpaceFolder(workspace_folder);

		final SrlzSearcher searcher = new SrlzSearcher(specs);

		final String filter_string = ".class";
		final Collection<JavaFileHandler> result = searcher.filterFiles(handler -> handler.containsString(filter_string));
		// result.print("result");
		// Sys.exit();

		// result.print("H");
		final List<JavaString> good_lines = Collections.newList();
		for (final JavaFileHandler H : result) {
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
		final List<ClassReference> classes = Collections.newList();
		for (final JavaString H : good_lines) {
			final String data = H.getString();
			final int begin = data.indexOf('(') + 1;
			final int end = data.indexOf(".class");
			final String class_name = data.substring(begin, end);
			final ClassReference ref = new ClassReference(class_name, H);
			classes.add(ref);
		}

		final Set<Class<?>> serializable_classes = Collections.newSet();

		for (final ClassReference class_ref : classes) {
			final String string_name = class_ref.getClassName();
			Class<?> clazz;
			try {
				clazz = Class.forName(string_name);
				serializable_classes.add(clazz);
			} catch (final ClassNotFoundException e) {
				L.d(class_ref);
				final JavaFileHandler H = class_ref.getHandler();
				L.d(H);
				e.printStackTrace();

				Sys.exit();
			}

		}

		serializable_classes.print("serializable_classes");

		final Collection<Class<?>> complete_list = completeList(serializable_classes);

		complete_list.print("complete_list classes");

		final String CLASS_NAME = "#CLASS_NAME#";
		final String import_template = "<extend-configuration-property name=\"gdx.reflect.include\" value=\"" + CLASS_NAME
			+ "\" />";

		L.d("producing import");

		for (final Class<?> clazz : complete_list) {
			final String class_name = clazz.getName();
			final String import_xml = import_template.replaceAll(CLASS_NAME, class_name);
			L.d(import_xml);

		}
	}

	private static Collection<Class<?>> completeList (final Set<Class<?>> start) {

		final Set<Class<?>> processing = Collections.newSet();
		final Set<Class<?>> processed = Collections.newSet();
		final Set<Class<?>> to_process = Collections.newSet();

		to_process.addAll(start);

		while (to_process.size() > 0) {
			processing.addAll(to_process);
			to_process.clear();
			for (final Class<?> clazz : processing) {
				if (processed.contains(clazz)) {
					continue;
				}

				List<Field> fields = Collections.newList(clazz.getDeclaredFields());
				fields = Collections.filter(fields, field -> !(Modifier.isStatic(field.getModifiers())));
				fields = Collections.filter(fields, new CollectionFilter<Field>() {

					@Override
					public boolean fits (final Field element) {
						final Class<?> type = element.getType();
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
				for (final Field field : fields) {
					final Type generic_type = field.getGenericType();
					final Class<?> type = field.getType();
					if (type == generic_type) {
						// L.d("MA|TCH");
						final String full_name = type.getName();
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

					final ParameterizedType subTypeList = (ParameterizedType)generic_type;
					final Type[] type_ars = subTypeList.getActualTypeArguments();
					final Set<Type> subtypes = Collections.newSet(type_ars);
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

		final List<Class<?>> result = Collections.filter(processed, new CollectionFilter<Class<?>>() {

			@Override
			public boolean fits (final Class<?> clazz) {
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
