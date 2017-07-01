
package com.jfixby.r3.physics.duplex;

import com.jfixby.r3.api.physics.CircleBody;
import com.jfixby.r3.api.physics.CircleShape;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;

public class DuplexCircleBody extends DuplexBody<CircleShape> implements CircleBody {

	DuplexCircleShape shape;

	public DuplexCircleBody (final List<CircleBody> list) {
		super(list);
		final List<CircleShape> sh = Collections.newList();
		for (int i = 0; i < list.size(); i++) {
			final CircleShape shape_i = list.getElementAt(i).shape();
			sh.add(shape_i);
		}

		this.shape = new DuplexCircleShape(sh);
	}

	@Override
	public CircleShape shape () {
		return this.shape;
	}

}
