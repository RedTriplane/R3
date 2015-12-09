package com.jfixby.red.triplane.fokker.assembler.ds;

import java.util.Vector;

import com.jfixby.cmns.api.collections.JUtils;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.log.L;

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
		return JUtils.newList(projects);
	}

}
