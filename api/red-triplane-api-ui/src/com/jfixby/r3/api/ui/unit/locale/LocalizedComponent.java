
package com.jfixby.r3.api.ui.unit.locale;

import com.jfixby.r3.api.ui.unit.layer.Component;

public interface LocalizedComponent extends Component {

	void setLocaleName (String locale_name);

	public String getLocaleName ();

}
