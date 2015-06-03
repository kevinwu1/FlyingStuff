package flyingStuff;

import java.awt.Component;

import javax.swing.JFrame;

public class Runner extends JFrame {
	static final int WIDTH = 550;
	static final int HEIGHT = 550;

	public Runner() {
		super("PONG TESTER");
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
	}
}
