
package com.jfixby.r3.fokker.font.red;

import com.jfixby.r3.engine.api.render.SpritesRenderer;
import com.jfixby.scarabei.api.log.L;

public class VerticesCache {
	float[][] spriteVertices;
	int[] sprites;

	public void updateNumberOfRegions (final int n) {
		this.spriteVertices = new float[n][];
		this.sprites = new int[n];
	}

	public void updateRegion (final int region, final float[] spriteVertices) {
		if (this.spriteVertices[region] == null || this.spriteVertices[region].length < spriteVertices.length) {
			this.spriteVertices[region] = new float[spriteVertices.length * 2];
			this.sprites[region] = spriteVertices.length / SpritesRenderer.SPRITE_SIZE;
			L.d("LEAK");
		}
		System.arraycopy(spriteVertices, 0, this.spriteVertices[region], 0, spriteVertices.length);

	}

	public int getNumberOfSprites (final int region) {
		return this.sprites[region];
	}

	public float[] getVertices (final int region) {
		return this.spriteVertices[region];
	}

}
