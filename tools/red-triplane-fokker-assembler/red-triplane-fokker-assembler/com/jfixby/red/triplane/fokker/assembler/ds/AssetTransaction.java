package com.jfixby.red.triplane.fokker.assembler.ds;

public class AssetTransaction {

	public AssetTransaction() {
	};

	String output;

	public void setOutputPath(String output) {
		this.output = output;
	}

	@Override
	public String toString() {
		return ">>> " + output + "";
	}

	public String getOutputPath() {
		return output;
	}

}