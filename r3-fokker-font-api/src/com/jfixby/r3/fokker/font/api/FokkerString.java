
package com.jfixby.r3.fokker.font.api;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public interface FokkerString {

	Array<TextureRegion> getRegions ();

	int getVertexCount (int region);

	float[] getVertices (int region);

	int getNumberOfSprites (int region);

	void prepare ();

}
