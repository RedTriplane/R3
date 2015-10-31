package com.jfixby.r3.api.ui.unit;


public abstract class Unit {

	// private UnitStateInspector state_inspector;

	public void onCreate(UnitManager unitManager) {
		// state_inspector.onCreate();
	};

	public final void onInit() {
		// UnitManager unitManager,
		// UnitStateInspector state_inspector
		// JUtils.checkNull("state_inspector", state_inspector);
		// this.state_inspector = state_inspector;
		// this.state_inspector.onInit();
		// this.onCreate(unitManager);
	};

	public void onStart() {
		// state_inspector.onStart();
	};

	public void onResume() {
		// state_inspector.onResume();
	};

	public void onPause() {
		// state_inspector.onPause();
	};

	public void onDestroy() {
		// state_inspector.onDestroy();
	};

}
