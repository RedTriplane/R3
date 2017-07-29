
package com.jfixby.r3.physics;

import com.jfixby.r3.api.physics.BodyDebugInfo;

public class DebugProperties implements BodyDebugInfo {
	private static final String ANONYMOUS = "anonymous";
	String name = ANONYMOUS;

	public String getName () {
		return name;
	}

	public void setName (final String name) {
		if (name == null) {
			this.name = ANONYMOUS;
		}
		this.name = name;
	}

// public String getDebugName() {
// return avatarProperties.getShape() + "["
// + this.simple_master.debugProperties.getName() + "]";
// }
}
