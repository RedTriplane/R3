
package com.jfixby.r3.activity.api.act;

import com.jfixby.scarabei.api.names.ID;

public interface UIEventsManagerComponent {

	void pushFadeOut (long period);

	void pushFadeIn (long period);

	void allowUserInput ();

	void loadUnit (ID ui_unit_id);

	void showLoadingScreen (ID loader_unit_id, boolean fadedOut);

	public <T> void pushAction (final UIAction<T> action);

	void disableUserInput ();

	void startEventsMachine ();

	void pushWait (long time);

}
