
package com.jfixby.r3.physics.body;

import com.jfixby.r3.api.physics.BODY_SHAPE_TYPE;
import com.jfixby.r3.api.physics.BoxBody;
import com.jfixby.r3.physics.ShapeData;

public class BoxBodyImpl extends BodyImpl implements BoxBody {

	public BoxBodyImpl () {
		super(new ShapeData(BODY_SHAPE_TYPE.BOX));
	}

}
