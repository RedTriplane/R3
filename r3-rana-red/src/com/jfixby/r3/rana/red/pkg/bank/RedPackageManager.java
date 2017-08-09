
package com.jfixby.r3.rana.red.pkg.bank;

import java.io.IOException;

import com.jfixby.r3.rana.api.pkg.AssetsTankSpecs;
import com.jfixby.r3.rana.api.pkg.FileSystemBankSettings;
import com.jfixby.r3.rana.api.pkg.PackageSearchParameters;
import com.jfixby.r3.rana.api.pkg.PackageSearchResult;
import com.jfixby.r3.rana.api.pkg.PackagesBank;
import com.jfixby.r3.rana.api.pkg.PackagesManager;
import com.jfixby.r3.rana.api.pkg.PackagesManagerComponent;
import com.jfixby.r3.rana.api.pkg.PackagesManagerConfig;
import com.jfixby.r3.rana.api.pkg.PackagesTank;
import com.jfixby.r3.rana.api.pkg.RemoteBankSettings;
import com.jfixby.r3.rana.api.pkg.io.BankHeaderInfo;
import com.jfixby.r3.rana.api.pkg.io.cfg.HttpAssetsFolder;
import com.jfixby.r3.rana.api.pkg.io.cfg.LocalAssetsFolder;
import com.jfixby.r3.rana.api.pkg.io.cfg.PackageManagerConfig;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.assets.Names;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.CollectionConverter;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.FilesList;
import com.jfixby.scarabei.api.file.LocalFileSystem;
import com.jfixby.scarabei.api.json.Json;
import com.jfixby.scarabei.api.json.JsonString;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.net.http.Http;
import com.jfixby.scarabei.api.net.http.HttpFileSystem;
import com.jfixby.scarabei.api.net.http.HttpFileSystemSpecs;
import com.jfixby.scarabei.api.net.http.HttpURL;
import com.jfixby.scarabei.api.promise.Future;
import com.jfixby.scarabei.api.promise.Promise;
import com.jfixby.scarabei.api.taskman.TaskManager;

public class RedPackageManager implements PackagesManagerComponent {

// private static final boolean COLLECT_TANKS = true;
// private final File assets_cache_folder;
	boolean deployed = false;

	final Map<ID, PackagesBank> resources = Collections.newMap();

	public RedPackageManager (final RedResourcesManagerSpecs specs) {
		this.deployed = false;

// final Collection<RemoteBankSpecs> remotebanks = specs.remotebanks;
// for (final RemoteBankSpecs rbank : remotebanks) {
// final RemoteBankSettings element = new RemoteBankSettings();
// element.bankURL = Debug.checkNull("remote bank url", rbank.getUrl());
// element.tanks.addAll(rbank.listTanks());
// this.remoteBanksToDepoloy.add(element);
// }
	}

// public Promise<Void,Collection<PackagesBank>> findAndInstallResources (final File assets_folder) {
// final Promise<Void,Collection<PackagesBank>> resources = this.findBanks(assets_folder);
// final Collection<PackagesBank> list = resources.await();
// this.installBanks(list);
// return resources;
// }

	@Override
	public Promise<Void, PackagesManagerConfig> readPackagesManagerConfig (final File assets_cache_folder) {

		final Future<Void, PackagesManagerConfig> future = new Future<Void, PackagesManagerConfig>() {

			@Override
			public PackagesManagerConfig deliver (final Void v) throws Throwable {
				final RedPackagesManagerConfig config = new RedPackagesManagerConfig();
				final PackageManagerConfig local_config = RedPackageManager.this.loadConfigFile(LocalFileSystem.ApplicationHome())
					.await();
				if (local_config != null) {
					for (final LocalAssetsFolder folder : local_config.local_banks) {
						final String java_path = folder.path;
						final File dir = LocalFileSystem.newFile(java_path);

						final Collection<FileSystemBankSettings> locals = RedPackageManager.this.findBanks(dir).await();
						config.localBanks.addAll(locals);

					}

					for (final HttpAssetsFolder folder : local_config.remote_banks) {

						final List<String> tanks = Collections.newList(folder.tanks);
						// final HttpURL bankURL =
						// Http.newURL("https://s3.eu-central-1.amazonaws.com/com.red-triplane.assets/bank-tinto/");
						final HttpURL bankURL = Http.newURL(folder.bank_url);
						final RemoteBankSettings element = new RemoteBankSettings();
						element.bankURL = Debug.checkNull("remote bank url", bankURL);
						element.tanks.addAll(tanks);
						config.remoteBanksToDepoloy.add(element);

					}
				}

				return config;
			}
		};

		// final File resourcesConfigFile = LocalFileSystem.ApplicationHome().child(ResourcesConfigFile.FILE_NAME);
		return TaskManager.newPromise(future);
	}

	private Promise<Void, FileSystemBankSettings> findBank (final File bankFolder) throws IOException {
		final Future<Void, FileSystemBankSettings> bank = new Future<Void, FileSystemBankSettings>() {

			@Override
			public FileSystemBankSettings deliver (final Void v) throws Throwable {
				if (!bankFolder.exists()) {
					L.e("bank not found", bankFolder);
					return null;
				}
				final BankHeader bankHeader_ = RedPackageManager.this.findAndLoadBankHeader(bankFolder).await();

				if (bankHeader_ == null) {
					return null;
				}

				L.d("found bank", bankHeader_);

				final ID bank_name = Names.newID(bankHeader_.getName());
// final RedBank bank = new RedBank(bank_name);

				final FileSystemBankSettings bank = new FileSystemBankSettings();
				bank.name = bankHeader_.getName();
				bank.bankFolder = bankHeader_.getRoot();
				final FilesList tanks = bank.bankFolder.listSubFolders();
				final List<String> tankNames = Collections.newList();
				Collections.convertCollection(tanks, tankNames, new CollectionConverter<File, String>() {
					@Override
					public String convert (final File x) {
						return x.getName();
					}
				});
				bank.tanks.addAll(tankNames);

				return bank;
			}
		};

		return TaskManager.newPromise(bank);
	}

// public void removeResource (final Resource resource) {
// this.resources.remove(resource);
// }

	@Override
	public Promise<Void, Collection<FileSystemBankSettings>> findBanks (final RemoteBankSettings remoteBankSettings,
		final File cacheFolder) {

		final HttpURL bankUrl = remoteBankSettings.bankURL;
		final Iterable<String> tanks = remoteBankSettings.tanks;
		Debug.checkNull("bankUrl", bankUrl);
		Debug.checkNull("tanks", tanks);

		final HttpFileSystemSpecs specs = Http.newHttpFileSystemSpecs();

		final HttpURL url = bankUrl;
		specs.setRootUrl(url);
		specs.setCacheSize(200);

		final Future<Void, Collection<FileSystemBankSettings>> future = new Future<Void, Collection<FileSystemBankSettings>>() {

			@Override
			public Collection<FileSystemBankSettings> deliver (final Void v) throws Throwable {
				final HttpFileSystem fs = Http.newHttpFileSystem(specs);

				final File httpRemote = fs.ROOT();
				final Collection<FileSystemBankSettings> banks = RedPackageManager.this.findBanks(httpRemote).await();
				for (final FileSystemBankSettings bank : banks) {
					if (true) {
						final File bank_cache_folder = cacheFolder.child(bank.name);
						bank.cacheFolder = bank_cache_folder;
					}
				}
				return banks;
			}
		};

		return TaskManager.newPromise(future);

	}

	@Override
	public Promise<Void, Collection<FileSystemBankSettings>> findBanks (final File assets_folder) {
		final Future<Void, Collection<FileSystemBankSettings>> future = new Future<Void, Collection<FileSystemBankSettings>>() {
			@Override
			public Collection<FileSystemBankSettings> deliver (final Void v) throws Throwable {

				final List<FileSystemBankSettings> result = Collections.newList();

				{
					final FileSystemBankSettings bank = RedPackageManager.this.findBank(assets_folder).await();
					if (bank != null) {
						result.add(bank);
						return result;
					}
				}

				for (final File file : assets_folder.listDirectChildren()) {
					if (file.isFile()) {
						continue;
					}
					final FileSystemBankSettings bank = RedPackageManager.this.findBank(file).await();
					if (bank != null) {
						result.add(bank);
					}
				}
				return result;
			}

		};

		return TaskManager.newPromise(future);
	}

	@Override
	public PackageSearchResult findPackages (final PackageSearchParameters search_params) {
		final RedPackageSearchResult result = new RedPackageSearchResult(search_params);
		Debug.checkNull("search_params", search_params);
		for (int i = 0; i < this.resources.size(); i++) {
			final PackageSearchResult result_i = this.resources.getValueAt(i).findPackages(search_params);
			result.add(result_i);
		}

		return result;
	}

	@Override
	public PackagesBank getBank (final ID name) {
		return this.resources.get(name);
	}

	private void installBank (final PackagesBank group) {
		Debug.checkNull("resource_to_install", group);
		final ID name = group.getName();

		if (this.resources.containsKey(name)) {
			Err.reportError("Resource with this name <" + name + "> is already installed: " + this.resources.get(name));
		}

		this.resources.put(name, group);
	}

	@Override
	public void installBanks (final Collection<PackagesBank> resources) {
		for (final PackagesBank r : resources) {
			this.installBank(r);
		}
	}

// public Promise<Void,Collection<PackagesBank>> loadAssetsFolder (final File assets_folder) {
// final Future<Void,Collection<PackagesBank>> future = new Future<Void,Collection<PackagesBank>>() {
//
// @Override
// public Collection<PackagesBank> deliver () throws Throwable {
// Debug.checkNull("assets_folder", assets_folder);
// if (assets_folder.exists() && assets_folder.isFolder()) {
// final Promise<Void,Collection<PackagesBank>> locals = RedPackageManager.this.findBanks(assets_folder);
// // locals.print("locals");
// final Collection<PackagesBank> localsList = locals.await();
// for (final PackagesBank local : localsList) {
// local.rebuildIndex().await();
// }
// return localsList;
// }
// return null;
// }
// };
// return TaskManager.newPromise(future);
//
// }

	public Promise<Void, PackageManagerConfig> loadConfigFile (final File applicationHome) {

		final Future<Void, PackageManagerConfig> future = new Future<Void, PackageManagerConfig>() {

			@Override
			public PackageManagerConfig deliver (final Void v) throws Throwable {
				PackageManagerConfig config = null;
				final File resources_config_file = applicationHome.child(PackageManagerConfig.FILE_NAME);

				if (!resources_config_file.exists()) {
					return null;
				}

				L.d("reading", resources_config_file);

				final String configString = resources_config_file.readToString();

				config = Json.deserializeFromString(PackageManagerConfig.class, configString);
				return config;
			}
		};

		return TaskManager.newPromise(future);

	}

// void loadRemoteBank (final HttpURL bankURL, final Iterable<String> tanks, final File assets_cache_folder) throws IOException {
// final PackagesBank bank = this.installRemoteBank(bankURL, assets_cache_folder, tanks);
// bank.rebuildAllIndexes();
// }

// @Override
// public PackageFormat newPackageFormat (final String format_name) {
// return new PackageFormatImpl(format_name);
// }

	@Override
	public PackagesTank newResource (final AssetsTankSpecs resSpec) throws IOException {
		return new RedResource(resSpec);
	}

	@Override
	public AssetsTankSpecs newResourceSpecs () {
		return new RedResourceSpecs();
	}

	@Override
	public void printAllIndexes () {
		for (int i = 0; i < this.resources.size(); i++) {
			this.resources.getValueAt(i).printAllIndexes();
		}
	}

	@Override
	public void printAllPackages () {
		this.resources.print("All installed resources");
		final PackageSearchParameters search_params = new PackageSearchParameters();
		search_params.getAllFlag = true;
		final PackageSearchResult packages = this.findPackages(search_params);
		packages.list().print("All available packages");
		;
	}

	@Override
	public void printAllResources () {
		this.resources.print("resources");
	}

	private PackageManagerConfig tryToMakeConfigFile (final File applicationHome) {
		final PackageManagerConfig config = new PackageManagerConfig();

		final LocalAssetsFolder assets_folder = new LocalAssetsFolder();
		assets_folder.path = "path?";
		config.local_banks.add(assets_folder);

		final File resources_config_file = applicationHome.child(PackageManagerConfig.FILE_NAME);
		try {
			if (resources_config_file.exists()) {
				L.e("file exists", resources_config_file);
				final String data = resources_config_file.readToString();
				L.e(data);
				return null;
			}
			final String stringData = Json.serializeToString(config).toString();

			L.d("writing", resources_config_file);
			resources_config_file.writeString(stringData);
			L.d(stringData);

		} catch (final IOException e) {
			e.printStackTrace();
		}
		return config;

	}

	private Promise<Void, BankHeader> findAndLoadBankHeader (final File bank_folder) throws IOException {
		final Future<Void, BankHeader> plan = new Future<Void, BankHeader>() {
			@Override
			public BankHeader deliver (final Void x) throws Throwable {

				if (!(bank_folder.exists() && bank_folder.isFolder())) {
					return null;
				}

				final File header_file = bank_folder.child(BankHeaderInfo.FILE_NAME);
				if (!header_file.exists()) {
					return null;
				}

				String data;
				try {
					data = header_file.readToString();
				} catch (final IOException e) {
					e.printStackTrace();
					return null;
				}

				final JsonString json_data = Json.newJsonString(data);

				final BankHeaderInfo headerInfo = Json.deserializeFromString(BankHeaderInfo.class, json_data);

				final BankHeader header = new BankHeader(headerInfo, bank_folder);
				return header;

			}

		};

		final Promise<Void, BankHeader> promise = TaskManager.newPromise(plan);
		return promise;
	}

	@Override
	public Promise<Void, Collection<FileSystemBankSettings>> findBanks (final Collection<RemoteBankSettings> remoteBankSettings,
		final File cacheFolder) {
		final Future<Void, Collection<FileSystemBankSettings>> future = new Future<Void, Collection<FileSystemBankSettings>>() {

			@Override
			public Collection<FileSystemBankSettings> deliver (final Void v) throws Throwable {
				final List<FileSystemBankSettings> results = Collections.newList();
				for (final RemoteBankSettings set : remoteBankSettings) {
					final Promise<Void, Collection<FileSystemBankSettings>> bankPromise = RedPackageManager.this.findBanks(set,
						cacheFolder);
					results.addAll(bankPromise.await());
				}
				return results;
			}
		};

		return TaskManager.newPromise(future);
	}

	@Override
	public Promise<Void, Collection<PackagesBank>> loadBanks (final Collection<FileSystemBankSettings> localBanks) {
		final Future<Void, Collection<PackagesBank>> future = new Future<Void, Collection<PackagesBank>>() {

			@Override
			public Collection<PackagesBank> deliver (final Void v) throws Throwable {
				final List<PackagesBank> results = Collections.newList();
				for (final FileSystemBankSettings set : localBanks) {
					final Promise<Void, PackagesBank> bankPromise = RedPackageManager.this.loadBank(set);
					results.add(bankPromise.await());
				}
				return results;
			}
		};

		return TaskManager.newPromise(future);
	}

	@Override
	public Promise<Void, PackagesBank> loadBank (final FileSystemBankSettings bankSettings) {
		final Future<Void, PackagesBank> future = new Future<Void, PackagesBank>() {
			@Override
			public PackagesBank deliver (final Void v) throws Throwable {
				final ID id = Names.newID(bankSettings.name);
				final RedBank bank = new RedBank(id);

				for (final String tankNname : bankSettings.tanks) {
					final File tank = bankSettings.bankFolder.child(tankNname);
					final AssetsTankSpecs resSpec = RedPackageManager.this.newResourceSpecs();
					resSpec.setFolder(tank);
					if (bankSettings.cacheFolder == null) {
						resSpec.setCachingRequired(false);
					} else {
						resSpec.setCachingRequired(true);
						resSpec.setCacheFolder(bankSettings.cacheFolder.child(tankNname));
					}
					final String tankName = tank.getName();
					resSpec.setName(bankSettings.name + "/" + tankName);
					resSpec.setShortName(tankName);
					final PackagesTank resource = RedPackageManager.this.newResource(resSpec);
					bank.addResource(resource);
				}

				bank.rebuildIndex();

				return bank;
			}
		};
		return TaskManager.newPromise(future);
	}

	@Override
	public Promise<Void, Collection<PackagesBank>> deploy (final File assets_folder, final File assets_cache_folder) {

		final Future<Void, Collection<PackagesBank>> future = new Future<Void, Collection<PackagesBank>>() {

			@Override
			public Collection<PackagesBank> deliver (final Void v) throws Throwable {
				{
					final Promise<Void, Collection<FileSystemBankSettings>> assetsFolderPromise = PackagesManager.invoke()
						.findBanks(assets_folder);
					final Collection<FileSystemBankSettings> assetsFolderBanks = assetsFolderPromise.await();
					final Promise<Void, PackagesManagerConfig> configPromise = PackagesManager.invoke()
						.readPackagesManagerConfig(assets_cache_folder);
					final PackagesManagerConfig packmanConfig = configPromise.await();
					final Collection<FileSystemBankSettings> localBankFiles = packmanConfig.localBanks();
					final Collection<RemoteBankSettings> remoteSettings = packmanConfig.remoteBanks();
					final Collection<FileSystemBankSettings> remoteBankFiles = PackagesManager.invoke()
						.findBanks(remoteSettings, assets_cache_folder).await();
					final Promise<Void, Collection<PackagesBank>> localBanksPromise = PackagesManager.invoke()
						.loadBanks(localBankFiles);
					final Promise<Void, Collection<PackagesBank>> assetsFolderBanksPromise = PackagesManager.invoke()
						.loadBanks(assetsFolderBanks);
					final Promise<Void, Collection<PackagesBank>> remoteBanksPromise = PackagesManager.invoke()
						.loadBanks(remoteBankFiles);
					final Collection<PackagesBank> localBanks = localBanksPromise.await();
					final Collection<PackagesBank> assetsBanks = assetsFolderBanksPromise.await();
					final Collection<PackagesBank> remoteBanks = remoteBanksPromise.await();

					RedPackageManager.this.installBanks(assetsBanks);
					RedPackageManager.this.installBanks(localBanks);
					RedPackageManager.this.installBanks(remoteBanks);

					return PackagesManager.invoke().listInstalledBanks();
				}
			}
		};

		return TaskManager.newPromise(future);

	}

	@Override
	public Collection<PackagesBank> listInstalledBanks () {
		return this.resources.values();
	}

}
