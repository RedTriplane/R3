
package com.jfixby.r3.uiact;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;

public interface UIEventsManagerComponent {

	LoadTask prepareLoadUITask (Collection<ID> asetsToLoad);

	LoadTask prepareLoadUITask (ID... asetsToLoad);

	void pushTaskToLoader (LoadTask task, UILoaderListener ui_loader_listener);

	void pushFadeOut (long period);

	void pushFadeIn (long period);

	void allowUserInput ();

	void pushLoadAssetsTask (Collection<ID> newList, UILoaderListener loader_listener);

	void switchToUI (ID ui_unit_id);

	void showLoadingScreen (ID loader_unit_id, boolean fadedOut);

	AnimationsMachine newAnimationsMachine ();

	public <T> UIActionStatus pushAction (final UIAction<T> action);

	void disableUserInput ();

	void startEventsMachine ();

	void pushWait (long time);

// public <T extends UIController> T getUIController ();

}
