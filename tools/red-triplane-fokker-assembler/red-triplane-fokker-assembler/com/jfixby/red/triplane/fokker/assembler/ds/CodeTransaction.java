package com.jfixby.red.triplane.fokker.assembler.ds;

public class CodeTransaction {

	public CodeTransaction() {
	};

	String input;
	String output;

	public void setInputPath(String input) {
		this.input = input;
	}

	public void setOutputPath(String output) {
		this.output = output;
	}

	@Override
	public String toString() {
		return "" + input + " >>> " + output + "";
	}

	public String getOutputPath() {
		return output;
	}

	public String getInputPath() {
		return input;
	}

}