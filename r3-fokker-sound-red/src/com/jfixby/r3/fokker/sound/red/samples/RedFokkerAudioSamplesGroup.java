
package com.jfixby.r3.fokker.sound.red.samples;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.jfixby.r3.fokker.io.ToGdxFileAdaptor;
import com.jfixby.r3.rana.api.AssetsContainer;
import com.jfixby.r3.rana.api.AssetsGroup;
import com.jfixby.r3.rana.api.format.PackageFormat;
import com.jfixby.r3.rana.api.loader.PackageReaderInput;
import com.jfixby.r3.sound.red.io.AudioPackageData;
import com.jfixby.r3.sound.red.io.AudioSample;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.names.ID;
import com.jfixby.scarabei.api.names.Names;

public class RedFokkerAudioSamplesGroup implements AssetsGroup {

	private File package_root_file;
	private List<RedFokkerAudioSample> list = null;

	public void read (final PackageReaderInput input, final FokkerSoundLoader fokkerSoundLoader, final RedFokkerAudioSamples registry)
		throws IOException {

		this.package_root_file = input.packageRootFile;
		// final PackageReaderListener reader_listener = input.getPackageReaderListener();

		if (!this.package_root_file.exists()) {
			final String msg = "File not found: " + this.package_root_file;
			L.e(msg);
			final IOException e = new IOException(msg);
			// if (reader_listener != null) {
			// reader_listener.onError(e);
			// } else
			{
				e.printStackTrace();
			}
			return;
		}

		final PackageFormat format = input.packageInfo.packageFormat;
		Debug.checkTrue("list == null", this.list == null);
		this.list = Collections.newList();
		this.readGdxSound(registry, input);
	}

	private void readGdxSound (final RedFokkerAudioSamples registry, final PackageReaderInput input) throws IOException {
		final File package_root_file = input.packageRootFile;
		// final PackageHandler handler = input.getPackageHandler();
		final Collection<ID> assets = input.packageInfo.packedAssets;
		final AssetsContainer container = input.assetsContainer;

		final AudioPackageData packageData = package_root_file.readJson(AudioPackageData.class);

		for (final AudioSample sample : packageData.samples) {
			final String asset_id_string = sample.id;
			final ID asset_id = Names.newID(asset_id_string);

			final String file_name = sample.file_name;
			final File file = package_root_file.parent().child(file_name);
			final ToGdxFileAdaptor gdx_file = new ToGdxFileAdaptor(file);

			final com.badlogic.gdx.audio.Sound gdxSound = Gdx.audio.newSound(gdx_file);
			final RedFokkerAudioSample data = new RedFokkerAudioSample(asset_id, gdxSound, this);
			container.addAsset(asset_id, data);
			registry.register(asset_id, data);
			this.list.add(data);
		}

	}

	@Override
	public void dispose () {
		for (final RedFokkerAudioSample e : this.list) {
			e.getGdxSound().dispose();
		}
		this.list = null;
	}

	@Override
	public String toString () {
		if (this.list != null) {
			return this.package_root_file.toString();
		}
		return "<disposed>";
	}
}
