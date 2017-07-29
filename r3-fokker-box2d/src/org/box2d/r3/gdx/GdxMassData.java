
package org.box2d.r3.gdx;

import org.box2d.r3.api.MassData;

public class GdxMassData implements MassData {

	final private com.badlogic.gdx.physics.box2d.Body gdx_body;

	public GdxMassData (com.badlogic.gdx.physics.box2d.Body gdx_body) {
		this.gdx_body = gdx_body;
	}

	@Override
	public void setMass (double mass) {
		final com.badlogic.gdx.physics.box2d.MassData data = this.gdx_body.getMassData();
		data.mass = (float)mass;
		this.gdx_body.setMassData(data);
	}

	@Override
	public void setI (double i) {
		final com.badlogic.gdx.physics.box2d.MassData data = this.gdx_body.getMassData();
		data.I = (float)i;
		this.gdx_body.setMassData(data);
	}

	public void set (MassData mass_data) {
		this.setI(mass_data.getI());
		this.setMass(mass_data.getMass());
	}

	@Override
	public double getI () {
		final com.badlogic.gdx.physics.box2d.MassData data = this.gdx_body.getMassData();
		return data.I;
	}

	@Override
	public double getMass () {
		final com.badlogic.gdx.physics.box2d.MassData data = this.gdx_body.getMassData();
		return data.mass;
	}
}
