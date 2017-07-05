
package com.jfixby.r3.uiact;

import com.jfixby.scarabei.api.ComponentInstaller;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;

public class UIEventsManager {

	static private ComponentInstaller<UIEventsManagerComponent> componentInstaller = new ComponentInstaller<UIEventsManagerComponent>(
		"UIEventsManagerComponent");

	public static final void installComponent (final UIEventsManagerComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final UIEventsManagerComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final UIEventsManagerComponent component () {
		return componentInstaller.getComponent();
	}

	public static LoadTask prepareLoadUITask (final Collection<ID> asetsToLoad) {
		return invoke().prepareLoadUITask(asetsToLoad);
	}

	public static LoadTask prepareLoadUITask (final ID... asetsToLoad) {
		return invoke().prepareLoadUITask(asetsToLoad);
	}

	public static void pushTaskToLoader (final LoadTask task, final UILoaderListener ui_loader_listener) {
		invoke().pushTaskToLoader(task, ui_loader_listener);
	}

	public static void showLoadingScreen (final ID loader_unit_id, final boolean fadedOut) {
		invoke().showLoadingScreen(loader_unit_id, fadedOut);
	}

	public static void pushFadeOut (final long period) {
		invoke().pushFadeOut(period);
	}

	public static void loadUnit (final ID ui_unit_id) {
		invoke().switchToUI(ui_unit_id);
	}

	public static void pushFadeIn (final long period) {
		invoke().pushFadeIn(period);
	}

	public static void allowUserInput () {
		invoke().allowUserInput();
	}

	public static void pushLoadAssetsTask (final Collection<ID> newList, final UILoaderListener loader_listener) {
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

	public static void startEventsMachine () {
		invoke().startEventsMachine();
	}

	public static void pushWait (final long time) {
		invoke().pushWait(time);
	}

// public static <T extends UIController> T getUIController () {
// return invoke().getUIController();
// }

}
