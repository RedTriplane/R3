
package com.jfixby.r3.activity.red.raster;

import com.jfixby.r3.io.texture.slicer.SlicesCompositionInfo;
import com.jfixby.r3.rana.api.Asset;
import com.jfixby.r3.rana.api.AssetsGroup;
import com.jfixby.scarabei.api.json.Json;
import com.jfixby.scarabei.api.names.ID;
import com.jfixby.scarabei.api.names.Names;

public class TileSet implements Asset {

	private final ID asset_id;
	private final SlicesCompositionInfo structure;
	private final TileSetGroup master;

	public TileSet (final TileSetGroup comp, final SlicesCompositionInfo structure) {
		this.structure = structure;
		this.master = comp;
		if (this.master != null) {
			this.master.add(this);
		}
		this.asset_id = Names.newID(structure.getAssetID());
	}

	@Override
	public ID getAssetID () {
		return this.asset_id;
	}

	public TileSet copy () {
		final SlicesCompositionInfo composition = Json.deserializeFromString(SlicesCompositionInfo.class,
			Json.serializeToString(this.structure));
		return new TileSet(null, composition);
	}

	public SlicesCompositionInfo getStructure () {
		return this.structure;
	}

	public int size () {
		return this.structure.tiles.size();
	}

	@Override
	public AssetsGroup getGroup () {
		return this.master;
	}

}
