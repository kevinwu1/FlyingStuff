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

import flyingStuff.Ball.Function;

class BallTest1 extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	BallList balls = new BallList();
	public static int WIDTH;
	public static int HEIGHT;
	private boolean[] keys;
	private boolean pressed = false;
	private static final int BALL_CD = 3;
	private int ballCD = 0;
	private int ballkillCD = 0;
	private int t = 0;
	private BufferedImage back;

	public BallTest1() {
		setBackground(Color.WHITE);
		setVisible(true);
		balls = new BallList();
		// for (int i = 0; i < 1; i++) {
		// balls.add();
		// }
		Ball ball1 = new Ball.BallBuilder().XY(100, 500).C(Color.RED).R(20).build();
		// ball1.moveLine(3, 4);
		// ball1.moveCurveRel(new Function<Double>() {
		// final int tim = 20;
		//
		// @Override
		// public int time() {
		// return tim;
		// }
		//
		// @Override
		// public Double x(Double t) {
		// return t;
		// }
		//
		// @Override
		// public Double y(Double t) {
		// System.out.println(t);
		// return Math.sin(Math.PI * t * 2) / 3;
		// }
		// }, 600, 500);
		Function<Double> lower = new Function<Double>() {
			private final int tim = 20;

			@Override
			public int time() {
				return tim;
			}

			@Override
			public Double x(Double t) {
				return (1 - Math.cos(t * Math.PI)) / 2;
			}

			@Override
			public Double y(Double t) {
				return Math.sin(t * Math.PI) / 2;
			}
		};
		// ball1.moveCurveRel(lower, 700, 500);
		ball1.moveCycleCurve(new int[] { 600 }, new int[] { 500 }, lower, lower);
		balls.add(ball1);

		keys = new boolean[2];
		this.addKeyListener(this);
		this.addMouseListener(this);
		new Thread(this).start();
	}

	@Override
	public void update(Graphics window) {
		paint(window);
	}

	@Override
	public void paint(Graphics window) {
		t++;
		Graphics2D twoDGraph = (Graphics2D) window;
		if (back == null)
			back = (BufferedImage) createImage(getWidth(), getHeight());
		Graphics graphToBack = back.createGraphics();
		balls.drawAll(graphToBack, t);
		if (keys[0]) {
			if ((ballCD = (ballCD + 1) % BALL_CD) == 1)
				balls.add();
		}
		else if (ballCD != 0) {
			ballCD = (ballCD + 1) % BALL_CD;
		}
		if (pressed) {

		}
		if (keys[1]) {
			if ((ballkillCD = (ballkillCD + 1) % BALL_CD) == 1) {
				balls.remove(graphToBack);
			}
		}
		else if (ballkillCD != 0) {
			ballkillCD = (ballkillCD + 1) % BALL_CD;
		}

		twoDGraph.drawImage(back, null, 0, 0);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == Runner.addBallKey) {
			keys[0] = true;
		}
		if (e.getKeyCode() == Runner.removeBallKey) {
			keys[1] = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == Runner.addBallKey) {
			keys[0] = false;
		}
		if (e.getKeyCode() == Runner.removeBallKey) {
			keys[1] = false;
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
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		balls.converge(e.getX(), e.getY(), t);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		balls.scatter();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}