
package com.jfixby.r3.fokker.adaptor;

import com.jfixby.r3.engine.api.screen.ScreenComponent;
import com.jfixby.r3.engine.api.screen.ScreenDimentions;
import com.jfixby.r3.engine.api.screen.ScreenDimentionsChecker;

public class GdxAdaptorViewportState implements ScreenComponent, ScreenDimentions {

	private final GdxAdaptor master;
	private int actual_width = -1;
	private int actual_height = -1;
	private int stated_width = -1;
	private int stated_height = -1;

	private long update_cycle;
	boolean need_update = true;
	private double debugScaleFactor = 1d;

	public GdxAdaptorViewportState (final GdxAdaptor gdxAdaptor) {
		this.master = gdxAdaptor;
	}

	public void updateViewport (final int width, final int height) {
		this.actual_width = width;
		this.actual_height = height;
		this.stated_width = (int)(this.actual_width * this.debugScaleFactor);
		this.stated_height = (int)(this.actual_height * this.debugScaleFactor);
	}

	public void updateViewport () {
		this.updateViewport(this.actual_width, this.actual_height);
	}

	@Override
	public int getScreenWidth () {
		return this.stated_width;
	}

	@Override
	public int getScreenHeight () {
		return this.stated_height;
	}

	@Override
	public long getLastUpdateCycleNumber () {
		return this.update_cycle;
	}

	@Override
	public String toString () {
		return "ViewportState[" + this.actual_width + " x " + this.actual_height + "] : " + this.update_cycle + "";
	}

	public void checkNeedUpdateFlag (final long cycle) {
		if (this.need_update) {
			this.update_cycle = cycle;
			this.need_update = false;
		}
	}

	public void flagNeedUpdate () {
		this.need_update = true;
	}

	public boolean isValid () {
		return this.actual_height > 0 && this.actual_width > 0;
	}

	@Override
	public boolean isInValidState () {
		return this.isValid();
	}

	@Override
	public ScreenDimentionsChecker newScreenDimentionsChecker () {
		return new RedScreenDimentionsChecker();
	}

	@Override
	public ScreenDimentions getScreenDimensions () {
		return this;
	}

	@Override
	public double getWidthToHeightRatio () {
		return this.actual_width * 1d / this.actual_height;
	}

	@Override
	public void setDebugScaleFactor (final double debugScaleFactor) {
		this.debugScaleFactor = debugScaleFactor;
		this.updateViewport();
		this.flagNeedUpdate();

	}

	@Override
	public double getDebugScaleFactor () {
		return this.debugScaleFactor;
	}

}
