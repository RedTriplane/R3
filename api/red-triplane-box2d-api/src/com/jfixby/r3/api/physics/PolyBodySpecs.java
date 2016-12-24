package com.jfixby.r3.api.physics;

import com.jfixby.scarabei.api.collections.Collection;

public interface PolyBodySpecs {

	Collection<PolyBodyCircle> listCircles();

	PolyBodyCircle newCircle();

	Collection<PolyBodyChain> listPolyBodyChains();

	PolyBodyChain newChain();

	void addChain(PolyBodyChain c);

	void addCircle(PolyBodyCircle c);

}
