package flyingStuff;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

class BallTest1 extends Canvas implements Runnable {
	private Ball ball;

	public BallTest1() {
		setBackground(Color.WHITE);
		setVisible(true);

		ball = new Ball();

		new Thread(this).start();
	}

	public void update(Graphics window) {
		paint(window);
	}

	public void paint(Graphics window) {

		ball.moveAndDraw(window);

		if (!(ball.getX() >= 10 && ball.getX() <= 550)) {
			ball.setXSpeed(-ball.getXSpeed());
		}

		if (!(ball.getY() >= 10 && ball.getY() <= 450)) {
			ball.setYSpeed(-ball.getYSpeed());
		}
	}

	public void run() {
		try {
			while (true) {
				Thread.currentThread().sleep(19);
				repaint();
			}
		} catch (Exception e) {
		}
	}
}