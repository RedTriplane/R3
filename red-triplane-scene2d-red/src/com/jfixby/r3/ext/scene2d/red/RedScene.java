
package com.jfixby.r3.ext.scene2d.red;

import com.jfixby.r3.api.render.BLEND_MODE;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.LayerBasedComponent;
import com.jfixby.r3.api.ui.unit.animation.Animation;
import com.jfixby.r3.api.ui.unit.animation.AnimationFactory;
import com.jfixby.r3.api.ui.unit.animation.EventsGroupSpecs;
import com.jfixby.r3.api.ui.unit.animation.FrameAnimationSpecs;
import com.jfixby.r3.api.ui.unit.animation.LayersAnimation;
import com.jfixby.r3.api.ui.unit.animation.LayersAnimationSpecs;
import com.jfixby.r3.api.ui.unit.animation.PositionAnchor;
import com.jfixby.r3.api.ui.unit.animation.PositionsSequence;
import com.jfixby.r3.api.ui.unit.animation.PositionsSequenceSpecs;
import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.camera.CameraFactory;
import com.jfixby.r3.api.ui.unit.camera.CameraSpecs;
import com.jfixby.r3.api.ui.unit.camera.SIMPLE_CAMERA_POLICY;
import com.jfixby.r3.api.ui.unit.input.Button;
import com.jfixby.r3.api.ui.unit.input.ButtonSpecs;
import com.jfixby.r3.api.ui.unit.input.CustomInput;
import com.jfixby.r3.api.ui.unit.input.CustomInputSpecs;
import com.jfixby.r3.api.ui.unit.input.InputComponent;
import com.jfixby.r3.api.ui.unit.input.InputSpecs;
import com.jfixby.r3.api.ui.unit.input.TouchArea;
import com.jfixby.r3.api.ui.unit.input.TouchAreaSpecs;
import com.jfixby.r3.api.ui.unit.input.UserInputFactory;
import com.jfixby.r3.api.ui.unit.layer.Component;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.layer.VisibleComponent;
import com.jfixby.r3.api.ui.unit.locale.LocalizedComponent;
import com.jfixby.r3.api.ui.unit.parallax.Parallax;
import com.jfixby.r3.api.ui.unit.parallax.ParallaxElementSpecs;
import com.jfixby.r3.api.ui.unit.parallax.ParallaxFactory;
import com.jfixby.r3.api.ui.unit.parallax.ParallaxSpecs;
import com.jfixby.r3.api.ui.unit.raster.CanvasComponent;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.r3.api.ui.unit.shader.ShaderComponent;
import com.jfixby.r3.api.ui.unit.shader.ShaderFactory;
import com.jfixby.r3.api.ui.unit.shader.ShaderSpecs;
import com.jfixby.r3.api.ui.unit.txt.RasterizedString;
import com.jfixby.r3.api.ui.unit.txt.RasterizedStringSpecs;
import com.jfixby.r3.api.ui.unit.txt.TextBar;
import com.jfixby.r3.api.ui.unit.txt.TextBarSpecs;
import com.jfixby.r3.api.ui.unit.txt.TextFactory;
import com.jfixby.r3.ext.api.scene2d.srlz.Action;
import com.jfixby.r3.ext.api.scene2d.srlz.ActionsGroup;
import com.jfixby.r3.ext.api.scene2d.srlz.Anchor;
import com.jfixby.r3.ext.api.scene2d.srlz.CameraSettings;
import com.jfixby.r3.ext.api.scene2d.srlz.CameraSettings.MODE;
import com.jfixby.r3.ext.api.scene2d.srlz.LayerElement;
import com.jfixby.r3.ext.api.scene2d.srlz.ParallaxSettings;
import com.jfixby.r3.ext.api.scene2d.srlz.RASTER_BLEND_MODE;
import com.jfixby.r3.ext.api.scene2d.srlz.ShaderParameterType;
import com.jfixby.r3.ext.api.scene2d.srlz.ShaderParameterValue;
import com.jfixby.r3.ext.api.scene2d.srlz.ShaderSettings;
import com.jfixby.r3.ext.scene2d.api.Scene2DComponent;
import com.jfixby.rana.api.asset.AssetHandler;
import com.jfixby.rana.api.asset.AssetsConsumer;
import com.jfixby.rana.api.asset.LoadedAssets;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.assets.Names;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.CollectionFilter;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.collections.Mapping;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.color.Colors;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.projections.OffsetProjection;
import com.jfixby.scarabei.api.geometry.projections.RotateAndOffsetProjection;
import com.jfixby.scarabei.api.json.Json;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.sys.Sys;
import com.jfixby.scarabei.api.sys.settings.SystemSettings;
import com.jfixby.strings.api.Text;

public class RedScene implements Scene2DComponent, LayerBasedComponent {

	private static final String PREFIX = "                    ";
	private Layer root;
	// private List<Raster> all_rasters = JUtils.newList();
	private final List<CanvasComponent> canvas_components = Collections.newList();
	private final List<Animation> animations = Collections.newList();
	private final List<Raster> rasters = Collections.newList();
	private final List<Scene2DComponent> child_scenes = Collections.newList();
	private final List<TextBar> text_fields = Collections.newList();

	private final List<ShaderComponent> shaders = Collections.newList();
	private final List<LocalizedComponent> localized_components = Collections.newList();
	private final List<Parallax> parallaxes = Collections.newList();
	private final Map<String, InputComponent> inputs_components = Collections.newMap();
	private final List<Layer> layers_list = Collections.newList();
	private SceneStructureAsset original_structure;
	final Float2 originalDimentions = Geometry.newFloat2();
	double opacity = 1d;

	public RedScene () {

	}

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

	static private <T extends Component> T restore (final ComponentsFactory components_factory, final LayerElement element,
		final Settings settings) {
		settings.push(element.name);
		Component component = null;
		final RedScene currentScene = settings.currentScene;
		if (element.is_sublayer) {
			final Layer new_layer = components_factory.newLayer();
			component = new_layer;
			new_layer.setName(element.name);

			currentScene.layers_list.add(new_layer);
			currentScene.restoreSubLayer(new_layer, element, settings);
		} else if (element.is_raster) {
			final ID asset_id = Names.newID(element.raster_id);
			final Raster raster = components_factory.getRasterDepartment().newRaster(asset_id);
			component = raster;
			raster.setName(element.name);
			final float opacity = (float)(element.opacity * settings.debug_opacity);
			raster.setOpacity(opacity);
// L.d(raster + "", opacity);
// raster.setDebugColor(Colors.newRandomColor(0));
			raster.setWidth(element.width);
			raster.setHeight(element.height);
			raster.setOriginRelative(element.origin_relative_x, element.origin_relative_y);
			raster.setPosition(element.position_x, element.position_y);
			raster.setBlendMode(blend(element.blend_mode));
			raster.setDebugRenderFlag(element.debug_mode);
			raster.setDebugRenderFlag(!true || settings.debug_raster);

			// raster.setRotation(element.rotation - Angles.g30().toRadians());
			raster.setRotation(element.rotation);
			// raster.setSize(100, 100);
			currentScene.rasters.add(raster);
			currentScene.canvas_components.add(raster);

		} else if (element.is_animation) {

			final ID animation_id = Names.newID(element.animation_id);

			final Animation animation = currentScene.restoreAnimation(element, components_factory, animation_id, settings);
			component = animation;

			animation.setDebugRenderFlag(element.debug_mode);

			currentScene.animations.add(animation);

		} else if (element.is_child_scene) {

			final RedScene scene = restoreChildScene(element, components_factory, settings);
			currentScene.child_scenes.add(scene);
// this.canvas_components.add(scene);
			component = scene;

		} else if (element.is_user_input) {

			final Component scene = currentScene.restoreUserInput(element, components_factory, currentScene.canvas_components,
				settings);

			component = scene;

		} else if (element.is_text) {

			final TextBar text = currentScene.restoreText(element, components_factory, settings);
			currentScene.text_fields.add(text);
			currentScene.localized_components.add(text);

			component = text;

		} else if (element.is_parallax) {

			final Parallax parallax = currentScene.restoreParallax(element, components_factory, settings);

			currentScene.parallaxes.add(parallax);

			component = parallax;

		} else if (element.is_shader) {

			final ShaderComponent shader = currentScene.restoreShader(element, components_factory, settings);
			currentScene.shaders.add(shader);
			component = shader;

		} else {
			Err.reportError("Element's type: " + Json.serializeToString(element) + " is not supported yet");
		}
		// layer.attachComponent(component);

		Debug.checkNull("component", component);
		if (component instanceof VisibleComponent) {
			final VisibleComponent vc = (VisibleComponent)component;
			if (element.is_hidden) {
				vc.hide();
			} else {
				vc.show();
			}
		}
		settings.pop(element.name);
		return (T)component;

	}

	// private EventsSequence restoreAnimationsSequence(LayerElement element,
	// ComponentsFactory components_factory,
	// List<CanvasComponent> canvas_components) {
	// return restoreSequenceAnimation(element, components_factory,
	// canvas_components);
	// }

	static public BLEND_MODE blend (final RASTER_BLEND_MODE blend_mode) {
		if (blend_mode == RASTER_BLEND_MODE.NORMAL) {
			return BLEND_MODE.GDX_DEFAULT;
		}
		if (blend_mode == RASTER_BLEND_MODE.UNKNOWN) {
			return BLEND_MODE.GDX_DEFAULT;
		}
		if (blend_mode == RASTER_BLEND_MODE.MULTIPLY) {
			return BLEND_MODE.Multiply;
		}

		return BLEND_MODE.GDX_DEFAULT;
	}

	private Component restoreUserInput (final LayerElement element, final ComponentsFactory components_factory,
		final List<CanvasComponent> canvas_components, final Settings settings) {
		Debug.checkNull("element.input_settings", element.input_settings);

		Component component = null;

		final UserInputFactory input_factory = components_factory.getUserInputDepartment();
		// Layer child_scene = components_factory.newLayer();

		final Float2 position = Geometry.newFloat2(element.position_x, element.position_y);
		final boolean debug_mode = element.debug_mode || false;
		if (element.input_settings.is_button) {
			final ButtonSpecs button_specs = input_factory.newButtonSpecs();

			button_specs.setName(element.name);

			this.restoreTouchAreas(element.input_settings.touch_area, button_specs, components_factory, settings);
			{
				final LayerElement child_element = element.input_settings.on_pressed;
				if (child_element != null) {
					final VisibleComponent raster = this.restore(components_factory, child_element, settings);
					button_specs.setOnPressedRaster(raster);
				}
			}
			{
				final LayerElement child_element = element.input_settings.on_released;
				if (child_element != null) {
					final VisibleComponent raster = this.restore(components_factory, child_element, settings);
					button_specs.setOnReleasedRaster(raster);
				}
			}

			final Button button = input_factory.newButton(button_specs);
			button.setDebugRenderFlag(debug_mode);

			component = button;
			this.inputs_components.put((element.name), button);
		} else if (element.input_settings.is_switch) {
			final ButtonSpecs button_specs = input_factory.newButtonSpecs();

			button_specs.setName(element.name);

			this.restoreTouchAreas(element.input_settings.touch_area, button_specs, components_factory, settings);

			for (int i = 0; i < element.children.size(); i++) {
				final SceneStructureAsset structure = settings.getStructure();
				final LayerElement child_element = element.children.elementAt(i, structure.structure());
				final VisibleComponent component_e = this.restore(components_factory, child_element, settings);
				if (!(component_e instanceof Raster)) {
// stack.print();
					Err.reportError(component_e + "");
				}
				final Raster raster = (Raster)component_e;
				button_specs.addOption(raster);
			}

			final Button button = input_factory.newButton(button_specs);
			button.setDebugRenderFlag(debug_mode);

			component = button;
			this.inputs_components.put((element.name), button);
		} else if (element.input_settings.is_custom) {
			final CustomInputSpecs button_specs = input_factory.newCustomInputSpecs();

			button_specs.setName(element.name);

			this.restoreTouchAreas(element.input_settings.touch_area, button_specs, components_factory, settings);

			for (int i = 0; i < element.children.size(); i++) {
				final SceneStructureAsset structure = settings.getStructure();
				final LayerElement child_element = element.children.elementAt(i, structure.structure());
				final Raster raster = (Raster)this.restore(components_factory, child_element, settings);
				button_specs.addOption(raster);

			}

			final CustomInput button = input_factory.newCustomInput(button_specs);
			button.setDebugRenderFlag(debug_mode);
			button.setPosition(position);
			button.updateChildrenPositionRespectively();
			component = button;
			this.inputs_components.put((element.name), button);
		} else if (element.input_settings.is_touch_area) {

			final SceneStructureAsset structure = settings.getStructure();
			final LayerElement area_element = element.input_settings.touch_area.children.elementAt(0, structure.structure());
			final TouchAreaSpecs touch_area_spec = components_factory.getUserInputDepartment().newTouchAreaSpecs();
// final TouchArea touch_area_component = components_factory.getUserInputDepartment().newTouchArea(touch_area_spec);
			touch_area_spec.setArea(area_element.position_x, area_element.position_y, area_element.width, area_element.height);
			touch_area_spec.setName(element.name);

			final TouchArea area = input_factory.newTouchArea(touch_area_spec);
			area.setDebugRenderFlag(debug_mode);

			component = area;
			this.inputs_components.put((element.name), area);
		} else {
			Err.reportError("Unknown button type " + element.input_settings);
		}

		Debug.checkNull("button", component);

		return component;
	}

	private void restoreTouchAreas (final LayerElement touch_area, final InputSpecs button_specs,
		final ComponentsFactory components_factory, final Settings settings) {
		for (int i = 0; i < touch_area.children.size(); i++) {
			final SceneStructureAsset structure = settings.getStructure();
			final LayerElement area = touch_area.children.elementAt(i, structure.structure());
			final TouchAreaSpecs touch_area_spec = components_factory.getUserInputDepartment().newTouchAreaSpecs();
// final TouchArea touch_area_component = components_factory.getUserInputDepartment().newTouchArea(touch_area_spec);
			touch_area_spec.setArea(area.position_x, area.position_y, area.width, area.height);
			touch_area_spec.setName(area.name);

			// AssetID button_id = button_specs.getID();
			// touch_area_spec.setID(button_id.child(touch_area_spec.getName()));

			button_specs.addTouchArea(touch_area_spec);

		}

	}

	private static RedScene restoreChildScene (final LayerElement element, final ComponentsFactory components_factory,
		final Settings parentSettings) {
		Debug.checkNull("element.child_scene_settings", element.child_scene_settings);

		final RedScene scene = new RedScene();

		final ID structure_id = Names.newID(element.child_scene_settings.child_scene_id);

		final AssetHandler reg = LoadedAssets.obtainAsset(structure_id, (AssetsConsumer)components_factory);
		if (reg == null) {
			LoadedAssets.printAllLoadedAssets();
			Err.reportError("Missing asset: " + structure_id);
		}
		final SceneStructureAsset structure = (SceneStructureAsset)reg.asset();
		final Settings settings = parentSettings.copy(structure, scene);

		settings.scene_name = structure_id + "";
		L.d("deploying", structure_id);

		Debug.checkNull("structure", structure);
		LoadedAssets.releaseAsset(reg, (AssetsConsumer)components_factory);

		scene.deployScene(components_factory, structure, settings);

		scene.setName(structure_id.toString());

		return scene;
	}

	private static Parallax restoreParallax (final LayerElement element, final ComponentsFactory components_factory,
		final Settings settings) {

		final ParallaxFactory factory = components_factory.getParallaxDepartment();
		final SceneStructureAsset structure = settings.getStructure();
		final ParallaxSpecs specs = factory.newParallaxSpecs();
		specs.setName(element.name);
		final double posX = element.position_x;
		final double posY = element.position_y;
		specs.setPositionX(posX);
		specs.setPositionY(posY);
		specs.setWidth(element.width);
		specs.setHeight(element.height);

		for (int i = 0; i < element.children.size(); i++) {
			final LayerElement childElement = element.children.elementAt(i, structure.structure());
			final Component child = restore(components_factory, childElement, settings);
			final ParallaxElementSpecs childSpec = specs.newParallaxElementSpecs();
			childSpec.setComponent(child);

			final ParallaxSettings parallax_settings = childElement.parallax_settings;
			Debug.checkNull("childElement.parallax_settings", parallax_settings);

			childSpec.setMultiplierX(parallax_settings.multiplier_x);
			childSpec.setMultiplierY(parallax_settings.multiplier_y);
			childSpec.setMultiplierZ(parallax_settings.multiplier_z);

			specs.addChild(childSpec);
		}

		final Parallax parallax = factory.newParallax(specs);

		return parallax;
	}

	private TextBar restoreText (final LayerElement element, final ComponentsFactory components_factory, final Settings settings) {
		Debug.checkNull("element.text_settings", element.text_settings);

		final TextFactory text_factory = components_factory.getTextDepartment();

		final String textStringID = element.text_settings.text_value_asset_id;
		Text text = null;
		if (textStringID != null) {
			final ID text_asset_id = Names.newID(textStringID);
			final AssetHandler text_asset = LoadedAssets.obtainAsset(text_asset_id, (AssetsConsumer)components_factory);
			text = (Text)text_asset.asset();
		} else if (element.text_settings.text_value_raw != null) {
			text = null;
		} else {
			Err.reportError("text_value_asset_id is null " + settings.getStackPath() + " " + element);
		}

		final TextBarSpecs text_bar_specs = text_factory.newTextBarSpecs();

		text_bar_specs.setLocaleName(settings.locale_name);

		final ID font_id = Names.newID(element.text_settings.font_settings.name);
		final SceneStructureAsset structure = settings.getStructure();
		if (element.children.size() != 0) {
			final LayerElement raster_element = element.children.elementAt(0, structure.structure());
			final Raster bg = (Raster)this.restore(components_factory, raster_element, settings);
			bg.setDebugRenderFlag(!true);
			bg.setOriginRelative(0, 0);
			bg.setPosition(0, 0);
			text_bar_specs.setBackgroundRaster(bg);
		} else {
		}
		if (text != null) {
			text_bar_specs.setText(text);
		}
		if (element.text_settings.text_value_raw != null) {
			text_bar_specs.setRawText(element.text_settings.text_value_raw);
		}
		text_bar_specs.setPadding(element.text_settings.padding);

		text_bar_specs.setFont(font_id);
		text_bar_specs.setFontSize(element.text_settings.font_settings.font_size);
		text_bar_specs.setFontScale(element.text_settings.font_settings.font_scale);
		if (element.text_settings.font_settings.font_color == null) {

		} else {
			final Color fontColor = Colors.newColor(element.text_settings.font_settings.font_color);
			text_bar_specs.setFontColor(fontColor);
		}

		final TextBar text_bar = text_factory.newTextBar(text_bar_specs);
		text_bar.setLocaleName(settings.locale_name);

		final double canvas_x = element.position_x;
		final double canvas_y = element.position_y;
		text_bar.setPosition(0, 0);
		text_bar.setPosition(canvas_x, canvas_y);
		text_bar.setName(element.name);
		return text_bar;
	}

	private Animation restoreAnimation (final LayerElement element, final ComponentsFactory components_factory,
		final ID animation_id, final Settings settings) {
		Debug.checkNull("element.animation_settings", element.animation_settings);
		Animation animation = null;
		if (element.animation_settings.is_simple_animation) {
			animation = this.restoreSimpleAnimation(element, components_factory, settings);
		} else if (element.animation_settings.is_positions_modifyer_animation) {
			animation = restorePositionModifyersAnimation(element, components_factory, this.canvas_components, settings);
		} else {
			Err.reportError("Unknown animation type: " + element);

		}
		Debug.checkNull(element.name, animation);
		animation.setName(element.name);
		animation.setVisible(!element.is_hidden);
// animation.set
		return animation;
	}

	// private EventsSequence restoreSequenceAnimation(LayerElement element,
	// ComponentsFactory components_factory,
	// List<CanvasComponent> canvas_components2) {
	// AnimationFactory a_fact = components_factory.getAnimationDepartment();
	// EventsSequenceSpecs specs = a_fact.newEventsSequenceSpecs();
	//
	// Vector<ActionsGroup> events = element.action_settings.groups;
	//
	// Debug.checkNull("element.animation_settings.events", events);
	//
	// for (int i = 0; i < events.size(); i++) {
	// ActionsGroup events_group = events.get(i);
	// EventsGroupSpecs group_specs = a_fact.newEventsGroupSpecs();
	// restoreGroup(group_specs, events_group);
	// specs.addGroup(group_specs);
	// }
	//
	// Debug.checkNull("element.animation_settings",
	// element.animation_settings);
	//
	// specs.setIsLooped(element.animation_settings.is_looped);
	//
	// specs.setTimeStream(Sys.SystemTime());
	// EventsSequence animation = a_fact.newEventsSequence(specs);
	// return animation;
	// }

	private void restoreGroup (final EventsGroupSpecs group_specs, final ActionsGroup events_group) {
		for (int i = 0; i < events_group.actions.size(); i++) {
			final Action event = events_group.actions.get(i);

			final ID event_id = Names.newID(event.animation_id);

			group_specs.addEventID(event_id);
		}
	}

	private static PositionsSequence restorePositionModifyersAnimation (final LayerElement element,
		final ComponentsFactory components_factory, final List<CanvasComponent> canvas_components, final Settings settings) {
		final AnimationFactory a_fact = components_factory.getAnimationDepartment();
		final RedScene currentScene = settings.currentScene;

		if (element.children.size() > 1) {
			Err.reportError("");
		}

		Layer frame = null;
// settings.debug_raster = true;
		for (int i = 0; i < 1; i++) {
			final SceneStructureAsset structure = settings.getStructure();
			final LayerElement elment = element.children.elementAt(i, structure.structure());
			final Component component = restore(components_factory, elment, settings);
			if (!(component instanceof Layer)) {
				final String path = settings.getStackPath().toString();
				Err.reportError("failed to parse " + settings.scene_name + "@" + path);
			}
			frame = (Layer)component;
		}

// frame.print();
// Sys.exit();

		final RotateAndOffsetProjection projection = Geometry.getProjectionFactory().newOffsetAndRotate();

		final List<Anchor> anchors = Collections.newList(element.animation_settings.anchors);

		if (anchors.size() < 1) {

			Err.reportError("Not supported yet!");
		}
		if (anchors.size() == 1) {
			final Anchor anchor = anchors.getElementAt(0);
// frame.setPosition(anchor.position_x, anchor.position_y);
// projection.setOffsetX(x);
			Err.reportError("Not supported yet!");
		}

		final PositionsSequenceSpecs specs = a_fact.newPositionsSequenceSpecs();
		specs.setIsLooped(element.animation_settings.is_looped);
		specs.setUseSpline(element.animation_settings.use_spline);
		specs.setFramesContainer(frame);
		specs.setProjection(projection);
		specs.setTimeStream(settings.timeStream);
// specs.setComponentRequiresAttachment(true);

		for (int k = 0; k < anchors.size(); k++) {
			final PositionAnchor animation_anchor = a_fact.newAnchor(anchors.getElementAt(k).time());
			animation_anchor.setXY(anchors.getElementAt(k).position_x, anchors.getElementAt(k).position_y);
			specs.addAnchor(animation_anchor);

		}
		final PositionsSequence sequence = a_fact.newPositionsSequence(specs);

		if (element.animation_settings.autostart) {
			Err.reportError("animation_settings.autostart");
			sequence.startAnimation();
		}

		return sequence;

	}

	private List<CanvasComponent> listAllCanvasComponents () {
		return this.canvas_components;
	}

	private LayersAnimation restoreSimpleAnimation (final LayerElement element, final ComponentsFactory components_factory,
		final Settings settings) {
		final AnimationFactory a_fact = components_factory.getAnimationDepartment();
		final LayersAnimationSpecs specs = a_fact.newLayersAnimationSpecs();

		for (int i = 0; i < element.children.size(); i++) {
			final SceneStructureAsset structure = settings.getStructure();
			final LayerElement elment = element.children.elementAt(i, structure.structure());

			final VisibleComponent component = restore(components_factory, elment, settings);
			final VisibleComponent child = (component);

			final FrameAnimationSpecs frame = specs.newFrameSpecs();
			frame.setComponent(child);
			if (elment.animation_settings == null) {
				Err.reportError("No animation_settings found at <" + settings.scene_name + "> @ " + settings.getStackPath());
			}
			frame.setFrameTime(Long.parseLong(elment.animation_settings.frame_time));
			specs.addFrame(frame);

		}

		if (specs.getFrames().size() == 0) {
			Err.reportError("No frames foun in " + element);
		}

		Debug.checkNull("element.animation_settings", element.animation_settings);

		specs.setIsLooped(element.animation_settings.is_looped);
// specs.setUseBezier(element.animation_settings.use_bezier);
// specs.setFrameTime(element.animation_settings.single_frame_time());

		specs.setTimeStream(settings.timeStream);
		final LayersAnimation animation = a_fact.newLayerAnimation(specs);

		if (element.animation_settings.autostart) {
			Err.reportError("animation_settings.autostart " + settings.scene_name + "@" + settings.getStackPath());
			animation.startAnimation();
		}

		return animation;
	}

	private void restoreSubLayer (final Layer layer, final LayerElement celement, final Settings settings) {
		final ComponentsFactory components_factory = layer.getComponentsFactory();

		if (celement.offset_x != 0 || celement.offset_y != 0) {
			final OffsetProjection projection = Geometry.getProjectionFactory().newOffset(celement.offset_x, celement.offset_y);
			layer.setProjection(projection);
		}
		// if (celement.shader_settings != null) {
		// Shader shader = restoreShader(celement, components_factory);
		// shaders.add(shader);
		// layer.setShader(shader);
		// }
		final LayerElement element = celement;
		for (int i = 0; i < element.children.size(); i++) {
			final SceneStructureAsset structure = settings.getStructure();
			final LayerElement elment = element.children.elementAt(i, structure.structure());
			final Component component = restore(components_factory, elment, settings);
			layer.attachComponent(component);
		}
	}

	//
	private ShaderComponent restoreShader (final LayerElement celement, final ComponentsFactory components_factory,
		final Settings settings) {
		final ShaderSettings shader_settings = celement.shader_settings;

		final ShaderFactory shader_factory = components_factory.getShadersDepartment();

		final ID shader_asset_id = Names.newID(celement.shader_id);

		final ShaderSpecs shader_specs = shader_factory.newShaderSpecs();

		shader_specs.setShaderAssetID(shader_asset_id);
		final ShaderComponent shader = shader_factory.newShader(shader_specs);
		shader.setName(celement.name);
		for (int i = 0; i < shader_settings.params.size(); i++) {
			final ShaderParameterValue parameter = shader_settings.params.get(i);
			if (parameter.type == ShaderParameterType.FLOAT) {
				shader.setFloatParameterValue(parameter.name, Double.parseDouble(parameter.value));
			}
		}
		return shader;
	}

	private void restoreCamera (final CameraSettings camera_settings, final ComponentsFactory components_factory,
		final Layer layer) {

		final CameraFactory cam_fac = components_factory.getCameraDepartment();

		Debug.checkNull("camera_settings.mode", camera_settings.mode);

		if (camera_settings.mode == MODE.FIT_IN) {

			final CameraSpecs cam_spec = cam_fac.newCameraSpecs();
			cam_spec.setSimpleCameraPolicy(SIMPLE_CAMERA_POLICY.KEEP_ASPECT_RATIO_ON_SCREEN_RESIZE);
			// cam_spec.setCameraPolicy(policy);
			final Camera camera = cam_fac.newCamera(cam_spec);
			camera.setDebugName("SceneCamera");
			// camera.setDebugFlag(!true);
			// camera.setApertureOpacity(0.5);

			camera.setSize(camera_settings.width, camera_settings.height);
			camera.setOriginRelative(camera_settings.origin_relative_x, camera_settings.origin_relative_y);
			camera.setPosition(camera_settings.position_x, camera_settings.position_y);

			layer.setCamera(camera);
		} else if (camera_settings.mode == MODE.FILL_SCREEN) {
			final CameraSpecs cam_spec = cam_fac.newCameraSpecs();
			cam_spec.setSimpleCameraPolicy(SIMPLE_CAMERA_POLICY.EXPAND_CAMERA_VIEWPORT_ON_SCREEN_RESIZE);
			final Camera camera = cam_fac.newCamera(cam_spec);
			camera.setDebugName("SceneCamera");
			// camera.setDebugFlag(!true);
			// camera.setApertureOpacity(0.5);

// camera.setSize(camera_settings.width, camera_settings.height);
			camera.setOriginRelative(camera_settings.origin_relative_x, camera_settings.origin_relative_y);
			camera.setPosition(camera_settings.position_x, camera_settings.position_y);

			layer.setCamera(camera);

		} else if (camera_settings.mode == MODE.FILL_SCREEN_APERTURE_WRAP) {
			final CameraSpecs cam_spec = cam_fac.newCameraSpecs();
			cam_spec.setSimpleCameraPolicy(SIMPLE_CAMERA_POLICY.FILL_SCREEN_APERTURE_WRAP);
			final Camera camera = cam_fac.newCamera(cam_spec);
			camera.setDebugName("SceneCamera");
			// camera.setDebugFlag(!true);
			// camera.setApertureOpacity(0.5);

// camera.setSize(camera_settings.width, camera_settings.height);
			camera.setSize(camera_settings.width, camera_settings.height);
			camera.setOriginRelative(camera_settings.origin_relative_x, camera_settings.origin_relative_y);
			camera.setPosition(camera_settings.position_x, camera_settings.position_y);

			layer.setCamera(camera);

		} else {
			Err.reportError("Unknown camera mode: " + camera_settings.mode);
		}

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
		if (frame_element.is_animation) {
			Err.reportError("Broken structure: root.is_animation == true");
		}
		if (frame_element.is_raster) {
			Err.reportError("Broken structure: root.is_raster == true");
		}
		if (frame_element.is_user_input) {
			Err.reportError("Broken structure: root.is_user_input == true");
		}

		this.deployScene("root", components_factory, frame_element, this.root, settings);

	}

	public void deployScene (final String scene_name, final ComponentsFactory components_factory, final LayerElement root_element,
		final Layer root, final Settings settings) {

		// root = (Layer)

		final CameraSettings camera_settings = root_element.camera_settings;
		if (camera_settings != null) {
			this.restoreCamera(camera_settings, components_factory, root);
		}
		// if (root_element.shader_settings != null) {
		// Shader shader = restoreShader(root_element, components_factory);
		// root.setShader(shader);
		// }

// settings.debug_opacity = 1;

		for (int i = 0; i < root_element.children.size(); i++) {
			final SceneStructureAsset structure = settings.getStructure();
			final LayerElement child = root_element.children.elementAt(i, structure.structure());
			final Component vc = restore(components_factory, child, settings);
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
		final RasterizedStringSpecs string_specs = textFactory.newRasterStringSpecs();
// final RasterizedFontSpecs string_specs = textFactory.newFontSpecs();
// SystemSettings.getStringParameter(EngineParams.Assets.ASSET_INFO_TAG);
		String param = PREFIX;
		if (param == null) {
			SystemSettings.printSystemParameters();
			Sys.exit();
		}
		if (camera_settings != null) {
			param = param + "\n" + PREFIX + " Camera[" + camera_settings.width + " x " + camera_settings.height + "]";
			string_specs.setFontSize((float)(1f * camera_settings.height * 1f / 25f));
		} else {
			string_specs.setFontSize(10);
		}
		{
			param = param + "\n" + PREFIX + " scene_name=" + settings.scene_name;
		}

		string_specs.setFontScale(1);
		string_specs.setBorderSize(1);
		string_specs.setBorderColor(Colors.BLACK());
		string_specs.setFontColor(Colors.WHITE());

// final RasterizedFont font = textFactory.newFont(string_specs);
// string_specs.setFont(font);

		final RasterizedString string = textFactory.newRasterString(string_specs);
		string.setChars(param);
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
	public Camera getCamera () {
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

	@Override
	public Collection<ShaderComponent> listShaders () {
		return this.shaders;
	}

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
