
package com.jfixby.r3.api.shader.srlz;

import java.io.Serializable;
import java.util.ArrayList;

public class ShaderInfo implements Serializable {
	private static final long serialVersionUID = -1573483713364968116L;
	public ArrayList<ShaderParameterInfo> parameters_list = new ArrayList<ShaderParameterInfo>();
	public String shader_id;
	public String shader_folder_name;
	public boolean isOverlay = false;
}
