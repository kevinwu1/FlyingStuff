package flyingStuff;

public class Ball implements Locatable {
	int x, y, w, h;

	@Override
	public void setXY(int x, int y) {
		setX(x);
		setY(y);
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setWH(int w, int h) {
		setW(w);
	}

	@Override
	public void setW(int w) {
		this.w = w;
	}

	@Override
	public void setH(int h) {
		this.h = h;
	}

	@Override
	public int getW() {
		return w;
	}

	@Override
	public int getH() {
		return h;
	}

	@Override
	public boolean collides(Locatable obj) {
		return false;
		// TODO: implement collision
	}

}
