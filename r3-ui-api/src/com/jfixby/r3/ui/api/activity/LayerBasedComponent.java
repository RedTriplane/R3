package com.jfixby.r3.ui.api.activity;

import com.jfixby.r3.ui.api.activity.layer.Component;
import com.jfixby.r3.ui.api.activity.layer.Layer;

public interface LayerBasedComponent extends Component {

	public Layer getRoot();

}
