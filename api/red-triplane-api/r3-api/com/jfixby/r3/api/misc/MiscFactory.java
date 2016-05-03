
package com.jfixby.r3.api.misc;

import com.jfixby.r3.api.ui.unit.progress.ProgressBar;

public interface MiscFactory {

	ProgressBarSpecs newProgressBarSpecs ();

	ProgressBar newProgressBar (ProgressBarSpecs progress_bar_specs);

}
