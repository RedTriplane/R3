
package com.jfixby.r3.api.ui;

import com.jfixby.cmns.api.ComponentInstaller;
import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.r3.api.logic.LoadTask;
import com.jfixby.rana.api.pkg.PackageReaderListener;

public class UI {

	static private ComponentInstaller<UIComponent> componentInstaller = new ComponentInstaller<UIComponent>("UI");

	public static final void installComponent (final UIComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final UIComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final UIComponent component () {
		return componentInstaller.getComponent();
	}

	public static void showLoadingScreen (final AssetID loader_unit_id, final boolean fadedOut) {
		invoke().showLoadingScreen(loader_unit_id, fadedOut);
	}

	public static LoadTask prepareLoadUITask (final PackageReaderListener packageListener, final Collection<AssetID> asetsToLoad) {
		return invoke().prepareLoadUITask(packageListener, asetsToLoad);
	}

	public static LoadTask prepareLoadUITask (final PackageReaderListener packageListener, final AssetID... asetsToLoad) {
		return invoke().prepareLoadUITask(packageListener, asetsToLoad);
	}

	public static void pushTaskToLoader (final LoadTask task, final UILoaderListener ui_loader_listener) {
		invoke().pushTaskToLoader(task, ui_loader_listener);
	}

	public static void pushFadeOut (final long period) {
		invoke().pushFadeOut(period);
	}

	public static void loadUnit (final AssetID ui_unit_id) {
		invoke().switchToUI(ui_unit_id);
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

	public static <T> UIActionStatus pushAction (final UIAction<T> action) {
		return invoke().pushAction(action);
	}

	public static void disableUserInput () {
		invoke().disableUserInput();
	}

// public static <T extends UIController> T getUIController () {
// return invoke().getUIController();
// }

}
