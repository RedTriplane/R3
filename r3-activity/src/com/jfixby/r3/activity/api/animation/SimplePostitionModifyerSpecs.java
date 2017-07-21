package com.jfixby.r3.activity.api.animation;

import com.jfixby.r3.activity.api.raster.CanvasComponent;
import com.jfixby.scarabei.api.math.FloatMath;

public interface SimplePostitionModifyerSpecs extends AnimationSpecs {

	void setCanvasComponent(CanvasComponent frame);

	void setBeginAnchor(PositionAnchor begin_anchor);

	void setEndAnchor(PositionAnchor end_anchor);

	void setProgressFunction(AnimationProgressFunction animationProgressFunction);

	CanvasComponent getCanvasComponent();

	PositionAnchor getBeginAnchor();

	PositionAnchor getEndAnchor();

	AnimationProgressFunction getProgressFunction();

	public static final AnimationProgressFunction IDENTITY = new AnimationProgressFunction() {
		@Override
		public double valueFor(double t) {
			return t;
		}
	};
	public static final AnimationProgressFunction SQUARE = new AnimationProgressFunction() {
		@Override
		public double valueFor(double t) {
			return t * t;
		}
	};
	public static final AnimationProgressFunction ROOT = new AnimationProgressFunction() {
		@Override
		public double valueFor(double t) {
			return FloatMath.sqrt(t);
		}
	};

}
