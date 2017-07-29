
package com.jfixby.r3.fokker.io.assets.index;

import java.io.Serializable;
import java.util.Date;

import com.jfixby.scarabei.api.log.L;

public class AssetsInfo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 8157876423081828011L;
	String packed;
	String version = "0";

	public static final String FILE_NAME = "assets.info";

	public void next () {
		this.packed = new Date() + "";
		long current = Long.parseLong(this.version);
		current++;
		this.version = current + "";
	}

	public void print () {
		L.d("------[Assets Info]----------------------------------------------------------------------");
		L.d("          version - " + this.version);
		L.d("           packed - " + this.packed);
		// L.d("-----------------------------------------------------------------------------------------");
		L.d();
	}

	@Override
	public String toString () {
		return "AssetsInfo: {" + this.packed + "} version=" + this.version + "";
	}

}
