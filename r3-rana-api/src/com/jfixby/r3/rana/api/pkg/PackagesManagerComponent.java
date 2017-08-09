
package com.jfixby.r3.rana.api.pkg;

import java.io.IOException;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.promise.Promise;

public interface PackagesManagerComponent {

	Promise<PackagesManagerConfig> readPackagesManagerConfig ();

	Promise<Collection<FileSystemBankSettings>> findBanks (File assets_folder);

	Promise<Collection<FileSystemBankSettings>> findBanks (RemoteBankSettings remoteBankSettings, final File cacheFolder);

	Promise<Collection<FileSystemBankSettings>> findBanks (Collection<RemoteBankSettings> remoteBankSettings);

	Promise<Collection<PackagesBank>> loadBanks (Collection<FileSystemBankSettings> localBanks);

	Promise<PackagesBank> loadBank (FileSystemBankSettings bankSettings);

	Promise<Collection<PackagesBank>> deploy (File assets_folder);

	Collection<PackagesBank> listInstalledBanks ();

// Promise<Collection<PackagesBank>> findAndInstallResources (File assets_folder);

	void installBanks (Collection<PackagesBank> resources);

	void installBank (PackagesBank bank);

// Promise<Collection<PackagesBank>> loadAssetsFolder (File assets_folder);

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
