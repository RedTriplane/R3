package com.jfixby.r3.api.shader.srlz;

import java.io.Serializable;
import java.util.ArrayList;

public class ShaderInfo implements Serializable {
	public ArrayList<ShaderParameterInfo> parameters_list = new ArrayList<ShaderParameterInfo>();
	public String shader_id;
	public String shader_folder_name;
}
