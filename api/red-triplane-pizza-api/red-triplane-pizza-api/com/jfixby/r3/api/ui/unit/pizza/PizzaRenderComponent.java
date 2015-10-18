package com.jfixby.r3.api.ui.unit.pizza;

import com.jfixby.r3.api.ui.unit.ComponentsFactory;

public interface PizzaRenderComponent {

	PizzaRendererSpecs newPizzaRendererSpecs();

	PizzaRenderer newPizzaRenderer(PizzaRendererSpecs specs,
			ComponentsFactory master);

}
