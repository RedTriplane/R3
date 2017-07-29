
package com.jfixby.r3.fokker;

import com.jfixby.r3.engine.api.EngineVersion;
import com.jfixby.r3.engine.api.RedTriplaneComponent;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.debug.Debug;

public class Fokker implements RedTriplaneComponent {

	private static final FokkerVersion VERSION = new FokkerVersion("Fokker", "#170707", "https://github.com/RedTriplane/Fokker");
	private ID starter;

	@Override
	public EngineVersion VERSION () {
		return VERSION;
	}

	@Override
	public void setGameStarter (final ID starter) {
		Debug.checkNull("EngineStarter", starter);
		this.starter = starter;
	}

	@Override
	public ID getGameStarter () {
		return this.starter;
	}

}
