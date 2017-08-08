
package com.jfixby.r3.rana.red.pkg.bank;

import com.jfixby.r3.rana.api.pkg.FileSystemBankSettings;
import com.jfixby.r3.rana.api.pkg.PackagesManagerConfig;
import com.jfixby.r3.rana.api.pkg.RemoteBankSettings;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;

public class RedPackagesManagerConfig implements PackagesManagerConfig {

	protected final List<RemoteBankSettings> remoteBanksToDepoloy = Collections.newList();
	protected final List<FileSystemBankSettings> localBanks = Collections.newList();

	@Override
	public Collection<FileSystemBankSettings> localBanks () {
		return this.localBanks;
	}

	@Override
	public Collection<RemoteBankSettings> remoteBanks () {
		return this.remoteBanksToDepoloy;
	}

}
