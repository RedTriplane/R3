
package com.jfixby.r3.render.api;

public interface ShaderParameter {

	public String TYPE_FLOAT ();

	public String TYPE_INT ();

	String getName ();

	String getType ();

	<T> T getDefaultValue ();

}
