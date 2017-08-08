
package com.jfixby.r3.rana.api.pkg;

import com.jfixby.scarabei.api.collections.Collection;

public interface PackagesManagerConfig {

	public Collection<FileSystemBankSettings> localBanks ();

	public Collection<RemoteBankSettings> remoteBanks ();

}
