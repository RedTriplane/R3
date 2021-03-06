
package com.jfixby.r3.rana.api.pkg;

import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.names.ID;

public class PackerSpecs {

	public File packageFolder;
	public final Set<File> packedFiles = Collections.newSet();
	public final Set<ID> packedAssets = Collections.newSet();
	public final Set<ID> requiredAssets = Collections.newSet();
	public String rootFileName;
	public String packageFormat;
	public String version;

}
