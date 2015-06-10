package flyingStuff;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

public class AudioTest {
	final Minim minim;
	final AudioPlayer player;
	private double prevLevel = 0;
	int bt = 0;

	private double prevmax;

	public static void main(String[] args) {
		new AudioTest().run();
	}

	public void run() {
		setup();
	}

	public AudioTest() {
		minim = new Minim(this);
		player = minim.loadFile("ka.mp3");
	}

	private void setup() {
		player.play();
	}

	public double getPulse() {
		double o = -1;
		double l = player.mix.level();
		double diff = l - prevLevel;
		double lmax = max(player.mix);
		double mdiff = lmax - prevmax;
		if (diff > 0.05) {
			// System.out.print("beat" + bt++ + ", \t");
			// System.out.printf("lvl: %.2f ", l);
			// System.out.printf("dif: %.2f", l - prevLevel);
			// System.out.printf("|max: \t%.2f", lmax);
			// if (diff > 0.2 || lmax > 0.98) {
			// System.out.print("\tboom, diff");
			// }
			// System.out.println();
			o = lmax * (l + diff);
		}
		prevLevel = l;
		prevmax = lmax;
		return o;
	}

	private static double max(AudioBuffer b) {
		double m = 0;
		for (double d : b.toArray())
			m = Math.max(m, d);
		return m;
	}

	public String sketchPath(String fileName) {
		return "D:\\workspace\\FlyingStuff\\" + fileName;
	}

	public InputStream createInput(String fileName) {
		try {
			return new FileInputStream(new File(fileName));
		}
		catch (FileNotFoundException e) {
			System.err.println("crap");
			e.printStackTrace();
		}
		return null;
	}
}
