package de.cubeisland.games.dhbw.resource.bag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Sounds {

	public static Sound load() {
		return Gdx.audio.newSound(Gdx.files.internal("sound/cardflip.wav"));
	}
}
