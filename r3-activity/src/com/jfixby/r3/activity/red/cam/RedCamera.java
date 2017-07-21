
package com.jfixby.r3.activity.red.cam;

import java.util.ArrayList;

import com.jfixby.r3.activity.api.camera.CameraProjection;
import com.jfixby.r3.activity.api.camera.CanvasCamera;
import com.jfixby.r3.activity.api.camera.CanvasCameraSpecs;
import com.jfixby.r3.activity.api.camera.SIMPLE_CAMERA_POLICY;
import com.jfixby.r3.activity.api.user.CanvasCameraManager;
import com.jfixby.r3.activity.red.RedComponentsFactory;
import com.jfixby.r3.activity.red.geo.RedRectangleComponent;
import com.jfixby.r3.activity.red.layers.ScreenDimentionsHolder;
import com.jfixby.r3.engine.api.render.RenderMachine;
import com.jfixby.r3.engine.api.render.TEXTURE_BLEND_MODE;
import com.jfixby.r3.engine.api.screen.Screen;
import com.jfixby.r3.engine.api.screen.ScreenDimentionsChecker;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.color.Colors;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.geometry.projections.Projection;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.math.Angle;
import com.jfixby.scarabei.api.math.FloatMath;

public class RedCamera implements CanvasCamera, CameraProjection, Projection {

	final Rectangle scren_area = Geometry.newRectangle();
	final Rectangle scren_apertured = Geometry.newRectangle();

	final Rectangle camera_vieport_zoomed = Geometry.newRectangle();
	final Rectangle camera_vieport_original = Geometry.newRectangle();

	final Rectangle camera_projection = Geometry.newRectangle();
// final Float2 camera_vieport_original_size = Geometry.newFloat2();

	double zoom = 1d;
	boolean projection_and_aperture_needs_update = true;

	@Override
	public String toString () {
		if (this.camera_name == null) {
			return "Camera[" + this.camera_vieport_zoomed + "]";
		} else {
			return "Camera(" + this.camera_name + ")[" + this.camera_vieport_zoomed + "]";
		}
	}

	boolean debug_mode = false;
	double aperture_opacity = 1d;
	private final ScreenDimentionsChecker checker;
	private final CanvasCameraManager manager;
	private final RedRectangleComponent debug_component;
	private final SIMPLE_CAMERA_POLICY policy;

	public RedCamera (final CanvasCameraSpecs cam_properties, final RedComponentsFactory redComponentsFactory) {

		this.checker = Screen.newScreenDimentionsChecker();
		this.manager = cam_properties.getCameraManager();
		this.policy = cam_properties.getSimpleCameraPolicy();
// Debug.checkNull("SIMPLE_CAMERA_POLICY is null", this.policy);
// Debug.printCallStack();
// this.aperture_opacity = 0.3;
		this.setSize(Screen.getScreenWidth(), Screen.getScreenHeight());
		this.scren_area.setWidth(Screen.getScreenWidth());
		this.scren_area.setHeight(Screen.getScreenHeight());

		this.debug_component = (RedRectangleComponent)redComponentsFactory.getGeometryDepartment()
			.newRectangle(this.camera_vieport_zoomed);
		this.debug_component.setBorderColor(Colors.YELLOW());
// this.setDebugFlag(true);
		this.debug_component.setDebugRenderFlag(true);
	}

	final ScreenDimentionsHolder holder = new ScreenDimentionsHolder();

	public void checkScreenDimentions () {
		if (this.checker.screenDimentionsHaveChanged()) {
			this.scren_area.setWidth(Screen.getScreenWidth());
			this.scren_area.setHeight(Screen.getScreenHeight());
			this.checker.okGotIt();
			this.flagProjectionAndApertureUpdateNeeded();

			if (this.manager != null) {
				this.holder.updateScreenDimentions();
				this.manager.onScreenUpdate(this.holder, this);
			} else {
				if (this.policy == SIMPLE_CAMERA_POLICY.KEEP_ASPECT_RATIO_ON_SCREEN_RESIZE) {
				} else if (this.policy == SIMPLE_CAMERA_POLICY.EXPAND_CAMERA_VIEWPORT_ON_SCREEN_RESIZE) {
// this.setSize(this.scren_area.getWidth(), this.scren_area.getHeight());
					final double width = this.scren_area.getWidth();
					final double height = this.scren_area.getHeight();

					if (width <= 0) {
						L.e("Camera width must be > 0 ");
					}
					if (height <= 0) {
						L.e("Camera height must be > 0 ");
					}
// this.camera_vieport_zoomed.setSize(width, height);
// this.camera_vieport_original.setSize(width * this.zoom, height * this.zoom);
					this.camera_vieport_original.setSize(width, height);
// this.zoom = 1d;
					this.flagProjectionAndApertureUpdateNeeded();

				} else if (this.policy == SIMPLE_CAMERA_POLICY.FILL_SCREEN_APERTURE_WRAP) {
				} else {
					Err.throwNotImplementedYet();
				}
			}
			this.checkAperture();
		}

	}

	public boolean isWithinAperture (final Float2 screen_point) {
		return this.scren_apertured.containsPoint(screen_point);
	}

	private ArrayList<RedCamera> myStack;
	private int myIndex;

	@Override
	public void unProject (final Float2 temp_point) {
		this.checkAperture();
		this.scren_area.toRelative(temp_point);
		this.camera_projection.toAbsolute(temp_point);
	}

	@Override
	public void project (final Float2 temp_point) {
		this.checkAperture();
		this.camera_projection.toRelative(temp_point);
		this.scren_area.toAbsolute(temp_point);
	}

	private void checkAperture () {
		if (this.projection_and_aperture_needs_update) {
			this.updateAperture();
			this.projection_and_aperture_needs_update = false;
		}
	}

	private double margin_height;
	private double margin_width;

	private void updateAperture () {

		if (this.camera_vieport_original.getHeight() == 0) {
			Err.reportError("Camera height == 0");
		}

		if (this.camera_vieport_original.getWidth() == 0) {
			Err.reportError("Camera width == 0");
		}

		if (this.scren_area.getHeight() == 0) {
			Err.reportError("Screen height == 0");
		}

		if (this.scren_area.getWidth() == 0) {
			Err.reportError("Screen width == 0");
		}

		this.camera_vieport_zoomed.setOriginRelativeX(this.camera_vieport_original.getOriginRelativeX());
		this.camera_vieport_zoomed.setOriginRelativeY(this.camera_vieport_original.getOriginRelativeY());
		this.camera_vieport_zoomed.setPosition(this.camera_vieport_original.getPosition());
		this.camera_vieport_zoomed.setWidth(this.camera_vieport_original.getWidth() / this.zoom);
		this.camera_vieport_zoomed.setHeight(this.camera_vieport_original.getHeight() / this.zoom);

		if (this.manager != null) {
			this.setupApertureToWrapCamera();
			return;
		}

		if (this.policy == SIMPLE_CAMERA_POLICY.KEEP_ASPECT_RATIO_ON_SCREEN_RESIZE) {
			this.setupApertureToKeepAspectRatio();
		} else if (this.policy == SIMPLE_CAMERA_POLICY.FILL_SCREEN_APERTURE_WRAP) {
			this.setupApertureToWrapCamera();
		} else if (this.policy == SIMPLE_CAMERA_POLICY.EXPAND_CAMERA_VIEWPORT_ON_SCREEN_RESIZE) {
			this.setupApertureToKeepAspectRatio();
		} else {
			L.e("policy", this.policy);
			Err.throwNotImplementedYet();
		}
	}

	private void setupApertureToWrapCamera () {

		this.camera_projection.setWidth(this.scren_area.getWidth());
		this.camera_projection.setHeight(this.scren_area.getHeight());

		this.scren_apertured.setWidth(this.camera_vieport_zoomed.getWidth());
		this.scren_apertured.setHeight(this.camera_vieport_zoomed.getHeight());

		final double margin_X = this.camera_projection.getWidth() - this.camera_vieport_zoomed.getWidth();
		final double margin_Y = this.camera_projection.getHeight() - this.camera_vieport_zoomed.getHeight();

		this.scren_apertured.setPositionX(margin_X / 2d);
		this.scren_apertured.setPositionY(margin_Y / 2d);

		this.camera_projection.setOriginRelativeX(0);
		this.camera_projection.setOriginRelativeY(0);

		this.camera_projection.setPositionX(this.camera_vieport_zoomed.getTopLeftCorner().getX() - margin_X / 2d);
		this.camera_projection.setPositionY(this.camera_vieport_zoomed.getTopLeftCorner().getY() - margin_Y / 2d);
		this.camera_projection.setOriginAbsolute(this.camera_vieport_zoomed.getPosition());

	}

	private void setupApertureToKeepAspectRatio () {
		final double screen_w2h = this.scren_area.getWidth() / this.scren_area.getHeight();
		final double camera_w2h = this.camera_vieport_zoomed.getWidth() / this.camera_vieport_zoomed.getHeight();

		if (screen_w2h < camera_w2h) {// margin_top+bottom
			final double projection_factor = this.scren_area.getWidth() / this.camera_vieport_zoomed.getWidth();
			final double anti_projection_factor = this.camera_vieport_zoomed.getWidth() / this.scren_area.getWidth();

			this.scren_apertured.setWidth(this.scren_area.getWidth());
			this.scren_apertured.setHeight(this.camera_vieport_zoomed.getHeight() * projection_factor);
			final double screen_margin = this.scren_area.getHeight() - this.scren_apertured.getHeight();

			this.scren_apertured.setPositionX(0);
			this.scren_apertured.setPositionY(screen_margin / 2);

			this.camera_projection.setWidth(this.camera_vieport_zoomed.getWidth());
			this.camera_projection.setHeight(this.scren_area.getHeight() * anti_projection_factor);

			final double camera_margin = this.camera_projection.getHeight() - this.camera_vieport_zoomed.getHeight();

			this.camera_projection.setOriginRelativeX(this.camera_vieport_zoomed.getOriginRelativeX());

			final double vp_or_distance = this.camera_vieport_zoomed.getOriginRelativeY() * this.camera_vieport_zoomed.getHeight();
			final double pj_or_distance = vp_or_distance + camera_margin / 2;

			final double pj_orY_relative = pj_or_distance / this.camera_projection.getHeight();

			this.camera_projection.setOriginRelativeY(pj_orY_relative);

			this.camera_projection.setPosition(this.camera_vieport_zoomed.getPosition());

		} else {// margin_sides
			final double projection_factor = this.scren_area.getHeight() / this.camera_vieport_zoomed.getHeight();
			final double anti_projection_factor = this.camera_vieport_zoomed.getHeight() / this.scren_area.getHeight();

			this.scren_apertured.setHeight(this.scren_area.getHeight());
			this.scren_apertured.setWidth(this.camera_vieport_zoomed.getWidth() * projection_factor);
			final double screen_margin = this.scren_area.getWidth() - this.scren_apertured.getWidth();

			this.scren_apertured.setPositionX(screen_margin / 2);
			this.scren_apertured.setPositionY(0);

			this.camera_projection.setHeight(this.camera_vieport_zoomed.getHeight());
			this.camera_projection.setWidth(this.scren_area.getWidth() * anti_projection_factor);

			final double camera_margin = this.camera_projection.getWidth() - this.camera_vieport_zoomed.getWidth();

			this.camera_projection.setOriginRelativeY(this.camera_vieport_zoomed.getOriginRelativeY());

			final double vp_or_distance = this.camera_vieport_zoomed.getOriginRelativeX() * this.camera_vieport_zoomed.getWidth();
			final double pj_or_distance = vp_or_distance + camera_margin / 2;

			final double pj_orY_relative = pj_or_distance / this.camera_projection.getWidth();

			this.camera_projection.setOriginRelativeX(pj_orY_relative);

			this.camera_projection.setPosition(this.camera_vieport_zoomed.getPosition());
		}
		if (this.debug_mode) {
			L.d("scren_area", this.scren_area);
			L.d("camera_vieport", this.camera_vieport_zoomed);
			//
			L.d("camera_projection", this.camera_projection);
			L.d("scren_apertured", this.scren_apertured);
			L.d();
		}
	}

	final ID asset_id = RenderMachine.DefaultAssets().BLACK();
	private String camera_name;

	public void renderAperture () {
		this.checkAperture();

		// RenderMachine.beginShapesMode(RENDER_MODE.RASTER);
		if (this.debug_mode) {
			RenderMachine.beginRasterMode(TEXTURE_BLEND_MODE.Normal, this.aperture_opacity * 0.3);
		} else {
			RenderMachine.beginRasterMode(TEXTURE_BLEND_MODE.Normal, this.aperture_opacity);
		}
		RenderMachine.drawAperture(this.scren_apertured.getTopLeftCorner().transformed().getX(),
			this.scren_apertured.getTopLeftCorner().transformed().getY(),
			this.scren_apertured.getBottomRightCorner().transformed().getX(),
			this.scren_apertured.getBottomRightCorner().transformed().getY(), this.asset_id);
		//
		RenderMachine.endRasterMode(TEXTURE_BLEND_MODE.Normal);
		// RenderMachine.endShapesMode(RENDER_MODE.RASTER);
		if (this.debug_mode) {
			this.debug_component.doDraw();
		}
	}

	@Override
	public double getWidth () {
		return this.camera_vieport_zoomed.getWidth();
	}

	@Override
	public double getHeight () {
		return this.camera_vieport_zoomed.getHeight();
	}

	@Override
	public CanvasPosition getPosition () {
		this.flagProjectionAndApertureUpdateNeeded();
		return this.camera_vieport_original.getPosition();
	}

	@Override
	public Angle getRotation () {
		return this.camera_vieport_original.getRotation();
	}

	@Override
	public double getOriginRelativeX () {
		return this.camera_vieport_original.getOriginRelativeX();
	}

	@Override
	public double getOriginRelativeY () {
		return this.camera_vieport_original.getOriginRelativeY();
	}

	@Override
	public void setOriginRelative (final double or_x, final double or_y) {
		this.camera_vieport_original.setOriginRelative(or_x, or_y);
		this.flagProjectionAndApertureUpdateNeeded();
	}

	@Override
	public void setPosition (final double x, final double y) {
		this.camera_vieport_original.setPosition(x, y);
		this.flagProjectionAndApertureUpdateNeeded();
	}

	@Override
	public void setRotation (final double Z_rot) {
		this.camera_vieport_original.setRotation(Z_rot);
		this.flagProjectionAndApertureUpdateNeeded();
	}

	@Override
	public void setSize (final double width, final double height) {
		if (width <= 0) {
			L.e("Camera width must be > 0 ");
		}
		if (height <= 0) {
			L.e("Camera height must be > 0 ");
		}
		this.camera_vieport_original.setSize(width, height);
// this.camera_vieport_original_size.setXY(width, height);
		this.zoom = 1d;
		this.flagProjectionAndApertureUpdateNeeded();
	}

	private void flagProjectionAndApertureUpdateNeeded () {
		this.projection_and_aperture_needs_update = true;
	}

// -UTIL-----------------------------------------------------------------------

	@Override
	public void setZoom (final double zoom) {
		this.zoom = zoom;
		this.flagProjectionAndApertureUpdateNeeded();
	}

	@Override
	public double getZoom () {
		return this.zoom;
	}

	public CameraProjection getCameraProjection () {
		return this;
	}

	@Override
	public void setWidth (final double width) {
		this.setSize(width, this.getHeight());
	}

	@Override
	public void setHeight (final double height) {
		this.setSize(this.getWidth(), height);
	}

	@Override
	public void setDebugFlag (final boolean b) {
		this.debug_mode = b;
	}

	@Override
	public void setOriginRelative (final ORIGIN_RELATIVE_HORIZONTAL orX, final ORIGIN_RELATIVE_VERTICAL orY) {
		this.setOriginRelative(orX.relative_value, orX.relative_value);
	}

	@Override
	public void setOriginRelativeX (final ORIGIN_RELATIVE_HORIZONTAL orX) {
		this.setOriginRelativeX(orX.relative_value);
	}

	@Override
	public void setOriginRelativeY (final ORIGIN_RELATIVE_VERTICAL orY) {
		this.setOriginRelativeY(orY.relative_value);
	}

	@Override
	public void setOriginRelativeX (final double ORIGIN_POSITION_HORIZONTAL) {
		this.setOriginRelative(ORIGIN_POSITION_HORIZONTAL, this.getOriginRelativeY());
	}

	@Override
	public void setOriginRelativeY (final double ORIGIN_POSITION_VERTICAL) {
		this.setOriginRelative(this.getOriginRelativeX(), ORIGIN_POSITION_VERTICAL);
	}

	@Override
	public void setRotation (final Angle Z_rot) {
		this.setRotation(Z_rot.toRadians());
	}

	@Override
	public void setPosition (final ReadOnlyFloat2 newPostition) {
		this.setPosition(newPostition.getX(), newPostition.getY());
	}

	@Override
	public void setApertureOpacity (final double d) {
		this.aperture_opacity = d;
	}

	@Override
	public double getApertureOpacity () {
		return this.aperture_opacity;
	}

	@Override
	public void setPositionX (final double camera_x) {
		this.setPosition(camera_x, this.getPositionY());
	}

	@Override
	public double getPositionY () {
		return this.getPosition().getY();
	}

	@Override
	public void setPositionY (final double camera_y) {
		this.setPosition(this.getPositionX(), camera_y);
	}

	@Override
	public double getPositionX () {
		return this.getPosition().getX();
	}

	public int getScreenAperturedX () {
		return (int)FloatMath.round(this.scren_apertured.getPositionX());
	}

	public int getScreenAperturedY () {
		return (int)FloatMath.round(this.scren_apertured.getPositionY());
	}

	public int getScreenAperturedWidth () {
		return (int)FloatMath.round(this.scren_apertured.getWidth());
	}

	public int getScreenAperturedHeight () {
		return (int)FloatMath.round(this.scren_apertured.getHeight());
	}

	@Override
	public void setDebugName (final String camera_name) {
		this.camera_name = camera_name;
	}

	@Override
	public String getDebugName () {
		return this.camera_name;
	}

	public void setStack (final ArrayList<RedCamera> cameras_stack) {
		this.myStack = cameras_stack;
		this.myIndex = cameras_stack.size() - 1;
	}

	public void removeStack () {
		this.myStack = null;
		this.myIndex = -1;
	}

	@Override
	final public Projection projection () {
		return this;
	}

	@Override
	public void setPosition () {
		this.setPosition(0, 0);
	}

}
