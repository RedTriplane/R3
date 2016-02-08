package com.jfixby.r3.ext.font.gdx;

import com.jfixby.r3.api.ui.unit.raster.Tile;

public class RedTextTile {

	private char character;
	private RedCharSettings tile_info;
	private Tile raster;

	public RedTextTile(char c, RedCharSettings tile_info, Tile raster) {
		this.character = c;
		this.tile_info = tile_info;
		this.raster = raster;
	}

}
