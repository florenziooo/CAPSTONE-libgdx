package io.mygame.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

import static com.badlogic.gdx.math.MathUtils.random;

public class SoundManager implements Disposable {
    private Music bgMusic;
    private float bgVolume;
    private GameManager gameManager;

    private float soundVolume;
    private Sound ambience;
    private Sound currentSound;

    public SoundManager(Builder builder) {
        this.bgVolume = 0.1f;
        this.soundVolume = 1f;

        this.bgMusic = builder.bgMusic;
        this.bgMusic.setLooping(true);

        this.ambience = builder.ambience;

        this.currentSound = builder.currentSound;
    }

    public SoundManager addSound(String name) {
        switch (name) {
            case "walk":
                currentSound = Gdx.audio.newSound(Gdx.files.internal("sound/footsteps/walk_" + (random.nextInt(7) + 1) + ".ogg"));
                long soundId = currentSound.play(); // Play and get sound instance ID
                currentSound.setVolume(soundId, 0.5f + random.nextFloat() * 0.5f); // Set volume for this instance
                break;
            case "click":
                currentSound = Gdx.audio.newSound(Gdx.files.internal("sound/ui/select/select_" + (random.nextInt(3) + 1) + ".ogg"));
                currentSound.play();
            default:
                break;
        }
        return this;
    }


    public static class Builder {
        private Music bgMusic;
        private Sound ambience;
        private Sound currentSound;

        public Builder setbgMusic(String name) {
            this.bgMusic = Gdx.audio.newMusic(Gdx.files.internal(name));
            this.bgMusic.setVolume(build().bgVolume);
            this.bgMusic.play();
            return this;
        }

        public Builder setAmbience(String name) {
            this.ambience = Gdx.audio.newSound(Gdx.files.internal(name));
            this.ambience.setVolume(this.ambience.loop(), 0.2f);
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
