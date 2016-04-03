package com.jfixby.r3.ext.api.font;

import java.util.Vector;

public class TileSet implements java.io.Serializable {

    public int font_color_r;
    public int font_color_g;
    public int font_color_b;
    public int font_color_a;

    public Vector<CharRasterInfo> tiles = new Vector<CharRasterInfo>();

}
