
package com.jfixby.r3.physics.duplex;

import com.jfixby.r3.api.physics.BoxShape;
import com.jfixby.scarabei.api.collections.List;

public class DuplexBoxShape implements BoxShape {

	private List<BoxShape> list;

	public DuplexBoxShape (List<BoxShape> list) {
		this.list = list;
	}

	@Override
	public void setSize (double width, double height) {
		for (int i = 0; i < list.size(); i++) {
			list.getElementAt(i).setSize(width, height);
		}
	}

	@Override
	public void setWidth (double width) {
		for (int i = 0; i < list.size(); i++) {
			list.getElementAt(i).setWidth(width);
		}
	}

	@Override
	public void setHeight (double height) {
		for (int i = 0; i < list.size(); i++) {
			list.getElementAt(i).setHeight(height);
		}
	}

	@Override
	public double getWidth () {
		return list.getElementAt(0).getWidth();
	}

	@Override
	public double getHeight () {
		return list.getElementAt(0).getHeight();
	}

}
