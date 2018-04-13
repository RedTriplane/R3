
package com.jfixby.r3.activity.red.scene;

import com.jfixby.r3.activity.api.ComponentsFactory;
import com.jfixby.r3.activity.api.LayerBasedComponent;
import com.jfixby.r3.activity.api.animation.Animation;
import com.jfixby.r3.activity.api.camera.CanvasCamera;
import com.jfixby.r3.activity.api.input.InputComponent;
import com.jfixby.r3.activity.api.layer.Component;
import com.jfixby.r3.activity.api.layer.Layer;
import com.jfixby.r3.activity.api.locale.LocalizedComponent;
import com.jfixby.r3.activity.api.parallax.Parallax;
import com.jfixby.r3.activity.api.raster.CanvasComponent;
import com.jfixby.r3.activity.api.raster.Raster;
import com.jfixby.r3.activity.api.scene.Scene2DComponent;
import com.jfixby.r3.activity.api.txt.TextBar;
import com.jfixby.r3.activity.api.txt.TextBarSpecs;
import com.jfixby.r3.activity.api.txt.TextFactory;
import com.jfixby.r3.activity.api.ui.ninepatch.NinePatch;
import com.jfixby.r3.io.scene2d.CameraSettings;
import com.jfixby.r3.io.scene2d.LayerElement;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.CollectionFilter;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.collections.Mapping;
import com.jfixby.scarabei.api.color.Colors;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.names.ID;
import com.jfixby.scarabei.api.sys.Sys;
import com.jfixby.scarabei.api.util.Utils;

public class RedScene implements Scene2DComponent, LayerBasedComponent {

	private static final String PREFIX = "                    ";
	Layer root;
	// private List<Raster> all_rasters = JUtils.newList();
	final List<CanvasComponent> canvas_components = Collections.newList();
	final List<Animation> animations = Collections.newList();
	final List<Raster> rasters = Collections.newList();
	final List<Scene2DComponent> child_scenes = Collections.newList();
	final List<TextBar> text_fields = Collections.newList();

// private final List<ShaderComponent> shaders = Collections.newList();
	final List<LocalizedComponent> localized_components = Collections.newList();
	final List<Parallax> parallaxes = Collections.newList();
	final List<NinePatch> ninepatches = Collections.newList();
	final Map<String, InputComponent> inputs_components = Collections.newMap();
	final List<Layer> layers_list = Collections.newList();
	private SceneStructureAsset original_structure;
	final Float2 originalDimentions = Geometry.newFloat2();
	double opacity = 1d;

	private final RedSceneConstructor constructor = new RedSceneConstructor();

	public RedScene () {

	}

	@Override
	public void dispose () {
		final ComponentsFactory factory = this.root.getComponentsFactory();
// factory.getRasterDepartment().newRaster(asset_id)
		this.root.detatchAllComponents();
	}

	@Override
	public ReadOnlyFloat2 getOriginalDimentions () {
		return this.originalDimentions;
	}

	@Override
	public String toString () {
		if (this.original_structure != null) {
			return "RedScene[" + this.original_structure.getAssetID() + "]";
		} else {
			return "RedScene";
		}
	}

	@Override
	public Layer getRoot () {
		if (this.root == null) {
			Err.reportError("Root is null. Did you deploy this scene?");
		}
		return this.root;
	}

	private List<CanvasComponent> listAllCanvasComponents () {
		return this.canvas_components;
	}

	@Override
	public void print () {
		L.d("---Scene[" + this.original_structure.structure_name() + "]------------");
		this.root.print();
		// for (Component layer : root.listChildren()) {
		// ((Layer) layer).print();
		// }
		L.d("---Scene", "END------------");

	}

	public void deployScene (final ComponentsFactory components_factory, final SceneStructureAsset structure,
		final Settings settings) {
		this.original_structure = structure.copy();

		this.originalDimentions.setXY(this.original_structure.original_width(), this.original_structure.original_height());

		this.root = components_factory.newLayer();
		final LayerElement frame_element = this.original_structure.root();
		if (Utils.equalObjects(frame_element.is_animation, Boolean.TRUE)) {
			Err.reportError("Broken structure: root.is_animation == true");
		}
		if (Utils.equalObjects(frame_element.is_raster, Boolean.TRUE)) {
			Err.reportError("Broken structure: root.is_raster == true");
		}
		if (Utils.equalObjects(frame_element.is_user_input, Boolean.TRUE)) {
			Err.reportError("Broken structure: root.is_user_input == true");
		}

		this.deployScene("root", components_factory, frame_element, this.root, settings);

	}

	public void deployScene (final String scene_name, final ComponentsFactory components_factory, final LayerElement root_element,
		final Layer root, final Settings settings) {

		// root = (Layer)

		final CameraSettings camera_settings = root_element.camera_settings;
		if (camera_settings != null) {
			this.constructor.restoreCamera(camera_settings, components_factory, root);
		}
		// if (root_element.shader_settings != null) {
		// Shader shader = restoreShader(root_element, components_factory);
		// root.setShader(shader);
		// }

// settings.debug_opacity = 1;

		for (int i = 0; i < root_element.children.size(); i++) {
			final SceneStructureAsset structure = settings.getStructure();
			final LayerElement child = root_element.children.elementAt(i, structure.structure());

// final wrong structure
			final Component vc = this.constructor.restore(components_factory, child, settings);
			root.attachComponent(vc);
		}

		root.setName(scene_name);
		// root.closeInputValve();

		if (settings.render_debug_info) {
			this.setupDebugInfo(root, components_factory, camera_settings, settings);
		}

	}

	private void setupDebugInfo (final Layer root_layer, final ComponentsFactory components_factory,
		final CameraSettings camera_settings, final Settings settings) {

		final TextFactory textFactory = components_factory.getTextDepartment();
		final TextBarSpecs string_specs = textFactory.newTextBarSpecs();
// final RasterizedFontSpecs string_specs = textFactory.newFontSpecs();
// SystemSettings.getStringParameter(EngineParams.Assets.ASSET_INFO_TAG);
		String param = PREFIX;
		if (param == null) {
// SystemSettings.printSystemParameters();
			Sys.exit();
		}
		if (camera_settings != null) {
			param = param + "\n" + PREFIX + " Camera[" + camera_settings.width + " x " + camera_settings.height + "]";
			string_specs.fontSize = ((float)(1f * camera_settings.height * 1f / 25f));
		} else {
			string_specs.fontSize = (10);
		}
		{
			param = param + "\n" + PREFIX + " scene_name=" + settings.scene_name;
		}

		string_specs.borderSize = (1);
		string_specs.borderColor = (Colors.BLACK());
		string_specs.fontColor = (Colors.WHITE());

// final RasterizedFont font = textFactory.newFont(string_specs);
// string_specs.setFont(font);

		final TextBar string = textFactory.newTextBar(string_specs);
// string.setText(param);
// string.getText();
		L.e("setupDebugInfo", param);
		root_layer.attachComponent(string);
	}

// @Override
// public void hide () {
// this.root.hide();
// }
//
// @Override
// public void show () {
// this.root.show();
// }
//
// @Override
// public boolean isVisible () {
// return this.root.isVisible();
// }

	@Override
	public void setName (final String name) {
		this.root.setName(name);
	}

	@Override
	public String getName () {
		return this.root.getName();
	}

	@Override
	public Collection<Animation> listAnimations () {
		return this.animations;
	}

	@Override
	public Mapping<String, InputComponent> listInputComponents () {
		return this.inputs_components;
	}

	@Override
	public CanvasCamera getCamera () {
		return this.root.getCamera();
	}

	@Override
	public ComponentsFactory getComponentsFactory () {
		return this.root.getComponentsFactory();
	}

	@Override
	public ID getAssetID () {
		return this.original_structure.getAssetID();
	}

	@Override
	public Collection<Raster> listRaster () {
		return this.rasters;
	}

	@Override
	public Collection<LocalizedComponent> listLocalizedComponents () {
		return this.localized_components;
	}

	@Override
	public Collection<TextBar> listTextBars () {
		return this.text_fields;
	}

// @Override
// public Collection<ShaderComponent> listShaders () {
// return this.shaders;
// }

	@Override
	public Collection<Layer> findLayer (final String layerName) {
		return this.layers_list.filter(new CollectionFilter<Layer>() {
			@Override
			public boolean fits (final Layer layer) {
				return layer.getName().equals(layerName);
			}
		});
	}

	@Override
	public void show () {
		this.root.show();
	}

	@Override
	public void hide () {
		this.root.hide();
	}

	@Override
	public Collection<Parallax> listParallaxes () {
		return this.parallaxes;
	}

	@Override
	public void startAllAnimations () {
		for (final Animation animation : this.animations) {
			animation.startAnimation();
		}
		for (final Scene2DComponent child : this.child_scenes) {
			child.startAllAnimations();
		}
	}

}
