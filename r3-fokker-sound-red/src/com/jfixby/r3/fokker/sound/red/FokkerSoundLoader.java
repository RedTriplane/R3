
package com.jfixby.r3.fokker.sound.red;

import java.io.IOException;

import com.jfixby.r3.fokker.sound.api.FokkerAudioPackageReader;
import com.jfixby.r3.rana.api.AssetsContainer;
import com.jfixby.r3.rana.api.format.PackageFormat;
import com.jfixby.r3.rana.api.loader.PackageLoader;
import com.jfixby.r3.rana.api.loader.PackageReaderInput;
import com.jfixby.r3.sound.red.io.PackageFormats;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;

public class FokkerSoundLoader implements PackageLoader, FokkerAudioPackageReader {
	public static final PackageFormat MP3 = new PackageFormat(PackageFormats.MP3);
	public static final PackageFormat WAV = new PackageFormat(PackageFormats.WAV);
	public static final PackageFormat OGG = new PackageFormat(PackageFormats.OGG);

	final List<PackageFormat> acceptablePackageFormats = Collections.newList();
	private final RedFokkerSounds registry;

	FokkerSoundLoader (final RedFokkerSounds registry) {
		this.acceptablePackageFormats.add(MP3);
		this.acceptablePackageFormats.add(WAV);
		this.acceptablePackageFormats.add(OGG);
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
