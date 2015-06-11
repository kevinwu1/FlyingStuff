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
	static final int WIDTH = 1920;
	static final int HEIGHT = 1080;
	static int INNER_WIDTH;
	static int INNER_HEIGHT;
	static final char ADD_BALL_KEY = 'q', REMOVE_BALL_KEY = 'x', FRICTION_KEY = 'f', PREV_KEY = 'j', PAUSE_KEY = ' ',
			SHUFFLE_KEY = 's', RANDOM_KEY = 'r', NEXT_KEY = 'l', HELP_KEY = 'h';

	public Runner() {
		super("Bounce");
		setSize(WIDTH, HEIGHT);

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String args[]) {
		Audio.readFromDir(args[0]);

		run = new Runner();
		INNER_WIDTH = run.getContentPane().getWidth();
		INNER_HEIGHT = run.getContentPane().getHeight();

		final JPanel titleScreen = new JPanel();
		titleScreen.setLayout(null);

		JLabel credits = new JLabel();
		credits.setText("Made by Kevin Wu and Nathan Mar");
		credits.setSize(200, 50);
		credits.setLocation((INNER_WIDTH - 200) / 2, INNER_HEIGHT - credits.getHeight());
		titleScreen.add(credits);

		JLabel instr = new JLabel("Press " + KeyEvent.getKeyText(ADD_BALL_KEY) + " to spawn a ball. Press "
				+ KeyEvent.getKeyText(REMOVE_BALL_KEY) + " to remove a ball. Press " + FRICTION_KEY
				+ " to toggle friction. Click to make balls converge.");
		instr.setSize(instr.getText().length() * 6, 200);
		instr.setLocation((INNER_WIDTH - instr.getWidth()) / 2, INNER_HEIGHT - 600);
		titleScreen.add(instr);

		JButton startBut = new JButton("Start");
		startBut.setBackground(Color.GREEN);
		startBut.setText("Start");
		startBut.setSize(200, 50);
		startBut.setLocation((Runner.INNER_WIDTH - startBut.getWidth()) / 2, Runner.INNER_HEIGHT - 100);
		titleScreen.add(startBut);
		run.add(titleScreen);
		startBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				titleScreen.setVisible(false);
				run.remove(titleScreen);
				run.add(new BallTest1());
			}
		});

		// TODO
		// features
		// sound
	}
}
