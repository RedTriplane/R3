package com.jfixby.r3.ui.api.activity;

public interface ActivityStateInspector {

	void onCreate();

	void onResume();

	void onPause();

	void onDestroy();

	void onInit();

	void onStart();

}
