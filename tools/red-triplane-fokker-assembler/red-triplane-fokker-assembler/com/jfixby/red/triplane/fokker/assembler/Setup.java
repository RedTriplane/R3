package com.jfixby.red.triplane.fokker.assembler;

import com.jfixby.cmns.adopted.gdx.json.GdxJson;
import com.jfixby.cmns.api.collections.JUtils;
import com.jfixby.cmns.api.filesystem.LocalFileSystem;
import com.jfixby.cmns.api.io.IO;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.md5.MD5;
import com.jfixby.cmns.api.sys.Sys;
import com.jfixby.cmns.jutils.desktop.DesktopUtils;
import com.jfixby.red.desktop.filesystem.win.WinFileSystem;
import com.jfixby.red.desktop.log.DesktopLogger;
import com.jfixby.red.desktop.sys.DesktopSystem;
import com.jfixby.red.io.RedIO;
import com.jfixby.red.util.md5.AlpaeroMD5;

public class Setup {

	public static void setup() {
		L.installComponent(new DesktopLogger());
		JUtils.installComponent(new DesktopUtils());
		IO.installComponent(new RedIO());
		LocalFileSystem.installComponent(new WinFileSystem());
		MD5.installComponent(new AlpaeroMD5());
		Sys.installComponent(new DesktopSystem());
		Json.installComponent(new GdxJson());
	}
}
