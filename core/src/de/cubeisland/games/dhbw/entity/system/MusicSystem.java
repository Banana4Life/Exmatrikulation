package de.cubeisland.games.dhbw.entity.system;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.audio.Music;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This system holds the songs and plays them in a random order.
 *
 * @author Phillip Schichtel
 */
public class MusicSystem extends EntitySystem implements Music.OnCompletionListener {

    private static final float VOLUME = .05f;
    private final List<Music> songs;
    private boolean started = false;
    private int currentSong = 0;

    public MusicSystem(List<Music> songs) {
        this.songs = new ArrayList<>(songs);

        Collections.shuffle(this.songs);

        for (Music song : songs) {
            song.setOnCompletionListener(this);
        }
    }

    private static void start(Music song) {
        song.play();
        song.setVolume(VOLUME);
        song.setLooping(false);
    }

    @Override
    public void update(float deltaTime) {
        if (!started) {
            start(this.songs.get(currentSong));
            started = true;
        }
    }

    @Override
    public void onCompletion(Music music) {
        music.stop();
        start(this.songs.get(++currentSong % this.songs.size()));
    }
}
