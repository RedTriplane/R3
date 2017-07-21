
package com.jfixby.r3.activity.red;

import java.util.ArrayList;

import com.jfixby.r3.activity.api.camera.CameraProjection;
import com.jfixby.r3.activity.red.cam.RedCamera;
import com.jfixby.r3.activity.red.layers.FastList;
import com.jfixby.r3.activity.red.layers.RedLayer;
import com.jfixby.r3.engine.api.render.Drawable;
import com.jfixby.r3.engine.api.render.RenderMachine;
import com.jfixby.scarabei.api.debug.DEBUG_TIMER_MODE;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.debug.DebugTimer;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.geometry.projections.Projection;
import com.jfixby.scarabei.api.geometry.projections.ProjectionsStack;
import com.jfixby.scarabei.api.sys.settings.ExecutionMode;
import com.jfixby.scarabei.api.sys.settings.SystemSettings;

public class RedLayerRenderer {

	private final RedLayer root_layer;
	final ArrayList<RedCamera> cameras_stack = new ArrayList<RedCamera>(20);
	final RedProjectionsStack projectction_stack = new RedProjectionsStack();
	// final LinkedList<RedCamera> cameras_stack = new LinkedList<RedCamera>();
	private final DebugTimer component_debug_timer;
	// final List<RedShader> shaders_stack = Collections.newList();
	private final DebugTimer frame_debug_timer;

	public RedLayerRenderer (final RedLayer root_layer) {
		this.root_layer = root_layer;
		final RedCamera root_camera = root_layer.getCamera();
		// RedShader root_shader = root_layer.getShader();
		insertElement(this.cameras_stack, root_camera);
		// shaders_stack.insertElementAt(root_shader, 0);
		this.component_debug_timer = Debug.newTimer();
		this.frame_debug_timer = Debug.newTimer(DEBUG_TIMER_MODE.NANOSECONDS);
		this.execMode = SystemSettings.getExecutionMode();
	}

	final public void renderAll () {

		RenderMachine.component().beginFrame();
		RenderMachine.component().clearScreen();

		renderLayer(this.root_layer, this.component_debug_timer, this.cameras_stack, this.execMode, this.projectction_stack);

		RenderMachine.component().endFrame();

// this.logger.log();

	}

	static final private void insertElement (final java.util.List<RedCamera> cameras_stack, final RedCamera camera) {
		cameras_stack.add(camera);
	}

	static int last = 0;

	static final private RedCamera removeFromStack (final java.util.ArrayList<RedCamera> cameras_stack) {
		last = cameras_stack.size() - 1;
		return cameras_stack.remove(last);
	}

	static final private RedCamera getCurrent (final java.util.List<RedCamera> cameras_stack) {
		last = cameras_stack.size() - 1;
		return cameras_stack.get(last);
	}

// final FPSLogger logger = new FPSLogger();

	final static private void renderLayer (final RedLayer layer, final DebugTimer timer, final ArrayList<RedCamera> cameras_stack,
		final ExecutionMode execMode, final ProjectionsStack projections_stack) {
		if (!layer.isVisible()) {
			return;
		}

		final RedCamera camera = layer.getCamera();
		if (camera != null) {
			stackInCamera(camera, cameras_stack);
		}

		final Projection projection = layer.getProjection();
		if (projection != null) {
			stackInProjection(projection, projections_stack);
		}

		final FastList<Object> renderable_components = layer.listRenderableComponents();
		final int N = renderable_components.size();
		for (int i = 0; i < N; i++) {
			final Object e = renderable_components.getElementAt(i);
			if (e instanceof RedLayer) {
				renderLayer((RedLayer)e, timer, cameras_stack, execMode, projections_stack);
			} else {
				renderComponent((Drawable)e, timer, execMode, layer);
			}
		}
		if (camera != null) {
			camera.renderAperture();
			stackOutCamera(camera, cameras_stack);
		}
		if (projection != null) {
			stackOutProjection(projection, projections_stack);
		}
	}

	final static private void stackOutCamera (final RedCamera camera, final ArrayList<RedCamera> cameras_stack) {
		final RedCamera removed = removeFromStack(cameras_stack);
		camera.removeStack();
		if (removed != camera) {
			// cameras_stack.print("cameras stack");
			Err.reportError("Camera stack is corrupred!");
		}
		final RedCamera previous_camera = getCurrent(cameras_stack);
		final CameraProjection projection = previous_camera.getCameraProjection();
		RenderMachine.component().setCameraProjection(projection.projection());
	}

	final static private void stackInCamera (final RedCamera camera, final ArrayList<RedCamera> cameras_stack) {
		insertElement(cameras_stack, camera);
		camera.setStack(cameras_stack);
		final CameraProjection projection = camera.getCameraProjection();
		RenderMachine.component().setCameraProjection(projection.projection());
	}

	final static private void stackInProjection (final Projection projection, final ProjectionsStack projections_stack) {
		projections_stack.push(projection);
		RenderMachine.component().setProjection(projections_stack);
	}

	private static void stackOutProjection (final Projection projection, final ProjectionsStack projections_stack) {
		final Projection top_projection = projections_stack.pop();
		Debug.checkTrue(top_projection == projection);
		if (projections_stack.size() == 0) {
			RenderMachine.component().setProjection(null);
		}
	}

	private final ExecutionMode execMode;

	final static private void renderComponent (final Drawable e, final DebugTimer timer, final ExecutionMode execMode,
		final RedLayer parent) {
		if (!e.isVisible()) {
			return;
		}
		try {
			e.doDraw();
		} catch (final Throwable xe) {
			throw new FailedToRenderComponentException(xe, parent);
		}
	}
}
