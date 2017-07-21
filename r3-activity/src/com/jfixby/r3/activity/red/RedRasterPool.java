
package com.jfixby.r3.activity.red;

import com.jfixby.r3.activity.api.ComponentsFactory;
import com.jfixby.r3.activity.api.LayerBasedComponent;
import com.jfixby.r3.activity.api.layer.Layer;
import com.jfixby.r3.activity.api.raster.Raster;
import com.jfixby.r3.activity.api.raster.RasterPool;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;

public class RedRasterPool implements RasterPool, LayerBasedComponent {

	private final Layer root;
	private final ID assetID;
	private final RedComponentsFactory master;

	public RedRasterPool (final RedComponentsFactory master, final ID assetID) {
		this.assetID = assetID;
		this.master = master;
		this.root = this.master.newLayer();
	}

	@Override
	public Layer getRoot () {
		return this.root;
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
	public ID getAssetID () {
		return this.assetID;
	}

	@Override
	public ComponentsFactory getComponentsFactory () {
		return this.master;
	}

	final List<Raster> freeElements = Collections.newList();

	@Override
	public Raster newInstance () {
		if (this.freeElements.size() == 0) {
			final Raster element = this.master.getRasterDepartment().newRaster(this.assetID);
			this.root.attachComponent(element);
			this.freeElements.add(element);
		}
		final Raster element = this.freeElements.removeElementAt(0);
		return element;
	}

}
