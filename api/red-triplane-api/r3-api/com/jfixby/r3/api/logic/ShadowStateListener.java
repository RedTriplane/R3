package com.jfixby.r3.api.logic;

public interface ShadowStateListener {

	void beginShadowing(float value_begin, float value_end);

	void updateShadow(float value_current);

}
