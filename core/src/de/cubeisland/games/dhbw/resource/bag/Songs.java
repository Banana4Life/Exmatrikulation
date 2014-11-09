package de.cubeisland.games.dhbw.resource.bag;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import life.banana4.util.resourcebags.FileRef;
import life.banana4.util.resourcebags.ResourceBag;

import java.lang.reflect.Field;

/**
 * This bag holds the music that's playing in the game.
 *
 * @author Phillip Schichtel
 */
public class Songs extends ResourceBag<Music> {

	//public Music main;
	public Music abnormal_perfection;
	public Music ending_chapter;
	public Music moments;
	public Music frisson;

	@Override
	protected Music load(FileRef fileRef, Field field) {
		return Gdx.audio.newMusic(Gdx.files.internal(fieldToFileRef(field, fileRef).getPath() + ".mp3"));
	}
}
