
package com.jfixby.r3.fokker.shader.red;

import java.io.IOException;

import com.jfixby.r3.fokker.assets.api.shader.io.ShaderInfo;
import com.jfixby.r3.fokker.assets.api.shader.io.ShadersContainer;
import com.jfixby.r3.rana.api.AssetsContainer;
import com.jfixby.r3.rana.api.AssetsGroup;
import com.jfixby.r3.rana.api.loader.PackageReaderInput;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.assets.Names;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.io.IO;

public class ShadersGroup implements AssetsGroup {

	final List<ShaderEntry> list = Collections.newList();

	public ShadersGroup () {

	}

	void read (final PackageReaderInput input, final RedFokkerShaders redFokkerShaders) throws IOException {
		final File package_root_file = input.packageRootFile;
// final PackageHandler handler = input.getPackageHandler();
		final AssetsContainer container = input.assetsContainer;

		final ShadersContainer file_container = this.deserialize(ShadersContainer.class, package_root_file);
		for (final ShaderInfo shader : file_container.shaders) {
			final ID asset_id = Names.newID(shader.shader_id);
			final String shader_folder_name = shader.shader_folder_name;
			final File shader_folder = package_root_file.parent().child(shader_folder_name);
			final ShaderEntry entry = new ShaderEntry(asset_id, shader, shader_folder, file_container, this);
			container.addAsset(asset_id, entry);
			redFokkerShaders.register(asset_id, entry);
		}
	}

	private <T> T deserialize (final Class<T> class1, final File file) throws IOException {
		try {
			return IO.deserialize(class1, file.readBytes());
		} catch (final IOException e) {
			e.printStackTrace();
			throw new IOException("Failed to read " + file, e);
		}
	}

	@Override
	public void dispose () {
		for (final ShaderEntry e : this.list) {
			e.dispose();
		}

		Err.throwNotImplementedYet();// dispose from regisrty?
	}

}
