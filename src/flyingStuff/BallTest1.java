package flyingStuff;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

class BallTest1 extends Canvas implements Runnable {
	BallList balls = new BallList();
	public static int WIDTH;
	public static int HEIGHT;

	private BufferedImage back;

	public BallTest1() {
		setBackground(Color.WHITE);
		setVisible(true);
		balls = new BallList();
		for (int i = 0; i < 100; i++) {
			balls.add();
		}
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
		// ball.moveAndDraw(graphToBack);

		// if (ball.getLeft() < 0
		// || ball.getRight() > window.getClipBounds().getWidth()) {
		// ball.setXV(-ball.getXV());
		// }
		//
		// if (ball.getTop() < 0
		// || ball.getBot() > window.getClipBounds().getHeight()) {
		// ball.setYV(-ball.getYV());
		// }
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