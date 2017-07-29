
package com.jfixby.r3.physics.body;

import com.jfixby.r3.api.physics.BODY_SHAPE_TYPE;
import com.jfixby.r3.api.physics.CircleBody;
import com.jfixby.r3.physics.ShapeData;

public class CircleBodyImpl extends BodyImpl implements CircleBody {

	public CircleBodyImpl () {
		super(new ShapeData(BODY_SHAPE_TYPE.CIRCLE));
	}

}
