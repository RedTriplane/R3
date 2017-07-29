
package com.jfixby.r3.physics.body;

import com.jfixby.r3.api.physics.BODY_SHAPE_TYPE;
import com.jfixby.r3.api.physics.PolyBody;
import com.jfixby.r3.api.physics.PolyBodySpecs;
import com.jfixby.r3.physics.ShapeData;

public class PolyBodyImpl extends BodyImpl implements PolyBody {

	public PolyBodyImpl (PolyBodySpecs geometry) {
		super(new ShapeData(BODY_SHAPE_TYPE.POLY));
		setupGeometry(geometry);
	}

}
