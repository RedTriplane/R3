package com.jfixby.red.triplane.fokker.assembler;

import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.file.File;
import com.jfixby.tool.eclipse.dep.EclipseProjectInfo;

public class CoreAssembley extends AbstractAssembley {
	

	public CoreAssembley(FokkerAssembley fokkerAssembley, List<EclipseProjectInfo> dependency_list,
			File gradle_output_project_folder) {
		super(fokkerAssembley, dependency_list, gradle_output_project_folder);
		
	}

	

}
