package flyingStuff;

import java.awt.Color;
import java.awt.Graphics;

public class Ball implements Locatable {
	static final int X = 50, Y = 50, R = 10, XV = 3, YV = 3;
	static final Color C = Color.RED;
	int x, y, r, xv, yv;
	Color c;

	public Ball() {
		setX((int) (Math.random() * 200));
		setY((int) (Math.random() * 200));
		setR(5);
		setXV(3);
		setYV(3);

	}

	public Ball(int x, int y, int r, int xv, int yv, Color c) {
		setX(x);
		setY(y);
		setR(r);
		setXV(xv);
		setYV(yv);
		setColor(c);
	}

	static class BallBuilder {
		private int x, y, r, xv, yv;
		private Color c;

		BallBuilder() {
			x = X;
			y = Y;
			r = R;
			xv = XV;
			yv = YV;
			c = C;
		}

		BallBuilder X(int x) {
			this.x = x;
			return this;
		}

		BallBuilder Y(int y) {
			this.y = y;
			return this;
		}

		BallBuilder R(int r) {
			this.r = r;
			return this;
		}

		BallBuilder XV(int xv) {
			this.xv = xv;
			return this;
		}

		BallBuilder YV(int yv) {
			this.yv = yv;
			return this;
		}

		BallBuilder C(Color c) {
			this.c = c;
			return this;
		}

		Ball build() {
			return new Ball(x, y, r, xv, yv, c);
		}
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

	@Override
	public int getLeft() {
		return getX() - getR();
	}

	@Override
	public int getTop() {
		return getY() - getR();
	}

	// public void moveAndDraw(Graphics window) {
	// window.clearRect(getLeft(), getTop(), getR() * 2, getR() * 2);
	// setX(getX() + getXV());
	// setY(getY() + getYV());
	// draw(window, getColor());
	// turn();
	// }

	public void draw(Graphics window, Color c) {
		window.setColor(c);
		window.fillOval(getLeft(), getTop(), getR() * 2, getR() * 2);
	}

	public void turn() {
		if (getLeft() < 0 || getRight() > Runner.INNER_WIDTH) {
			setXV(-getXV());
		}

		if (getTop() < 0 || getBot() > Runner.INNER_HEIGHT) {
			setYV(-getYV());
		}
	}

	public void clear(Graphics window) {
		window.setColor(Color.white);
		window.fillOval(getLeft(), getTop(), getR() * 2, getR() * 2);
	}

	public void move() {
		setX(getX() + getXV());
		setY(getY() + getYV());
		turn();
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

	@Override
	public int getRight() {
		return x + r;
	}

	@Override
	public int getBot() {
		return y + r;
	}
}
