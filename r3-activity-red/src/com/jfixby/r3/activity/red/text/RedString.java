
package com.jfixby.r3.activity.red.text;

import com.jfixby.r3.activity.api.raster.UI_BLEND_MODE;
import com.jfixby.r3.activity.api.txt.TextBarSpecs;
import com.jfixby.r3.activity.red.RedComponentsFactory;
import com.jfixby.r3.activity.red.geo.RedRectangleComponent;
import com.jfixby.r3.engine.api.render.Drawable;
import com.jfixby.r3.engine.api.render.RenderMachine;
import com.jfixby.r3.engine.api.render.TEXTURE_BLEND_MODE;
import com.jfixby.r3.fokker.font.api.FokkerFonts;
import com.jfixby.r3.fokker.font.api.FokkerStringHandler;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.font.RasterStringSettings;
import com.jfixby.scarabei.api.strings.Text;

public class RedString implements Drawable {

	private final RasterStringSettings rasterStringSettings;

	private Text text;

	private FokkerStringHandler string;

	public RedString (final TextBarSpecs text_specs, final RedComponentsFactory master) {

		this.text = text_specs.text;

		this.rasterStringSettings = FokkerFonts.newStringSpec();

		this.rasterStringSettings.fontSize = text_specs.fontSize;
		this.rasterStringSettings.fontColor = text_specs.fontColor;
		this.rasterStringSettings.borderSize = text_specs.borderSize;
		this.rasterStringSettings.fontID = text_specs.fontID;
		this.rasterStringSettings.borderColor = text_specs.borderColor;
		this.rasterStringSettings.string = this.text.getString();

		this.string = FokkerFonts.obtainString(this.rasterStringSettings);

		//

		this.debug_rectangle = (RedRectangleComponent)master.getGeometryDepartment().newRectangle();
		// debug_rectangle.setDebugRenderFlag(true);
		this.debug_rectangle.setBorderColor(this.debug_rectangle.getDebugColor());
		// this.setDebugRenderFlag(!true);
	}

	public final RedRectangleComponent debug_rectangle;
	private final UI_BLEND_MODE mode = UI_BLEND_MODE.Normal;
	float opacity = 1f;

	@Override
	public void doDraw () {
		if (!this.visible) {
			return;
		}

		RenderMachine.beginDrawComponent(this);
		RenderMachine.beginRasterMode(this.TOtxtmODE(this.mode), this.opacity);

		RenderMachine.drawString(this.rasterStringSettings);

		RenderMachine.endRasterMode(this.TOtxtmODE(this.mode));
		RenderMachine.endDrawComponent(this);
		if (this.getDebugRenderFlag()) {
			this.debug_rectangle.doDraw();
		}
	}

	boolean debug = false;

	private boolean getDebugRenderFlag () {
		return this.debug;
	}

	private TEXTURE_BLEND_MODE TOtxtmODE (final UI_BLEND_MODE mode2) {
		// Err.throwNotImplementedYet();
		return TEXTURE_BLEND_MODE.Normal;
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
		this.rasterStringSettings.string = this.text.getString();
		this.string = FokkerFonts.obtainString(this.rasterStringSettings);
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
