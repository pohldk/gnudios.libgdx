package com.gnudios.libgdx.model.assets;

import com.badlogic.gdx.audio.Music;

import java.util.ArrayList;

public class MusicHandler {

    private AssetLoader2 assetLoader;
    private ArrayList<Music> musicArray = new ArrayList<>();
    private ArrayList<Music> playingMusicArray = new ArrayList<>();
    private float musicVolume = 1;
    private float currentMusicVolume = 1;

    public MusicHandler(AssetLoader2 assetLoader2) {
        this.assetLoader = assetLoader2;
    }

    public Music loadMusic(String musicFileName) {
        Music music = assetLoader.getMusic(musicFileName);
        musicArray.add(music);

        return music;
    }

    public void playMusic(Music music) {
        playingMusicArray.add(music);
        music.setVolume(currentMusicVolume);
        music.play();

        // This automatically flushes the array of old played music. Could find a better way of doing this, but it works like this.
        deleteFinishedPlaying();
    }

    public Music playMusic(String musicFileName) {
        Music music = loadMusic(musicFileName);
        playMusic(music);
        return music;
    }

    public void resumeMusic(Music music) {
        playingMusicArray.add(music);
        music.setVolume(currentMusicVolume);
        music.play();
    }

    public void stopMusic(Music music) {
        music.stop();
    }

    public void pauseMusic(Music music) {
        music.pause();
    }

    public void unmuteMusicId(Music music) {
        currentMusicVolume = musicVolume;

        music.setVolume(currentMusicVolume);
    }

    public void muteAllMusic() {
        currentMusicVolume = 0;

        for (Music music : playingMusicArray) {
            music.setVolume(currentMusicVolume);
        }
    }

    public void unmuteAllMusic() {
        currentMusicVolume = musicVolume;

        for (Music music : playingMusicArray) {
            music.setVolume(currentMusicVolume);
        }
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
    }

    public float getCurrentMusicVolume() {
        return currentMusicVolume;
    }

    public void setCurrentMusicVolume(int currentMusicVolume) {
        this.currentMusicVolume = currentMusicVolume;
    }

    public void deleteFinishedPlaying() {
        ArrayList<Music> tempArray = new ArrayList<>();

        if (playingMusicArray.size() > 0) {
            for (Music music : playingMusicArray) {
                if (!music.isPlaying()) {
                    tempArray.add(music);
                }
            }
        }

        for (Music music : tempArray) {
            playingMusicArray.remove(music);
        }

        tempArray.clear();
    }

    /**
     * Starts playing startMusic, then queues up repeatableMusic to play in a
     * loop afterwards. startMusic and repeatableMusic needs to be initialized
     * before starting theme music.
     *
     * @param startMusic
     * @param repeatableMusic
     */
    public void playThemeMusic(String startMusic, String repeatableMusic) {
        playThemeMusic(loadMusic(startMusic), loadMusic(repeatableMusic));
    }

    private void playThemeMusic(Music startMusic, final Music repeatableMusic) {
        startMusic.setLooping(false);
        startMusic.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music aMusic) {
                playMusic(repeatableMusic);
            }
        });
        repeatableMusic.setLooping(true);
        this.playMusic(startMusic);
    }
}
