
package com.jfixby.r3.ext.api.scene2d.srlz;

public class LayerElement implements java.io.Serializable {

	public String uid;

	LayerElement () {
	}

	LayerElement (final String id) {
		this.uid = id;
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 4233761597492511259L;
	public boolean is_raster = false;
	public boolean is_sublayer = false;
	public boolean is_animation = false;
	public boolean is_hidden = false;
	public boolean is_child_scene = false;
	public boolean is_user_input = false;
	public boolean is_text = false;
	public boolean is_shader = false;
	public boolean is_progress = false;

	public boolean debug_mode = false;

	public String name;
	public String raster_id;
	public String animation_id;
	public String event_id;
	public String input_id;
	public String textbar_id;
	public String shader_id;
	public RASTER_BLEND_MODE blend_mode = null;

	public double opacity = 1;
	public double position_x;
	public double position_y;

	public double origin_relative_x;
	public double origin_relative_y;

	public double rotation;

	public double width;
	public double height;

	public LayerChildren children = new LayerChildren();
	public AnimationSettings animation_settings = null;
	public ChildSceneSettings child_scene_settings = null;
	public InputSettings input_settings = null;
	public CameraSettings camera_settings = null;
	public TextSettings text_settings = null;
	public ShaderSettings shader_settings = null;
	public ProgressSettings progress_settings = null;

}
