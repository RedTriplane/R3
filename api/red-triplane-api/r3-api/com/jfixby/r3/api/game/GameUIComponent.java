package com.jfixby.r3.api.game;

import com.jfixby.r3.api.ui.UILoaderListener;

public interface GameUIComponent {

	void showLoadingScreen();

	LoadTask prepareLoadGameUITask();

	void pushTaskToLoader(LoadTask task, UILoaderListener ui_loader_listener);

	void pushFadeOut(long period);

	void switchToGameUI();

	void pushFadeIn(long period);

	void allowUserInput();

	void showTextTestScene();

	void showShaderTestScene();

}
