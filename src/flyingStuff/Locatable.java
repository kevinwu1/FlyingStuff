package flyingStuff;

interface Locatable {
	public void setXY(int x, int y);

	public void setX(int x);

	public void setY(int y);

	public int getX();

	public int getY();

	public void setWH(int w, int h);

	public void setW(int w);

	public void setH(int h);

	public int getW();

	public int getH();

	public boolean collides(Locatable obj);
}
