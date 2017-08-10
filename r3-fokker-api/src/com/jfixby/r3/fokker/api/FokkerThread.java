
package com.jfixby.r3.fokker.api;

import com.jfixby.scarabei.api.ComponentInstaller;
import com.jfixby.scarabei.api.promise.Future;
import com.jfixby.scarabei.api.promise.Promise;
import com.jfixby.scarabei.api.taskman.TaskManagerComponent;

public class FokkerThread {

	static private ComponentInstaller<FokkerThreadComponent> componentInstaller = new ComponentInstaller<>("FokkerThread");

	public static final void installComponent (final FokkerThreadComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static void installComponent (final String className) {
		componentInstaller.installComponent(className);
	}

	public static final FokkerThreadComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final FokkerThreadComponent component () {
		return componentInstaller.getComponent();
	}

	public static <R> Promise<R> executeInMainThread (final String debugName, final Future<Void, R> future) {
		return invoke().executeInMainThread(debugName, future);
	}

	public static TaskManagerComponent taskManager () {
		return invoke().taskManager();
	}

}
