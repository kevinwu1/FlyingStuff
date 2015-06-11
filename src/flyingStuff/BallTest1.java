package flyingStuff;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

class BallTest1 extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	BallList balls = new BallList();
	public static int WIDTH;
	public static int HEIGHT;
	private boolean[] keys;
	private int t = 0;
	private BufferedImage back;
	private boolean help = true;
	// audio
	Audio audio;
	// mouse vel
	private int x1 = -1, x2 = -1, y1 = -1, y2 = -1;

	public BallTest1() {
		setBackground(Color.WHITE);
		setVisible(true);
		if (Audio.N != 0)
			audio = new Audio();
		balls = new BallList();
		for (int i = 0; i < 3; i++) {
			balls.add();
		}
		// Ball ball1 = new Ball.BallBuilder().XY(400, 500).C(Color.RED).R(400).build();
		// ball1.moveLine(3, 4);
		// balls.add(ball1);
		keys = new boolean[3];
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		new Thread(this).start();
	}

	@Override
	public void update(Graphics window) {
		paint(window);
	}

	@Override
	public void paint(Graphics window) {
		t++;
		if (Audio.N != 0) {
			if (!audio.isPlaying() && !Audio.paused)
				audio.goNext();
		}
		Graphics2D twoDGraph = (Graphics2D) window;
		if (back == null)
			back = (BufferedImage) createImage(getWidth(), getHeight());
		Graphics graphToBack = back.createGraphics();
		((Graphics2D) graphToBack).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphToBack.clearRect(0, 0, 1400, 240);
		balls.checkRemove(graphToBack);
		balls.drawAll(graphToBack, t);
		if (Audio.N != 0) {
			double fact = audio.getPulse();
			if (fact != -1)
				balls.pulse(fact);
		}
		if (help) {
			graphToBack.setFont(new Font("Unifont", Font.PLAIN, 20));
			graphToBack.setColor(Ball.friction ? Color.GREEN : Color.RED);
			graphToBack.drawString("[F]riction is " + (Ball.friction ? "On" : "Off"), 10, 20);
			if (Audio.N != 0) {
				graphToBack.setColor(audio.isPlaying() ? Color.GREEN : Color.RED);
				graphToBack.drawString((audio.isPlaying() ? "Playing" : "Paused") + " [SPACE]", 10, 40);
				graphToBack.setColor(Color.BLACK);
				graphToBack.drawString("Song: " + Audio.getCurrentSongName(), 10, 60);
				graphToBack.drawString("Next Song: " + Audio.whatsNext(), 10, 80);
				graphToBack.drawString("Go To Next Song [L]", 10, 100);
				graphToBack.drawString("Go To Prev Song [J] ", 10, 120);
			}
			graphToBack.setColor(Color.BLACK);
			graphToBack.drawString("Spawn Ball [Q] ", 10, 140);
			graphToBack.drawString("Delete Ball [X] ", 10, 160);
			if (Audio.N != 0) {
				graphToBack.setColor(Audio.isShuffled() ? Color.GREEN : Color.RED);
				graphToBack.drawString("Toggle Shuffle [S] " + (Audio.isShuffled() ? "Shuffled" : ""), 10, 180);
				graphToBack.setColor(Color.BLACK);
				graphToBack.drawString("Random Song [R] ", 10, 200);
			}
			graphToBack.drawString("Show/Hide Help [H] ", 10, 220);
		}
		twoDGraph.drawImage(back, null, 0, 0);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == Runner.REMOVE_BALL_KEY) {
			keys[1] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == Runner.REMOVE_BALL_KEY) {
			keys[1] = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == Runner.ADD_BALL_KEY) {
			balls.add();
		}
		if (e.getKeyChar() == Runner.REMOVE_BALL_KEY) {
			balls.remove();
		}
		if (e.getKeyChar() == Runner.FRICTION_KEY) {
			Ball.friction = !Ball.friction;
		}
		if (e.getKeyChar() == Runner.PREV_KEY) {
			if (Audio.N != 0)
				audio.goPrev();
		}
		if (e.getKeyChar() == Runner.PAUSE_KEY) {
			if (Audio.N != 0)
				audio.togglePause();
		}
		if (e.getKeyChar() == Runner.NEXT_KEY) {
			if (Audio.N != 0)
				audio.goNext();
		}
		if (e.getKeyChar() == Runner.HELP_KEY) {
			help = !help;
		}
		if (e.getKeyChar() == Runner.RANDOM_KEY) {
			if (Audio.N != 0) {
				Audio.toggleShuffleSongs();
				if (!Audio.isShuffled())
					Audio.toggleShuffleSongs();
				audio.goNext();
			}
		}
		if (e.getKeyChar() == Runner.SHUFFLE_KEY) {
			if (Audio.N != 0)
				Audio.toggleShuffleSongs();
		}

	}

	@Override
	public void run() {
		if (Audio.N != 0)
			audio.run();
		try {
			while (true) {
				Thread.sleep(10);
				repaint();
			}
		}
		catch (Exception e) {
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		x1 = e.getX();
		y1 = e.getY();
		balls.converge(e.getX(), e.getY(), t, 50);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// System.out.println(x1 + "," + x2 + "|" + y1 + "," + y2);
		if (x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0) {
			balls.scatter(10);
		}
		else {
			int dxv = x2 - x1;
			int dyv = y2 - y1;
			int vel = (int) Math.sqrt(dxv * dxv + dyv * dyv);
			balls.scatter(vel);
		}
		x1 = x2 = y1 = y2 = -1;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x2 = x1;
		y2 = y1;
		x1 = e.getX();
		y1 = e.getY();
		balls.converge(e.getX(), e.getY(), t, 10);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}