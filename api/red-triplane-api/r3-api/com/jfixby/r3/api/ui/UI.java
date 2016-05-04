
package com.jfixby.r3.api.ui;

import com.jfixby.cmns.api.ComponentInstaller;
import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.r3.api.game.LoadTask;

public class UI {

	static private ComponentInstaller<GameUIComponent> componentInstaller = new ComponentInstaller<GameUIComponent>("GameUI");

	public static final void installComponent (final GameUIComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final GameUIComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final GameUIComponent component () {
		return componentInstaller.getComponent();
	}

	public static void showLoadingScreen (final AssetID loader_unit_id, final boolean fadedOut) {
		invoke().showLoadingScreen(loader_unit_id, fadedOut);
	}

	public static LoadTask prepareLoadGameUITask (final Collection<AssetID> asetsToLoad) {
		return invoke().prepareLoadGameUITask(asetsToLoad);
	}

	public static LoadTask prepareLoadGameUITask (final AssetID... asetsToLoad) {
		return invoke().prepareLoadGameUITask(asetsToLoad);
	}

	public static void pushTaskToLoader (final LoadTask task, final UILoaderListener ui_loader_listener) {
		invoke().pushTaskToLoader(task, ui_loader_listener);
	}

	public static void pushFadeOut (final long period) {
		invoke().pushFadeOut(period);
	}

	public static void loadUnit (final AssetID game_ui_unit_id) {
		invoke().switchToGameUI(game_ui_unit_id);
	}

	public static void pushFadeIn (final long period) {
		invoke().pushFadeIn(period);
	}

	public static void allowUserInput () {
		invoke().allowUserInput();
	}

	public static void pushLoadAssetsTask (final Collection<AssetID> newList, final UILoaderListener loader_listener) {
		invoke().pushLoadAssetsTask(newList, loader_listener);
	}

	public static AnimationsMachine newAnimationsMachine () {
		return invoke().newAnimationsMachine();
	}

}
