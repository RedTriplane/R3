
package com.jfixby.r3.api.ui.unit.projection;

public interface ProjectionsStack extends Projection {

	void push (Projection projection);

	Projection pop ();

	Projection peek ();

	int size ();

}
