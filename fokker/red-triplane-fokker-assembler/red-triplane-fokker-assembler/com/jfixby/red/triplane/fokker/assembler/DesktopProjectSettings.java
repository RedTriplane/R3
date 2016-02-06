package com.jfixby.red.triplane.fokker.assembler;

import java.io.IOException;

import com.jfixby.cmns.api.file.File;
import com.jfixby.tool.eclipse.dep.EclipseProjectInfo;

public class DesktopProjectSettings {

	private EclipseProjectInfo project_info;

	public DesktopProjectSettings(File project_folder) throws IOException {
		this.project_info = new EclipseProjectInfo(project_folder);
	}

	public EclipseProjectInfo getEclipseProjectInfo() {
		return project_info;
	}

}
