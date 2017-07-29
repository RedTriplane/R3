
package com.jfixby.r3.physics.duplex;

import com.jfixby.r3.api.physics.BoxBody;
import com.jfixby.r3.api.physics.BoxShape;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;

public class DuplexBoxBody extends DuplexBody<BoxShape> implements BoxBody {
	DuplexBoxShape shape;

	public DuplexBoxBody (final List<BoxBody> list) {
		super(list);
		final List<BoxShape> sh = Collections.newList();
		for (int i = 0; i < list.size(); i++) {
			final BoxShape shape_i = list.getElementAt(i).shape();
			sh.add(shape_i);
		}

		this.shape = new DuplexBoxShape(sh);
	}

	@Override
	public BoxShape shape () {
		return this.shape;
	}

}
