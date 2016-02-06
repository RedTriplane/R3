package com.jfixby.red.triplane.fokker.assembler;

import com.jfixby.cmns.api.file.File;
import com.jfixby.tool.eclipse.dep.EclipseProjectInfo;

public class FokkerAssembleySpecs {

	private File gradle_path;
	private DesktopProjectSettings desktop;
	private AndroidProjectSettings android;
	private iOSProjectSettings ios;
	private GwtProjectSettings gwt;
	private File workspace_folder;

	public void setGradleProjectPath(File gradle_path) {
		this.gradle_path = gradle_path;
	}

	public void setDesktopProject(DesktopProjectSettings desktop) {
		this.desktop = desktop;
	}

	public void setAndroidProject(AndroidProjectSettings android) {
		this.android = android;

	}

	public void setiOSProject(iOSProjectSettings ios) {
		this.ios = ios;

	}

	public void setGWTProject(GwtProjectSettings gwt) {
		this.gwt = gwt;

	}

	public DesktopProjectSettings getDesktopProject() {
		return desktop;
	}

	public AndroidProjectSettings getAndroidProject() {
		return android;
	}

	public iOSProjectSettings getiOSProject() {
		return ios;
	}

	public GwtProjectSettings getGWTProject() {
		return gwt;
	}

	public void setWorkSpaceFolder(File workspace_folder) {
		this.workspace_folder = workspace_folder;
	}

	public File getWorkSpaceFolder() {
		return this.workspace_folder;
	}

	public DesktopProjectSettings getDesktopProjectSettings() {
		return this.desktop;
	}

	public GwtProjectSettings getGwtProjectSettings() {
		return this.gwt;
	}

	public iOSProjectSettings getiOSProjectSettings() {
		return this.ios;
	}

	public AndroidProjectSettings getAndroidProjectSettings() {
		return this.android;
	}
}
