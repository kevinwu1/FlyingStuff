package flyingStuff;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

class BallTest1 extends Canvas implements Runnable {
	private Ball ball;

	public BallTest1() {
		setBackground(Color.WHITE);
		setVisible(true);

		ball = new Ball(10, 10, 10, 3, 3, Color.RED);

		new Thread(this).start();
	}

	@Override
	public void update(Graphics window) {
		paint(window);
	}

	@Override
	public void paint(Graphics window) {

		ball.moveAndDraw(window);

		if (!(ball.getX() >= 10 && ball.getX() <= 550)) {
			ball.setXV(-ball.getXV());
		}

		if (!(ball.getY() >= 10 && ball.getY() <= 450)) {
			ball.setYV(-ball.getYV());
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(19);
				repaint();
			}
		}
		catch (Exception e) {
		}
	}
}