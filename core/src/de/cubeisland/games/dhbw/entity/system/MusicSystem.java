package de.cubeisland.games.dhbw.entity.system;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.audio.Music;

import java.util.List;

public class MusicSystem extends EntitySystem implements Music.OnCompletionListener {

    private boolean started = false;
    private int currentSong = 0;
    private final List<Music> songs;

    private static final float VOLUME = .05f;

    public MusicSystem(List<Music> songs) {
        this.songs = songs;

        for (Music song : songs) {
            song.setOnCompletionListener(this);
        }
    }

    @Override
    public void update(float deltaTime) {
        if (!started) {
            start(this.songs.get(currentSong));
            started = true;
        }
    }

    private static void start(Music song) {
        song.play();
        song.setVolume(VOLUME);
        song.setLooping(false);
    }

    @Override
    public void onCompletion(Music music) {
        music.stop();
        start(this.songs.get(++currentSong % this.songs.size()));
    }
}
