
package com.jfixby.red.triplane.fokker.assembler.ds;

import java.util.ArrayList;

import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.log.L;

public class ProjectAssemblerConfig {

	private String project_name;

	ProjectAssemblerConfig () {
		super();
	}

	private final ArrayList<String> sources = new ArrayList<String>();

	public ProjectAssemblerConfig (final String project_name) {
		Debug.checkNull("project_name", project_name);
		Debug.checkEmpty("project_name", project_name);
		this.project_name = project_name;
	}

	public void addSourceFolder () {
		this.sources.add(this.project_name);
	}

	public void addSourceFolder (final String folder) {
		Debug.checkNull("folder", folder);
		Debug.checkEmpty("folder", folder);
		this.sources.add(folder);
	}

	public void print (final String prefix) {
		L.d(prefix + this.project_name);
		for (int i = 0; i < this.sources.size(); i++) {
			final String folder = this.sources.get(i);
			L.d(prefix + " > " + folder);
		}

	}

	public List<String> listSourceFolders () {
		return Collections.newList(this.sources);
	}

	public String getName () {
		return this.project_name;
	}

}
