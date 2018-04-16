
package com.jfixby.r3.activity.red.scene;

import java.io.IOException;

import com.jfixby.r3.activity.api.ComponentsFactory;
import com.jfixby.r3.activity.api.UIFactory;
import com.jfixby.r3.activity.api.animation.Animation;
import com.jfixby.r3.activity.api.animation.AnimationFactory;
import com.jfixby.r3.activity.api.animation.EventsGroupSpecs;
import com.jfixby.r3.activity.api.animation.FrameAnimationSpecs;
import com.jfixby.r3.activity.api.animation.LayersAnimation;
import com.jfixby.r3.activity.api.animation.LayersAnimationSpecs;
import com.jfixby.r3.activity.api.animation.PositionAnchor;
import com.jfixby.r3.activity.api.animation.PositionsSequence;
import com.jfixby.r3.activity.api.animation.PositionsSequenceSpecs;
import com.jfixby.r3.activity.api.camera.CameraFactory;
import com.jfixby.r3.activity.api.camera.CanvasCamera;
import com.jfixby.r3.activity.api.camera.CanvasCameraSpecs;
import com.jfixby.r3.activity.api.camera.SIMPLE_CAMERA_POLICY;
import com.jfixby.r3.activity.api.input.CustomInput;
import com.jfixby.r3.activity.api.input.CustomInputSpecs;
import com.jfixby.r3.activity.api.input.InputSpecs;
import com.jfixby.r3.activity.api.input.TouchArea;
import com.jfixby.r3.activity.api.input.TouchAreaSpecs;
import com.jfixby.r3.activity.api.input.UserInputFactory;
import com.jfixby.r3.activity.api.layer.Component;
import com.jfixby.r3.activity.api.layer.Layer;
import com.jfixby.r3.activity.api.layer.VisibleComponent;
import com.jfixby.r3.activity.api.parallax.Parallax;
import com.jfixby.r3.activity.api.parallax.ParallaxElementSpecs;
import com.jfixby.r3.activity.api.parallax.ParallaxFactory;
import com.jfixby.r3.activity.api.parallax.ParallaxSpecs;
import com.jfixby.r3.activity.api.raster.CanvasComponent;
import com.jfixby.r3.activity.api.raster.Raster;
import com.jfixby.r3.activity.api.raster.UI_BLEND_MODE;
import com.jfixby.r3.activity.api.txt.TextBar;
import com.jfixby.r3.activity.api.txt.TextBarSpecs;
import com.jfixby.r3.activity.api.txt.TextFactory;
import com.jfixby.r3.activity.api.ui.ninepatch.NinePatch;
import com.jfixby.r3.activity.api.ui.ninepatch.NinePatchSettings;
import com.jfixby.r3.io.scene2d.Action;
import com.jfixby.r3.io.scene2d.ActionsGroup;
import com.jfixby.r3.io.scene2d.Anchor;
import com.jfixby.r3.io.scene2d.CameraSettings;
import com.jfixby.r3.io.scene2d.CameraSettings.MODE;
import com.jfixby.r3.io.scene2d.LayerElement;
import com.jfixby.r3.io.scene2d.ParallaxSettings;
import com.jfixby.r3.io.scene2d.RASTER_BLEND_MODE;
import com.jfixby.r3.rana.api.asset.AssetHandler;
import com.jfixby.r3.rana.api.asset.AssetsConsumer;
import com.jfixby.r3.rana.api.asset.LoadedAssets;
import com.jfixby.r3.rana.api.manager.AssetsManager;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.color.Colors;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.geometry.projections.OffsetProjection;
import com.jfixby.scarabei.api.geometry.projections.RotateAndOffsetProjection;
import com.jfixby.scarabei.api.json.Json;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.names.ID;
import com.jfixby.scarabei.api.names.Names;
import com.jfixby.scarabei.api.strings.Text;
import com.jfixby.scarabei.api.strings.TextSpawner;
import com.jfixby.scarabei.api.util.Utils;

public class RedSceneConstructor {
	private final RedMaterialDesignConstructor materealDesignConstructor = new RedMaterialDesignConstructor(this);

	<T extends Component> T restore (final ComponentsFactory components_factory, final LayerElement element,
		final Settings settings) {
		settings.push(element.name);
		Component component = null;
		final RedScene currentScene = settings.currentScene;
		if (Utils.equalObjects(element.is_sublayer, Boolean.TRUE)) {
			final Layer new_layer = components_factory.newLayer();
			component = new_layer;
			new_layer.setName(element.name);

			currentScene.layers_list.add(new_layer);
			this.restoreSubLayer(new_layer, element, settings);
		} else if (Utils.equalObjects(element.is_raster, Boolean.TRUE)) {
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
			raster.setOriginRelative(this.toDouble(element.origin_relative_x), this.toDouble(element.origin_relative_y));
			raster.setPosition(element.position_x, element.position_y);
			raster.setBlendMode(this.blend(element.blend_mode));
			raster.setDebugRenderFlag(Utils.equalObjects(element.debug_mode, Boolean.TRUE));
			raster.setDebugRenderFlag(!true || settings.debug_raster);

			// raster.setRotation(element.rotation - Angles.g30().toRadians());
			raster.setRotation(this.toDouble(element.rotation));
			// raster.setSize(100, 100);
			currentScene.rasters.add(raster);
			currentScene.canvas_components.add(raster);

		} else if (Utils.equalObjects(element.is_animation, Boolean.TRUE)

		) {

			final ID animation_id = Names.newID(element.animation_id);

			final Animation animation = this.restoreAnimation(element, components_factory, animation_id, settings);
			component = animation;

			animation.setDebugRenderFlag(element.debug_mode);

			currentScene.animations.add(animation);

		} else if (

		Utils.equalObjects(element.is_child_scene, Boolean.TRUE)

		) {

			final RedScene scene = this.restoreChildScene(element, components_factory, settings);
			currentScene.child_scenes.add(scene);
// this.canvas_components.add(scene);
			component = scene;

		} else if (

		Utils.equalObjects(element.is_user_input, Boolean.TRUE)

		) {

			final Component scene = this.restoreUserInput(element, components_factory, currentScene.canvas_components, settings);

			component = scene;

		} else if (

		Utils.equalObjects(element.is_text, Boolean.TRUE)

		) {

			final TextBar text = this.restoreText(element, components_factory, settings);
			currentScene.text_fields.add(text);
			currentScene.localized_components.add(text);

			component = text;

		} else if (

		Utils.equalObjects(element.is_parallax, Boolean.TRUE)

		) {

			final Parallax parallax = this.restoreParallax(element, components_factory, settings);

			currentScene.parallaxes.add(parallax);

			component = parallax;

		} else if (

		Utils.equalObjects(element.is_9_patch, Boolean.TRUE)

		) {

			final NinePatch ninpatch = this.restoreNinePatch(element, components_factory, settings);

			currentScene.ninepatches.add(ninpatch);

			component = ninpatch;

		} else if (

		Utils.equalObjects(element.is_material_design, Boolean.TRUE)

		) {

			final Component component0 = this.materealDesignConstructor.restoreMaterialDesign(element, components_factory, settings);

// currentScene.ninepatches.add(ninpatch);

			component = component0;

		} else if (

		Utils.equalObjects(element.is_shader, Boolean.TRUE)

		) {

// final ShaderComponent shader = currentScene.restoreShader(element, components_factory, settings);
// currentScene.shaders.add(shader);
// component = shader;
			Err.throwNotImplementedYet();

		} else {
			Err.reportError("Element's type: " + Json.serializeToString(element) + " is not supported yet");
		}
		// layer.attachComponent(component);
		if (component == null) {
			L.d("settings.stack", "[" + settings.structure.getAssetID() + "] @ " + Utils.newRelativePath(settings.stack));
// Err.reportError("Components is null " + Json.serializeToString(element) + " is not supported yet");

		}

		Debug.checkNull("component", component);
		if (component instanceof VisibleComponent) {
			final VisibleComponent vc = (VisibleComponent)component;
			if (

			Utils.equalObjects(element.is_hidden, Boolean.TRUE)

			) {
				vc.hide();
			} else {
				vc.show();
			}
		}
		settings.pop(element.name);
		return (T)component;

	}

	// EventsSequence restoreAnimationsSequence(LayerElement element,
	// ComponentsFactory components_factory,
	// List<CanvasComponent> canvas_components) {
	// return restoreSequenceAnimation(element, components_factory,
	// canvas_components);
	// }

	Double toDouble (final Double x) {
		if (x == null) {
			return 0d;
		}
		return x;
	}

	NinePatch restoreNinePatch (final LayerElement element, final ComponentsFactory components_factory, final Settings settings) {

		final UIFactory fac = components_factory.getUIDepartment();

		final NinePatchSettings n9settings = fac.newNinePatchSettings();

		final NinePatch n9 = fac.newNinePatch(n9settings);

		n9settings.name = element.name;

		final Rectangle shape = n9.shape();

		final float opacity = (float)(element.opacity * settings.debug_opacity);
		n9.setOpacity(opacity);
// L.d(raster + "", opacity);
// raster.setDebugColor(Colors.newRandomColor(0));
		shape.setWidth(element.width);
		shape.setHeight(element.height);
		shape.setOriginRelative(element.origin_relative_x, element.origin_relative_y);
		shape.setPosition(element.position_x, element.position_y);

		n9.setBlendMode(this.blend(element.blend_mode));
		n9.setDebugRenderFlag(element.debug_mode);
		n9.setDebugRenderFlag(!true || settings.debug_raster);

		// raster.setRotation(element.rotation - Angles.g30().toRadians());
		n9.setRotation(element.rotation);

		return n9;
	}

	public UI_BLEND_MODE blend (final RASTER_BLEND_MODE blend_mode) {
		if (blend_mode == RASTER_BLEND_MODE.NORMAL) {
			return UI_BLEND_MODE.Normal;
		}
		if (blend_mode == RASTER_BLEND_MODE.UNKNOWN) {
			return UI_BLEND_MODE.Normal;
		}
		if (blend_mode == RASTER_BLEND_MODE.MULTIPLY) {
			return UI_BLEND_MODE.Multiply;
		}

		return UI_BLEND_MODE.Normal;
	}

	Component restoreUserInput (final LayerElement element, final ComponentsFactory components_factory,
		final List<CanvasComponent> canvas_components, final Settings settings) {

		final RedScene currentScene = settings.currentScene;
		Debug.checkNull("element.input_settings", element.input_settings);

		Component component = null;

		final UserInputFactory input_factory = components_factory.getUserInputDepartment();
		// Layer child_scene = components_factory.newLayer();

		final Float2 position = Geometry.newFloat2(element.position_x, element.position_y);
		final boolean debug_mode = element.debug_mode || false;
		if (element.input_settings.is_button) {
			Err.throwNotImplementedYet();
// final ButtonSpecs button_specs = input_factory.newButtonSpecs();
//
// button_specs.setName(element.name);
//
// this.restoreTouchAreas(element.input_settings.touch_area, button_specs, components_factory, settings);
// {
// final LayerElement child_element = element.input_settings.on_pressed;
// if (child_element != null) {
// final VisibleComponent raster = this.restore(components_factory, child_element, settings);
// button_specs.setOnPressedRaster(raster);
// }
// }
// {
// final LayerElement child_element = element.input_settings.on_released;
// if (child_element != null) {
// final VisibleComponent raster = this.restore(components_factory, child_element, settings);
// button_specs.setOnReleasedRaster(raster);
// }
// }
//
// final Button button = input_factory.newButton(button_specs);
// button.setDebugRenderFlag(debug_mode);
//
// component = button;
// this.inputs_components.put((element.name), button);
		} else if (element.input_settings.is_switch) {
			Err.throwNotImplementedYet();
// final ButtonSpecs button_specs = input_factory.newButtonSpecs();
//
// button_specs.setName(element.name);
//
// this.restoreTouchAreas(element.input_settings.touch_area, button_specs, components_factory, settings);
//
// for (int i = 0; i < element.children.size(); i++) {
// final SceneStructureAsset structure = settings.getStructure();
// final LayerElement child_element = element.children.elementAt(i, structure.structure());
// final VisibleComponent component_e = this.restore(components_factory, child_element, settings);
// if (!(component_e instanceof Raster)) {
//// stack.print();
// Err.reportError(component_e + "");
// }
// final Raster raster = (Raster)component_e;
// button_specs.addOption(raster);
// }
//
// final Button button = input_factory.newButton(button_specs);
// button.setDebugRenderFlag(debug_mode);
//
// component = button;
// this.inputs_components.put((element.name), button);
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
			currentScene.inputs_components.put((element.name), button);
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
			currentScene.inputs_components.put((element.name), area);
		} else {
			Err.reportError("Unknown button type " + element.input_settings);
		}

		Debug.checkNull("button", component);

		return component;
	}

	void restoreTouchAreas (final LayerElement touch_area, final InputSpecs button_specs,
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

	RedScene restoreChildScene (final LayerElement element, final ComponentsFactory components_factory,
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
		settings.stack.clear();

		settings.scene_name = structure_id + "";
		L.d("deploying", structure_id);

		Debug.checkNull("structure", structure);
		LoadedAssets.releaseAsset(reg, (AssetsConsumer)components_factory);

		scene.deployScene(components_factory, structure, settings);

		scene.setName(structure_id.toString());

		return scene;
	}

	Parallax restoreParallax (final LayerElement element, final ComponentsFactory components_factory, final Settings settings) {

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
			final Component child = this.restore(components_factory, childElement, settings);
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

	TextBar restoreText (final LayerElement element, final ComponentsFactory components_factory, final Settings settings) {
		Debug.checkNull("element.text_settings", element.text_settings);

		final TextFactory text_factory = components_factory.getTextDepartment();

// final String textStringID = element.text_settings.text_value_asset_id;
// Text text = null;
// if (textStringID != null) {
// final ID text_asset_id = Names.newID(textStringID);
// final AssetHandler text_asset = LoadedAssets.obtainAsset(text_asset_id, (AssetsConsumer)components_factory);
// text = (Text)text_asset.asset();
// } else if (element.text_settings.text_value_raw != null) {
// text = null;
// } else {
// Err.reportError("text_value_asset_id is null " + settings.getStackPath() + " " + element);
// }

		final TextBarSpecs text_bar_specs = text_factory.newTextBarSpecs();

		text_bar_specs.name = (element.name);

		final ID font_id = Names.newID(element.text_settings.font_settings.name);
		final SceneStructureAsset structure = settings.getStructure();
		if (element.children.size() != 0) {
			final LayerElement raster_element = element.children.elementAt(0, structure.structure());
			final Raster bg = (Raster)this.restore(components_factory, raster_element, settings);
			bg.setDebugRenderFlag(!true);
			bg.setOriginRelative(0, 0);
			bg.setPosition(0, 0);
			text_bar_specs.backgroundRaster = (bg);
		} else {
		}

		text_bar_specs.padding = (element.text_settings.padding);

		final ID text_value_asset_id = Names.newID(element.text_settings.text_value_asset_id);
		Text text = null;
		{
			final AssetsConsumer consumer = new AssetsConsumer() {};
			AssetHandler stringData = LoadedAssets.obtainAsset(text_value_asset_id, consumer);

			if (stringData == null) {
				try {
					AssetsManager.autoResolveAsset(text_value_asset_id);
				} catch (final IOException e) {
					e.printStackTrace();
					Err.reportError(e);
				}
				stringData = LoadedAssets.obtainAsset(text_value_asset_id, consumer);
			}

			final TextSpawner data = stringData.asset();
			text = data.newInstance();

			LoadedAssets.releaseAsset(stringData, consumer);
		}

		text_bar_specs.text = text;

		text_bar_specs.fontID = (font_id);
		text_bar_specs.fontSize = (element.text_settings.font_settings.font_size);
// text_bar_specs.setFontScale(element.text_settings.font_settings.font_scale);
		if (element.text_settings.font_settings.font_color == null) {

		} else {
			final Color fontColor = Colors.newColor(element.text_settings.font_settings.font_color);
			text_bar_specs.fontColor = (fontColor);
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

	LayersAnimation restoreSimpleAnimation (final LayerElement element, final ComponentsFactory components_factory,
		final Settings settings) {
		final AnimationFactory a_fact = components_factory.getAnimationDepartment();
		final LayersAnimationSpecs specs = a_fact.newLayersAnimationSpecs();

		for (int i = 0; i < element.children.size(); i++) {
			final SceneStructureAsset structure = settings.getStructure();
			final LayerElement elment = element.children.elementAt(i, structure.structure());

			final VisibleComponent component = this.restore(components_factory, elment, settings);
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

	void restoreSubLayer (final Layer layer, final LayerElement celement, final Settings settings) {
		final ComponentsFactory components_factory = layer.getComponentsFactory();

		if (Utils.equalObjects(celement.offset_x, 0d) || Utils.equalObjects(celement.offset_y, 0d)) {
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
			final Component component = this.restore(components_factory, elment, settings);
			layer.attachComponent(component);
		}
	}

	//
// ShaderComponent restoreShader (final LayerElement celement, final ComponentsFactory components_factory,
// final Settings settings) {
// final ShaderSettings shader_settings = celement.shader_settings;
//
// final ShaderFactory shader_factory = components_factory.getShadersDepartment();
//
// final ID shader_asset_id = Names.newID(celement.shader_id);
//
// final ShaderSpecs shader_specs = shader_factory.newShaderSpecs();
//
// shader_specs.shaderID = (shader_asset_id);
// final ShaderComponent shader = shader_factory.newShader(shader_specs);
// shader.setName(celement.name);
// for (int i = 0; i < shader_settings.params.size(); i++) {
// final ShaderParameterValue parameter = shader_settings.params.get(i);
// if (parameter.type == ShaderParameterType.FLOAT) {
// shader.setFloatParameterValue(parameter.name, Double.parseDouble(parameter.value));
// }
// }
// return shader;
// }

	void restoreCamera (final CameraSettings camera_settings, final ComponentsFactory components_factory, final Layer layer) {

		final CameraFactory cam_fac = components_factory.getCameraDepartment();

		Debug.checkNull("camera_settings.mode", camera_settings.mode);

		if (camera_settings.mode == MODE.FIT_IN) {

			final CanvasCameraSpecs cam_spec = cam_fac.newCameraSpecs();
			cam_spec.setSimpleCameraPolicy(SIMPLE_CAMERA_POLICY.KEEP_ASPECT_RATIO_ON_SCREEN_RESIZE);
			// cam_spec.setCameraPolicy(policy);
			final CanvasCamera camera = cam_fac.newCamera(cam_spec);
			camera.setDebugName("SceneCamera");
			// camera.setDebugFlag(!true);
			// camera.setApertureOpacity(0.5);

			camera.setSize(camera_settings.width, camera_settings.height);
			camera.setOriginRelative(camera_settings.origin_relative_x, camera_settings.origin_relative_y);
			camera.setPosition(camera_settings.position_x, camera_settings.position_y);

			layer.setCamera(camera);
		} else if (camera_settings.mode == MODE.FILL_SCREEN) {
			final CanvasCameraSpecs cam_spec = cam_fac.newCameraSpecs();
			cam_spec.setSimpleCameraPolicy(SIMPLE_CAMERA_POLICY.EXPAND_CAMERA_VIEWPORT_ON_SCREEN_RESIZE);
			final CanvasCamera camera = cam_fac.newCamera(cam_spec);
			camera.setDebugName("SceneCamera");
			// camera.setDebugFlag(!true);
			// camera.setApertureOpacity(0.5);

// camera.setSize(camera_settings.width, camera_settings.height);
			camera.setOriginRelative(camera_settings.origin_relative_x, camera_settings.origin_relative_y);
			camera.setPosition(camera_settings.position_x, camera_settings.position_y);

			layer.setCamera(camera);

		} else if (camera_settings.mode == MODE.FILL_SCREEN_APERTURE_WRAP) {
			final CanvasCameraSpecs cam_spec = cam_fac.newCameraSpecs();
			cam_spec.setSimpleCameraPolicy(SIMPLE_CAMERA_POLICY.FILL_SCREEN_APERTURE_WRAP);
			final CanvasCamera camera = cam_fac.newCamera(cam_spec);
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

	Animation restoreAnimation (final LayerElement element, final ComponentsFactory components_factory, final ID animation_id,
		final Settings settings) {
		Debug.checkNull("element.animation_settings", element.animation_settings);
		Animation animation = null;
		if (element.animation_settings.is_simple_animation) {
			animation = this.restoreSimpleAnimation(element, components_factory, settings);
		} else if (element.animation_settings.is_positions_modifyer_animation) {
			final RedScene currentScene = settings.currentScene;

			animation = this.restorePositionModifyersAnimation(element, components_factory, currentScene.canvas_components,
				settings);
		} else {
			Err.reportError("Unknown animation type: " + element);

		}
		Debug.checkNull(element.name, animation);
		animation.setName(element.name);
		animation.setVisible(!element.is_hidden);
// animation.set
		return animation;
	}

	// EventsSequence restoreSequenceAnimation(LayerElement element,
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

	void restoreGroup (final EventsGroupSpecs group_specs, final ActionsGroup events_group) {
		for (int i = 0; i < events_group.actions.size(); i++) {
			final Action event = events_group.actions.get(i);

			final ID event_id = Names.newID(event.animation_id);

			group_specs.addEventID(event_id);
		}
	}

	PositionsSequence restorePositionModifyersAnimation (final LayerElement element, final ComponentsFactory components_factory,
		final List<CanvasComponent> canvas_components, final Settings settings) {
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
			final Component component = this.restore(components_factory, elment, settings);
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
			animation_anchor.position().setXY(anchors.getElementAt(k).position_x, anchors.getElementAt(k).position_y);
			specs.addAnchor(animation_anchor);

		}
		final PositionsSequence sequence = a_fact.newPositionsSequence(specs);

		if (element.animation_settings.autostart) {
			Err.reportError("animation_settings.autostart");
			sequence.startAnimation();
		}

		return sequence;

	}
}
