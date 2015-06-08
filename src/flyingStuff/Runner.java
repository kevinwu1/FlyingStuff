package flyingStuff;

import java.awt.Component;

import javax.swing.JFrame;

public class Runner extends JFrame {
	static Runner run;
	static final int WIDTH = 800;
	static final int HEIGHT = 800;
	static int INNER_WIDTH;
	static int INNER_HEIGHT;

	public Runner() {
		super("Bounce");
		setSize(WIDTH, HEIGHT);

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String args[]) {
		run = new Runner();
		INNER_WIDTH = run.getContentPane().getWidth();
		INNER_HEIGHT = run.getContentPane().getHeight();
		BallTest1 padTest = new BallTest1();
		((Component) padTest).setFocusable(true);
		run.getContentPane().add(padTest);
		// TODO
		// Mouse click converge.
		// keyboard buttons add ball,

		// features
		// sound
	}
}
