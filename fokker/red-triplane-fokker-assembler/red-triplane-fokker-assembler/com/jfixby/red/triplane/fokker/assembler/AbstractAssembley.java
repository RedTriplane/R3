package com.jfixby.red.triplane.fokker.assembler;

import com.jfixby.cmns.api.collections.List;
import com.jfixby.tool.eclipse.dep.EclipseProjectInfo;

public abstract class AbstractAssembley {
	private FokkerAssembley master;
	private List<EclipseProjectInfo> dependencies;

	public AbstractAssembley(FokkerAssembley fokkerAssembley, List<EclipseProjectInfo> dependency_list) {
		this.master = fokkerAssembley;
		this.dependencies = dependency_list;
	}

	public void printTransactions(String tag) {
		// L.d();
		this.dependencies.print("Assembley[" + tag + "]");
	}

}
