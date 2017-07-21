
package com.jfixby.r3.activity.red.scene;

import com.jfixby.r3.rana.api.AssetsGroup;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;

public class SceneStructuresGroup implements AssetsGroup {

	final List<SceneStructureAsset> list = Collections.newList();

	public void add (final SceneStructureAsset sceneStructureAsset) {
		this.list.add(sceneStructureAsset);
	}

	@Override
	public void dispose () {
		this.list.clear();
	}

}
