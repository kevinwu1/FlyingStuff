package flyingStuff;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class BallList {
	private List<Ball> ballList;
	private static final Color[] a = { Color.GREEN, Color.BLUE, Color.BLACK, Color.RED, };
	int c = 0;

	public BallList() {
		ballList = new ArrayList<Ball>();
	}

	public void add() {
		int r = 25;
		ballList.add(new Ball.BallBuilder().X(rand(r, Runner.INNER_WIDTH - r)).Y(rand(r, Runner.INNER_HEIGHT - r)).R(r).XV(rand(2, 6)).YV(rand(2, 6))
				.C(a[c++ % a.length]).build());

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

	public int getLength() {
		return ballList.size();
	}

}
