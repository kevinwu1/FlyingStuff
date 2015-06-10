package flyingStuff;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import flyingStuff.Ball.Function;

public class BallList {
	private List<Ball> ballList;
	int c = 0;

	@SuppressWarnings("unused")
	public BallList() {
		ballList = new ArrayList<Ball>();
	}

	public void add() {
		int r = rand(5, 20);
		ballList.add(new Ball.BallBuilder().X(rand(r, Runner.INNER_WIDTH - r)).Y(rand(r, Runner.INNER_HEIGHT - r)).R(r)
				.C(new Color(rand(0, 256), rand(0, 256), rand(0, 256))).build());

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
		for (Ball b : ballList) {
			b.clear(window);
		}
		for (Ball b : ballList) {
			b.move(t);
			b.draw(window, b.getColor());
		}

	}

	public void converge(final int x, final int y, final int clickTime, final int time) {
		for (Ball b : ballList) {
			// b.moveCurveRel(new Function<Double>() {
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
			// return (1 - (t - fact) / (1 - fact))
			// * Math.sin(Math.PI / 2 - 2 * Math.PI * (t - fact) / (1 - fact)) /
			// div;
			// }
			//
			// @Override
			// public int timeDiff() {
			// return clickTime;
			// }
			//
			// }, x, y);
			final int xs = b.getX();
			final int ys = b.getY();
			final int dx = x - xs;
			final int dy = y - ys;
			b.moveCurveAbs(new Function<Integer>() {
				@Override
				public Integer y(Integer t) {
					return ys + (dy * (t - clickTime)) / time;
				}

				@Override
				public Integer x(Integer t) {
					return xs + (dx * (t - clickTime)) / time;
				}

				@Override
				public int time() {
					return time + clickTime;
				}

				@Override
				public int timeDiff() {
					return clickTime;
				}
			});
		}
	}

	public void scatter(int vel) {
		for (Ball b : ballList) {
			double ang = Math.random() * Math.PI * 2;
			b.moveLineAcc(vel * Math.cos(ang), vel * Math.sin(ang));
		}
	}

	public void pulse(double fact) {
		for (Ball b : ballList)
			b.pulse(fact);
	}

	public int getLength() {
		return ballList.size();
	}

	public List<Ball> getBalls() {
		return ballList;
	}

}
