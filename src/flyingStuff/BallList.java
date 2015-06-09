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
		ballList.add(new Ball.BallBuilder().X(rand(r, Runner.INNER_WIDTH - r)).Y(rand(r, Runner.INNER_HEIGHT - r))
				.R(rand(5, 20)).C(new Color(rand(0, 256), rand(0, 256), rand(0, 256))).build());

	}

	public void add(Ball b) {
		ballList.add(b);
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

	public void drawAll(Graphics window, int t) {
		// System.out.println("a");
		for (Ball b : ballList) {
			b.clear(window);
		}
		for (Ball b : ballList) {
			b.move(t);
			b.draw(window, b.getColor());
		}

	}

	public void converge(final int x, final int y, final int clickTime) {
		final int time = 50;
		for (Ball b : ballList) {
			final int xs = b.getX();
			final int ys = b.getY();
			final int dx = x - xs;
			final int dy = y - ys;
			b.moveAbs(new Ball.Function() {
				@Override
				public int y(int t) {
					return ys + (dy * (t - clickTime)) / time;
				}

				@Override
				public int x(int t) {
					// System.out.println(t - clickTime + ": " + (xs + (dx * (t - clickTime)) / time));
					return xs + (dx * (t - clickTime)) / time;
				}

				@Override
				public int time() {
					return time + clickTime;
				}
			});
		}
	}

	public void scatter() {
		for (Ball b : ballList) {
			double ang = Math.random() * Math.PI * 2;
			int vel = 10;
			b.moveLineAcc(vel * Math.cos(ang), vel * Math.sin(ang));
		}
	}

	public int getLength() {
		return ballList.size();
	}

}
