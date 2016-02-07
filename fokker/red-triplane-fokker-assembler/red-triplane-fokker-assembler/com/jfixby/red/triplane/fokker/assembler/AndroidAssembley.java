package com.jfixby.red.triplane.fokker.assembler;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.file.File;
import com.jfixby.tool.eclipse.dep.EclipseProjectInfo;

public class AndroidAssembley extends AbstractAssembley {

	public AndroidAssembley(FokkerAssembley fokkerAssembley, Collection<EclipseProjectInfo> dependency_list,
			File gradle_output_project_folder) {
		super(fokkerAssembley, dependency_list, gradle_output_project_folder);
	}

}
