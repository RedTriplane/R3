package com.jfixby.r3.ext.font.gdx;

import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.r3.api.ui.unit.raster.Tile;
import com.jfixby.r3.ext.api.font.TextTilesSequence;

public class RedTextTilesSequence implements TextTilesSequence {
	final List<RedTextTile> tiles = Collections.newList();

	public void appendChar(char c, RedCharSettings tile_info, Tile raster) {
		RedTextTile tile = new RedTextTile(c, tile_info, raster);
		tiles.add(tile);
	}

}
