
package com.jfixby.r3.physics;

import com.jfixby.r3.api.physics.PolyBodyChain;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.geometry.Geometry;

public class RedPolyBodyChain implements PolyBodyChain {

	final Set<Float2> vertices = Collections.newSet();

	@Override
	public Collection<Float2> listVerices () {
		return vertices;
	}

	@Override
	public void addVertex (double x, double y) {
		vertices.add(Geometry.newFloat2(x, y));
	}

	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vertices == null) ? 0 : vertices.hashCode());
		return result;
	}

	@Override
	public boolean equals (Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		RedPolyBodyChain other = (RedPolyBodyChain)obj;
		if (vertices == null) {
			if (other.vertices != null) return false;
		} else if (!equalvericces(other.vertices)) return false;
		return true;
	}

	private boolean equalvericces (Set<Float2> vertices2) {
		return Geometry.equalFloat2Collections(vertices, vertices2);
	}

	@Override
	public void addVertex (Float2 position) {
		this.addVertex(position.getX(), position.getY());
	}

}
