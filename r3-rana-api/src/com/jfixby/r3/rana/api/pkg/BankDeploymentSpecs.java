
package com.jfixby.r3.rana.api.pkg;

import java.util.ArrayList;

import com.jfixby.scarabei.api.file.File;

public class BankDeploymentSpecs {

	public File bankParentFolder;
	public String bankName;

	public final ArrayList<TankDeploymentSpecs> tanks = new ArrayList<TankDeploymentSpecs>();

}
