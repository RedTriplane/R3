
package com.jfixby.r3.activity.red.mdesign;

import com.jfixby.r3.activity.api.LayerBasedComponent;
import com.jfixby.r3.activity.api.layer.Layer;
import com.jfixby.r3.activity.api.mtdesign.Drawer;
import com.jfixby.r3.activity.red.RedComponentsFactory;
import com.jfixby.r3.activity.red.layers.RedLayer;

public class RedDrawer implements Drawer, LayerBasedComponent {

	private final RedComponentsFactory master;
	private final RedLayer root;

	public RedDrawer (final RedComponentsFactory master) {
		this.master = master;
		this.root = master.newLayer();
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
