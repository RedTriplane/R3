
package com.jfixby.r3.activity.red.text;

import com.jfixby.r3.activity.api.txt.TextBarSpecs;
import com.jfixby.r3.engine.api.render.Drawable;
import com.jfixby.r3.engine.api.render.RenderMachine;
import com.jfixby.r3.fokker.font.api.FokkerFonts;
import com.jfixby.r3.fokker.font.api.FokkerStringHandler;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.font.StringSpec;
import com.jfixby.scarabei.api.strings.Text;

public class RedString implements Drawable {

	private final StringSpec stringSpec;

	private Text text;

	private FokkerStringHandler string;

	public RedString (final TextBarSpecs text_specs) {

		this.text = text_specs.text;

		this.stringSpec = FokkerFonts.newStringSpec();

		this.stringSpec.fontSize = text_specs.fontSize;
		this.stringSpec.fontColor = text_specs.fontColor;
		this.stringSpec.borderSize = text_specs.borderSize;
		this.stringSpec.fontID = text_specs.fontID;
		this.stringSpec.borderColor = text_specs.borderColor;
		this.stringSpec.string = this.text.getString();

		this.string = FokkerFonts.obtainString(this.stringSpec);
	}

	@Override
	public void doDraw () {
		if (!this.visible) {
			return;
		}

		RenderMachine.drawString(this.string);
	}

	@Override
	public boolean isVisible () {
		return this.visible;
	}

	boolean visible = true;

	public void setText (final Text text) {
		Debug.checkNull("text", text);
		this.text = text;

		FokkerFonts.disposeString(this.string);
		this.stringSpec.string = this.text.getString();
		this.string = FokkerFonts.obtainString(this.stringSpec);
	}

	public void switchLocale (final String locale_name) {
		this.text.switchLocale(locale_name);
		this.setText(this.text);
	}

	public String getLocaleName () {
		return this.text.getLocaleName();
	}

	public Text getText () {
		return this.text;
	}
}
