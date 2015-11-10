package com.jfixby.red.triplane.fokker.assembler.ds;

import java.util.Vector;

import com.jfixby.cmns.api.collections.JUtils;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.log.L;

public class ProjectAssemblerConfig {

	private String project_name;

	ProjectAssemblerConfig() {
		super();
	}

	private final Vector<String> sources = new Vector<String>();

	public ProjectAssemblerConfig(String project_name) {
		JUtils.checkNull("project_name", project_name);
		JUtils.checkEmpty("project_name", project_name);
		this.project_name = project_name;
	}

	public void addSourceFolder() {
		sources.add(project_name);
	}

	public void addSourceFolder(String folder) {
		JUtils.checkNull("folder", folder);
		JUtils.checkEmpty("folder", folder);
		sources.add(folder);
	}

	public void print(String prefix) {
		L.d(prefix + project_name);
		for (int i = 0; i < sources.size(); i++) {
			String folder = sources.get(i);
			L.d(prefix + " > " + folder);
		}

	}

	public List<String> listSourceFolders() {
		return JUtils.newList(sources);
	}

	public String getName() {
		return project_name;
	}

}
