
package com.jfixby.r3.io.texture.slicer;

import java.io.Serializable;
import java.util.ArrayList;

public class SlicesCompositionInfo implements Serializable {

	private static final long serialVersionUID = -4138188908227530645L;

	public String composition_asset_id_string;

	public int width = -1;
	public int height = -1;

	public int cell_width = 0;
	public int cell_height = 0;
	public int cell_margin = 0;

	public ArrayList<SliceInfo> tiles = new ArrayList<SliceInfo>();

	public void addTile (final int i, final int j, final String postfix, final boolean is_empty, final double width,
		final double height) {
		final SliceInfo t = new SliceInfo();
		t.index_x = i;
		t.index_y = j;
		t.tile_width = width;
		t.tile_height = height;
		t.postfix = postfix;
		t.is_empty = is_empty;
		this.tiles.add(t);
	}

	public String getAssetID () {
		return this.composition_asset_id_string;
	}
}
