
package com.jfixby.r3.physics;

import com.jfixby.r3.api.physics.PolyBodyChain;
import com.jfixby.r3.api.physics.PolyBodyCircle;
import com.jfixby.r3.api.physics.PolyBodySpecs;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.log.L;

public class RedPolyBodySpecs implements PolyBodySpecs {

	final Set<PolyBodyCircle> circles = Collections.newSet();
	final Set<PolyBodyChain> chains = Collections.newSet();

	public void setup (PolyBodySpecs specs) {

		Collection<PolyBodyCircle> other_circles = specs.listCircles();
		circles.clear();
		chains.clear();
		for (PolyBodyCircle c : other_circles) {
			PolyBodyCircle nc = this.newCircle();
			nc.setXYR(c.getX(), c.getY(), c.getRadius());
			this.addCircle(nc);
		}
		Collection<PolyBodyChain> other_chains = specs.listPolyBodyChains();
		for (PolyBodyChain c : other_chains) {
			PolyBodyChain nc = this.newChain();
			setupChain(c, nc);
			this.addChain(nc);
		}

		L.d("memory leak:setup");

	}

	private void setupChain (PolyBodyChain input, PolyBodyChain output) {
		Collection<Float2> vertices = input.listVerices();
		for (Float2 vertex : vertices) {
			output.addVertex(vertex.getX(), vertex.getY());
		}
	}

	@Override
	public void addCircle (PolyBodyCircle c) {
		circles.add(c);
	}

	@Override
	public void addChain (PolyBodyChain c) {
		chains.add(c);
	}

	@Override
	public Collection<PolyBodyCircle> listCircles () {
		return circles;
	}

	@Override
	public PolyBodyCircle newCircle () {
		return new RedPolyBodyCircle();
	}

	@Override
	public PolyBodyChain newChain () {
		return new RedPolyBodyChain();
	}

	@Override
	public Collection<PolyBodyChain> listPolyBodyChains () {
		return chains;
	}

	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chains == null) ? 0 : chains.hashCode());
		result = prime * result + ((circles == null) ? 0 : circles.hashCode());
		return result;
	}

	@Override
	public boolean equals (Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		RedPolyBodySpecs other = (RedPolyBodySpecs)obj;
		if (chains == null) {
			if (other.chains != null) return false;
		} else if (!chains.equals(other.chains)) return false;
		if (circles == null) {
			if (other.circles != null) return false;
		} else if (!circles.equals(other.circles)) return false;
		return true;
	}

}
