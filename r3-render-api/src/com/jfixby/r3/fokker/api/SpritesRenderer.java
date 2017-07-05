
package com.jfixby.r3.fokker.api;

public class SpritesRenderer {

	final static public int spriteVertex (final int sprite_number, final int vertice_name) {
		return vertice_name + sprite_number * SPRITE_SIZE;
	}

	public final static int A_x = 0;
	public final static int A_y = 1;
	public final static int B_x = 15;
	public final static int B_y = 16;
	public final static int C_x = 10;
	public final static int C_y = 11;
	public final static int D_x = 5;
	public final static int D_y = 6;

	final public static float round (final double x) {
		return (float)x;
	}

	public static final int VERTEX_SIZE = 2 + 1 + 2;
	public static final int SPRITE_SIZE = 4 * VERTEX_SIZE;

}
