package flyingStuff;

import java.awt.Color;
import java.awt.Graphics;

public class Ball implements Locatable {
	int x, y, r, xv, yv;
	Color c;

	public Ball() {
		setX((int) (Math.random() * 200));
		setY((int) (Math.random() * 200));
		setR(5);
		setXV(3);
		setYV(3);

	}

	public Ball(int x, int y, int r, int xv, int yv) {
		setX(x);
		setY(r);
		setR(5);
		setXV(xv);
		setYV(yv);

	}

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

	public int getLeft() {
		return getX() - getR();
	}

	public int getTop() {
		return getY() - getR();
	}

	public void moveAndDraw(Graphics window) {
		window.clearRect(getLeft(), getTop(), getR() * 2, getR() * 2);
		setX(getX() + getXV());
		setY(getY() + getYV());
		draw(window, getColor());
	}

	public void draw(Graphics window, Color c) {
		window.setColor(c);
		window.fillOval(getLeft(), getTop(), getR() * 2, getR() * 2);
	}

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
