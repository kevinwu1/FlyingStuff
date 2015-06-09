package flyingStuff;

import java.awt.Color;
import java.awt.Graphics;

public class Ball implements Locatable {
	static final int X = 50, Y = 50, R = 10;
	static final Color C = Color.RED;
	private int x, y, r;
	private Color c;

	// boundary detection
	private final int rb, lb, tb, bb;

	// movement
	private short moveMode;
	private static final short moveLineMode = 0, moveLineAccMode = 1, moveCurveAbsMode = 2, moveCurveRelMode = 3,
			cycleCurvesMode = 4;
	// moveLine
	private int xv, yv;
	// moveLineAcc
	private double xAcc, yAcc;
	private double xvAcc, yvAcc;
	// moveCurve
	private Function<Integer> abs;
	private Function<Double> rel;
	private int xref = 0, yref = 0; // reflections to stay in boundary
	private boolean xneg = false, yneg = false; // necessary for math
	// relCurve
	private int dx, dy, xStart, yStart;
	// CycleCurve
	private Function<Double>[] relCurves;
	private int[] cCX;
	private int[] cCY;
	private int[] cumulCurveTimes;
	private int len;
	private int curCur;

	static abstract class Function<T> {
		public abstract int timeDiff();

		public abstract int time();

		public abstract T x(T t);

		public abstract T y(T t);
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
		private Function<Integer> f;

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

		BallBuilder Move(Function<Integer> f) {
			this.f = f;
			return this;
		}

		Ball build() {
			if (f == null)
				return new Ball(x, y, r, c);
			return new Ball(x, y, r, c).initMove(f);
		}
	}

	public Ball initMove(Function<Integer> abs) {
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

	@Override
	public int getLeft() {
		return getX() - getR();
	}

	@Override
	public int getTop() {
		return getY() - getR();
	}

	public void draw(Graphics window, Color c) {
		window.setColor(c);
		window.fillOval(getLeft(), getTop(), getR() * 2, getR() * 2);
	}

	public void clear(Graphics window) {
		window.setColor(Color.white);
		window.fillOval(getLeft(), getTop(), getR() * 2, getR() * 2);
	}

	public void moveLine(int xv, int yv) {
		moveMode = moveLineMode;
		this.xv = xv;
		this.yv = yv;
	}

	public void moveLineAcc(double xv, double yv) {
		moveMode = moveLineAccMode;
		xvAcc = xv;
		yvAcc = yv;
		xAcc = x;
		yAcc = y;
	}

	public void moveCurveAbs(Function<Integer> abs) {
		moveMode = moveCurveAbsMode;
		this.abs = abs;
		clearRefl();
	}

	public void moveCurveRel(Function<Double> rel, int xt, int yt) {
		moveMode = moveCurveRelMode;
		this.rel = rel;
		clearRefl();
		xStart = x;
		yStart = y;
		dx = xt - x;
		dy = yt - y;
	}

	@SafeVarargs
	public final void moveCycleCurve(int[] xCoords, int[] yCoords, Function<Double>... curves) {
		if (xCoords.length != yCoords.length)
			throw new IllegalArgumentException("Coordinate array lengths do not match");
		if (curves.length != xCoords.length + 1)
			throw new IllegalArgumentException("Need exactly N+1 curves and N coordinates");
		len = curves.length;
		moveMode = cycleCurvesMode;
		cCX = new int[len];
		cCY = new int[len];
		for (int i = 0; i < len - 1; i++) {
			cCX[i] = xCoords[i];
			cCY[i] = yCoords[i];
		}
		cCX[len - 1] = x;
		cCY[len - 1] = y;
		relCurves = curves;
		cumulCurveTimes = new int[curves.length];
		curCur = 0;
		cumulCurveTimes[0] = curves[0].time();
		clearRefl();
		for (int i = 1; i < curves.length; i++) {
			cumulCurveTimes[i] = cumulCurveTimes[i - 1] + curves[i].time();
		}
	}

	public void move(int t) {
		switch (moveMode) {
		case moveLineMode:
			moveLine();
			break;
		case moveLineAccMode:
			moveLineAcc();
			break;
		case moveCurveAbsMode:
			moveCurveAbs(t);
			break;
		case moveCurveRelMode:
			moveCurveRel(t);
			break;
		case cycleCurvesMode:
			moveCycleCurves(t);
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

	private void moveCurveAbs(int t) {
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

	private void moveCurveRel(int t) {
		t -= rel.timeDiff();
		if (t > rel.time())
			return;
		double xg, yg;
		int xf, yf;
		xg = rel.x(t / (double) rel.time()).doubleValue();
		yg = rel.y(t / (double) rel.time()).doubleValue();
		System.out.println(t + ": " + xg + ", " + yg);
		xf = (int) (xg * dx - yg * dy) + xStart;
		yf = (int) (xg * dy + yg * dx) + yStart;
		int xt = xref + (xneg ? -1 : 1) * xf;
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
		int yt = yref + (yneg ? -1 : 1) * yf;
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

	private void cycleCurveRel(int t, Function<Double> rel, int xStart, int yStart, int xTar, int yTar) {
		if (t > rel.time())
			return;
		int dx = xTar - xStart;
		int dy = yTar - yStart;
		double xg, yg;
		int xf, yf;
		xg = rel.x(t / (double) rel.time()).doubleValue();
		yg = rel.y(t / (double) rel.time()).doubleValue();
		xf = (int) (xg * dx - yg * dy) + xStart;
		yf = (int) (xg * dy + yg * dx) + yStart;
		int xt = xref + (xneg ? -1 : 1) * xf;
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
		int yt = yref + (yneg ? -1 : 1) * yf;
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

	private void moveCycleCurves(int t) {
		t %= cumulCurveTimes[len - 1];
		if (t > cumulCurveTimes[curCur]) {
			curCur++;
			curCur %= len;
			clearRefl();
		}
		cycleCurveRel(t - cumulCurveTimes[curCur], relCurves[curCur], cCX[curCur], cCY[curCur],
				cCX[(curCur + 1) % len], cCY[(curCur + 1) % len]);
	}

	private void clearRefl() {
		xref = yref = 0;
		xneg = yneg = false;
	}
}
