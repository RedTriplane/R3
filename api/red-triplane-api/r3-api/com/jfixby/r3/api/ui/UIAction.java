
package com.jfixby.r3.api.ui;

import com.jfixby.r3.api.ui.unit.UnitFunctionality;

public interface UIAction<T extends UnitFunctionality> {

	public void perform (T ui);

}
