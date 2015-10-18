package com.jfixby.r3.ext.api.scene2d.srlz;

import java.util.Vector;

public class LayerElement {

	public boolean is_raster = false;
	public boolean is_sublayer = false;
	public boolean is_animation = false;
	// public boolean is_action = false;
	public boolean is_hidden = false;
	public boolean is_child_scene = false;
	public boolean is_user_input = false;

	public boolean debug_mode = false;

	public String name;
	public String raster_id;
	public String animation_id;
	public String event_id;
	public String input_id;

	public double opacity = 1;
	public double position_x;
	public double position_y;

	public double origin_relative_x;
	public double origin_relative_y;

	public double rotation;

	public double width;
	public double height;

	public Vector<LayerElement> children = new Vector<LayerElement>();
	public AnimationSettings animation_settings = null;
	public ChildSceneSettings child_scene_settings = null;
	public InputSettings input_settings = null;
	public CameraSettings camera_settings = null;

}
