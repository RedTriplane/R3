package com.jfixby.r3.fokker.api;

public interface ViewPortState {

	int getScreenWidth();

	int getScreenHeight();

	long getLastUpdateCycleNumber();

	boolean isValid();

}
