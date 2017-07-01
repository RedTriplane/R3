
package com.jfixby.r3.api.unit;

import com.jfixby.scarabei.api.assets.ID;

public interface UnitOperatorComponent {

	UnitHandler deployUnit (ID unitID);

	UnitHandler push (UnitHandler unitHandler);

}
