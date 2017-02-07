package com.jfixby.r3.api.ui.unit.camera;

import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.scarabei.api.math.Angle;

public interface Camera {

	public static final float ABSOLUTE_DARKNESS = 1f;
	public static final float ABSOLUTE_CLEAR = 0f;

	public void setSize(double width, double height);

	public CanvasPosition getPosition();

	public void setPosition(ReadOnlyFloat2 newPostition);

	public void setPosition(double x, double y);

	public void setWidth(double width);

	public void setHeight(double height);

	public void setZoom(double zoom);

	public double getZoom();

	public void setRotation(Angle Z_rot);

	public void setRotation(double Z_rot);

	public Angle getRotation();

	public double getWidth();

	public double getHeight();

	public CameraProjection getCameraProjection();

	void setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL orX, ORIGIN_RELATIVE_VERTICAL orY);

	void setOriginRelativeX(ORIGIN_RELATIVE_HORIZONTAL orX);

	void setOriginRelativeY(ORIGIN_RELATIVE_VERTICAL orY);

	void setOriginRelative(double ORIGIN_POSITION_HORIZONTAL, double ORIGIN_POSITION_VERTICAL);

	void setOriginRelativeX(double ORIGIN_POSITION_HORIZONTAL);

	void setOriginRelativeY(double ORIGIN_POSITION_VERTICAL);

	public double getOriginRelativeX();

	public double getOriginRelativeY();

	public void setDebugFlag(boolean b);

	public void setApertureOpacity(double d);

	public double getApertureOpacity();

	public void setPositionX(double camera_x);

	public void setPositionY(double camera_y);

	public double getPositionX();

	public double getPositionY();

	public void setDebugName(String camera_name);

	public String getDebugName();

}
