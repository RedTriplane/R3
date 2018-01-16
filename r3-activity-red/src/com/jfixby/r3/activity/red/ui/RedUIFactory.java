
package com.jfixby.r3.activity.red.ui;

import com.jfixby.r3.activity.api.UIFactory;
import com.jfixby.r3.activity.api.ui.ninepatch.NinePatch;
import com.jfixby.r3.activity.api.ui.ninepatch.NinePatchSettings;
import com.jfixby.r3.activity.red.RedComponentsFactory;

public class RedUIFactory implements UIFactory {

	private final RedComponentsFactory master;

	public RedUIFactory (final RedComponentsFactory redComponentsFactory) {
		this.master = redComponentsFactory;
	}

	@Override
	public NinePatchSettings newNinePatchSettings () {
		return new NinePatchSettings();
	}

	@Override
	public NinePatch newNinePatch (final NinePatchSettings settings) {
		return new RedNinePatch(settings);
	}

}
