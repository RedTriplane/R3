package com.jfixby.r3.api.game;

import com.jfixby.cmns.api.ComponentInstaller;

public class GameLogic {

	static private ComponentInstaller<GameLogicComponent> componentInstaller = new ComponentInstaller<GameLogicComponent>(
			"GameLogic");

	public static final void installComponent(
			GameLogicComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final GameLogicComponent invoke() {
		return componentInstaller.invokeComponent();
	}

	public static final GameLogicComponent component() {
		return componentInstaller.getComponent();
	}

	public static void startGame() {
		componentInstaller.invokeComponent().startGame();
	}

}
