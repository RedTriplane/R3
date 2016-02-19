package com.jfixby.r3.api.ui.unit.txt;

import com.jfixby.r3.api.ui.unit.layer.VisibleComponent;

public interface Shader extends VisibleComponent {

	void setParameter(String name, String value);

	public String getParameter(String name);

}
