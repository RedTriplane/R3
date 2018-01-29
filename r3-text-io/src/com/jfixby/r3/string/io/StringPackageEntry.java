
package com.jfixby.r3.string.io;

public class StringPackageEntry implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -684725662464182053L;
	public String id = null;
	public String string_data = null;

	@Override
	public String toString () {
		return "StringEntry [string_id=" + this.id + ", string_data=" + this.string_data + "]";
	}

}
