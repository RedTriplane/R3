
package com.jfixby.r3.physics.duplex;

import com.jfixby.r3.api.physics.BodyPositionController;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.math.Angle;

public class DuplexLocation implements BodyPositionController {

	private List<BodyPositionController> list;

	public DuplexLocation (List<BodyPositionController> list) {
		this.list = list;
	}

	@Override
	public Angle getRotation () {
		return list.getElementAt(0).getRotation();
	}

	@Override
	public double getVelocityX () {
		return list.getElementAt(0).getVelocityX();
	}

	@Override
	public double getVelocityY () {
		return list.getElementAt(0).getVelocityY();
	}

	@Override
	public double getX () {
		return list.getElementAt(0).getX();
	}

	@Override
	public double getY () {
		return list.getElementAt(0).getY();
	}

	@Override
	public void setY (double y) {
		for (int i = 0; i < list.size(); i++) {
			list.getElementAt(i).setY(y);
		}
	}

	@Override
	public void setX (double x) {
		for (int i = 0; i < list.size(); i++) {
			list.getElementAt(i).setX(x);
		}
	}

	@Override
	public void setVelocity (double vx, double vy) {
		for (int i = 0; i < list.size(); i++) {
			list.getElementAt(i).setVelocity(vx, vy);
		}
	}

	@Override
	public void setRotation (Angle angle) {
		for (int i = 0; i < list.size(); i++) {
			list.getElementAt(i).setRotation(angle);
		}
	}

	@Override
	public void setPosition (double x, double y) {
		for (int i = 0; i < list.size(); i++) {
			list.getElementAt(i).setPosition(x, y);
		}
	}

}
