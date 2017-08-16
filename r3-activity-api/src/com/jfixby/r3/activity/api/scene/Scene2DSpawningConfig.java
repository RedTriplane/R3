
package com.jfixby.r3.activity.api.scene;

import com.jfixby.scarabei.api.names.ID;
import com.jfixby.scarabei.api.sys.Sys;
import com.jfixby.scarabei.api.time.TimeStream;

public class Scene2DSpawningConfig {

	public ID structureID;

	public String defaultLocaleName = "русский";

	public float debugOpacity = 1f;

	public boolean renderDebugInfo;

	public TimeStream animationsTimeStream = Sys.SystemTime();

}
