
package com.jfixby.r3.assets.packer.raster;

import java.io.IOException;

import com.jfixby.rana.api.pkg.StandardPackageFormats;
import com.jfixby.red.engine.core.resources.PackageUtils;
import com.jfixby.red.engine.core.resources.PackerSpecs;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.assets.Names;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.FilesList;
import com.jfixby.scarabei.api.file.LocalFileSystem;
import com.jfixby.scarabei.api.log.L;

public class SystemRasterPacker {
	public static void pack (final File tank) throws IOException {

		final File shaders = LocalFileSystem.ApplicationHome().child("raster");
		final FilesList files_list = shaders.listDirectChildren();
		for (int i = 0; i < files_list.size(); i++) {
			final File file = files_list.getElementAt(i);
			try {
				packSystemRaster(tank, file);
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void packSystemRaster (final File tank, final File file) throws IOException {

		final String packagename = file.nameWithoutExtension();
		final File pkg = tank.child(packagename);
		L.d("paking", pkg);
		pkg.makeFolder();

		final String id_string = packagename;
		final ID asset = Names.newID(id_string);
		final File asset_folder = pkg;

		final PackerSpecs specs = new PackerSpecs();
		specs.setPackageFolder(asset_folder);

		specs.addPackedFile(file);

		specs.setRootFileName(file.getName());

		final File root_file = file;
		final List<ID> packed = Collections.newList();
		final ID id_i = Names.newID(packagename);
		packed.add(id_i);

		specs.setPackedAssets(packed);

		final List<ID> required = Collections.newList();
		specs.setRequiredAssets(required);

		specs.setPackageFormat(StandardPackageFormats.libGDX.Texture);
		specs.setVersion("1.0");
		L.d("packing", root_file);
		PackageUtils.pack(specs);

	}
}
