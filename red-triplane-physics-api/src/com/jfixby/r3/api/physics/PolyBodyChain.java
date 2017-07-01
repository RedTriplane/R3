package com.jfixby.r3.api.physics;

import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.floatn.Float2;

public interface PolyBodyChain {

	Collection<Float2> listVerices();

	void addVertex(double x, double y);

	void addVertex(Float2 position);

}
