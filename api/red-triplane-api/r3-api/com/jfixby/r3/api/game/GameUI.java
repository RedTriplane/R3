package com.jfixby.r3.api.game;

import com.jfixby.cmns.api.ComponentInstaller;
import com.jfixby.r3.api.ui.UILoaderListener;

public class GameUI {

	static private ComponentInstaller<GameUIComponent> componentInstaller = new ComponentInstaller<GameUIComponent>(
			"GameUI");

	public static final void installComponent(GameUIComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final GameUIComponent invoke() {
		return componentInstaller.invokeComponent();
	}

	public static final GameUIComponent component() {
		return componentInstaller.getComponent();
	}

	public static void showLoadingScreen() {
		invoke().showLoadingScreen();
	}

	public static LoadTask prepareLoadGameUITask() {
		return invoke().prepareLoadGameUITask();
	}

	public static void pushTaskToLoader(LoadTask task, UILoaderListener ui_loader_listener) {
		invoke().pushTaskToLoader(task, ui_loader_listener);
	}

	public static void pushFadeOut(long period) {
		invoke().pushFadeOut(period);
	}

	public static void switchToGameUI() {
		invoke().switchToGameUI();
	}

	public static void pushFadeIn(long period) {
		invoke().pushFadeIn(period);
	}

	public static void allowUserInput() {
		invoke().allowUserInput();
	}

	public static void showTextTestScene() {
		invoke().showTextTestScene();
	}

	public static void showShaderTestScene() {
		invoke().showShaderTestScene();
	}

}
