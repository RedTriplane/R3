
package org.box2d.r3.jbox2d.f;

import org.box2d.r3.api.BodyDef;
import org.box2d.r3.api.Box2DComponent;
import org.box2d.r3.api.Box2DWorld;
import org.box2d.r3.api.Box2DWorldSpecs;
import org.box2d.r3.api.CircleShape;
import org.box2d.r3.api.FixtureDef;
import org.box2d.r3.api.PolygonShape;

public class JBox2DFloat implements Box2DComponent {

	public JBox2DFloat () {

	}

	@Override
	public Box2DWorld newBox2DWorld (final Box2DWorldSpecs specs) {
		return new JWorld(specs);

	}

	@Override
	public Box2DWorldSpecs newBox2DWorldSpecs () {
		return new JWorldSpecs();
	}

	@Override
	public FixtureDef newFixtureDef () {
		return new JFixtureDef();
	}

	@Override
	public BodyDef newBodyDef () {
		return new JBodyDef();
	}

	@Override
	public PolygonShape newPolygonShape () {
		return new JPolygonShape();
	}

	@Override
	public CircleShape newCircleShape () {
		return new JCircleShape();
	}

}
