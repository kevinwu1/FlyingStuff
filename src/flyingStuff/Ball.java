package flyingStuff;

import java.awt.Color;
import java.awt.Graphics;

public class Ball implements Locatable {
	static final int X = 50, Y = 50, R = 10;
	static final Color C = Color.RED;
	private int x, y, r;
	private Color c;
	private Function abs;

	// boundary detection
	private final int rb, lb, tb, bb;

	private short moveMode;
	// moveAbs, mode 0
	private int xref = 0, yref = 0;
	private boolean xneg = false, yneg = false;
	// moveLine, mode 1
	private int xv, yv;
	// moveLineAcc, mode 2
	private double xAcc, yAcc;
	private double xvAcc, yvAcc;

	static abstract class Function {
		public abstract int time();

		public abstract int x(int t);

		public abstract int y(int t);
	}

	public Ball() {
		this(new BallBuilder().XY((int) (Math.random() * 200), (int) (Math.random() * 200)).R(5));
	}

	public Ball(int x, int y, int r, Color c) {
		setX(x);
		setY(y);
		setR(r);
		rb = Runner.INNER_WIDTH - r;
		lb = r;
		tb = r;
		bb = Runner.INNER_HEIGHT - r;
		setColor(c);
	}

	public Ball(BallBuilder b) {
		setX(b.x);
		setY(b.y);
		setR(b.r);
		rb = Runner.INNER_WIDTH - r;
		lb = r;
		tb = r;
		bb = Runner.INNER_HEIGHT - r;
		setColor(b.c);
	}

	static class BallBuilder {
		private int x, y, r;
		private Color c;
		private Function f;

		BallBuilder() {
			x = X;
			y = Y;
			r = R;
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

		BallBuilder XY(int x, int y) {
			this.x = x;
			this.y = y;
			return this;
		}

		BallBuilder R(int r) {
			this.r = r;
			return this;
		}

		BallBuilder C(Color c) {
			this.c = c;
			return this;
		}

		BallBuilder Move(Function f) {
			this.f = f;
			return this;
		}

		Ball build() {
			if (f == null)
				return new Ball(x, y, r, c);
			return new Ball(x, y, r, c).initMove(f);
		}
	}

	public Ball initMove(Function abs) {
		this.abs = abs;
		return this;
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

	public void setColor(Color c) {
		this.c = c;
	}

	public Color getColor() {
		return c;
	}

	@Override
	public int getLeft() {
		return getX() - getR();
	}

	@Override
	public int getTop() {
		return getY() - getR();
	}

	public void moveLineAcc(double xv, double yv) {
		moveMode = 2;
		xvAcc = xv;
		yvAcc = yv;
		xAcc = x;
		yAcc = y;
	}

	public void moveLine(int xv, int yv) {
		moveMode = 0;
		this.xv = xv;
		this.yv = yv;
	}

	public void moveAbs(Function abs) {
		moveMode = 1;
		this.abs = abs;
		xref = yref = 0;
		xneg = yneg = false;
	}

	public void draw(Graphics window, Color c) {
		window.setColor(c);
		window.fillOval(getLeft(), getTop(), getR() * 2, getR() * 2);
	}

	public void clear(Graphics window) {
		window.setColor(Color.white);
		window.fillOval(getLeft(), getTop(), getR() * 2, getR() * 2);
	}

	public void move(int t) {
		switch (moveMode) {
		case 0:
			moveLine();
			break;
		case 1:
			moveAbs(t);
			break;
		case 2:
			moveLineAcc();
			break;
		default:
			break;
		}
	}

	private void moveLine() {
		x += xv;
		y += yv;
		if (x > rb || x < lb)
			x += xv = -xv;
		if (y > bb || y < tb)
			y += yv = -yv;
	}

	private void moveAbs(int t) {
		if (t > abs.time())
			return;
		int xt = xref + (xneg ? -1 : 1) * abs.x(t);
		if (x > rb) {
			xref = 2 * rb - xref;
			xneg = !xneg;
			xt = rb - (x - rb);
		}
		else if (x < lb) {
			xref = 2 * lb - xref;
			xneg = !xneg;
			xt = lb - x + lb;
		}
		x = xt;
		int yt = yref + (yneg ? -1 : 1) * abs.y(t);
		if (y > bb) {
			yref = 2 * bb - yref;
			yneg = !yneg;
			yt = bb - (y - bb);
		}
		else if (y < lb) {
			yref = 2 * tb - yref;
			yneg = !yneg;
			yt = tb - y + tb;
		}
		y = yt;
	}

	private void moveLineAcc() {
		xAcc += xvAcc;
		yAcc += yvAcc;
		if (xAcc > rb || xAcc < lb)
			xAcc += xvAcc = -xvAcc;
		if (yAcc > bb || yAcc < tb)
			yAcc += yvAcc = -yvAcc;
		x = (int) xAcc;
		y = (int) yAcc;
	}

	@Override
	public void setR(int r) {
		this.r = r;
	}

	@Override
	public int getR() {
		return r;
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
