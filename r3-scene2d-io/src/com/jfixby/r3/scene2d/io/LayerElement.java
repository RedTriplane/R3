
package com.jfixby.r3.scene2d.io;

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
	public Boolean is_raster = null;
	public Boolean is_sublayer = null;
	public Boolean is_animation = null;
	public Boolean is_hidden = null;
	public Boolean is_child_scene = null;
	public Boolean is_9_patch = null;
	public Boolean is_user_input = null;
	public Boolean is_text = null;
	public Boolean is_shader = null;
	public Boolean is_parallax = null;
	public Boolean is_material_design = null;

	public Boolean debug_mode = null;

	public String name;
	public String raster_id;
	public String animation_id;
	public String event_id;
	public String textbar_id;
	public String shader_id;
	public RASTER_BLEND_MODE blend_mode = null;

	public Double opacity = 1d;
	public Double position_x;
	public Double position_y;

	public Double offset_x;
	public Double offset_y;

	public Double origin_relative_x;
	public Double origin_relative_y;

	public Double rotation;

	public Double width;
	public Double height;

	public LayerChildren children = new LayerChildren();
	public AnimationSettings animation_settings = null;
	public ChildSceneSettings child_scene_settings = null;
	public InputSettings input_settings = null;
	public CameraSettings camera_settings = null;
	public TextSettings text_settings = null;
	public ShaderSettings shader_settings = null;
	public ProgressSettings progress_settings = null;
	public ParallaxSettings parallax_settings = null;
	public NinePatchSettings nine_patch_settings = null;
	public MaterialDesignSettings material_design_settings = null;

}
