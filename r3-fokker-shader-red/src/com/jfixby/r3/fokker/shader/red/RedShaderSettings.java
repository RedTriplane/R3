
package com.jfixby.r3.fokker.shader.red;

import com.jfixby.r3.engine.api.render.ShaderSettings;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.collections.Mapping;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.java.LegacyFloat;
import com.jfixby.scarabei.api.java.LegacyInt;
import com.jfixby.scarabei.api.log.L;

public class RedShaderSettings implements ShaderSettings {
	private final Map<String, LegacyFloat> float_param_values = Collections.newMap();
	private final Map<String, LegacyInt> int_param_values = Collections.newMap();

	@Override
	public Rectangle shape () {
		return null;
	}

	@Override
	public Mapping<String, LegacyFloat> floatParams () {
		return this.float_param_values;
	}

	@Override
	public Mapping<String, LegacyInt> intParams () {
		return this.int_param_values;
	}

	@Override
	public final boolean hasParams () {
		return this.float_param_values.size() > 0 || this.int_param_values.size() > 0;
	}

	final public void setFloatParameterValue (final String parameter_name, final double value) {
		LegacyFloat float_value = this.float_param_values.get(parameter_name);
		if (float_value == null) {
			float_value = new LegacyFloat();
			this.float_param_values.put(parameter_name, float_value);
		}
		float_value.value = (float)value;
	}

	final public void setIntParameterValue (final String parameter_name, final long value) {
		LegacyInt float_value = this.int_param_values.get(parameter_name);
		if (float_value == null) {
			float_value = new LegacyInt();
			this.int_param_values.put(parameter_name, float_value);
		}
		float_value.value = (int)value;
	}

	public double getFloatParameterValue (final String parameter_name) {
		LegacyFloat float_value = this.float_param_values.get(parameter_name);
		if (float_value == null) {
			float_value = new LegacyFloat();
			this.float_param_values.put(parameter_name, float_value);
		}
		return float_value.value;
	}

	public long getIntParLegacyIntterValue (final String parameter_name) {
		LegacyInt value = this.int_param_values.get(parameter_name);
		if (value == null) {
			value = new LegacyInt();
			this.int_param_values.put(parameter_name, value);
		}
		return value.value;
	}

	public void printParameterValues () {
		L.d("Shader parameters list:");
		for (int i = 0; i < this.float_param_values.size(); i++) {
			final LegacyFloat value = this.float_param_values.getValueAt(i);
			final String param_name = this.float_param_values.getKeyAt(i);
			L.d("   float: " + param_name, value);
		}

		for (int i = 0; i < this.int_param_values.size(); i++) {
			final LegacyInt value = this.int_param_values.getValueAt(i);
			final String param_name = this.int_param_values.getKeyAt(i);
			L.d("     int: " + param_name, value);
		}
	}

}
