package main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/*
    main.AudioManager - handles all audio for the game

    Loads and plays .WAV files. Songs loop automatically when they end.
    Only one music track and one sound effect can be playing at a time.

    music examples:

    audio.play("res/audio/main_menu.wav");  load and play a track (loops)
    audio.pause
    audio.resume
    audio.restart
    audio.stop
    audio.setVolume(0.5f)
    audio.fadeOut(3000);                        fade out a track over the span of 3 seconds
    audio.fadeIn("res/audio/menu.wav", 3000);   fade in a new track over a span of 3 seconds
    audio.crossfade("res/audio/next.wav", 3000); crossfade to a new track over a span of 3 seconds
    audio.isPlaying();     returns true if the music is currently playing
    audio.getCurrentTrack(); returns the file path of the currently playing track
    audio.getVolume();       returns the current music volume

    Sound effect examples:

    audio.playSFX("res/audio/click.wav");   play a sound effect once (no loop)
    audio.stopSFX(); stop the current sound effect
    audio.setSFXVolume(0.8f);  set sound effect volume independently
    audio.isSFXPlaying();      returns true if a sound effect is playing
    audio.getCurrentSFX();     returns the file path of the currently playing sound effect
    audio.getSFXVolume();      returns the current volume of the SFX channel
 */


public class AudioManager {
    //Music
    
    private Clip clip;      //the active music clip
    private String currentTrack = "";
    private float currentVolume = 1.0f; //music volume level (0.0 - 1.0)
    private boolean paused = false;     //whether the music is paused
    private long pausePosition = 0;     //the microsecond position the music was paused (used for unpausing)

    private Thread fadeThread;          //background thread for fading

    //Sound Effects

    private Clip sfxClip;               //the active sound effect clip
    private String currentSFX = "";
    private float sfxVolume = 1.0f;

    //Playback

    public void play(String filePath) {
        stop();
        clip = loadClip(filePath);
        if (clip == null) return;
        currentTrack = filePath;
        paused = false;
        pausePosition = 0;
        applyVolume(currentVolume);
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    //pause playback (does nothing if already paused or not playing)

    public void pause() {
        if (clip == null || !clip.isRunning()) return;
        pausePosition = clip.getMicrosecondPosition();
        clip.stop();
        paused = true;
    }

    //resumes playback from the position it was paused at

    public void resume() {
        if (clip == null || !paused) return;
        clip.setMicrosecondPosition(pausePosition);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        paused = false;
    }

    //restarts the current track from the beginning
    //does nothing if no track is loaded

    public void restart() {
        if (clip == null) return;
        paused = false;
        pausePosition = 0;
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    //stops playback and unloads the current track entirely

    public void stop() {
        cancelFade();
        if (clip != null) {
            clip.stop();
            clip.close();
            clip = null;
        }
        currentTrack = "";
        paused = false;
        pausePosition = 0;
    }

    //Volume

    //Sets the playback volume immediately

    public void setVolume(float volume) {
        currentVolume = Math.max(0f, Math.min(1f, volume));
        applyVolume(currentVolume);
    }

    //returns the current volume level

    public float getVolume() {
        return currentVolume;
    }

    //increases the volume by the given step. max is 1.0

    public void volumeUp(float step) {
        setVolume(currentVolume + step);
    }

    //decreases the volume by the given step

    public void volumeDown(float step) {
        setVolume(currentVolume - step);
    }

    //Fades and Crossfade

    //Gradually fades out and stops the current track

    public void fadeOut(int durationMs) {
        if (clip == null || !clip.isRunning()) return;
        cancelFade();
        float startVolume = currentVolume;
        fadeThread = new Thread(() -> {
            int steps = 60;
            int stepDelay = durationMs / steps;
            float volumeStep = startVolume / steps;
            for (int i = 0; i < steps; i++) {
                if (Thread.currentThread().isInterrupted()) return;
                setVolume(currentVolume - volumeStep);
                sleep(stepDelay);
            }
            stop();
            currentVolume = startVolume; //restore volume for next track
        });
        fadeThread.setDaemon(true);
        fadeThread.start();
    }

    //Fades a new track in from silence to the current volume

    public void fadeIn(String filePath, int durationMs) {
        cancelFade();
        float targetVolume = currentVolume;
        clip = loadClip(filePath);
        if (clip == null) return;
        currentTrack = filePath;
        paused = false;
        applyVolume(0f);
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        fadeThread = new Thread(() -> {
            int steps = 60;
            int stepDelay = durationMs / steps;
            float volumeStep = targetVolume / steps;
            for (int i = 0; i < steps; i++) {
                if (Thread.currentThread().isInterrupted()) return;
                setVolume(currentVolume + volumeStep);
                sleep(stepDelay);
            }
            setVolume(targetVolume);
        });
        fadeThread.setDaemon(true);
        fadeThread.start();
    }

    //fades out the current track while fading a new one in

    public void crossfade(String filePath, int durationMs) {
        if (clip == null) {
            fadeIn(filePath, durationMs);
            return;
        }
        cancelFade();

        //capture current state before new clip overrides
        final Clip oldClip = clip;
        final float startVolume = currentVolume;

        //load and start the new clip at zero volume
        Clip newClip = loadClip(filePath);
        if (newClip == null) return;
        currentTrack = filePath;
        clip = newClip;
        paused = false;
        applyVolumeToClip(newClip, 0f);
        newClip.setFramePosition(0);
        newClip.loop(Clip.LOOP_CONTINUOUSLY);

        fadeThread = new Thread(() -> {
            int steps = 60;
            int stepDelay = durationMs / steps;
            for (int i = 0; i <= steps; i++) {
                if (Thread.currentThread().isInterrupted()) {
                    oldClip.stop();
                    oldClip.close();
                    return;
                }
                float progress = (float) i / steps;
                applyVolumeToClip(oldClip, startVolume * (1f - progress));
                applyVolumeToClip(newClip, startVolume * progress);
                sleep(stepDelay);
            }
            oldClip.stop();
            oldClip.close();
            currentVolume = startVolume;
        });
        fadeThread.setDaemon(true);
        fadeThread.start();
    }

    //Sound effects

    //play a sound effect once

    public void playSFX(String filePath) {
        stopSFX();
        sfxClip = loadClip(filePath);
        if (sfxClip == null) return;
        currentSFX = filePath;
        applyVolumeToClip(sfxClip, sfxVolume);
        sfxClip.setFramePosition(0);
        //clean up
        sfxClip.addLineListener(event -> {
            if (event.getType() == LineEvent.Type.STOP) {
                if (sfxClip != null && !sfxClip.isRunning()) {
                    sfxClip.close();
                    sfxClip = null;
                    currentSFX = "";
                }
            }
        });
        sfxClip.start();
    }

    //stops the current sound effect early and unloads it, does nothing if no SFX is playing

    public void stopSFX() {
        if (sfxClip != null) {
            sfxClip.stop();
            sfxClip.close();
            sfxClip = null;
        }
        currentSFX = "";
    }

    //sets the sound effect volume independently of music volume

    public void setSFXVolume(float volume) {
        sfxVolume = Math.max(0f, Math.min(1f, volume));
        applyVolumeToClip(sfxClip, sfxVolume);
    }

    //returns true if a sound effect is currently playing

    public boolean isSFXPlaying() {
        return sfxClip != null && sfxClip.isRunning();
    }

    //returns the file path of the currently loaded sound effect

    public String getCurrentSFX() {
        return currentSFX;
    }

    //State queries

    //returns true if audio is currenly playing (not paused, or stopped)

    public boolean isPlaying() {
        return clip != null && clip.isRunning();
    }

    //returns true if a track is loaded and currently paused

    public boolean isPaused() {
        return paused;
    }

    //returns the file path of the currently loaded track, or an empty string if nothing is loaded

    public String getCurrentTrack() {
        return currentTrack;
    }

    //returns the current playback in seconds

    public double getPositionSeconds() {
        if (clip == null) return 0;
        return clip.getMicrosecondPosition() / 1_000_000.0;
    }

    //returns the total duration of the current track in seconds

    public double getDurationSeconds() {
        if (clip == null) return 0;
        return clip.getMicrosecondLength() / 1_000_000.0;
    }

    //Private helpers

    //loads a WAV file into a clip, returns null and prints a warning on failure

    private Clip loadClip(String path) {

        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(path));
            Clip c = AudioSystem.getClip();
            c.open(stream);
            return c;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("[main.AudioManager] Could not load audio: " + path);
            return null;
        }
    }

    //converts a 0.0-1.0 linear volume to decibels and applies it to the active clip

    private void applyVolume(float volume) {
        if (clip == null) return;
        applyVolumeToClip(clip, volume);
    }

    //applies a volume level to a specific clip (used during crossfade)

    private void applyVolumeToClip(Clip target, float volume) {
        if (target == null) return;
        try {
            FloatControl gainControl =
                    (FloatControl) target.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = volume <= 0f
                    ? gainControl.getMinimum()
                    : 20f * (float) Math.log10(volume);
                    dB = Math.max(gainControl.getMinimum(), Math.min(gainControl.getMaximum(), dB));
                    gainControl.setValue(dB);
        } catch (IllegalArgumentException e) {
            System.err.println("[main.AudioManager] Volume control not supported for this clip");
        }
    }

    //interrupts and clears any running fade thread

    private void cancelFade() {
        if (fadeThread != null && fadeThread.isAlive()) {
            fadeThread.interrupt();
            fadeThread = null;
        }
    }

    //Sleeps the current thread

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}