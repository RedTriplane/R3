package com.jfixby.red.triplane.fokker.assembler.ds;

import java.util.Vector;

import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.debug.Debug;
import com.jfixby.cmns.api.log.L;

public class ProjectAssemblerConfig {

	private String project_name;

	ProjectAssemblerConfig() {
		super();
	}

	private final Vector<String> sources = new Vector<String>();

	public ProjectAssemblerConfig(String project_name) {
		Debug.checkNull("project_name", project_name);
		Debug.checkEmpty("project_name", project_name);
		this.project_name = project_name;
	}

	public void addSourceFolder() {
		sources.add(project_name);
	}

	public void addSourceFolder(String folder) {
		Debug.checkNull("folder", folder);
		Debug.checkEmpty("folder", folder);
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
		return Collections.newList(sources);
	}

	public String getName() {
		return project_name;
	}

}
