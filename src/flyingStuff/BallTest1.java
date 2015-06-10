package flyingStuff;

import java.awt.Canvas;
import java.awt.Color;
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
	private static final int BALL_CD = 0;
	private int ballCD = 0;
	private int ballkillCD = 0;
	private int t = 0;
	private BufferedImage back;
	// audio
	AudioTest at;
	// mouse vel
	private int x1 = -1, x2 = -1, y1 = -1, y2 = -1;

	public BallTest1() {
		setBackground(Color.WHITE);
		setVisible(true);
		at = new AudioTest();
		balls = new BallList();
		// for (int i = 0; i < 1; i++) {
		// balls.add();
		// }
		// Ball ball1 = new Ball.BallBuilder().XY(400, 500).C(Color.RED).R(400).build();
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
		// ball1.moveCurveRel(new Function<Double>() {
		// private final int div = 2;
		// private double fact = 0.24;
		//
		// @Override
		// public int time() {
		// return 200;
		// }
		//
		// @Override
		// public Double x(Double t) {
		// if (t < fact)
		// return t / fact;
		// return 1 + (1 - (t - fact) / (1 - fact))
		// * Math.cos(Math.PI / 2 - 2 * Math.PI * (t - fact) / (1 - fact)) /
		// div;
		// }
		//
		// @Override
		// public Double y(Double t) {
		// if (t < fact)
		// return Math.pow(((1 - 1 / (1 + Math.pow(Math.E, 10 * (t / fact -
		// 0.5)))) / div) * 2, 2) / 2;
		// return (1 - (t - fact) / (1 - fact)) * Math.sin(Math.PI / 2 - 2 *
		// Math.PI * (t - fact) / (1 - fact))
		// / div;
		// }
		//
		// @Override
		// public int timeDiff() {
		// return 200;
		// }
		//
		// }, 600, 500);
		// Function<Double> lower = new Function<Double>() {
		// private final int tim = 20;
		//
		// @Override
		// public int time() {
		// return tim;
		// }
		//
		// @Override
		// public Double x(Double t) {
		// return (1 - Math.cos(t * Math.PI)) / 2;
		// }
		//
		// @Override
		// public Double y(Double t) {
		// return Math.sin(t * Math.PI) / 2;
		// }
		//
		// @Override
		// public int timeDiff() {
		// return tim;
		// }
		// };
		// ball1.moveCurveRel(lower, 700, 500);
		// ball1.moveCycleCurve(new int[] { 600 }, new int[] { 500 }, lower,
		// lower);
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
		// for (int i = 0; i < balls.getLength(); i++) {
		// while (balls.getBalls().get(i).getR() < 30) {
		// balls.getBalls().get(i).grow();
		// }
		// while (balls.getBalls().get(i).getR() > 1) {
		// balls.getBalls().get(i).shrink();
		// }
		// }
		Graphics2D twoDGraph = (Graphics2D) window;
		if (back == null)
			back = (BufferedImage) createImage(getWidth(), getHeight());
		Graphics graphToBack = back.createGraphics();
		((Graphics2D) graphToBack).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		balls.drawAll(graphToBack, t);
		if (ballCD == 0) {
			if (keys[0]) {
				balls.add();
				ballCD = BALL_CD;
			}
		}
		else
			ballCD--;
		if (ballkillCD == 0) {
			if (keys[1]) {
				balls.remove(graphToBack);
				ballkillCD = BALL_CD;
			}
		}
		else
			ballkillCD--;

		double fact = at.getPulse();
		if (fact != -1)
			balls.pulse(fact);
		twoDGraph.drawImage(back, null, 0, 0);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == Runner.ADD_BALL_KEY) {
			keys[0] = true;
		}
		if (e.getKeyCode() == Runner.REMOVE_BALL_KEY) {
			keys[1] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == Runner.ADD_BALL_KEY) {
			keys[0] = false;
		}
		if (e.getKeyCode() == Runner.REMOVE_BALL_KEY) {
			keys[1] = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

		if (e.getKeyChar() == Runner.FRICTION_KEY) {
			Ball.friction = !Ball.friction;
		}
	}

	@Override
	public void run() {
		at.run();
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