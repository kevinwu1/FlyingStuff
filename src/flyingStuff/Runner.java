package flyingStuff;

import java.awt.Component;

import javax.swing.JFrame;

public class Runner extends JFrame {
	private static final int WIDTH = 550;
	private static final int HEIGHT = 550;

	public Runner() {
		super("PONG TESTER");
		setSize(WIDTH, HEIGHT);

		// getContentPane().add(new BlockTestTwo());

		// uncomment when you are ready to test the Ball
		// /getContentPane().add(new BallTestTwo());

		BallTest1 padTest = new BallTest1();
		((Component) padTest).setFocusable(true);
		getContentPane().add(padTest);

		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String args[]) {
		Runner run = new Runner();
	}
}
