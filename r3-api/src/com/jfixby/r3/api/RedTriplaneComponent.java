
package com.jfixby.r3.api;

public interface RedTriplaneComponent {

	EngineVersion VERSION ();

	void setGameStarter (GameStarter starter);

	GameStarter getGameStarter ();

}
