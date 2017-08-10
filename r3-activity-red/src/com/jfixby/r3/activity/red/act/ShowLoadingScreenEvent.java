//
// package com.jfixby.r3.activity.red.act;
//
// import com.jfixby.r3.activity.api.Activity;
// import com.jfixby.r3.activity.api.act.ShadowStateListener;
// import com.jfixby.r3.activity.api.spawn.ActivityListener;
// import com.jfixby.r3.activity.api.spawn.ActivityMachine;
// import com.jfixby.r3.activity.api.spawn.Intent;
// import com.jfixby.scarabei.api.assets.ID;
// import com.jfixby.scarabei.api.debug.Debug;
// import com.jfixby.scarabei.api.log.L;
//
// public class ShowLoadingScreenEvent extends UIEvent {
// boolean unit_created = false;
//
// private final ActivityListener load_listener = new ActivityListener() {
// @Override
// public void onActivityStarted () {
// L.d("Activity started", ShowLoadingScreenEvent.this.tintoUIManager.getLoader().getActivity());
// }
//
// @Override
// public void onActivityCreated (final Activity unit) {
// Debug.checkNull(unit);
// ShowLoadingScreenEvent.this.tintoUIManager.getLoader().setActivity(unit);
// ShowLoadingScreenEvent.this.tintoUIManager.setCurrent(ShowLoadingScreenEvent.this.tintoUIManager.getLoader());
// ShowLoadingScreenEvent.this.unit_created = true;
//
// if (ShowLoadingScreenEvent.this.fadedOut) {
// if (unit instanceof ShadowStateListener) {
// final ShadowStateListener shadow_state_listener = (ShadowStateListener)unit;
// shadow_state_listener.updateShadow(1);
// } else {
// L.e("Activity <" + unit + " is not instance of ShadowStateListener");
// }
// }
// }
// };
//
// private final RedUIManager tintoUIManager;
//
// private final ID asset_id;
//
// private final boolean fadedOut;
//
// public ShowLoadingScreenEvent (final RedUIManager tintoUIManager, final ID asset_id, final boolean fadedOut) {
// this.tintoUIManager = tintoUIManager;
// this.asset_id = Debug.checkNull(asset_id);
// this.fadedOut = fadedOut;
// }
//
// @Override
// public void go () {
//
// final Intent intent = ActivityMachine.newIntent(this.asset_id);
//
// final ActivityListener listener = this.load_listener;
// intent.setActivityListener(listener);
// ActivityMachine.nextActivity(intent);
// }
//
// @Override
// public boolean isOver () {
// return this.unit_created;
// }
//
// }
