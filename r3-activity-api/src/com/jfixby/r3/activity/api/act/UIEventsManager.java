
package com.jfixby.r3.activity.api.act;

import com.jfixby.scarabei.api.ComponentInstaller;
import com.jfixby.scarabei.api.assets.ID;

public class UIEventsManager {

	static private ComponentInstaller<UIEventsManagerComponent> componentInstaller = new ComponentInstaller<>(
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

	public static AnimationsMachine newAnimationsMachine () {
		return invoke().newAnimationsMachine();
	}

	public static <T> void pushAction (final UIAction<T> action) {
		invoke().pushAction(action);
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

}
