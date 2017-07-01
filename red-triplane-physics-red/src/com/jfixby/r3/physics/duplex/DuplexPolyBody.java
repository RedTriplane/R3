
package com.jfixby.r3.physics.duplex;

import com.jfixby.r3.api.physics.PolyBody;
import com.jfixby.r3.api.physics.PolyShape;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;

public class DuplexPolyBody extends DuplexBody<PolyShape> implements PolyBody {

	DuplexPolyShape shape;

	public DuplexPolyBody (List<PolyBody> list) {
		super(list);
		List<PolyShape> sh = Collections.newList();
		for (int i = 0; i < list.size(); i++) {
			PolyShape shape_i = list.getElementAt(i).shape();
			sh.add(shape_i);
		}

		shape = new DuplexPolyShape(sh);
	}

	@Override
	public PolyShape shape () {
		return shape;
	}

}
