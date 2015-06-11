package flyingStuff;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

public class Audio {
	final Minim minim;
	AudioPlayer player;
	private double prevLevel = 0;
	int bt = 0;

	private static boolean shuffled = false;
	static final String[] formats = { "AIFC", "AIF", "AU", "SND", "WAV", "MP3" };
	static int N;
	static String songDir;
	static private int song;
	static String[] songs;
	static int[] songOrder;
	static int[] noShuffleOrder;

	public static void readFromDir(String dir) {
		songDir = dir + File.separator;
		songs = listFilesForFolder(new File(songDir));
		songOrder = new int[N = songs.length];
		noShuffleOrder = new int[N];
		for (int i = 0; i < N; i++)
			songOrder[i] = noShuffleOrder[i] = i;
	}

	public static void toggleShuffleSongs() {
		shuffled = !shuffled;
		if (shuffled) { // was not shuffled, is now shuffled

			for (int a = 0, i = N - 1; i > 0; i--) {
				int sw = (int) (Math.random() * i);
				a = songOrder[i];
				songOrder[i] = songOrder[sw];
				songOrder[sw] = a;
			}
			for (int i = 0; i < N; i++)
				if (song == songOrder[i]) {
					song = i;
					break;
				}
		}
		else
			song = songOrder[song];
	}

	public static String[] listFilesForFolder(final File folder) {
		List<String> o = new ArrayList<>();
		for (final File fileEntry : folder.listFiles()) {
			String name = fileEntry.getName();
			for (String format : formats)
				if (name.toUpperCase().endsWith(format))
					o.add(name);
		}
		return o.toArray(new String[o.size()]);
	}

	public static String getPrevSongName() {
		if (shuffled)
			return songs[songOrder[song = (song + N - 1) % N]];
		return songs[song = (song + N - 1) % N];
	}

	public static String getNextSongName() {
		if (shuffled)
			return songs[songOrder[song = (song + 1) % N]];
		return songs[song = (song + 1) % N];
	}

	public static String getCurrentSongName() {
		if (shuffled)
			return songs[songOrder[song]];
		return songs[song];
	}

	public static String whatsNext() {
		if (shuffled)
			return songs[songOrder[(song + 1) % N]];
		return songs[(song + 1) % N];
	}

	public void goPrev() {
		player.pause();
		player = minim.loadFile(songDir + getPrevSongName());
		player.play();
	}

	public void togglePause() {
		if (player.isPlaying())
			player.pause();
		else
			player.play();
	}

	public void goNext() {
		player.pause();
		player = minim.loadFile(songDir + getNextSongName());
		player.play();
	}

	public static boolean isShuffled() {
		return shuffled;
	}

	public static void printOrder() {
		for (int i = 0; i < N; i++)
			System.out.println(songs[songOrder[i]]);

	}

	public void run() {
		setup();
	}

	public Audio() {
		minim = new Minim(this);
		player = minim.loadFile(songDir + getCurrentSongName());
	}

	private void setup() {
		player.play();
	}

	public boolean isPlaying() {
		return player.isPlaying();
	}

	public double getPulse() {
		double o = -1;
		double l = player.mix.level();
		double diff = l - prevLevel;
		double lmax = max(player.mix);
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
