package flyingStuff;

interface Locatable {

	public int getXV();

	public int getYV();

	public void setXV(int xv);

	public void setYV(int yv);

	public void setXY(int x, int y);

	public void setX(int x);

	public void setY(int y);

	public int getX();

	public int getY();

	public void setR(int r);

	public int getR();

	public int getLeft();

	public int getRight();

	public int getBot();

	public int getTop();

	public boolean collides(Locatable obj);
}
