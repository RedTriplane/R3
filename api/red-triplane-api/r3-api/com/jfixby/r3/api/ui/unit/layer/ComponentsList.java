package com.jfixby.r3.api.ui.unit.layer;

public interface ComponentsList<T extends NamedElement> {

	public T findElementByName(String element_name);

	public void print(String tag);

}
