package io.mygame.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

public class SoundManager implements Disposable {
    private float volume;

    private Music bgMusic;
    private Sound ambience;
    private Sound currentSound;

    public SoundManager(Builder builder) {
        volume = 1.0f;

        this.bgMusic = builder.bgMusic;
        this.bgMusic.setVolume(volume); // Start with volume 0
        this.bgMusic.setLooping(true);
        this.bgMusic.play();

        this.ambience = builder.ambience;
        this.ambience.setVolume(this.ambience.loop(), 1.0f); // Start with volume 0


        this.currentSound = builder.currentSound;
    }

    public static class Builder {
        private Music bgMusic;
        private Sound ambience;
        private Sound currentSound;

        public Builder setbgMusic(String name) {
            this.bgMusic = Gdx.audio.newMusic(Gdx.files.internal(name));
            return this;
        }

        public Builder setAmbience(String name) {
            this.ambience = Gdx.audio.newSound(Gdx.files.internal(name));
            this.ambience.setVolume(this.ambience.loop(), 1.0f);
            return this;
        }

        public SoundManager build() {
            return new SoundManager(this);
        }
    }

    @Override
    public void dispose() {
        if (bgMusic != null) {
            bgMusic.dispose();
        }
        if (ambience != null) {
            ambience.dispose();
        }
    }

    // Example methods to interact with the sounds
    public void playBackgroundMusic() {
        if (bgMusic != null) {
            bgMusic.setLooping(true);
            bgMusic.play();
        }
    }
}
