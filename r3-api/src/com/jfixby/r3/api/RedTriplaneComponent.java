
package com.jfixby.r3.api;

import com.jfixby.scarabei.api.assets.ID;

public interface RedTriplaneComponent {

	EngineVersion VERSION ();

	void setGameStarter (ID starter);

	ID getGameStarter ();

}
