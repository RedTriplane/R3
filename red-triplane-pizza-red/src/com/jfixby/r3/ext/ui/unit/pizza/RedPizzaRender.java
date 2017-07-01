
package com.jfixby.r3.ext.ui.unit.pizza;

import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.pizza.PizzaRenderComponent;
import com.jfixby.r3.api.ui.unit.pizza.PizzaRenderer;
import com.jfixby.r3.api.ui.unit.pizza.PizzaRendererSpecs;

public class RedPizzaRender implements PizzaRenderComponent {

	@Override
	public PizzaRenderer newPizzaRenderer (final PizzaRendererSpecs specs, final ComponentsFactory master) {
		return new RedPizzaRenderer(specs, master);
	}

}
