package com.jfixby.r3.ext.api.scene2d.srlz;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.rana.api.asset.Asset;

public class SceneStructure implements Asset, java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -927468651743679015L;

    public String structure_name;

    public LayerElement root = new LayerElement();

    @Override
    public AssetID getAssetID() {
	return Names.newAssetID(structure_name);
    }

}
