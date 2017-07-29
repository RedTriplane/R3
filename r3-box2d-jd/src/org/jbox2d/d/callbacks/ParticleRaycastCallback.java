package org.jbox2d.d.callbacks;

import org.jbox2d.d.common.Vector2;

public interface ParticleRaycastCallback {
  /**
   * Called for each particle found in the query. See
   * {@link RayCastCallback#reportFixture(org.jbox2d.dynamics.Fixture, Vector2, Vector2, double)} for
   * argument info.
   * 
   * @param index
   * @param point
   * @param normal
   * @param fraction
   * @return
   */
  double reportParticle(int index, Vector2 point, Vector2 normal, double fraction);

}
