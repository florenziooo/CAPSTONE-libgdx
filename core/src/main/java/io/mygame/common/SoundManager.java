package io.mygame.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Array;

import static com.badlogic.gdx.math.MathUtils.random;

public class SoundManager implements Disposable {
    private static Music bgMusic;
    private float globalVolume;
    private final float musicVolume;
    private final float ambienceVolume;
    private final float walkVolume;

    private Sound ambience;
    private long ambienceLoopId;

    private final Array<Sound> activeSounds;
    private final Array<Music> activeMusic;

    public SoundManager(Builder builder) {
        this.globalVolume = 1.0f;
        this.musicVolume = 0.2f;
        this.ambienceVolume = 0.2f;
        this.walkVolume = 1.0f;
        this.activeSounds = new Array<>();
        this.activeMusic = new Array<>();

        if (bgMusic != null && bgMusic.isPlaying()) {
            bgMusic.stop();
            bgMusic.dispose();
        }

        bgMusic = builder.bgMusic;
        if (bgMusic != null) {
            bgMusic.setLooping(true);
            bgMusic.setVolume(this.globalVolume * this.musicVolume);
            bgMusic.play();
            activeMusic.add(bgMusic);
        }
    }

    public void setGlobalVolume(float volume) {
        this.globalVolume = volume;

        for (Music music : activeMusic) {
            music.setVolume(volume * musicVolume);
        }

        if (ambience != null) {
            ambience.setVolume(ambienceLoopId, volume * ambienceVolume);
        }
    }

    public void addSound(String name) {
        try {
            switch (name) {
                case "walk":
                    Sound walkSound = Gdx.audio.newSound(Gdx.files.internal("sound/footsteps/walk_" + (random.nextInt(5) + 1) + ".ogg"));
                    walkSound.play(globalVolume * walkVolume);
                    activeSounds.add(walkSound);
                    break;
                case "click":
                    Sound clickSound = Gdx.audio.newSound(Gdx.files.internal("sound/ui/select/select_" + (random.nextInt(3) + 1) + ".ogg"));
                    clickSound.play(globalVolume);
                    activeSounds.add(clickSound);
                    break;
                case "ambience":
                    ambience = Gdx.audio.newSound(Gdx.files.internal("sound/ambience/mild_traffic.mp3"));
                    ambienceLoopId = ambience.loop(globalVolume * ambienceVolume);
                    activeSounds.add(ambience);
                    break;
            }
        } catch (NullPointerException e) {
            System.out.println("Error adding sound: ");
        }
    }

    @Override
    public void dispose() {
        if (ambience != null) {
            ambience.stop(ambienceLoopId);
            ambience.dispose();
        }

        for (Music music : activeMusic) {
            music.stop();
            music.dispose();
        }

        for (Sound sound : activeSounds) {
            sound.dispose();
        }

        activeMusic.clear();
        activeSounds.clear();
    }

    public static class Builder {
        private Music bgMusic;

        public Builder setbgMusic(String name) {
            try {
                this.bgMusic = Gdx.audio.newMusic(Gdx.files.internal(name));
            } catch (NullPointerException e) {
                System.out.println("Error setting the bgMusic correctly.");
            }
            return this;
        }

        public SoundManager build() {
            return new SoundManager(this);
        }
    }
}
