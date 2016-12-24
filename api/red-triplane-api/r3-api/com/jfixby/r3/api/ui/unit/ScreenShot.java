
package com.jfixby.r3.api.ui.unit;

import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.image.ColorMap;

public interface ScreenShot {

	ColorMap toColorMap ();

	void saveToFile (File screenSHotFile);

}
