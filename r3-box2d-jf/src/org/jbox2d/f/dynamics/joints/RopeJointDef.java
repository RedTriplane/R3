package org.jbox2d.f.dynamics.joints;

import org.jbox2d.f.common.Vector2;

/**
 * Rope joint definition. This requires two body anchor points and a maximum lengths. Note: by
 * default the connected objects will not collide. see collideConnected in b2JointDef.
 * 
 * @author Daniel Murphy
 */
public class RopeJointDef extends JointDef {

  /**
   * The local anchor point relative to bodyA's origin.
   */
  public final Vector2 localAnchorA = new Vector2();

  /**
   * The local anchor point relative to bodyB's origin.
   */
  public final Vector2 localAnchorB = new Vector2();

  /**
   * The maximum length of the rope. Warning: this must be larger than b2_linearSlop or the joint
   * will have no effect.
   */
  public float maxLength;

  public RopeJointDef() {
    super(JointType.ROPE);
    localAnchorA.set(-1.0f, 0.0f);
    localAnchorB.set(1.0f, 0.0f);
  }
}
