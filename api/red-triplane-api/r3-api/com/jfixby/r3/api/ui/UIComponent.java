
package com.jfixby.r3.api.ui;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.r3.api.logic.LoadTask;
import com.jfixby.rana.api.pkg.PackageReaderListener;

public interface UIComponent {

	LoadTask prepareLoadUITask (final PackageReaderListener packageListener, Collection<AssetID> asetsToLoad);

	LoadTask prepareLoadUITask (final PackageReaderListener packageListener, AssetID... asetsToLoad);

	void pushTaskToLoader (LoadTask task, UILoaderListener ui_loader_listener);

	void pushFadeOut (long period);

	void pushFadeIn (long period);

	void allowUserInput ();

	void pushLoadAssetsTask (Collection<AssetID> newList, UILoaderListener loader_listener);

	void switchToUI (AssetID ui_unit_id);

	void showLoadingScreen (AssetID loader_unit_id, boolean fadedOut);

	AnimationsMachine newAnimationsMachine ();

	public <T> UIActionStatus pushAction (final UIAction<T> action);

	void disableUserInput ();

// public <T extends UIController> T getUIController ();

}
