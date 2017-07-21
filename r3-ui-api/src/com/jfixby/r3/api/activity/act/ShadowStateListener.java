
package com.jfixby.r3.api.activity.act;

public interface ShadowStateListener {

	void updateShadow (float s);

	void beginShadowing (float value_begin, float value_end);

	void endShadowing (float value_begin, float value_end);

}
