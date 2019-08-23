
package com.jfixby.r3.fokker.sound.red.samples;

import java.io.IOException;

import com.jfixby.r3.fokker.sound.api.samples.FokkerAudioSamplesPackageReader;
import com.jfixby.r3.rana.api.AssetsContainer;
import com.jfixby.r3.rana.api.format.PackageFormat;
import com.jfixby.r3.rana.api.loader.PackageLoader;
import com.jfixby.r3.rana.api.loader.PackageReaderInput;
import com.jfixby.r3.sound.red.io.PackageFormats;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;

public class FokkerSoundLoader implements PackageLoader, FokkerAudioSamplesPackageReader {
	public static final PackageFormat R3_AUDIO_PACKAGE = new PackageFormat(PackageFormats.R3_AUDIO_PACKAGE);

	final List<PackageFormat> acceptablePackageFormats = Collections.newList();
	private final RedFokkerAudioSamples registry;

	FokkerSoundLoader (final RedFokkerAudioSamples registry) {
		this.acceptablePackageFormats.add(R3_AUDIO_PACKAGE);
		this.registry = registry;
	}

	@Override
	public Collection<PackageFormat> listAcceptablePackageFormats () {
		return this.acceptablePackageFormats;
	}

	@Override
	public AssetsContainer doReadPackage (final PackageReaderInput input) throws IOException {
		final RedFokkerAudioSamplesGroup group = new RedFokkerAudioSamplesGroup();
		group.read(input, FokkerSoundLoader.this, FokkerSoundLoader.this.registry);
		return input.assetsContainer;
	}

	@Override
	public PackageLoader reader () {
		return this;
	}

}
