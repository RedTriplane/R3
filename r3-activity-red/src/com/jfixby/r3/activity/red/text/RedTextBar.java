
package com.jfixby.r3.activity.red.text;

import com.jfixby.r3.activity.api.LayerBasedComponent;
import com.jfixby.r3.activity.api.geometry.RectangleComponent;
import com.jfixby.r3.activity.api.layer.Layer;
import com.jfixby.r3.activity.api.txt.TextBar;
import com.jfixby.r3.activity.api.txt.TextBarSpecs;
import com.jfixby.r3.activity.red.RedComponentsFactory;
import com.jfixby.r3.activity.red.layers.RedLayer;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.geometry.projections.Projection;
import com.jfixby.scarabei.api.math.Angle;
import com.jfixby.scarabei.api.strings.Text;

public class RedTextBar implements TextBar, LayerBasedComponent {

	private final String name;
	private final RedLayer root;
// private final Raster backgroundRaster;
	private final Rectangle shape;

	final RedString string;
	private final RectangleComponent debugRectangle;

	public RedTextBar (final TextBarSpecs text_specs, final RedComponentsFactory componentsFactory) {
		this.name = text_specs.name;

		this.string = new RedString(text_specs, componentsFactory);

		this.root = componentsFactory.newLayer();

		this.shape = Geometry.newRectangle(10, 10);

		this.shape.setPosition(50, 100);

		this.debugRectangle = componentsFactory.getGeometryDepartment().newRectangle(this.shape);

		final Projection projection = Geometry.getProjectionFactory()//
			.newOffsetAndRotate(this.shape);

		this.root.setProjection(projection);

		this.root.attachComponent(this.string);

		this.root.attachComponent(this.debugRectangle);
	}

	@Override
	public void setText (final Text text) {
		this.string.setText(text);
	}

	@Override
	public void setLocaleName (final String locale_name) {
		this.string.switchLocale(locale_name);
	}

	@Override
	public String getLocaleName () {
		return this.string.getLocaleName();
	}

	@Override
	public void setDebugRenderFlag (final boolean b) {
	}

	@Override
	public boolean getDebugRenderFlag () {
		return false;
	}

	@Override
	public void hide () {
		this.root.hide();
	}

	@Override
	public void show () {
		this.root.show();
	}

	@Override
	public boolean isVisible () {
		return this.root.isVisible();
	}

	@Override
	public void setVisible (final boolean b) {
		this.root.setVisible(b);
	}

	@Override
	public void setName (final String name) {
		this.root.setName(name);
	}

	@Override
	public String getName () {
		return this.root.getName();
	}

	@Override
	public void setPosition (final CanvasPosition position) {
		this.shape.setPosition(position);
	}

	@Override
	public void setPosition (final ReadOnlyFloat2 position) {
		this.shape.setPosition(position);
	}

	@Override
	public void offset (final double x, final double y) {
		Err.throwNotImplementedYet();
	}

	@Override
	public double getPositionX () {
		return this.shape.getPositionX();
	}

	@Override
	public double getPositionY () {
		return this.shape.getPositionY();
	}

	@Override
	public Angle getRotation () {
		return this.shape.getRotation();
	}

	@Override
	public void setRotation (final Angle rotation) {
		this.shape.setRotation(rotation);
	}

	@Override
	public void setRotation (final double rotation) {
		this.shape.setRotation(rotation);
	}

	@Override
	public void setPositionX (final double x) {
		this.shape.setPositionX(x);
	}

	@Override
	public void setPositionY (final double y) {
		this.shape.setPositionY(y);
	}

	@Override
	public void setPosition (final double x, final double y) {
		this.shape.setPositionX(x);
		this.shape.setPositionY(y);
	}

	@Override
	public Layer getRoot () {
		return this.root;
	}

	@Override
	public Text getText () {
		return this.string.getText();
	}

}
