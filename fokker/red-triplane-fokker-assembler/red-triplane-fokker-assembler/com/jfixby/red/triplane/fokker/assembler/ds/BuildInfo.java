package com.jfixby.red.triplane.fokker.assembler.ds;

import java.util.Date;

import com.jfixby.scarabei.api.log.L;

public class BuildInfo {

	public long build_number = 0;
	public Date build_time = new Date();

	public void next() {
		build_number++;
		build_time = new Date();
	}

	public void print() {
		L.d("Build info:");
		L.d("      version", build_number);
		L.d("             ", build_time);
		L.d();
	}

	public String getVerstionString() {
		return "\"build." + build_number + " (" + build_time + ")\"";
	}

}
