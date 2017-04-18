
package com.jfixby.red.triplane.fokker.assembler.ds;

import java.util.ArrayList;

import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.log.L;

public class ProjectAssemblerContainer {

	private final ArrayList<ProjectAssemblerConfig> projects = new ArrayList<>();

	public void addProject (final ProjectAssemblerConfig project) {
		this.projects.add(project);
	}

	public void print (final String prefix) {
		for (int i = 0; i < this.projects.size(); i++) {
			final ProjectAssemblerConfig element = this.projects.get(i);
			element.print(prefix);
			L.d();
		}
	}

	public List<ProjectAssemblerConfig> listProjects () {
		return Collections.newList(this.projects);
	}

}
