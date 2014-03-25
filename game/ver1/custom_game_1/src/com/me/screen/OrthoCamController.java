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

package com.me.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class OrthoCamController extends InputAdapter {
	final OrthographicCamera camera;
	final Vector3 curr = new Vector3();
	final Vector3 last = new Vector3(-1, -1, -1);
	final Vector3 delta = new Vector3();

	public OrthoCamController (OrthographicCamera camera) {
		this.camera = camera;
	}

	@Override
	public boolean touchDragged (int x, int y, int pointer) {
		Gdx.app.log("touch dragged location:", "x:"+x + "|y:" + y + "|pointer:"+pointer);
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Gdx.app.log("touch down location:", "x:"+screenX + "|y:" + screenY + "|pointer:"+pointer +"|button:"+button);

//		return super.touchDown(screenX, screenY, pointer, button);
		return false;
	}
	
	@Override
	public boolean touchUp (int x, int y, int pointer, int button) {
		Gdx.app.log("touch up location:", "x:"+x + "|y:" + y + "|pointer:"+pointer+"|button:"+button);
		return false;
	}
}
