
package com.jfixby.r3.activity.red.scene;

import com.jfixby.r3.io.scene2d.LayerElement;
import com.jfixby.r3.io.scene2d.SceneStructure;
import com.jfixby.r3.rana.api.Asset;
import com.jfixby.r3.rana.api.AssetsGroup;
import com.jfixby.scarabei.api.names.ID;
import com.jfixby.scarabei.api.names.Names;

public class SceneStructureAsset implements Asset {

	private final SceneStructuresGroup master;
	private final SceneStructure structure;

	public SceneStructureAsset (final SceneStructuresGroup group, final SceneStructure structure) {
		this.master = group;
		this.structure = structure;
		if (group != null) {
			group.add(this);
		}
	}

	@Override
	public AssetsGroup getGroup () {
		return this.master;
	}

	@Override
	public ID getAssetID () {
		return Names.newID(this.structure.structure_name);
	}

	public SceneStructureAsset copy () {
		return new SceneStructureAsset(null, this.structure);
	}

	public LayerElement root () {
		return this.structure.root;
	}

	public double original_width () {
		return this.structure.original_width;
	}

	public double original_height () {
		return this.structure.original_height;
	}

	public String structure_name () {
		return this.structure.structure_name;
	}

	public SceneStructure structure () {
		return this.structure;
	}

}
