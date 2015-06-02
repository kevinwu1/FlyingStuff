package flyingStuff;

import java.util.ArrayList;
import java.util.List;

public class BallList {
	private List<Ball> ballList;

	public BallList() {
		ballList = new ArrayList<Ball>();
	}

	public void add() {
		ballList.add(new Ball());
	}

}
