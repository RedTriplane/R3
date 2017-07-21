
package com.jfixby.r3.api.activity.animation;

public interface AnimationLifecycleListener {

	void onAnimationDone (Animation animation, int loopIndex);

	void onAnimationStart (Animation animation);

	void onLoop (Animation animation, int loopIndex);

}
