/**
 * 
 */
package com.jfixby.r3.api.unit.components.physics.body;

import com.jfixby.r3.api.unit.components.physics.AbstractBody;

/**
 * @author Major Tom
 * 
 */
public interface Body extends AbstractBody {

	public BODY_SHAPE_TYPE getShapeType();

	PhysicalProperties physics();

	BodyListeners listeners();

	BodyPositionController location();

	BodyDebugInfo debugInfo();

	BodyMassController mass();

	

	// IShape shape();

}
