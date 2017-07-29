package org.box2d.r3.api;

public interface QueryCallback {
	/**
	 * Called for each fixture found in the query AABB.
	 * 
	 * @return false to terminate the query.
	 */
	public boolean reportFixture(Fixture fixture);
}
