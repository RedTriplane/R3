
package com.jfixby.r3.ext.scene2d.red;

import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.time.TimeStream;
import com.jfixby.scarabei.api.util.JUtils;
import com.jfixby.scarabei.api.util.path.RelativePath;

class Settings {

	@Override
	public String toString () {
		return "Settings [structure=" + this.structure + ", currentScene=" + this.currentScene + ", debug_opacity="
			+ this.debug_opacity + ", locale_name=" + this.locale_name + ", render_debug_info=" + this.render_debug_info
			+ ", scene_name=" + this.scene_name + "]";
	}

	public final SceneStructureAsset structure;
	public final RedScene currentScene;

	public Settings (final SceneStructureAsset structure, final RedScene currentScene) {
		this.structure = structure;
		this.currentScene = currentScene;
	}

	public float debug_opacity = 1f;
	public String locale_name;
	public boolean render_debug_info;
	public String scene_name;
	public boolean debug_raster = false;
	public TimeStream timeStream = null;

	public Settings copy (final SceneStructureAsset nextStructure, final RedScene nextScene) {
		final Settings copy = new Settings(nextStructure, nextScene);
		copy.debug_opacity = this.debug_opacity;
		copy.locale_name = this.locale_name;
		copy.render_debug_info = this.render_debug_info;
		copy.scene_name = this.scene_name;
		copy.timeStream = this.timeStream;
		copy.debug_raster = this.debug_raster;
		copy.stack.addAll(this.stack);
		return copy;
	}

	public SceneStructureAsset getStructure () {
		return this.structure;
	}

	final List<String> stack = Collections.newList();

	public void pop (final String name) {
		this.stack.removeElementAt(this.stack.size() - 1);
	}

	public void push (final String name) {
		this.stack.add(name);
	}

	public RelativePath getStackPath () {
		return JUtils.newRelativePath(this.stack);
	}

}
