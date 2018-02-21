
package com.jfixby.r3.fokker.render;

import java.io.IOException;

import com.jfixby.r3.engine.api.render.DefaultShaders;
import com.jfixby.r3.engine.api.render.Drawable;
import com.jfixby.r3.engine.api.render.RENDER_PARAMS;
import com.jfixby.r3.engine.api.render.RenderMachineComponent;
import com.jfixby.r3.engine.api.render.ScreenShot;
import com.jfixby.r3.engine.api.render.ShaderSettings;
import com.jfixby.r3.engine.api.render.TEXTURE_BLEND_MODE;
import com.jfixby.r3.fokker.font.api.FokkerFonts;
import com.jfixby.r3.fokker.render.geo.FokkerShapesRenderer;
import com.jfixby.r3.fokker.render.raster.FokkerRasterRenderer;
import com.jfixby.r3.fokker.render.shader.FokkerDefaultShaders;
import com.jfixby.r3.fokker.render.shader.FokkerShaderRenderer;
import com.jfixby.r3.fokker.shader.api.FokkerShaders;
import com.jfixby.r3.fokker.texture.api.FokkerTextures;
import com.jfixby.r3.rana.api.loader.PackagesLoader;
import com.jfixby.r3.rana.api.manager.AssetsManager;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.color.Colors;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.debug.StateSwitcher;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.font.RasterStringSettings;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.geometry.projections.Projection;
import com.jfixby.scarabei.api.names.ID;
import com.jfixby.scarabei.api.sys.settings.SystemSettings;

public class FokkerRenderMachine implements RenderMachineComponent {

	private static StateSwitcher<RENDER_MACHINE_STATE> render_state;
	//

	public FokkerRenderMachine () {
// this.init();
	}

	static Color CLEAR_SCREEN_COLOR;
	// Point offset;

	final FokkerShapesRenderer shapes_renderer = new FokkerShapesRenderer();
	final FokkerRasterRenderer raster_renderer = new FokkerRasterRenderer();
	final RenderBuffer no_primary_buffer = new NoRenderBuffer();
// final RenderBuffer primary_buffer = new FrameRenderBuffer();
	final RenderBuffer primary_buffer = this.no_primary_buffer;
	final FokkerShaderRenderer shader_renderer = new FokkerShaderRenderer();

	public Projection camera_projection;
	public Projection layer_projection;
	private RedFokkerRasterManager raster_manager;
	// public final CurrentFokkerShader current_shader = new
	// CurrentFokkerShader();

	private Drawable currentComponent;

	private final FokkerDefaultAssets defaultAssets = new FokkerDefaultAssets();

	private final FokkerDefaultShaders defaultShaders = new FokkerDefaultShaders(this);

	@Override
	final public void deploy () {
		render_state = Debug.newStateSwitcher(RENDER_MACHINE_STATE.NEW);

		// L.d("init()", render_state);
		render_state.setDebugName("render_state");
		render_state.setDebugFlag(!true);

		PackagesLoader.registerPackageReader(FokkerShaders.invoke().packageReader().reader());
		PackagesLoader.registerPackageReader(FokkerTextures.invoke().packageReader().reader());
		PackagesLoader.registerPackageReader(FokkerFonts.invoke().packageReader().reader());

		this.raster_manager = new RedFokkerRasterManager(this);

		// L.d("init()", raster_manager);
		// background_color = Colors.BLACK().customize().setBlue(0.4f);

		// background_color = Colors.BLACK().customize();
		// offset = Geometry.newPoint();

		this.primary_buffer.init();
		this.shapes_renderer.init(this);
		this.raster_renderer.init(this, this.primary_buffer);
		this.shader_renderer.init(this.raster_renderer);

		// shader_renderer.init(this);

		expectState(RENDER_MACHINE_STATE.NEW);
		switchState(RENDER_MACHINE_STATE.READY);
		initClearScreenColor();

		try {
			AssetsManager.autoResolveAssets(this.DefaultAssets().list());
		} catch (final IOException e) {
			e.printStackTrace();
			Err.reportError(e);
		}

		GdxRender.init(FokkerRenderMachine.this.raster_renderer);

	}

	@Override
	final public void beginFrame () {
		// L.d();
		expectState(RENDER_MACHINE_STATE.READY);
		switchState(RENDER_MACHINE_STATE.FRAME);

		this.primary_buffer.beginFrame();
		this.shapes_renderer.beginFrame();
		this.raster_renderer.beginFrame();

		// this.shader_renderer.beginFrame();

		// L.d("begin()", render_state);
	}

	@Override
	final public void setCameraProjection (final Projection camera) {
		expectState(RENDER_MACHINE_STATE.FRAME);
		this.camera_projection = camera;
		if (this.camera_projection == null) {
			this.camera_projection = Geometry.getProjectionFactory().IDENTITY();
		}
		// L.d("setProjection()", render_state);
	}

	@Override
	final public void setProjection (final Projection projection) {
		expectState(RENDER_MACHINE_STATE.FRAME);
		this.layer_projection = projection;
		if (this.layer_projection == null) {
			this.layer_projection = Geometry.getProjectionFactory().IDENTITY();

		}
	}

	@Override
	final public void beginDrawComponent (final Drawable fokkerDrawable) {
		expectState(RENDER_MACHINE_STATE.FRAME);
		this.currentComponent = fokkerDrawable;
	}

	@Override
	final public void endDrawComponent (final Drawable fokkerDrawable) {
		expectState(RENDER_MACHINE_STATE.FRAME);
		// L.d("endDrawComponent()", render_state);
		this.currentComponent = null;
	}

	@Override
	final public void endFrame () {
		expectState(RENDER_MACHINE_STATE.FRAME);
		switchState(RENDER_MACHINE_STATE.READY);

		// offset.setXY();
		this.shapes_renderer.endFrame();
		this.raster_renderer.endFrame();
		this.primary_buffer.endFrame();
		Debug.component().checkTrue(this.layer_projection == Geometry.getProjectionFactory().IDENTITY());
// Debug.component().checkTrue(this.camera_projection == IDENTITY_PROJECTION);

		// this.shader_renderer.endFrame();
		// L.d("end()", render_state);
	}

	@Override
	final public void clearScreen () {
		expectState(RENDER_MACHINE_STATE.FRAME);

		GdxRender.clearScreen(CLEAR_SCREEN_COLOR);
	}

	static final private void initClearScreenColor () {
		if (CLEAR_SCREEN_COLOR == null) {
			final String hexstring = SystemSettings.getStringParameter(RENDER_PARAMS.CLEAR_SCREEN_COLOR_ARGB,
				Colors.FUCHSIA().toShortHexString());
			if (hexstring == null) {
				// Sys.printSystemParameters();
				// Sys.exit();
				CLEAR_SCREEN_COLOR = Colors.FUCHSIA();
			} else {
				CLEAR_SCREEN_COLOR = Colors.newColor(hexstring);
			}
		}
	}

	@Override
	final public void drawLine (final Color color, final ReadOnlyFloat2 a, final ReadOnlyFloat2 b) {
		expectState(RENDER_MACHINE_STATE.SHAPES);
		this.shapes_renderer.drawLine(color, a, b);
		// L.d("drawLine()", render_state);
	}

	@Override
	final public void drawTriangle (final Color color, final ReadOnlyFloat2 a, final ReadOnlyFloat2 b, final ReadOnlyFloat2 c) {
		expectState(RENDER_MACHINE_STATE.SHAPES);
		this.shapes_renderer.drawTriangle(color, a, b, c);
		// L.d("drawTriangle()", render_state);
	}

	static final private RENDER_MACHINE_STATE switchState (final RENDER_MACHINE_STATE next_state) {
		render_state.switchState(next_state);
		return next_state;
	}

	static final private void expectState (final RENDER_MACHINE_STATE expected) {
		render_state.expectState(expected);

	}

	@Override
	final public void drawRaster (final ID spriteAssetID, final Rectangle shape) {
		expectState(RENDER_MACHINE_STATE.RASTER);

		this.raster_renderer.drawSprite(spriteAssetID, shape);
		// L.d("drawRaster()", render_state);
	}

	public RedFokkerRasterManager getLoadedRasterManager () {
		return this.raster_manager;
	}

	@Override
	final public void endShapesMode () {
		expectState(RENDER_MACHINE_STATE.SHAPES);
		switchState(RENDER_MACHINE_STATE.FRAME);
		this.shapes_renderer.close();
	}

	@Override
	final public void beginShapesMode () {
		expectState(RENDER_MACHINE_STATE.FRAME);
		switchState(RENDER_MACHINE_STATE.SHAPES);
		this.shapes_renderer.open();
// throw new Error("disable shapes! " + this.currentComponent);
	}

	@Override
	final public void beginRasterMode (final TEXTURE_BLEND_MODE blend_mode, final double opacity) {
		Debug.component().checkNull("blend_mode", blend_mode);
		expectState(RENDER_MACHINE_STATE.FRAME);
		switchState(RENDER_MACHINE_STATE.RASTER);
		this.raster_renderer.open(blend_mode, opacity, null, null);
		// this.frame_buffer.updateMode(blend_mode);
	}

	@Override
	final public void endRasterMode (final TEXTURE_BLEND_MODE blend_mode) {
		Debug.component().checkNull("blend_mode", blend_mode);
		expectState(RENDER_MACHINE_STATE.RASTER);
		switchState(RENDER_MACHINE_STATE.FRAME);
		this.raster_renderer.close(blend_mode, null);

	}

	@Override
	final public void drawAperture (final double ax, final double ay, final double bx, final double by, final ID spriteAssetID) {
		expectState(RENDER_MACHINE_STATE.RASTER);

		this.raster_renderer.drawAperture(spriteAssetID, ax, ay, bx, by);
	}

	@Override
	final public void drawCircle (final Color color, final double center_x, final double center_y, final double radius) {
		expectState(RENDER_MACHINE_STATE.SHAPES);
		this.shapes_renderer.drawCircle(color, center_x, center_y, radius);
	}

	@Override
	final public void drawString (final RasterStringSettings specs, final CanvasPosition position) {
		expectState(RENDER_MACHINE_STATE.RASTER);

		this.raster_renderer.drawString(specs, position);

	}

	@Override
	public void beginShaderMode (final ID fokkerShader, final ShaderSettings params) {
		Debug.component().checkNull("fokkerShader", fokkerShader);
		expectState(RENDER_MACHINE_STATE.FRAME);
		switchState(RENDER_MACHINE_STATE.SHADER);
		this.shader_renderer.open(fokkerShader, params);

	}

	@Override
	public void endShaderMode (final ID fokkerShader) {
		expectState(RENDER_MACHINE_STATE.SHADER);
		switchState(RENDER_MACHINE_STATE.FRAME);
		this.shader_renderer.close(fokkerShader);
	}

	@Override
	public void applyShader () {
		expectState(RENDER_MACHINE_STATE.SHADER);
		// switchState(RENDER_MACHINE_STATE.FRAME);
		this.shader_renderer.applyShader();
	}

	@Override
	public FokkerDefaultAssets DefaultAssets () {
		return this.defaultAssets;
	}

	@Override
	public ScreenShot makeScreenShot (final int areaWidth, final int areaHeight, final int areaX, final int areaY) {
		return new FokkerScreenShot(areaWidth, areaHeight, areaX, areaY);
	}

	@Override
	public DefaultShaders DefaultShaders () {
		return this.defaultShaders;
	}

}
