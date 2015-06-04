package flyingStuff;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class BallList {
	private List<Ball> ballList;
	private static final Color[] a = { Color.GREEN, Color.BLUE, Color.BLACK,
			Color.RED, };
	int c = 0;

	public BallList() {
		ballList = new ArrayList<Ball>();
	}

	public void add() {
		int r = 15;
		ballList.add(new Ball.BallBuilder()
				.X((int) (Math.random() * (Runner.WIDTH - r - r)) + r)
				.Y((int) (Math.random() * (Runner.HEIGHT - r - r)) + r).R(r)
				.XV((int) (Math.random() * 4) + 2)
				.YV((int) (Math.random() * 4) + 2).C(a[c++ % (a.length)])
				.build());

	}

	public void drawAll(Graphics window) {
		// System.out.println("a");
		for (int i = 0; i < ballList.size(); i++) {

			ballList.get(i).moveAndDraw(window);

		}

	}

	public int getLength() {
		return ballList.size();
	}

}
