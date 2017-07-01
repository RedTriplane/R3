
package com.jfixby.r3.ext.scene2d.red;

import com.jfixby.r3.ext.api.scene2d.srlz.SceneStructure;
import com.jfixby.scarabei.api.assets.ID;

public class LayerStructureRegistrationEntry {

// private final PackageHandler handler;

	public LayerStructureRegistrationEntry () {
// this.handler = handler;
	}

	@Override
	public String toString () {
		return "LayerStructureRegistrationEntry [asset_id=" + this.asset_id + ", structure=" + this.structure + "]";
	}

	private ID asset_id;
	private SceneStructure structure;

	public void setAssetId (final ID asset_id) {
		this.asset_id = asset_id;
	}

	public void setStructure (final SceneStructure structure) {
		this.structure = structure;
	}

	public ID getAssetID () {
		return this.asset_id;
	}

	public SceneStructure getStructure () {
		return this.structure;
	}

// public long getPackageTimeStamp () {
// return this.handler.getVersion().getTimeStamp();
// }
//
// public long readPackageTimeStamp () {
// return this.handler.getVersion().reReadTimeStamp();
// }

}
