package org.box2d.r3.jbox2d.d;

import org.box2d.r3.api.MassData;

public class JMassData implements MassData {

	final private org.jbox2d.d.dynamics.Body gdx_body;
	org.jbox2d.d.collision.shapes.MassData mass_data = new org.jbox2d.d.collision.shapes.MassData();

	public JMassData(org.jbox2d.d.dynamics.Body gdx_body) {
		this.gdx_body = gdx_body;
	}

	@Override
	public void setMass(double mass) {
		this.gdx_body.getMassData(mass_data);
		mass_data.mass =  mass;
		this.gdx_body.setMassData(mass_data);
	}

	@Override
	public void setI(double i) {
		this.gdx_body.getMassData(mass_data);
		mass_data.I =  i;
		this.gdx_body.setMassData(mass_data);
	}

	public void set(MassData mass_data) {
		this.setI(mass_data.getI());
		this.setMass(mass_data.getMass());
	}

	@Override
	public double getI() {
		this.gdx_body.getMassData(mass_data);
		return mass_data.I;
	}

	@Override
	public double getMass() {
		this.gdx_body.getMassData(mass_data);
		return mass_data.mass;
	}
}
