
package com.jfixby.r3.ext.ui.unit.pizza;

import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.geometry.PolyComponent;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.pizza.PizzaRenderer;
import com.jfixby.r3.api.ui.unit.pizza.PizzaRendererSpecs;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.utl.pizza.api.PizzaLandscape;
import com.jfixby.utl.pizza.api.PizzaLandscapeListener;
import com.jfixby.utl.pizza.api.PizzaTile;

public class RedPizzaRenderer implements PizzaRenderer, PizzaLandscapeListener {

	private final Layer root;

	private final PizzaLandscape landscape;

	private final ComponentsFactory factory;

	private PolyComponent active_area_poly;

	public RedPizzaRenderer (final PizzaRendererSpecs specs, final ComponentsFactory master) {
		this.factory = master;
		this.root = master.newLayer();
		this.landscape = Debug.checkNull("pizzaLandscape", specs.pizzaLandscape);
		this.landscape.setLandscapeListener(this);

		// active_area_poly = factory.getGeometryDepartment().newPoly();
		// active_area_poly.getShape().setSize(4);
		// LandscapeActiveArea active_area = landscape.getActiveArea();
		// active_area_poly.getShape().getVertex(0).relative()
		// .set(active_area.getTopLeftCorner());
		// active_area_poly.getShape().getVertex(1).relative()
		// .set(active_area.getTopRightCorner());
		// active_area_poly.getShape().getVertex(2).relative()
		// .set(active_area.getBottomLeftCorner());
		// active_area_poly.getShape().getVertex(3).relative()
		// .set(active_area.getBottomRightCorner());
	}

	@Override
	public Layer getRoot () {
		return this.root;
	}

	@Override
	public void onBlockRemove (final PizzaTile block) {
	}

	@Override
	public void onBlockAdd (final PizzaTile block) {
	}

	@Override
	public void onBlockFocus (final PizzaTile block) {
	}

}
