package flyingStuff;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class BallList {
	private List<Ball> ballList;
	int c = 0;

	@SuppressWarnings("unused")
	public BallList() {
		ballList = new ArrayList<Ball>();
	}

	public void add() {
		int r = 25;
		ballList.add(new Ball.BallBuilder().X(rand(r, Runner.INNER_WIDTH - r))
				.Y(rand(r, Runner.INNER_HEIGHT - r)).R(rand(5, 20))
				.XV(rand(2, 6)).YV(rand(2, 6))
				.C(new Color(rand(0, 256), rand(0, 256), rand(0, 256))).build());

	}

	public void remove(Graphics window) {
		if (ballList.size() > 0) {
			int removed = ballList.size() - 1;
			ballList.get(removed).clear(window);
			ballList.remove(removed);
		}

	}

	private static int rand(int lo, int hi) {
		return lo + (int) (Math.random() * (hi - lo));
	}

	public void drawAll(Graphics window) {
		// System.out.println("a");
		for (Ball b : ballList) {
			b.clear(window);
		}
		for (Ball b : ballList) {
			b.move();
			b.draw(window, b.getColor());
		}

	}

	public void converge(int x, int y) {
		for (Ball b : ballList) {
			int dx = x - b.getX();
			int dy = y - b.getY();
			b.setXV(dx / 100);
			b.setYV(dy / 100);
		}
	}

	public void scatter() {
		for (Ball b : ballList) {
			double ang = Math.random() * Math.PI * 2;
			b.setXV((int) (Math.cos(ang) * 5));
			b.setYV((int) (Math.sin(ang) * 5));
		}
	}

	public int getLength() {
		return ballList.size();
	}

}
