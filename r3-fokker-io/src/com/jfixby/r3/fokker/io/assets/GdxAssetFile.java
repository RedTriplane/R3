
package com.jfixby.r3.fokker.io.assets;

import java.io.IOException;
import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.FileFilter;
import com.jfixby.scarabei.api.file.FileHash;
import com.jfixby.scarabei.api.file.FileInputStream;
import com.jfixby.scarabei.api.file.FileOutputStream;
import com.jfixby.scarabei.api.file.FileSystem;
import com.jfixby.scarabei.api.file.FilesList;
import com.jfixby.scarabei.api.io.IO;
import com.jfixby.scarabei.api.java.ByteArray;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.strings.Strings;
import com.jfixby.scarabei.api.util.path.AbsolutePath;
import com.jfixby.scarabei.api.util.path.RelativePath;
import com.jfixby.scarabei.red.filesystem.RedFilesList;

public class GdxAssetFile implements File {

	final private AbsolutePath<FileSystem> absolute_path;
	private final GdxAssetsFileSystem fs;
	private final RelativePath relative;
	private FileHandle gdx_file;

	public GdxAssetFile (final AbsolutePath<FileSystem> output_file_path, final GdxAssetsFileSystem file_system) {
		this.absolute_path = output_file_path;
		this.fs = file_system;
		this.relative = this.absolute_path.getRelativePath();
		if (true) {
			final Boolean isFile = this.fs.index.get(this.relative);
			this.gdx_file = Gdx.files.internal(this.getGdxInternalPathString());

			if (isFile == null) {
				if (this.gdx_file.exists()) {
					this.throwOutdated(output_file_path);
				}
			} else {
				if (isFile == true) {
					if (!this.gdx_file.exists()) {
						this.throwOutdated(output_file_path);
					}
				}
			}

			// -------------------------------------

			// if (gdx_file == null) {
			// Err.reportError(this.getGdxInternalPathString() + "");
			// }
			// if (gdx_file.exists() && isFile == null) {
			// Err.reportError("Index is outdated: " + gdx_file);
			// }
			// if (!gdx_file.exists() && isFile == true) {
			// Err.reportError("Index is outdated: " + gdx_file);
			// }
			// if (gdx_file.exists() && isFile != null) {
			// if (gdx_file.isDirectory() && isFile == false) {
			// Err.reportError("Index is outdated: " + gdx_file);
			// }
			// }
		}
	}

	private void throwOutdated (final AbsolutePath<FileSystem> output_file_path) {
// this.fs.index.print("index");
		Err.reportError("Index is outdated: " + output_file_path);
	}

	@Override
	public AbsolutePath<FileSystem> getAbsoluteFilePath () {
		return this.absolute_path;
	}

	String getGdxInternalPathString () {
		return GdxAssetsFileSystem.internalFileName(this.absolute_path.getRelativePath(), this.fs);
	}

	@Override
	public boolean isFile () {
		final Boolean isFile = this.fs.index.get(this.relative);
		if (isFile == null) {
			return false;
		}
		return isFile;
	}

	@Override
	public boolean isFolder () {
		final Boolean isFile = this.fs.index.get(this.relative);
		if (isFile == null) {
			return false;
		}
		return !isFile;
	}

	@Override
	public boolean exists () {
		final Boolean isFile = this.fs.index.get(this.relative);
		if (isFile == null) {
			return false;
		}
		// return isFile != null;
		return isFile || true;
	}

	@Override
	public boolean rename (final String new_name) {
		Err.reportError("Read-only file system!");
		return false;
	}

	@Override
	public boolean makeFolder () {
		// java.io.File f = new java.io.File(this.getGdxInternalPathString());
		// return f.mkdirs();
		Err.reportError("Read-only file system!");
		return false;
	}

	@Override
	public boolean delete () {
		// if (this.isFolder()) {
		// this.clearFolder();
		// }
		// java.io.File f = new java.io.File(getGdxInternalPathString());
		//
		// L.d("deleting", f);
		// f.delete();
		Err.reportError("Read-only file system!");
		return false;
	}

	public static String toNativePathString (final String string) {
		return string.replaceAll(RelativePath.SEPARATOR, GdxAssetsFileSystem.OS_SEPARATOR + GdxAssetsFileSystem.OS_SEPARATOR);
	}

	@Override
	public FilesList listDirectChildren () {
		final FileHandle file = Gdx.files.internal(this.getGdxInternalPathString());
		if (!this.exists()) {
			Err.reportError("File does not exist: " + this.absolute_path);
		}
		if (this.isFolder()) {
			final Collection<String> my_steps = this.relative.steps();
			final RedFilesList listFiles = new RedFilesList();
			for (int i = 0; i < this.fs.index.size(); i++) {
				final RelativePath path = this.fs.index.getKeyAt(i);
				final RelativePath pp = path.parent();
				final Collection<String> candidate_steps = path.steps();
				if (my_steps.size() + 1 == candidate_steps.size()) {
					if (pp.equals(this.relative)) {
						final AbsolutePath<FileSystem> absolute_file = this.absolute_path.child(candidate_steps.getLast());
						final File addFile = absolute_file.getMountPoint().newFile(absolute_file);
						{
							listFiles.add(addFile);
						}
					}
				}
			}
			return listFiles;
		} else {
			Err.reportError("This is not a folder: " + this.absolute_path);
		}
		return null;
	}

// @Override
// public String toString () {
// return "@GdxAssetsFileSystem:>" + RelativePath.SEPARATOR + this.absolute_path.getRelativePath();
// }

	@Override
	public File child (final String child_name) {
		return new GdxAssetFile(this.getAbsoluteFilePath().child(child_name), this.getFileSystem());
	}

	@Override
	public String getName () {
		// java.io.File f = new java.io.File(this.getGdxInternalPathString());
		return this.absolute_path.getRelativePath().getLastStep();
	}

	@Override
	public GdxAssetsFileSystem getFileSystem () {
		return this.fs;
	}

	@Override
	public String nameWithoutExtension () {
		final java.io.File file = new java.io.File(this.getGdxInternalPathString());
		final String name = file.getName();
		final int dotIndex = name.lastIndexOf('.');
		if (dotIndex == -1) {
			return name;
		}
		return name.substring(0, dotIndex);
	}

	public String toAbsolutePathString () {
		return this.getGdxInternalPathString();
	}

	@Override
	public void writeBytes (final ByteArray bytes) throws IOException {
		throw new IOException("Read-only file system: " + this.getFileSystem());
	}

	@Override
	public void writeString (final String bytes) throws IOException {
		throw new IOException("Read-only file system: " + this.getFileSystem());
	}

	@Override
	public void writeBytes (final ByteArray bytes, final boolean append) throws IOException {
		throw new IOException("Read-only file system: " + this.getFileSystem());
	}

	@Override
	public void writeBytes (final byte[] bytes) throws IOException {
		throw new IOException("Read-only file system: " + this.getFileSystem());
	}

	@Override
	public void writeString (final String string, final boolean append) throws IOException {
		throw new IOException("Read-only file system: " + this.getFileSystem());
	}

	@Override
	public long getSize () {
		final FileHandle file = Gdx.files.internal(this.getGdxInternalPathString());
		return file.length();
	}

	@Override
	public java.io.File toJavaFile () {
		// FileHandle file =
		// Gdx.files.internal(this.getGdxInternalPathString());
		// java.io.File f = file.file();
		// return f;
		Err.reportError("Method is not supported: toJavaFile()");
		return null;
	}

	@Override
	public File parent () {
		return new GdxAssetFile(this.absolute_path.parent(), this.fs);
	}

	@Override
	public long lastModified () {
		return this.gdx_file.lastModified();
	}

	public FileHandle toFileHandle () {
		return this.gdx_file;
	}

	@Override
	public FilesList listAllChildren () throws IOException {

		final FileHandle file = Gdx.files.internal(this.getGdxInternalPathString());

		if (!this.exists()) {
			Err.reportError("File does not exist: " + this.absolute_path);
		}
		if (this.isFolder()) {
			final Collection<String> my_steps = this.relative.steps();
			final RedFilesList listFiles = new RedFilesList();
			for (int i = 0; i < this.fs.index.size(); i++) {
				final RelativePath path = this.fs.index.getKeyAt(i);
// final RelativePath pp = path.parent();
// final Collection<String> candidate_steps = path.steps();
				if (path.beginsWith(this.relative)) {
					if (path.size() > this.relative.size()) {
						final AbsolutePath<FileSystem> absolute_file = this.fs.ROOT().getAbsoluteFilePath().proceed(path);
// L.d("GDX", absolute_file);
						final File addFile = absolute_file.getMountPoint().newFile(absolute_file);
						{
							listFiles.add(addFile);
						}
					}
				}
			}
			return listFiles;
		} else {
			Err.reportError("This is not a folder: " + this.absolute_path);
		}
		return null;

// final List<File> filesQueue = Collections.newList();
// filesQueue.add(this);
// final RedFilesList result = new RedFilesList();
// collectChildren(filesQueue, result, false);
//
// return result;

	}

	private static final boolean DIRECT_CHILDREN = true;
	private static final boolean ALL_CHILDREN = !DIRECT_CHILDREN;

	static private void collectChildren (final List<File> filesQueue, final RedFilesList result, final boolean directFlag)
		throws IOException {
		while (filesQueue.size() > 0) {
			final File nextfile = filesQueue.removeElementAt(0);

			if (nextfile.isFolder()) {

				final FilesList files = nextfile.listDirectChildren();

				for (int i = 0; i < files.size(); i++) {
					final File child = files.getElementAt(i);
					result.add(child);
					if (directFlag == ALL_CHILDREN) {

						if (child.isFolder()) {
							filesQueue.add(child);
						}
					} else {

					}
				}

			} else {
				Err.reportError("This is not a folder: " + nextfile.getAbsoluteFilePath());
			}

		}
	}

	@Override
	public void clearFolder () throws IOException {
		if (this.isFolder()) {
			final FilesList children = this.listDirectChildren();
			for (int i = 0; i < children.size(); i++) {
				// WinFile file = new WinFile(child);
				final File child = children.getElementAt(i);
				child.delete();
				// L.d("deleting", child.getAbsoluteFilePath());
			}
		} else {
			L.e("Unable to clear", this.getAbsoluteFilePath());
			L.e("       this is not a folder.");
		}
	}

	@Override
	public int hashCode () {
		return this.getAbsoluteFilePath().hashCode();
	}

	@Override
	public boolean equals (final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final GdxAssetFile other = (GdxAssetFile)obj;

		return this.getAbsoluteFilePath().equals(other.getAbsoluteFilePath());
	}

	@Override
	public void checkIsFolder () throws IOException {
		this.checkExists();
		if (!this.isFolder()) {
			throw new IOException("" + this + " is not a folder");
		}
	}

	@Override
	public void checkExists () throws IOException {
		if (!this.exists()) {
			throw new IOException(this + " does not exist.");
		}
	}

	@Override
	public void checkIsFile () throws IOException {
		this.checkExists();
		if (!this.isFile()) {
			throw new IOException(this + " does not exist.");
		}
	}

	@Override
	public void writeData (final Object object) throws IOException {
		final ByteArray data = IO.serialize((Serializable)object);
		this.writeBytes(data);
	}

	@Override
	public File proceed (final RelativePath relativePath) {
		final AbsolutePath<FileSystem> file_path = this.getAbsoluteFilePath().proceed(relativePath);
		return this.getFileSystem().newFile(file_path);
	}

	@Override
	public boolean extensionIs (final String postfix) {
		final String name = this.getName().toLowerCase();
		final boolean result = name.endsWith("." + postfix.toLowerCase());
		return result;
	}

	@Override
	public FilesList listSubFolders () throws IOException {
		final RedFilesList listFiles = new RedFilesList();
		final FilesList children = this.listDirectChildren();
		for (final File file : children) {
			if (file.isFolder()) {
				listFiles.add(file);
			}
		}

		return listFiles;
	}

	@Override
	public String readToString () throws IOException {
		return Strings.newString(this.readBytes());
	}

	@Override
	public String readToString (final String encoding) throws IOException {
		return Strings.newString(this.readBytes(), encoding);
	}

	@Override
	public ByteArray readBytes () throws IOException {
		final FileInputStream is = this.getFileSystem().newFileInputStream(this);
		final ByteArray bytes;
		is.open();
		try {
			bytes = is.readAll();
			return bytes;
		} catch (final IOException e) {
			throw e;
		} finally {
			is.close();
		}
	}

	@Override
	final public String toString () {
		return this.getAbsoluteFilePath() + "";
	}

	@Override
	public <T> T readData (final Class<T> type) throws IOException {
		final ByteArray bytes = this.readBytes();
		return IO.deserialize(type, bytes);
	}

	@Override
	public FileHash calculateHash () throws IOException {
		return ((this.getAbstractFileSystem()).md5Hex(this));
	}

	private GdxAssetsFileSystem getAbstractFileSystem () {
		return this.getFileSystem();
	}

	@Override
	public FileInputStream newInputStream () {
		return this.getFileSystem().newFileInputStream(this);
	}

	@Override
	public FileOutputStream newOutputStream () {
		return this.getFileSystem().newFileOutputStream(this);
	}

	@Override
	public FileOutputStream newOutputStream (final boolean append) {
		return this.getFileSystem().newFileOutputStream(this, append);
	}

	@Override
	public String getExtension () throws IOException {
		if (this.isFolder()) {
			return "";
		}
		final String name = this.getName().toLowerCase();
		final int index = name.lastIndexOf('.');
		if (index < 0) {
			return "";
		}
		final String ext = name.substring(index + 1);
		return ext;
	}

	@Override
	public File copyTo (final String newFileName) throws IOException {
		final File outputFile = this.parent().child(Debug.checkNull("newFileName", newFileName));
		this.getFileSystem().copyFileToFile(this, outputFile);
		return outputFile;
	}

	@Override
	final public FilesList listDirectChildren (final FileFilter filter) throws IOException {
		return this.listDirectChildren().filter(filter);// ugly hack
	}

	@Override
	final public FilesList listAllChildren (final FileFilter filter) throws IOException {
		return this.listAllChildren().filter(filter);// ugly hack
	}

	@Override
	public boolean tryToClearFolder () {
		try {
			this.clearFolder();
			return true;
		} catch (final IOException e) {
			L.e(e);
			return false;
		}
	}

// @Override
// public FilesList listAllChildren () throws IOException {
// final List<File> filesQueue = Collections.newList();
// filesQueue.add(this);
// final RedFilesList result = new RedFilesList();
// collectChildren(filesQueue, result, false);
//
// return result;
//
// }
//
// static private void collectChildren (final List<File> filesQueue, final RedFilesList result, final boolean directFlag)
// throws IOException {
// while (filesQueue.size() > 0) {
// final File nextfile = filesQueue.removeElementAt(0);
//
// if (nextfile.isFolder()) {
//
// final FilesList files = nextfile.listDirectChildren();
//
// for (int i = 0; i < files.size(); i++) {
// final File child = files.getElementAt(i);
// result.add(child);
// if (directFlag == ALL_CHILDREN) {
//
// if (child.isFolder()) {
// filesQueue.add(child);
// }
// } else {
//
// }
// }
//
// } else {
// Err.reportError("This is not a folder: " + nextfile.getAbsoluteFilePath());
// }
//
// }
// }

}
