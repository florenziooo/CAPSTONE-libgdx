package io.mygame.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Array;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Manages the background music, sound effects, and global volume settings for the game.
 * Provides methods to play and stop sounds, adjust volume levels, and manage sound resources.
 */
public class SoundManager implements Disposable {
    /************ BACKGROUND MUSIC ************/
    private static Music bgMusic;

    /************ VOLUME SETTINGS ************/
    private float globalVolume;
    private final float musicVolume;
    private final float ambienceVolume;
    private final float walkVolume;

    /************ AMBIENCE SOUND ************/
    private Sound ambience;
    private long ambienceLoopId;

    /************ ACTIVE SOUND EFFECTS ************/
    private final Array<Sound> activeSounds;
    private final Array<Music> activeMusic;

    /**
     * Constructs a SoundManager with the specified background music and default volume settings.
     *
     * @param builder the builder that provides the background music to be set
     */
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

    /**
     * Sets the global volume for all active sounds and background music.
     * The volume is applied to both background music and ambience sounds.
     *
     * @param volume the global volume level to be set
     */
    public void setGlobalVolume(float volume) {
        this.globalVolume = volume;

        for (Music music : activeMusic) {
            music.setVolume(volume * musicVolume);
        }

        if (ambience != null) {
            ambience.setVolume(ambienceLoopId, volume * ambienceVolume);
        }
    }

    /**
     * Adds a sound effect to be played based on the specified sound name.
     * Supports sound effects like "walk", "click", and "ambience".
     *
     * @param name the name of the sound effect to be played
     */
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

    /**
     * Disposes of all active sounds and music, releasing associated resources.
     * This method stops all currently playing sounds and music, and clears the active sound lists.
     */
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

    /**
     * Builder class for constructing a SoundManager with a specific background music.
     */
    public static class Builder {
        private Music bgMusic;

        /**
         * Sets the background music for the SoundManager.
         *
         * @param name the file path of the background music to be set
         * @return this Builder instance for chaining
         */
        public Builder setbgMusic(String name) {
            try {
                this.bgMusic = Gdx.audio.newMusic(Gdx.files.internal(name));
            } catch (NullPointerException e) {
                System.out.println("Error setting the bgMusic correctly.");
            }
            return this;
        }

        /**
         * Builds and returns the configured SoundManager instance.
         *
         * @return the SoundManager instance
         */
        public SoundManager build() {
            return new SoundManager(this);
        }
    }
}
