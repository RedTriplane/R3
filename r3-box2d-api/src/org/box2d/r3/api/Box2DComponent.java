package org.box2d.r3.api;


public interface Box2DComponent {

	Box2DWorld newBox2DWorld(Box2DWorldSpecs specs);

	Box2DWorldSpecs newBox2DWorldSpecs();

	FixtureDef newFixtureDef();

	BodyDef newBodyDef();

	PolygonShape newPolygonShape();

	CircleShape newCircleShape();

	

}
