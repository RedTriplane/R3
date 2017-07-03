
package com.jfixby.r3.uiact;

public interface ShadowStateListener {

	void updateShadow (float s);

	void beginShadowing (float value_begin, float value_end);

	void endShadowing (float value_begin, float value_end);

}
