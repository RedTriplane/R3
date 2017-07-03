
package com.jfixby.r3.api;

import com.jfixby.scarabei.api.ComponentInstaller;

public class RedTriplane {
	static private ComponentInstaller<RedTriplaneComponent> componentInstaller = new ComponentInstaller<RedTriplaneComponent>(
		"RedTriplane");

	public static final void installComponent (final RedTriplaneComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final RedTriplaneComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final RedTriplaneComponent component () {
		return componentInstaller.getComponent();
	}

	public static EngineVersion VERSION () {
		return invoke().VERSION();
	}

	public static void setGameStarter (final GameStarter starter) {
		invoke().setGameStarter(starter);
	}

	public static GameStarter getGameStarter () {
		return invoke().getGameStarter();
	}

}
