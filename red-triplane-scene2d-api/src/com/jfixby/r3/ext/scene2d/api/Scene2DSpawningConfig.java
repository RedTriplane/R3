
package com.jfixby.r3.ext.scene2d.api;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.sys.Sys;
import com.jfixby.scarabei.api.time.TimeStream;

public class Scene2DSpawningConfig {

	public ID structureID;

	public String defaultLocaleName = "русский";

	public float debugOpacity = 1f;

	public boolean renderDebugInfo;

	public TimeStream animationsTimeStream = Sys.SystemTime();

}
