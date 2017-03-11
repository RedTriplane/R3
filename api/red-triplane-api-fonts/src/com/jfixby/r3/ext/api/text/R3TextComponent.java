
package com.jfixby.r3.ext.api.text;

import com.jfixby.rana.api.pkg.PackageReader;

public interface R3TextComponent {

	PackageReader getStringsPackageReader ();

	PackageReader getTextPackageReader ();

	PackageReader getTTFFontPackageReader ();

}
