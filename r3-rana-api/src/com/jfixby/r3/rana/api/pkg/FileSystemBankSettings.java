
package com.jfixby.r3.rana.api.pkg;

import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.file.File;

public class FileSystemBankSettings {
	public String name;
	public File bankFolder;
	public File cacheFolder;
	public final Set<String> tanks = Collections.newSet();

}
