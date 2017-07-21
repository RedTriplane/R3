package com.jfixby.r3.activity.api;

import com.jfixby.r3.activity.api.layer.Component;
import com.jfixby.r3.activity.api.layer.Layer;

public interface LayerBasedComponent extends Component {

	public Layer getRoot();

}
