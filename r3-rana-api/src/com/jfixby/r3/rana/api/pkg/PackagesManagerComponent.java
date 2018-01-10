
package com.jfixby.r3.rana.api.pkg;

import java.io.IOException;

import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.names.ID;

public interface PackagesManagerComponent {

	PackagesManagerConfig readPackagesManagerConfig () throws IOException;

	Collection<FileSystemBankSettings> findBanks (File assets_folder) throws IOException;

	Collection<FileSystemBankSettings> findBanks (RemoteBankSettings remoteBankSettings, final File cacheFolder)
		throws IOException;

	Collection<FileSystemBankSettings> findBanks (Collection<RemoteBankSettings> remoteBankSettings) throws IOException;

	Collection<PackagesBank> loadBanks (Collection<FileSystemBankSettings> localBanks) throws IOException;

	PackagesBank loadBank (FileSystemBankSettings bankSettings) throws IOException;

	Collection<PackagesBank> deploy (File assets_folder) throws IOException;

	Collection<PackagesBank> listInstalledBanks ();

// Collection<PackagesBank> findAndInstallResources (File assets_folder);

	void installBanks (Collection<PackagesBank> resources);

	void installBank (PackagesBank bank);

// Collection<PackagesBank> loadAssetsFolder (File assets_folder);

// PackageFormat newPackageFormat (String format_name);

// Collection<PackageReader> findPackageReaders (PackageFormat format);
//
// void registerPackageReader (PackageReader loader);

// Collection<PackageFormat> listAcceptablePackageFormats ();

// void printInstalledPackageReaders ();

	AssetsTankSpecs newResourceSpecs ();

	PackagesTank newResource (AssetsTankSpecs specs) throws IOException;

// void updateAll (ResourceRebuildIndexListener listener);

	void printAllResources ();

	void printAllIndexes ();

	PackagesBank getBank (ID name);

	PackageSearchResult findPackages (PackageSearchParameters search_params);

	void printAllPackages ();

}
