package flyingStuff;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

class BallTest1 extends Canvas implements Runnable, KeyListener {
	BallList balls = new BallList();
	public static final int WIDTH = 525;
	public static final int HEIGHT = 525;
	private boolean[] keys;

	private BufferedImage back;

	public BallTest1() {
		setBackground(Color.WHITE);
		setVisible(true);
		balls = new BallList();
		for (int i = 0; i < 2; i++) {
			balls.add();
		}
		keys = new boolean[1];

		// System.out.println(balls.getLength());
		new Thread(this).start();
	}

	@Override
	public void update(Graphics window) {
		paint(window);
	}

	@Override
	public void paint(Graphics window) {

		Graphics2D twoDGraph = (Graphics2D) window;
		if (back == null)
			back = (BufferedImage) createImage(getWidth(), getHeight());
		Graphics graphToBack = back.createGraphics();
		balls.drawAll(graphToBack);
		if (keys[0] == true) {
			System.out.println("a");
			balls.add();
		}
		twoDGraph.drawImage(back, null, 0, 0);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			keys[0] = true;
		}

	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			keys[0] = false;
		}

	}

	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(10);
				repaint();
			}
		} catch (Exception e) {
		}
	}
}