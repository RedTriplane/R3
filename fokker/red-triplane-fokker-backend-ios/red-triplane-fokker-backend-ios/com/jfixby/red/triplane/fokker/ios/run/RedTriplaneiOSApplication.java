
package com.jfixby.red.triplane.fokker.ios.run;

import java.util.Set;

import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.foundation.NSBundle;
import org.robovm.apple.foundation.NSDictionary;
import org.robovm.apple.foundation.NSObject;
import org.robovm.apple.uikit.UIDevice;
import org.robovm.apple.uikit.UIScreen;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.jfixby.r3.fokker.backend.RedDisplayMetrics;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.collections.Mapping;
import com.jfixby.scarabei.api.display.DisplayMetrics;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.LocalFileSystem;
import com.jfixby.scarabei.api.sys.SystemInfo;
import com.jfixby.scarabei.api.sys.SystemInfoTags;
import com.jfixby.scarabei.api.sys.settings.SystemSettings;
import com.jfixby.scarabei.api.ver.Version;
import com.jfixby.scarabei.ios.api.iOSAppVersion;
import com.jfixby.scarabei.ios.api.iOSComponent;
import com.jfixby.scarabei.ios.api.iOSSystemInfoTags;
import com.jfixby.scarabei.ios.api.camera.iOSCameraSetup;
import com.jfixby.scarabei.red.sys.RedDeviceInfo;

public class RedTriplaneiOSApplication implements iOSComponent {

	private final IOSApplication.Delegate iosLauncher;
	private final iOSLauncher launcher;

	public RedTriplaneiOSApplication (final iOSLauncher launcher) {
		if (launcher == null) {
			throw new Error("launcher is null");
		}
		this.iosLauncher = launcher.getDelegate();
		this.launcher = launcher;
	}

	public Mapping<String, String> listSettings () {
		final Map<String, String> map = Collections.newMap();
		final NSBundle bundle = NSBundle.getMainBundle();
		final NSDictionary infoDictionary = bundle.getInfoDictionary();

		final Set keys = infoDictionary.keySet();
		for (final Object key : keys) {
			final NSObject value = infoDictionary.get(key);
			map.put(key + "", (value + "").replaceAll("\n", "").replaceAll("    ", ""));
// value.dispose();
		}
// infoDictionary.dispose();
// bundle.dispose();
		return map;
	}

	private String getiOSSetting (final String key) {
		final NSBundle bundle = NSBundle.getMainBundle();
		final NSDictionary infoDictionary = bundle.getInfoDictionary();
		final NSObject val = infoDictionary.get(key);
// val.dispose();
// infoDictionary.dispose();
// bundle.dispose();
		final String result = val + "";
		return result;
	}

	@Override
	public File getDocuments () {
		return LocalFileSystem.ApplicationHome().child("Documents");
	}

	@Override
	public File getLibrary () {
		return LocalFileSystem.ApplicationHome().child("Library");
	}

	@Override
	public File getTmp () {
		return LocalFileSystem.ApplicationHome().child("tmp");
	}

	@Override
	public File getCaches () {
		return LocalFileSystem.ApplicationHome().child("Library").child("Caches");
	}

	@Override
	public File getApplicationSupport () {
		return LocalFileSystem.ApplicationHome().child("Library").child("Application Support");
	}

	@Override
	public File getLocal () {
		return LocalFileSystem.ApplicationHome().child("Library").child("local");
	}

	@Override
	public File getPreferences () {
		return LocalFileSystem.ApplicationHome().child("Library").child("Preferences");
	}

	@Override
	public long getMaxHeapSize () {
		return Runtime.getRuntime().totalMemory();
	}

	@Override
	public long getRecommendedHeapSize () {
		return Runtime.getRuntime().totalMemory();
	}

	@Override
	public iOSCameraSetup getCameraSetup () {
		Err.throwNotImplementedYet();
		return null;
	}

	final RedDisplayMetrics displayMetrics = new RedDisplayMetrics();

	@Override
	public DisplayMetrics getDisplayMetrics () {

		final CGRect bounds = UIScreen.getMainScreen().getBounds();

		final double height = bounds.getHeight();
		final double width = bounds.getWidth();

		this.displayMetrics.set(width, height);

		return this.displayMetrics;
	}

	@Override
	public iOSAppVersion getAppVersion () {

		final RedAndroidAppVersion version = new RedAndroidAppVersion();

		version.package_name = this.getiOSSetting("CFBundleIdentifier");
		version.name = this.getiOSSetting("CFBundleShortVersionString");
		version.build = this.getiOSSetting("CFBundleVersion");

		return version;
	}

	@Override
	public SystemInfo getSystemInfo () {
// this.listSettings().print("iOS system settings");

		final RedDeviceInfo deviceInfo = new RedDeviceInfo();
		{
			final Mapping<String, String> iosSystemSettings = this.listSettings();
			deviceInfo.putAll(iosSystemSettings);
		}

		{

			final DisplayMetrics displayMetrics = this.getDisplayMetrics();
			final double height = displayMetrics.getHeight();
			final double width = displayMetrics.getWidth();
			deviceInfo.putValue(iOSSystemInfoTags.Display.WIDTH, width);
			deviceInfo.putValue(iOSSystemInfoTags.Display.HEIGHT, height);
		}

		{
			final UIDevice device = UIDevice.getCurrentDevice();

			deviceInfo.putValue(iOSSystemInfoTags.Battery.Level, device.getBatteryLevel());
			deviceInfo.putValue(iOSSystemInfoTags.Battery.State, device.getBatteryState());

			deviceInfo.putValue(iOSSystemInfoTags.System.Name, device.getSystemName());
			deviceInfo.putValue(iOSSystemInfoTags.System.Version, device.getSystemVersion());

			deviceInfo.putValue(iOSSystemInfoTags.Device.Name, device.getName());
			deviceInfo.putValue(iOSSystemInfoTags.Device.Model, device.getModel());
			deviceInfo.putValue(iOSSystemInfoTags.Device.LocalizedModel, device.getLocalizedModel());
			deviceInfo.putValue(iOSSystemInfoTags.Device.IdentifierForVendor, device.getIdentifierForVendor());

		}

		{
			final iOSAppVersion version = this.getAppVersion();
			deviceInfo.putValue(iOSSystemInfoTags.App.Version.Name, version.getName());
			deviceInfo.putValue(iOSSystemInfoTags.App.Version.Build, version.getBuild());
			deviceInfo.putValue(iOSSystemInfoTags.App.Version.PackageName, version.getPackageName());
		}

		{
// deviceInfo.putValue(SystemInfoTags.System.OS_NAME, java.lang.System.getProperty("os.version"));
			deviceInfo.putValue(SystemInfoTags.System.OS_NAME, System.getProperty("os.name"));
			deviceInfo.putValue(SystemInfoTags.System.OS_VERSION, System.getProperty("os.version"));

		}

		{
			deviceInfo.putValue(Version.Tags.PackageName, SystemSettings.getStringParameter(Version.Tags.PackageName));
			deviceInfo.putValue(Version.Tags.VersionCode, SystemSettings.getStringParameter(Version.Tags.VersionCode));
			deviceInfo.putValue(Version.Tags.VersionName, SystemSettings.getStringParameter(Version.Tags.VersionName));
		}

		return deviceInfo;
	}

	@Override
	public File getAssetsFolder () {
		Err.throwNotImplementedYet();
		return null;
	}

}
