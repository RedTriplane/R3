package com.jfixby.red.triplane.fokker.assembler;

import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.tool.eclipse.dep.EclipseProjectInfo;

public class DesktopAssembley extends AbstractAssembley {

	public DesktopAssembley(FokkerAssembley fokkerAssembley, Collection<EclipseProjectInfo> dependency_list,
			File gradle_output_project_folder)  {
		super(fokkerAssembley, dependency_list,gradle_output_project_folder);
	}
}