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
package org.jbox2d.d.common;

import com.jfixby.scarabei.api.math.FloatMath;

/**
 * Global tuning constants based on MKS units and various integer maximums
 * (vertices per shape, pairs, etc.).
 */
public class Settings {

	public static final double SCALABILITY = 1.5;

	public static final double LEGACY_EPSILON = 1.1920928955078125E-7d;
	public static final double EPSILON = FloatMath.power(LEGACY_EPSILON,
			SCALABILITY);

	public static final double G = 1;
	// public static final double SCALABILITY = 0 + 1 *
	// FloatMath.FLOAT_EPSILON()
	// / FloatMath.DOUBLE_EPSILON();

	/** A "close to zero" double epsilon value for use */
	// public static final double EPSILON = 1.1920928955078125E-7d /
	// SCALABILITY;
	// public static final double EPSILON = FloatMath.EPSILON();

	// public static final double SINCOS_LUT_PRECISION = .00011d / SCALABILITY;
	// public static final float SINCOS_LUT_PRECISION = .00011f;
	// public static final double SINCOS_LUT_PRECISION = FloatMath.EPSILON();

	/**
	 * A small length used as a collision and constraint tolerance. Usually it
	 * is chosen to be numerically significant, but visually insignificant.
	 */
	// public static double linearSlop = legacylinearSlop;
	public static double linearSlop_legacy = FloatMath.power(0.005f, SCALABILITY);
	public static double linearSlop_collision = linearSlop_legacy;
	public static double linearSlop_constraint = linearSlop_legacy;

	public static double k_errorTol = FloatMath.power(1e-3f, SCALABILITY);;

	/**
	 * The radius of the polygon/edge shape skin. This should not be modified.
	 * Making this smaller means polygons will have and insufficient for
	 * continuous collision. Making it larger may create artifacts for vertex
	 * collision.
	 */
	public static double polygonRadius_collision = (2.0f * linearSlop_collision);
	public static double polygonRadius_constraint = (2.0f * linearSlop_constraint);
	public static double polygonRadius_legacy = (2.0f * linearSlop_legacy);

	/** Pi. */
	public static final double PI = Math.PI;

	// JBox2D specific settings
	public static int CONTACT_STACK_INIT_SIZE = 100;

	// Collision

	/**
	 * The maximum number of contact points between two convex shapes.
	 */
	public static int maxManifoldPoints = 2;

	/**
	 * The maximum number of vertices on a convex polygon.
	 */

	public static int maxPolygonVertices = 8;

	/**
	 * This is used to fatten AABBs in the dynamic tree. This allows proxies to
	 * move by a small amount without triggering a tree adjustment. This is in
	 * meters.
	 */
	public static double aabbExtension = 0.1f;

	/**
	 * This is used to fatten AABBs in the dynamic tree. This is used to predict
	 * the future position based on the current displacement. This is a
	 * dimensionless multiplier.
	 */
	public static double aabbMultiplier = 2.0f;

	/**
	 * A small angle used as a collision and constraint tolerance. Usually it is
	 * chosen to be numerically significant, but visually insignificant.
	 */
	public static double angularSlop = (2.0f / 180.0f * PI) / G;

	/**
	 * Maximum number of sub-steps per contact in continuous physics simulation.
	 */
	public static int maxSubSteps = 8;

	// Dynamics

	/**
	 * Maximum number of contacts to be handled to solve a TOI island.
	 */
	public static int maxTOIContacts = 32;

	/**
	 * A velocity threshold for elastic collisions. Any collision with a
	 * relative linear velocity below this threshold will be treated as
	 * inelastic.
	 */
	public static double velocityThreshold = 1.0f;

	/**
	 * The maximum linear position correction used when solving constraints.
	 * This helps to prevent overshoot.
	 */
	public static double maxLinearCorrection = 0.2f;

	/**
	 * The maximum angular position correction used when solving constraints.
	 * This helps to prevent overshoot.
	 */
	public static double maxAngularCorrection = (8.0f / 180.0f * PI);

	/**
	 * The maximum linear velocity of a body. This limit is very large and is
	 * used to prevent numerical problems. You shouldn't need to adjust this.
	 */
	public static double maxTranslation = 2.0f;
	public static double maxTranslationSquared = (maxTranslation * maxTranslation);

	/**
	 * The maximum angular velocity of a body. This limit is very large and is
	 * used to prevent numerical problems. You shouldn't need to adjust this.
	 */
	public static double maxRotation = (0.5f * PI);
	public static double maxRotationSquared = (maxRotation * maxRotation);

	/**
	 * This scale factor controls how fast overlap is resolved. Ideally this
	 * would be 1 so that overlap is removed in one time step. However using
	 * values close to 1 often lead to overshoot.
	 */
	public static double baumgarte = 0.2f;
	public static double toiBaugarte = 0.75f;

	// Sleep

	/**
	 * The time that a body must be still before it will go to sleep.
	 */
	public static double timeToSleep = 0.5f;

	/**
	 * A body cannot sleep if its linear velocity is above this tolerance.
	 */
	public static double linearSleepTolerance = 0.01f;

	/**
	 * A body cannot sleep if its angular velocity is above this tolerance.
	 */
	public static double angularSleepTolerance = (2.0f / 180.0f * PI);

	// Particle--------------------------------------------------------

	/**
	 * A symbolic constant that stands for particle allocation error.
	 */
	public static final int invalidParticleIndex = (-1);

	/**
	 * The standard distance between particles, divided by the particle radius.
	 */
	public static final double particleStride = 0.75f;

	/**
	 * The minimum particle weight that produces pressure.
	 */
	public static final double minParticleWeight = 1.0f;

	/**
	 * The upper limit for particle weight used in pressure calculation.
	 */
	public static final double maxParticleWeight = 5.0f;

	/**
	 * The maximum distance between particles in a triad, divided by the
	 * particle radius.
	 */
	public static final int maxTriadDistance = 2;
	public static final int maxTriadDistanceSquared = (maxTriadDistance * maxTriadDistance);

	/**
	 * The initial size of particle data buffers.
	 */
	public static final int minParticleBufferCapacity = 256;

	/**
	 * Friction mixing law. Feel free to customize this. TODO djm: add
	 * customization
	 * 
	 * @param friction1
	 * @param friction2
	 * @return
	 */
	public static double mixFriction(double friction1, double friction2) {
		return MathUtils.sqrt(friction1 * friction2);
	}

	/**
	 * Restitution mixing law. Feel free to customize this. TODO djm: add
	 * customization
	 * 
	 * @param restitution1
	 * @param restitution2
	 * @return
	 */
	public static double mixRestitution(double restitution1, double restitution2) {
		return restitution1 > restitution2 ? restitution1 : restitution2;
	}
}
