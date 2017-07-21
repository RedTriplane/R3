package com.jfixby.r3.api.activity;

import com.jfixby.r3.api.activity.layer.Component;
import com.jfixby.r3.api.activity.layer.Layer;

public interface LayerBasedComponent extends Component {

	public Layer getRoot();

}
