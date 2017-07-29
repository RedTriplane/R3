
package com.jfixby.r3.physics;

public class RedCollisionEvent {
	public enum COLLISION_EVENT_HEADER {
		BEGIN_CONTACT, END_CONTACT, LOST_CONTACT
	}

	private COLLISION_EVENT_HEADER header;
	private BodyAvatar body_a;
	private BodyAvatar body_b;

	public void dispose () {
		this.header = null;
		this.body_a = null;
		this.body_b = null;
	}

	public void setHeader (final COLLISION_EVENT_HEADER header) {
		this.header = header;
	}

	public COLLISION_EVENT_HEADER getHeader () {
		return this.header;
	}

	public void setAvatarA (final BodyAvatar red_body_a) {
		this.body_a = red_body_a;
	}

	public void setAvatarB (final BodyAvatar red_body_b) {
		this.body_b = red_body_b;
	}

	public BodyAvatar getAvatarA () {
		return this.body_a;
	}

	public BodyAvatar getAvatarB () {
		return this.body_b;
	}

	public String toDebugString () {
		return "[" + header + "] " + body_a.getDebugName() + " vs " + body_b.getDebugName() + "";
	}

}
