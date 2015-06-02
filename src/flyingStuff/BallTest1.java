package flyingStuff;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

class BallTest1 extends Canvas implements Runnable {
	private Ball ball;

	private BufferedImage back;

	public BallTest1() {
		setBackground(Color.WHITE);
		setVisible(true);
		ball = new Ball.BallBuilder().R(10).XV(30).YV(90).C(Color.RED).build();
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

		ball.moveAndDraw(graphToBack);

		if (ball.getLeft() < 0 || ball.getRight() > window.getClipBounds().getWidth()) {
			ball.setXV(-ball.getXV());
		}

		if (ball.getTop() < 0 || ball.getBot() > window.getClipBounds().getHeight()) {
			ball.setYV(-ball.getYV());
		}
		twoDGraph.drawImage(back, null, 0, 0);
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
}