
package com.jfixby.r3.activity.red;

import com.jfixby.r3.activity.api.ActivityManager;
import com.jfixby.r3.activity.api.ActivityToolkit;
import com.jfixby.r3.activity.api.ComponentsFactory;
import com.jfixby.r3.activity.api.RootLayer;
import com.jfixby.r3.activity.api.camera.CanvasCameraSpecs;
import com.jfixby.r3.activity.api.camera.SIMPLE_CAMERA_POLICY;
import com.jfixby.r3.activity.red.cam.RedCamera;
import com.jfixby.r3.activity.red.cam.RootCameraCameraViewportListener;
import com.jfixby.r3.activity.red.input.RedLayerInputProcessor;
import com.jfixby.r3.activity.red.layers.RedLayer;
import com.jfixby.r3.activity.red.tool.RedActivityTools;
import com.jfixby.r3.engine.api.exe.EngineState;
import com.jfixby.r3.engine.api.exe.InputQueue;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.projections.IdentityProjection;

public class RedActivityManager implements ActivityManager {

	private final ActivityContainer master;
	private final RedComponentsFactory components_factory;
	private final RedLayer root_layer;
	private final RootCameraCameraViewportListener cameraViewportListener = new RootCameraCameraViewportListener();
	private final RedCamera root_camera;
	// private RedShader root_shader;
	private final RedLayerInputProcessor input_processor;
	private final RedLayerWindowViewportProcessor viewport_processor;
	private final RedLayerTaskManager task_manager;;
	private final RedActivityTools unit_tools;
	private final RedLayerGraphicsRenderer graphics_renderer;
	private final RedLayerAudioRenderer audio_renderer;
	private final RedLayer user_root_layer;
	// private RedActivityClock unit_clock = new RedActivityClock("ActivityTime");
	// private RedActivityClock render_clock = new RedActivityClock("RenderTime");
	private final IdentityProjection root_projection;

	public RedActivityManager (final ActivityContainer unitContainer) {
		this.master = unitContainer;
		this.components_factory = new RedComponentsFactory(this);
		this.root_layer = this.components_factory.newLayer();
		this.root_layer.setName("ActivityManager Root");
		final CanvasCameraSpecs camera_specs = this.components_factory.getCameraDepartment().newCameraSpecs();
		camera_specs.setCameraManager(this.cameraViewportListener);
		camera_specs.setSimpleCameraPolicy(SIMPLE_CAMERA_POLICY.EXPAND_CAMERA_VIEWPORT_ON_SCREEN_RESIZE);
		this.root_camera = (RedCamera)this.components_factory.getCameraDepartment().newCamera(camera_specs);
		this.root_projection = Geometry.getProjectionFactory().IDENTITY();
		this.root_camera.setOriginRelative(0, 0);
		this.root_camera.setPosition(0, 0);
		this.root_camera.setDebugName("ActivityManager Root Camera");
		// root_shader = new RedShader();
		this.root_layer.setCamera(this.root_camera);
		this.root_layer.setProjection(this.root_projection);
		// this.root_layer.setShader(root_shader);
		this.input_processor = new RedLayerInputProcessor(this.root_layer);
		this.user_root_layer = this.components_factory.newLayer();
		this.root_layer.attachComponent(this.user_root_layer);
		this.viewport_processor = new RedLayerWindowViewportProcessor(this.root_layer);
		this.task_manager = new RedLayerTaskManager(this.root_layer);
		this.graphics_renderer = new RedLayerGraphicsRenderer(this.root_layer);
		this.audio_renderer = new RedLayerAudioRenderer(this.root_layer);
		this.unit_tools = new RedActivityTools();

	}

	@Override
	public RootLayer getRootLayer () {
		return this.user_root_layer;
	}

	@Override
	public ComponentsFactory getComponentsFactory () {
		return this.components_factory;
	}

	private void renderGraphics () {
		this.graphics_renderer.renderAll();
	}

	private void renderAudio () {
		this.audio_renderer.renderAll();
	}

	private void process_tasks () {
		this.task_manager.updateLayers();
	}

	private void process_viewport (final EngineState engine_state) {
		this.viewport_processor.deliverScreenDimentions();
	}

	private void process_input (final EngineState engine_state) {
		final InputQueue input = engine_state.getInputQueue();
		this.input_processor.deliverInput(input);
	}

	public void suspend () {
		this.input_processor.markAllAllKeysReleased();
	}

	public void resume () {
		this.input_processor.markAllAllKeysReleased();
	}

	public void renderGraphics (final EngineState engine_state) {
		this.renderGraphics();
	}

	public void renderAudio (final EngineState engine_state) {
		this.renderAudio();
	}

	public void update (final EngineState engine_state) {
		if (engine_state.isBroken()) {
			this.process_input(engine_state);
			this.process_viewport(engine_state);
			this.process_tasks();
			// AssetsManager.checkAll();
		} else {
			this.process_input(engine_state);
			this.process_viewport(engine_state);
			this.process_tasks();
			// AssetsManager.checkAll();
		}
	}

	public String getActivityDebugName () {
		return this.master.getActivityDebugName();
	}

	@Override
	public ActivityToolkit getToolkit () {
		return this.unit_tools;
	}

}
