
package com.jfixby.r3.api.ui;

import com.jfixby.rana.api.pkg.PackageReaderListener;
import com.jfixby.rana.api.pkg.cache.LoadTask;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;

public interface UIComponent {

	LoadTask prepareLoadUITask (final PackageReaderListener packageListener, Collection<ID> asetsToLoad);

	LoadTask prepareLoadUITask (final PackageReaderListener packageListener, ID... asetsToLoad);

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

// public <T extends UIController> T getUIController ();

}
