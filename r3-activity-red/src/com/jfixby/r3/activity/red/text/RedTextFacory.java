
package com.jfixby.r3.activity.red.text;

import com.jfixby.r3.activity.api.txt.TextBar;
import com.jfixby.r3.activity.api.txt.TextBarSpecs;
import com.jfixby.r3.activity.api.txt.TextFactory;
import com.jfixby.r3.activity.red.RedComponentsFactory;

public class RedTextFacory implements TextFactory {

	final RedComponentsFactory componentsFactory;

	public RedTextFacory (final RedComponentsFactory redComponentsFactory) {
		this.componentsFactory = redComponentsFactory;
	}

	@Override
	public TextBarSpecs newTextBarSpecs () {
		return new TextBarSpecs();
	}

	@Override
	public TextBar newTextBar (final TextBarSpecs text_specs) {
		return new RedTextBar(text_specs, this.componentsFactory);
	}

}
