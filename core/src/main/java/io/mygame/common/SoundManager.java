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
    private float musicVolume;
    private float ambienceVolume;
    private float walkVolume;

    private Sound ambience;
    private long ambienceLoopId;

    private Array<Sound> activeSounds;
    private Array<Music> activeMusic;

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
//
//    public void setAmbienceVolume(float volume) {
//        this.ambienceVolume = volume;
//        if (ambience != null) {
//            ambience.setVolume(ambienceLoopId, globalVolume * volume);
//        }
//    }
//
//    public void setWalkVolume(float volume) {
//        this.walkVolume = volume;
//    }

    public SoundManager addSound(String name) {
        switch (name) {
            case "walk":
                Sound walkSound = Gdx.audio.newSound(Gdx.files.internal("sound/footsteps/walk_" + (random.nextInt(7) + 1) + ".ogg"));
                long soundId = walkSound.play(globalVolume * walkVolume);
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
        return this;
    }

    public void playBackgroundMusic(String musicPath) {

        for (Music music : activeMusic) {
            music.stop();
            music.dispose();
        }
        activeMusic.clear();

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal(musicPath));
        bgMusic.setLooping(true);
        bgMusic.setVolume(this.globalVolume);
        bgMusic.play();
        activeMusic.add(bgMusic);
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
            this.bgMusic = Gdx.audio.newMusic(Gdx.files.internal(name));
            return this;
        }

        public SoundManager build() {
            return new SoundManager(this);
        }
    }
}
