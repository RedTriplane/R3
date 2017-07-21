
package com.jfixby.r3.activity.api;

public interface Activity {

	public void onCreate (final ActivityManager unitManager);

	public void onStart ();

	public void onResume ();

	public void onPause ();

	public void onDestroy ();

}
