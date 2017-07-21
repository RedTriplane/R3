
package com.jfixby.r3.activity.api.locale;

import com.jfixby.r3.activity.api.layer.Component;

public interface LocalizedComponent extends Component {

	void setLocaleName (String locale_name);

	public String getLocaleName ();

}
