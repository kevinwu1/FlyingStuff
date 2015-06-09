package flyingStuff;

interface Locatable {

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
