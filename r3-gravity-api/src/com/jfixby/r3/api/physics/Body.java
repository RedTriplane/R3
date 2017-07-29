/**
 * 
 */
package com.jfixby.r3.api.physics;

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
