package flyingStuff;

import java.awt.Component;

import javax.swing.JFrame;

public class Runner extends JFrame {
	static final int WIDTH = 525;
	static final int HEIGHT = 525;

	public Runner() {
		super("Bounce");
		setSize(WIDTH, HEIGHT);
		BallTest1 padTest = new BallTest1();
		((Component) padTest).setFocusable(true);
		getContentPane().add(padTest);

		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String args[]) {
		@SuppressWarnings("unused")
		Runner run = new Runner();
		// TODO
		// Mouse click converge.
		// keyboard buttons add ball,

		// features
		// sound
	}
}
