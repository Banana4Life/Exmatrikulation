package de.cubeisland.games.dhbw.resource.bag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class Songs {

	public static Music load() {
		return Gdx.audio.newMusic(Gdx.files.internal("sound/main.mp3"));
	}
}