package flyingStuff;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	private boolean pressed = false;
	private static final int BALL_CD = 3;
	private int ballCD = 0;
	@SuppressWarnings("unused")
	private int x, y;

	private BufferedImage back;

	public BallTest1() {
		setBackground(Color.WHITE);
		setVisible(true);
		balls = new BallList();
		for (int i = 0; i < 1; i++) {
			balls.add();
		}
		keys = new boolean[2];
		this.addKeyListener(this);
		this.addMouseListener(this);
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
		if (keys[0]) {
			if ((ballCD = (ballCD + 1) % BALL_CD) == 1)
				balls.add();
		}
		else if (ballCD != 0) {
			ballCD = (ballCD + 1) % BALL_CD;
		}
		if (pressed) {

		}
		twoDGraph.drawImage(back, null, 0, 0);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == Runner.addBallKey) {

			keys[0] = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == Runner.addBallKey) {
			keys[0] = false;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void run() {
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
		System.out.println(e.getX() + ", " + e.getY());
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressed = true;
		x = e.getX();
		y = e.getY();
		System.out.println("down: " + e.getX() + ", " + e.getY());
		balls.converge(e.getX(), e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		balls.scatter();
		System.out.println("up");
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}