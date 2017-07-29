package org.jbox2d.d.particle;

import org.jbox2d.d.common.Vector2;
import org.jbox2d.d.dynamics.Body;

public class ParticleBodyContact {
  /** Index of the particle making contact. */
  public int index;
  /** The body making contact. */
  public Body body;
  /** Weight of the contact. A value between 0.0f and 1.0f. */
  double weight;
  /** The normalized direction from the particle to the body. */
  public final Vector2 normal = new Vector2();
  /** The effective mass used in calculating force. */
  double mass;
}
