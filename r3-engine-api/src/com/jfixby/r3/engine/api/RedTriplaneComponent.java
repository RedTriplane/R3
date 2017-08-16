
package com.jfixby.r3.engine.api;

import com.jfixby.scarabei.api.names.ID;

public interface RedTriplaneComponent {

	EngineVersion VERSION ();

	void setGameStarter (ID starter);

	ID getGameStarter ();

}
