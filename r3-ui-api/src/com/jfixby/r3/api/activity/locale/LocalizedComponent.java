
package com.jfixby.r3.api.activity.locale;

import com.jfixby.r3.api.activity.layer.Component;

public interface LocalizedComponent extends Component {

	void setLocaleName (String locale_name);

	public String getLocaleName ();

}
