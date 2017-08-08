
package com.jfixby.r3.rana.api.pkg;

import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.net.http.HttpURL;

public class RemoteBankInstallArguments {
	public HttpURL bankUrl;
	public File assets_cache_folder;
	public Iterable<String> tanks;
}
