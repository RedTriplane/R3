package org.box2d.r3.api;

public interface ContactFilter {
	boolean shouldCollide(Fixture fixtureA, Fixture fixtureB);

}
