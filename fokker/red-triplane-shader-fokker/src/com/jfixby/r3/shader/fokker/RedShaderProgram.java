package com.jfixby.r3.shader.fokker;

import java.io.IOException;

import com.jfixby.cmns.api.file.File;
import com.jfixby.r3.api.shader.FragmentProgram;
import com.jfixby.r3.api.shader.ShaderProgram;
import com.jfixby.r3.api.shader.VertexProgram;

public class RedShaderProgram implements VertexProgram, FragmentProgram, ShaderProgram {

	private String data;

	public RedShaderProgram(File source_code_file) throws IOException {
		data = source_code_file.readToString();
	}

	@Override
	public String getSourceCode() {
		return data;
	}

}
