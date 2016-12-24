package com.jfixby.red.triplane.fokker.assembler.ds;

import java.util.Vector;

import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.log.L;

public class ProjectAssemblerContainer {

	private final Vector<ProjectAssemblerConfig> projects = new Vector<ProjectAssemblerConfig>();

	public void addProject(ProjectAssemblerConfig project) {
		projects.addElement(project);
	}

	public void print(String prefix) {
		for (int i = 0; i < projects.size(); i++) {
			ProjectAssemblerConfig element = projects.get(i);
			element.print(prefix);
			L.d();
		}
	}

	public List<ProjectAssemblerConfig> listProjects() {
		return Collections.newList(projects);
	}

}
