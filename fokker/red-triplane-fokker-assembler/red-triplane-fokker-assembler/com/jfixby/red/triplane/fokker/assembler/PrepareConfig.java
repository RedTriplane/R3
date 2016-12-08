
package com.jfixby.red.triplane.fokker.assembler;

import com.jfixby.cmns.api.desktop.DesktopSetup;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.red.triplane.fokker.assembler.ds.AssemblerConfig;
import com.jfixby.red.triplane.fokker.assembler.ds.ProjectAssemblerConfig;
import com.jfixby.red.triplane.fokker.assembler.ds.ProjectAssemblerContainer;

public class PrepareConfig {

	public static void main (final String[] args) throws Exception {
		DesktopSetup.deploy();

		final AssemblerConfig assembler_config = new AssemblerConfig();

		assembler_config.setGradleOutputFolder("D:\\[DEV]\\[CODE]\\[WS-GDX]\\red-triplane-fokker-1.6.4");

		final File workspace = null;

		assembler_config.setAssetsInput("D:\\[DEV]\\[GIT-2]\\TintoAssets\\tinto-assets");

		{
			addProject("jfixby-commons-api", assembler_config.core);
			addProject("jfixby-commons-red", assembler_config.core);

			addProject("red-triplane-api", assembler_config.core, "red-triplane-api", "red-triplane-api-ext",
				"red-triplane-api-refactor");
			addProject("red-triplane-fokker-api", assembler_config.core);

			addProject("red-triplane-core", assembler_config.core, "red-triplane-core", "red-triplane-ext");

			addProject("red-triplane-fokker-core", assembler_config.core, "red-triplane-fokker-core", "red-triplane-fokker-ext");

			addProject("red-triplane-fokker-frontend", assembler_config.core);

			addProject("red-triplane-fokker-gdx", assembler_config.core, "red-triplane-fokker-gdx", "red-triplane-fokker-gdx-tools",
				"red-triplane-fokker-gdx-box2d");

			addProject("tinto-ui", assembler_config.core);
			addProject("tinto-game", assembler_config.core);
			addProject("tinto-api", assembler_config.core);

			addProject("jfixby-util-filesystem-packing", assembler_config.core);
			addProject("jfixby-util-filesystem-packing-api", assembler_config.core);

			addProject("box2d-api", assembler_config.core);
			addProject("box2d-red-double", assembler_config.core, "box2d-red-double", "jbox2d-double");

			addProject("red-triplane-fokker-backends", assembler_config.core, "red-triplane-fokker-common");

			addProject("red-triplane-fokker-core", assembler_config.core, "red-triplane-fokker-core", "red-triplane-fokker-ext");

			addProject("red-triplane-resource-fsbased", assembler_config.core);

			addProject("jfixby-math-gwt", assembler_config.core);

		}
		{
			addProject("red-triplane-fokker-backends", assembler_config.desktop, "red-triplane-fokker-desktop");

			addProject("jfixby-log-desktop", assembler_config.desktop);

			addProject("jfixby-filesystem-win", assembler_config.desktop);

			addProject("jfixby-sys-desktop", assembler_config.desktop);

			addProject("jfixby-math-desktop", assembler_config.desktop);

			addProject("jfixby-jutils-desktop", assembler_config.desktop);

		}
		{
			addProject("red-triplane-fokker-backends", assembler_config.android, "red-triplane-fokker-android");
		}
		{
			// addProject("red-triplane-fokker-backends", assembler_config.html,
			// "red-triplane-fokker-html");

		}
		{
			addProject("red-triplane-fokker-backends", assembler_config.ios, "red-triplane-fokker-ios");
		}

		assembler_config.print();

		final String config_string = Json.serializeToString(assembler_config).toString();

		final String java_path = "fokker-assembler.config";
		final File mp = LocalFileSystem.newFile(java_path);
		mp.writeString(config_string);

	}

	private static void addProject (final String project_name, final ProjectAssemblerContainer container) {
		final ProjectAssemblerConfig project = new ProjectAssemblerConfig(project_name);
		project.addSourceFolder();
		container.addProject(project);
	}

	private static void addProject (final String project_name, final ProjectAssemblerContainer container,
		final String... source_folders) {
		final ProjectAssemblerConfig project = new ProjectAssemblerConfig(project_name);
		for (int i = 0; i < source_folders.length; i++) {
			final String folder = source_folders[i];
			project.addSourceFolder(folder);
		}

		container.addProject(project);
	}
}
