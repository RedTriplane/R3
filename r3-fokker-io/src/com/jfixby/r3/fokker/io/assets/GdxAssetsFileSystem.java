
package com.jfixby.r3.fokker.io.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.jfixby.r3.fokker.io.assets.index.GdxAssetsFileSystemIndex;
import com.jfixby.r3.fokker.io.assets.index.GdxAssetsFileSystemIndexEntry;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.FileInputStream;
import com.jfixby.scarabei.api.file.FileOutputStream;
import com.jfixby.scarabei.api.file.FileSystem;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.util.Utils;
import com.jfixby.scarabei.api.util.path.AbsolutePath;
import com.jfixby.scarabei.api.util.path.RelativePath;
import com.jfixby.scarabei.red.filesystem.AbstractFileSystem;

public class GdxAssetsFileSystem extends AbstractFileSystem implements FileSystem {

	final Map<RelativePath, Boolean> index = Collections.newMap();

	private final GdxAssetsFileSystemIndex index_data;

	public GdxAssetsFileSystem (final GdxAssetsFileSystemParams params) {
		Debug.checkNull("index_data", params.index_data);
		this.index_data = params.index_data;
	}

	public void checkIndex () {
		for (int i = 0; i < this.index_data.index.size(); i++) {
			final GdxAssetsFileSystemIndexEntry entry = this.index_data.index.get(i);
// L.d("checking", entry.path);
			final RelativePath path = Utils.newRelativePath(entry.path);
			String file_name = internalFileName(path, this);
			if (GdxAssetsFileSystemIndex.INDEX_FILE_NAME.equals(entry.path)) {
				file_name = path.toString();
			}
			this.index.put(path, entry.is_file);
			final FileHandle gdx_file = Gdx.files.internal(file_name);
			if (entry.is_file && !gdx_file.exists()) {
				L.d("         path", path);
				L.d("entry.is_file", entry.is_file);
				L.d("     gdx_file", gdx_file);
				final java.io.File file = gdx_file.file();
				if (file != null) {
					L.d("         file", file.getAbsolutePath());
				}
				L.d("       exists", gdx_file.exists());
				Err.reportError("GdxFileSystemIndex is corrupted. File not found: " + gdx_file);
			}
		}
	}

	public static String internalFileName (final RelativePath relativePath, final GdxAssetsFileSystem fs) {
		return GdxAssetsFileSystemIndex.internalFileName(relativePath, fs.index_data.collapsedFolders, fs.index_data.encryptedNames,
			fs.index_data.salt);
	}

	public static final String OS_SEPARATOR = "/";

	@Override
	public GdxAssetFile newFile (final AbsolutePath<FileSystem> file_path) {
		if (file_path == null) {
			Err.reportError("Filepath is null.");
		}
		if (file_path.getMountPoint() != this) {
			L.e("file_path", file_path);
			L.e("FileSystem", this.ROOT());
			Err.reportError("Path does not belong to this filesystem: " + file_path);
		}
		return new GdxAssetFile(file_path, this);
	}

	@Override
	public FileOutputStream newFileOutputStream (final File output_file, final boolean append) {
		Err.reportError("Read-only file system!");
		return null;
	}

	@Override
	public FileInputStream newFileInputStream (final File input_file) {
		if (input_file == null) {
			Err.reportError("Input file is null.");
		}
		if (input_file.getFileSystem() != this) {
			Err.reportError("Input file does not belong to this filesystem: " + input_file);
		}

		return new GdxAssetFileInputStream((GdxAssetFile)input_file);
	}

	final private String string_path = "GdxAssetsFileSystem";

	@Override
	public String toString () {
		return this.string_path;
	}

	public GdxAssetsFileSystemIndex getIndex () {
		return this.index_data;
	}

	@Override
	public boolean isReadOnlyFileSystem () {
		return true;
	}

	//////////////////////////////////////////////////////
}
