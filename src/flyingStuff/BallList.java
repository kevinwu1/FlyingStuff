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

		ballList.add(new Ball.BallBuilder()
				.X((int) (Math.random() * Runner.WIDTH))
				.Y((int) (Math.random() * Runner.HEIGHT)).R(10).XV(3).YV(4)
				.C(a[c++ % (a.length)]).build());

	}

	public void drawAll(Graphics window) {
		for (Ball b : ballList) {
			b.moveAndDraw(window);

		}

	}

}
