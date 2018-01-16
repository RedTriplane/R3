
package com.jfixby.r3.activity.api;

import com.jfixby.r3.activity.api.ui.ninepatch.NinePatch;
import com.jfixby.r3.activity.api.ui.ninepatch.NinePatchSettings;

public interface UIFactory {

	public NinePatchSettings newNinePatchSettings ();

	public NinePatch newNinePatch (NinePatchSettings settings);

}
