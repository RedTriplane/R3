package com.jfixby.r3.api.ui.unit.shader;

import com.jfixby.r3.api.ui.unit.layer.VisibleComponent;

public interface ShaderComponent extends VisibleComponent {

    void setFloatParameterValue(String name, double value);

}
