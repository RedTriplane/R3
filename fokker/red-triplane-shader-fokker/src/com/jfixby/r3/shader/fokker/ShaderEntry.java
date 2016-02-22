package com.jfixby.r3.shader.fokker;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.file.File;
import com.jfixby.r3.api.shader.ShaderAsset;
import com.jfixby.r3.api.shader.ShaderInfo;
import com.jfixby.r3.api.shader.ShadersContainer;
import com.jfixby.rana.api.asset.Asset;

public class ShaderEntry implements Asset, ShaderAsset {
	AssetID asset_id;
	ShaderInfo shader;
	File shader_folder;
	ShadersContainer container;

	public ShaderEntry(AssetID asset_id, ShaderInfo shader, File shader_folder, ShadersContainer container) {
		super();
		this.asset_id = asset_id;
		this.shader = shader;
		this.shader_folder = shader_folder;
		this.container = container;
	}

	@Override
	public AssetID getAssetID() {
		return asset_id;
	}

}
