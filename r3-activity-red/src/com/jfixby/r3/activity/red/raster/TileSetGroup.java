
package com.jfixby.r3.activity.red.raster;

import java.util.ArrayList;

import com.jfixby.r3.rana.api.AssetsGroup;

public class TileSetGroup implements AssetsGroup {

	final ArrayList<TileSet> sets = new ArrayList<TileSet>();

	@Override
	public void dispose () {
		this.sets.clear();
	}

	public void add (final TileSet tileSet) {
		this.sets.add(tileSet);
	}

}
