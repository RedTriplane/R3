/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.badlogic.gdx.backends.lwjgl;

import java.awt.Canvas;

import com.badlogic.gdx.ApplicationListener;

/** An OpenGL surface fullscreen or in a lightweight window. */
public class FokkerLwjglApplication extends LwjglApplication {

	public FokkerLwjglApplication (final ApplicationListener listener, final Canvas canvas) {
		super(listener, canvas);
	}

	public FokkerLwjglApplication (final ApplicationListener listener, final FokkerLwjglApplicationConfiguration config,
		final Canvas canvas) {
		super(listener, config, canvas);
	}

	public FokkerLwjglApplication (final ApplicationListener listener, final FokkerLwjglApplicationConfiguration config,
		final LwjglGraphics graphics) {
		super(listener, config, graphics);
	}

	public FokkerLwjglApplication (final ApplicationListener listener, final FokkerLwjglApplicationConfiguration config) {
		super(listener, config);
	}

	public FokkerLwjglApplication (final ApplicationListener listener, final String title, final int width, final int height) {
		super(listener, title, width, height);
	}

	public FokkerLwjglApplication (final ApplicationListener listener) {
		super(listener);
	}

}
