
package org.box2d.r3.jbox2d.f;

import org.box2d.r3.api.Shape;

public abstract class JShape implements Shape {

	// public com.badlogic.gdx.physics.box2d.Shape shape;
	//
	abstract public org.jbox2d.f.collision.shapes.Shape getGdxShape ();

	abstract public void update (org.jbox2d.f.collision.shapes.Shape gdx_shape);
	// {
	// return shape;
	//
	// }
	//
	// public void update(com.badlogic.gdx.physics.box2d.Shape gdx_shape) {
	// this.shape = gdx_shape;
	// }

}
