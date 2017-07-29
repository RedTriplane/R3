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

import java.io.Serializable;

/**
 * A 2D column vector
 */
public class Vector2 implements Serializable {
	private static final long serialVersionUID = 1L;

	public double x, y;

	public Vector2() {
		this(0, 0);
	}

	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2(Vector2 toCopy) {
		this(toCopy.x, toCopy.y);
	}

	/** Zero out this vector. */
	public final void setZero() {
		x = 0.0f;
		y = 0.0f;
	}

	/** Set the vector component-wise. */
	public final Vector2 set(double x, double y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/** Set this vector to another vector. */
	public final Vector2 set(Vector2 v) {
		this.x = v.x;
		this.y = v.y;
		return this;
	}

	/** Return the sum of this vector and another; does not alter either one. */
	public final Vector2 add(Vector2 v) {
		return new Vector2(x + v.x, y + v.y);
	}

	/**
	 * Return the difference of this vector and another; does not alter either
	 * one.
	 */
	public final Vector2 sub(Vector2 v) {
		return new Vector2(x - v.x, y - v.y);
	}

	/** Return this vector multiplied by a scalar; does not alter this vector. */
	public final Vector2 mul(double a) {
		return new Vector2(x * a, y * a);
	}

	/** Return the negation of this vector; does not alter this vector. */
	public final Vector2 negate() {
		return new Vector2(-x, -y);
	}

	/** Flip the vector and return it - alters this vector. */
	public final Vector2 negateLocal() {
		x = -x;
		y = -y;
		return this;
	}

	/** Add another vector to this one and returns result - alters this vector. */
	public final Vector2 addLocal(Vector2 v) {
		x += v.x;
		y += v.y;
		return this;
	}

	/** Adds values to this vector and returns result - alters this vector. */
	public final Vector2 addLocal(double x, double y) {
		this.x += x;
		this.y += y;
		return this;
	}

	/**
	 * Subtract another vector from this one and return result - alters this
	 * vector.
	 */
	public final Vector2 subLocal(Vector2 v) {
		x -= v.x;
		y -= v.y;
		return this;
	}

	/** Multiply this vector by a number and return result - alters this vector. */
	public final Vector2 mulLocal(double a) {
		x *= a;
		y *= a;
		return this;
	}

	/** Get the skew vector such that dot(skew_vec, other) == cross(vec, other) */
	public final Vector2 skew() {
		return new Vector2(-y, x);
	}

	/** Get the skew vector such that dot(skew_vec, other) == cross(vec, other) */
	public final void skew(Vector2 out) {
		out.x = -y;
		out.y = x;
	}

	/** Return the length of this vector. */
	public final double length() {
		return MathUtils.sqrt(x * x + y * y);
	}

	/** Return the squared length of this vector. */
	public final double lengthSquared() {
		return (x * x + y * y);
	}

	/**
	 * Normalize this vector and return the length before normalization. Alters
	 * this vector.
	 */
	public final double normalize() {
		double length = length();
		if (length < Settings.EPSILON) {
			return 0f;
		}

		double invLength = 1.0f / length;
		x *= invLength;
		y *= invLength;
		return length;
	}

	/**
	 * True if the vector represents a pair of valid, non-infinite doubleing
	 * point numbers.
	 */
	public final boolean isValid() {
		return !Double.isNaN(x) && !Double.isInfinite(x) && !Double.isNaN(y)
				&& !Double.isInfinite(y);
	}

	/** Return a new vector that has positive components. */
	public final Vector2 abs() {
		return new Vector2(MathUtils.abs(x), MathUtils.abs(y));
	}

	public final void absLocal() {
		x = MathUtils.abs(x);
		y = MathUtils.abs(y);
	}

	// @Override // annotation omitted for GWT-compatibility
	/** Return a copy of this vector. */
	public final Vector2 clone() {
		return new Vector2(x, y);
	}

	@Override
	public final String toString() {
		return "(" + x + "," + y + ")";
	}

	/*
	 * Static
	 */

	public final static Vector2 abs(Vector2 a) {
		return new Vector2(MathUtils.abs(a.x), MathUtils.abs(a.y));
	}

	public final static void absToOut(Vector2 a, Vector2 out) {
		out.x = MathUtils.abs(a.x);
		out.y = MathUtils.abs(a.y);
	}

	public final static double dot(final Vector2 a, final Vector2 b) {
		return a.x * b.x + a.y * b.y;
	}

	public final static double cross(final Vector2 a, final Vector2 b) {
		return a.x * b.y - a.y * b.x;
	}

	public final static Vector2 cross(Vector2 a, double s) {
		return new Vector2(s * a.y, -s * a.x);
	}

	public final static void crossToOut(Vector2 a, double s, Vector2 out) {
		final double tempy = -s * a.x;
		out.x = s * a.y;
		out.y = tempy;
	}

	public final static void crossToOutUnsafe(Vector2 a, double s, Vector2 out) {
		assert (out != a);
		out.x = s * a.y;
		out.y = -s * a.x;
	}

	public final static Vector2 cross(double s, Vector2 a) {
		return new Vector2(-s * a.y, s * a.x);
	}

	public final static void crossToOut(double s, Vector2 a, Vector2 out) {
		final double tempY = s * a.x;
		out.x = -s * a.y;
		out.y = tempY;
	}

	public final static void crossToOutUnsafe(double s, Vector2 a, Vector2 out) {
		assert (out != a);
		out.x = -s * a.y;
		out.y = s * a.x;
	}

	public final static void negateToOut(Vector2 a, Vector2 out) {
		out.x = -a.x;
		out.y = -a.y;
	}

	public final static Vector2 min(Vector2 a, Vector2 b) {
		return new Vector2(a.x < b.x ? a.x : b.x, a.y < b.y ? a.y : b.y);
	}

	public final static Vector2 max(Vector2 a, Vector2 b) {
		return new Vector2(a.x > b.x ? a.x : b.x, a.y > b.y ? a.y : b.y);
	}

	public final static void minToOut(Vector2 a, Vector2 b, Vector2 out) {
		out.x = a.x < b.x ? a.x : b.x;
		out.y = a.y < b.y ? a.y : b.y;
	}

	public final static void maxToOut(Vector2 a, Vector2 b, Vector2 out) {
		out.x = a.x > b.x ? a.x : b.x;
		out.y = a.y > b.y ? a.y : b.y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Vector2 other = (Vector2) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

}
