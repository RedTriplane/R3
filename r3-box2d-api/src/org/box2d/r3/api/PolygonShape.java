package org.box2d.r3.api;

import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.geometry.ClosedPolygonalChain;

public interface PolygonShape extends Shape {

	void setAsBox(double half_width, double half_height);

	void setVertices(Collection<Float2> vertices);

	ClosedPolygonalChain getClosedPolygonalChain();

	// ClosedPolygonalChain getClosedPolygonalChain();

	// Collection<Vertex> listVertices();

	// ClosedPolygonalChain getPoly();
}
