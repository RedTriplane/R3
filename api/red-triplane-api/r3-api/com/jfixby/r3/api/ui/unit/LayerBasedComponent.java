package com.jfixby.r3.api.ui.unit;

import com.jfixby.r3.api.ui.unit.layer.Component;
import com.jfixby.r3.api.ui.unit.layer.Layer;

public interface LayerBasedComponent extends Component {

	public Layer getRoot();

}
