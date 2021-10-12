package com.gnudios.libgdx.model.assets;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;

import java.util.ArrayList;

public class SoundHandler {

    private AssetLoader2 assetLoader;
    private ArrayList<Music> soundArray = new ArrayList<>();
    private ArrayList<Music> playingSoundArray = new ArrayList<>();
    private float soundVolume = 1;
    private float currentSoundVolume = 1;

    public SoundHandler(AssetLoader2 assetLoader2) {
        this.assetLoader = assetLoader2;
    }

    public Music loadSounds(String soundFileName) {
        Music music = assetLoader.getMusic(soundFileName);
        //	System.out.println("Do we add music?: " + soundArray.size());
        soundArray.add(music);

        return music;
    }

    public void playSound(Music music) {
        playingSoundArray.add(music);
        music.setVolume(currentSoundVolume);
        music.play();

        OnCompletionListener listener = new OnCompletionListener() {

            @Override
            public void onCompletion(Music music) {
                music.dispose();
                deleteFinishedPlaying();
            }
        };
        music.setOnCompletionListener(listener);

        // This automatically flushes the array of old played music. Could find a better way of doing this, but it works like this.
//		deleteFinishedPlaying();
    }

    public Music playSound(String soundFileName) {
        Music music = loadSounds(soundFileName);
        playSound(music);
        return music;
    }

    public void resumeSound(Music music) {
        playingSoundArray.add(music);
        music.setVolume(currentSoundVolume);
        music.play();
    }

    public void stopSound(Music music) {
        music.stop();
    }

    public void pauseSound(Music music) {
        music.pause();
    }

    public void unmuteSoundId(Music music) {
        currentSoundVolume = soundVolume;

        music.setVolume(currentSoundVolume);
    }

    public void muteAllSounds() {
        currentSoundVolume = 0;

        for (Music music : playingSoundArray) {
            music.setVolume(currentSoundVolume);
        }
    }

    public void unmuteAllSounds() {
        currentSoundVolume = soundVolume;

        for (Music music : playingSoundArray) {
            music.setVolume(currentSoundVolume);
        }
    }

    public float getSoundVolume() {
        return soundVolume;
    }

    public void setSoundVolume(float soundVolume) {
        this.soundVolume = soundVolume;
    }

    public float getCurrentSoundVolume() {
        return currentSoundVolume;
    }

    public void setCurrentSoundVolume(int currentSoundVolume) {
        this.currentSoundVolume = currentSoundVolume;
    }

    public void deleteFinishedPlaying() {
        ArrayList<Music> tempArray = new ArrayList<>();

        if (playingSoundArray.size() > 0) {
            for (Music music : playingSoundArray) {
                if (!music.isPlaying()) {
                    tempArray.add(music);
                }
            }
        }

        for (Music music : tempArray) {
            music.dispose();
            playingSoundArray.remove(music);
            soundArray.remove(music);
        }

        tempArray.clear();
    }

//	/**
//	 * Starts playing startMusic, then queues up repeatableMusic to play in a
//	 * loop afterwards. startMusic and repeatableMusic needs to be initialized
//	 * before starting theme music.
//	 * 
//	 * @param startMusic
//	 * @param repeatableMusic
//	 */
//	public void playThemeMusic(String startMusic, String repeatableMusic) {
//		playThemeMusic(loadSounds(startMusic), loadSounds(repeatableMusic));
//	}
//	
//	private void playThemeMusic(Music startMusic, final Music repeatableMusic) {
//		startMusic.setLooping(false);
//		startMusic.setOnCompletionListener(new Music.OnCompletionListener() {
//			@Override
//			public void onCompletion(Music aMusic) {
//				playSound(repeatableMusic);
//			}
//		});
//		repeatableMusic.setLooping(true);
//		this.playSound(startMusic);
//	}
}
