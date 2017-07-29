
package org.box2d.r3.jbox2d.f;

import org.box2d.r3.api.MassData;

public class JMassData implements MassData {

	final private org.jbox2d.f.dynamics.Body gdx_body;
	org.jbox2d.f.collision.shapes.MassData mass_data = new org.jbox2d.f.collision.shapes.MassData();

	public JMassData (final org.jbox2d.f.dynamics.Body gdx_body) {
		this.gdx_body = gdx_body;
	}

	@Override
	public void setMass (final double mass) {
		this.gdx_body.getMassData(this.mass_data);
		this.mass_data.mass = (float)mass;
		this.gdx_body.setMassData(this.mass_data);
	}

	@Override
	public void setI (final double i) {
		this.gdx_body.getMassData(this.mass_data);
		this.mass_data.I = (float)i;
		this.gdx_body.setMassData(this.mass_data);
	}

	public void set (final MassData mass_data) {
		this.setI(mass_data.getI());
		this.setMass(mass_data.getMass());
	}

	@Override
	public double getI () {
		this.gdx_body.getMassData(this.mass_data);
		return this.mass_data.I;
	}

	@Override
	public double getMass () {
		this.gdx_body.getMassData(this.mass_data);
		return this.mass_data.mass;
	}
}
