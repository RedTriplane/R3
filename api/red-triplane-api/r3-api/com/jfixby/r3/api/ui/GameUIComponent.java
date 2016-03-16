package com.jfixby.r3.api.ui;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.r3.api.game.LoadTask;

public interface GameUIComponent {

    LoadTask prepareLoadGameUITask(Collection<AssetID> asetsToLoad);

    LoadTask prepareLoadGameUITask(AssetID... asetsToLoad);

    void pushTaskToLoader(LoadTask task, UILoaderListener ui_loader_listener);

    void pushFadeOut(long period);

    void pushFadeIn(long period);

    void allowUserInput();

    void pushLoadAssetsTask(Collection<AssetID> newList, UILoaderListener loader_listener);

    void switchToGameUI(AssetID game_ui_unit_id);

    void showLoadingScreen(AssetID loader_unit_id);

    AnimationsMachine newAnimationsMachine();

}
