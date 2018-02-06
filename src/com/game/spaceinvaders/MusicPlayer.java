package com.game.spaceinvaders;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class MusicPlayer implements LineListener {

	String background;
	Thread player;
	Thread effect;
	private File soundFile;
	private AudioInputStream ais;
	private AudioFormat format;
	private DataLine.Info info;
	private Clip clip;
	private FloatControl gainControl;

	public MusicPlayer() {

	}

	public void playEffect(String fileName, float volume) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				playSound(fileName, volume);
			}
		});
		t.run();
	}

	public void playSound(String fileName, float volume) {
		try {
			soundFile = new File(fileName);
			ais = AudioSystem.getAudioInputStream(soundFile);
			format = ais.getFormat();
			info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(ais);
			gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(volume);
			clip.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(LineEvent arg0) {
		if (arg0.getType() == LineEvent.Type.STOP) {
			clip.close();
			clip.flush();
			clip.setFramePosition(0);
		}
		// TODO Auto-generated method stub

	}

}
