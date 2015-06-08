package flyingStuff;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Runner extends JFrame {
	static Runner run;
	static final int WIDTH = 1000;
	static final int HEIGHT = 1000;
	static int INNER_WIDTH;
	static int INNER_HEIGHT;
	static final int addBallKey = KeyEvent.VK_SPACE;
	static final int removeBallKey = KeyEvent.VK_LEFT;

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

		final JPanel titleScreen = new JPanel();
		titleScreen.setLayout(null);

		JButton startBut = new JButton();
		startBut.setBackground(Color.GREEN);
		startBut.setText("Start");
		startBut.setSize(200, 50);
		startBut.setLocation((Runner.INNER_WIDTH - startBut.getWidth()) / 2, Runner.INNER_HEIGHT - 100);
		titleScreen.add(startBut);

		JLabel credits = new JLabel();
		credits.setText("Made by Kevin Wu and Nathan Mar");
		credits.setSize(200, 50);
		credits.setLocation((INNER_WIDTH - 200) / 2, INNER_HEIGHT - credits.getHeight());
		titleScreen.add(credits);

		JLabel instr = new JLabel("Press " + KeyEvent.getKeyText(addBallKey) + " to spawn a ball. Press " + KeyEvent.getKeyText(removeBallKey)
				+ " to remove a ball. Click to make balls converge.");
		instr.setSize(instr.getText().length() * 6, 200);
		instr.setLocation((INNER_WIDTH - instr.getWidth()) / 2, INNER_HEIGHT - 600);
		titleScreen.add(instr);

		run.add(titleScreen);

		startBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				titleScreen.setVisible(false);
				run.remove(titleScreen);
				run.add(new BallTest1());
			}
		});
		// TitleScreen title = new TitleScreen();
		// ((Component) title).setFocusable(true);
		// run.getContentPane().add(title);

		// TODO
		// Mouse click converge.
		// keyboard buttons add ball,

		// features
		// sound
	}
}
