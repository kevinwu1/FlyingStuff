package flyingStuff;

import java.awt.Color;

public class Ball implements Locatable {
	int x, y, r, xv, yv;
	Color c;

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
	public boolean collides(Locatable obj) {
		return false;
		// TODO: implement collision
	}

	public void setColor(Color c) {
		this.c = c;
	}

	public Color getColor() {
		return c;
	}

	@Override
	public int getXV() {
		return xv;
	}

	@Override
	public int getYV() {
		return yv;
	}

	@Override
	public void setXV(int xv) {
		this.xv = xv;
	}

	@Override
	public void setYV(int yv) {
		this.yv = yv;
	}

	// public void moveAndDraw(Graphics window) {
	// window.clearRect(getX(), getY(), getW(), getH());
	// setX(getX() + getXV());
	// setY(getY() + getYV());
	// draw(window, getColor());
	// }
	// TODO: implement x,y in the middle

	// @Override
	// public void draw(Graphics window, Color c) {
	// window.setColor(c);
	// window.fillOval(getX(), getY(), getW(), getH());
	// }
	// TODO: implement x,y in the middle

	@Override
	public void setR(int r) {
		this.r = r;
	}

	@Override
	public int getR() {
		return r;
	}

	@Override
	public int getR2() {
		return r * r;
	}
}
