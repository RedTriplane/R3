
package com.jfixby.r3.activity.api.input;

import com.jfixby.scarabei.api.log.L;

public interface OnClickListener {

	public static final OnClickListener DEFAULT = new OnClickListener() {
		@Override
		public void onClick () {
			L.d("Click!");
		}
	};

	public void onClick ();

}
