package flyingStuff;


public class MovementData {
	private int x = 0;
	private int y = 0;
	private int s = -1;
	public static final MovementData wait = new MovementData(0, 0);

	public MovementData(String s) {
		for (char c : s.toUpperCase().toCharArray()) {
			if (c == 'U')
				y--;
			if (c == 'D')
				y++;
			if (c == 'L')
				x--;
			if (c == 'R')
				x++;
		}
	}

	public MovementData(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public MovementData(int x, int y, int s) {
		this(x, y);
		this.s = s;
	}

	int getX() {
		return x;
	}

	int getY() {
		return y;
	}

	int getS() {
		return s;
	}

	@Override
	public String toString() {
		return String.format("x(%d),y(%d)", x, y);
	}
}