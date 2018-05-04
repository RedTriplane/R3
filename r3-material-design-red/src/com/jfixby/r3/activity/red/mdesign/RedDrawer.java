
package com.jfixby.r3.activity.red.mdesign;

import com.jfixby.r3.activity.api.LayerBasedComponent;
import com.jfixby.r3.activity.api.camera.ScreenDimentions;
import com.jfixby.r3.activity.api.layer.Layer;
import com.jfixby.r3.activity.api.layer.VisibleComponent;
import com.jfixby.r3.activity.api.raster.Raster;
import com.jfixby.r3.activity.api.user.MouseInputEventListener;
import com.jfixby.r3.activity.api.user.ScreenChangeListener;
import com.jfixby.r3.activity.red.layers.RedLayer;
import com.jfixby.r3.material.api.DrawerSpecs;
import com.jfixby.r3.material.api.btn.Button;
import com.jfixby.r3.material.api.btn.ButtonSpecs;
import com.jfixby.r3.material.api.btn.Drawer;
import com.jfixby.scarabei.api.log.L;

public class RedDrawer implements Drawer, VisibleComponent, LayerBasedComponent {

	private final RedLayer root;
	private final Raster drawer_background;
	private final Raster left_icon;
	private final Raster top_bar_background;
	private final int top_bar_height;
	private final ScreenChangeListener listener = new ScreenChangeListener() {

		@Override
		public void onScreenChanged (final ScreenDimentions viewport_update) {
			RedDrawer.this.updateDimentions(viewport_update);
		}

	};
	private final Button top_left_button;
	private final MouseInputEventListener top_left_btn_litener = new MouseInputEventListener() {};

	private void updateDimentions (final ScreenDimentions viewport_update) {
		L.d("viewport_update", viewport_update);
		this.drawer_background.setPosition();
		final double width = viewport_update.getScreenWidth();
		this.drawer_background.setWidth(width);
		this.drawer_background.setHeight(this.top_bar_height);
	}

	public RedDrawer (final DrawerSpecs mtds) {
		this.root = master.newLayer();

		this.root.attachComponent(this.listener);

		this.drawer_background = mtds.drawer_background;
		this.root.attachComponent(this.drawer_background);

		this.left_icon = mtds.left_icon;

		final ButtonSpecs btnspc = master.getUserInputDepartment().newButtonSpecs();
		btnspc.setOnPressedRaster(this.left_icon);
		btnspc.setOnReleasedRaster(this.left_icon);

		this.top_left_button = master.getUserInputDepartment().newButton(btnspc);
		this.top_left_button.setDebugRenderFlag(true);
		this.top_left_button.setInputListener(this.top_left_btn_litener);

		this.top_bar_background = mtds.top_bar_background;
		this.top_bar_height = mtds.top_bar_height;
	}

	@Override
	public void hide () {
		this.root.hide();
	}

	@Override
	public void show () {
		this.root.show();
	}

	@Override
	public boolean isVisible () {
		return this.root.isVisible();
	}

	@Override
	public void setVisible (final boolean b) {
		this.root.setVisible(b);
	}

	@Override
	public void setName (final String name) {
		this.root.setName(name);

	}

	@Override
	public String getName () {
		return this.root.getName();
	}

	@Override
	public Layer getRoot () {
		return this.root;
	}

}
