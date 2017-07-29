/*******************************************************************************
 * Copyright (c) 2013, Daniel Murphy
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 	* Redistributions of source code must retain the above copyright notice,
 * 	  this list of conditions and the following disclaimer.
 * 	* Redistributions in binary form must reproduce the above copyright notice,
 * 	  this list of conditions and the following disclaimer in the documentation
 * 	  and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
/**
 * Created at 12:11:41 PM Jan 23, 2011
 */
package org.jbox2d.f.dynamics.joints;

import org.jbox2d.f.common.Settings;
import org.jbox2d.f.common.Vector2;
import org.jbox2d.f.dynamics.Body;

/**
 * Pulley joint definition. This requires two ground anchors, two dynamic body anchor points, and a
 * pulley ratio.
 * 
 * @author Daniel Murphy
 */
public class PulleyJointDef extends JointDef {

  /**
   * The first ground anchor in world coordinates. This point never moves.
   */
  public Vector2 groundAnchorA;

  /**
   * The second ground anchor in world coordinates. This point never moves.
   */
  public Vector2 groundAnchorB;

  /**
   * The local anchor point relative to bodyA's origin.
   */
  public Vector2 localAnchorA;

  /**
   * The local anchor point relative to bodyB's origin.
   */
  public Vector2 localAnchorB;

  /**
   * The a reference length for the segment attached to bodyA.
   */
  public float lengthA;

  /**
   * The a reference length for the segment attached to bodyB.
   */
  public float lengthB;

  /**
   * The pulley ratio, used to simulate a block-and-tackle.
   */
  public float ratio;

  public PulleyJointDef() {
    super(JointType.PULLEY);
    groundAnchorA = new Vector2(-1.0f, 1.0f);
    groundAnchorB = new Vector2(1.0f, 1.0f);
    localAnchorA = new Vector2(-1.0f, 0.0f);
    localAnchorB = new Vector2(1.0f, 0.0f);
    lengthA = 0.0f;
    lengthB = 0.0f;
    ratio = 1.0f;
    collideConnected = true;
  }

  /**
   * Initialize the bodies, anchors, lengths, max lengths, and ratio using the world anchors.
   */
  public void initialize(Body b1, Body b2, Vector2 ga1, Vector2 ga2, Vector2 anchor1, Vector2 anchor2, float r) {
    bodyA = b1;
    bodyB = b2;
    groundAnchorA = ga1;
    groundAnchorB = ga2;
    localAnchorA = bodyA.getLocalPoint(anchor1);
    localAnchorB = bodyB.getLocalPoint(anchor2);
    Vector2 d1 = anchor1.sub(ga1);
    lengthA = d1.length();
    Vector2 d2 = anchor2.sub(ga2);
    lengthB = d2.length();
    ratio = r;
    assert (ratio > Settings.EPSILON);
  }
}
