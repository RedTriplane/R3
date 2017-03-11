
package com.jfixby.r3.api;

import com.jfixby.r3.api.logic.GameStarter;

public interface RedTriplaneComponent {

	EngineVersion VERSION ();

	void setGameStarter (GameStarter starter);

	GameStarter getGameStarter ();

}
