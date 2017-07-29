package com.jfixby.r3.fokker.core.assets;

import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.log.L;

public class RedPolyBodySpecs  {

	final Set<RedPolyBodyCircle> circles = Collections.newSet();
	final Set<RedPolyBodyChain> chains = Collections.newSet();

	public void setup(RedPolyBodySpecs specs) {

		Collection<RedPolyBodyCircle> other_circles = specs.listCircles();
		circles.clear();
		chains.clear();
		for (RedPolyBodyCircle c : other_circles) {
			RedPolyBodyCircle nc = this.newCircle();
			nc.setXYR(c.getX(), c.getY(), c.getRadius());
			this.addCircle(nc);
		}
		Collection<RedPolyBodyChain> other_chains = specs.listPolyBodyChains();
		for (RedPolyBodyChain c : other_chains) {
			RedPolyBodyChain nc = this.newChain();
			setupChain(c, nc);
			this.addChain(nc);
		}

		L.d("memory leak:setup");

	}

	private void setupChain(RedPolyBodyChain input, RedPolyBodyChain output) {
		Collection<Float2> vertices = input.listVerices();
		for (Float2 vertex : vertices) {
			output.addVertex(vertex.getX(), vertex.getY());
		}
	}

	public void addCircle(RedPolyBodyCircle c) {
		circles.add(c);
	}

	
	public void addChain(RedPolyBodyChain c) {
		chains.add(c);
	}

	public Collection<RedPolyBodyCircle> listCircles() {
		return circles;
	}

	
	public RedPolyBodyCircle newCircle() {
		return new RedPolyBodyCircle();
	}

	public RedPolyBodyChain newChain() {
		return new RedPolyBodyChain();
	}


	public Collection<RedPolyBodyChain> listPolyBodyChains() {
		return chains;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chains == null) ? 0 : chains.hashCode());
		result = prime * result + ((circles == null) ? 0 : circles.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RedPolyBodySpecs other = (RedPolyBodySpecs) obj;
		if (chains == null) {
			if (other.chains != null)
				return false;
		} else if (!chains.equals(other.chains))
			return false;
		if (circles == null) {
			if (other.circles != null)
				return false;
		} else if (!circles.equals(other.circles))
			return false;
		return true;
	}

}
