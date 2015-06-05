package flyingStuff;

import java.util.LinkedList;
import java.util.Queue;

public abstract class Moveable implements Locatable {
	Queue<MovementData> move = new LinkedList<>();
	int x, y, s;

	public void wait(int times) {
		for (int i = 0; i < times; i++)
			move.add(MovementData.wait);
	}

	public void move(int x, int y) {
		move.add(new MovementData(x, y));
	}

	public void move(int x, int y, int wait) {
		move.add(new MovementData(x, y));
		wait(wait);
	}

	public void move(String direction) {
		move.add(new MovementData(direction));
	}

	public void move(String direction, int times) {
		move(direction, times, 0);
	}

	public void move(String direction, int times, int wait) {
		for (int i = 0; i < times; i++) {
			wait(wait);
			move(direction);
		}
	}

	protected void changeP() {
		MovementData m = move.poll();
		if (m != null) {
			setX(getX() + s * m.getX());
			setY(getY() + s * m.getY());
		}
	}
}
