package projekt;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *  Klasa sluzaca do odtwarzania muzyki
 */
public class Sound {

	/**
	 *  Metoda do odtwarzania muzyki, tworzaca nowy watek i odczytujaca plik audio
	 */
	public static synchronized void playSound(final String url) {
		new Thread(new Runnable() {
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(Sound.class.getResource(url));
					clip.open(inputStream);
					clip.start();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}).start();
	}
}
