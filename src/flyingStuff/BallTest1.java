package flyingStuff;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

class BallTest1 extends Canvas implements Runnable {
	private Ball ball;

	public BallTest1() {
		setBackground(Color.WHITE);
		setVisible(true);

		ball = new Ball.BallBuilder().X(10).Y(10).R(10).XV(3).YV(3).C(Color.RED).build();
		new Thread(this).start();
	}

	@Override
	public void update(Graphics window) {
		paint(window);
	}

	@Override
	public void paint(Graphics window) {

		ball.moveAndDraw(window);

		if (ball.getLeft() < 0 || ball.getRight() > Runner.WIDTH) {
			ball.setXV(-ball.getXV());
		}

		if (ball.getTop() < 0 || ball.getBot() > Runner.HEIGHT) {
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