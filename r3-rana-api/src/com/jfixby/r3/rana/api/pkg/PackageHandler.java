
package com.jfixby.r3.rana.api.pkg;

import java.io.IOException;

import com.jfixby.r3.rana.api.format.PackageFormat;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.names.ID;

public interface PackageHandler {

	public String getPackageName ();

	public PackageVersion getVersion ();

	public PackageFormat getFormat ();

	public PACKAGE_STATUS getStatus ();

	public Collection<ID> listPackedAssets ();

	public Collection<ID> listDependencies ();

	public void print ();

	public void install () throws IOException;

	public File getRootFile (boolean sandBoxMode) throws IOException;

}
