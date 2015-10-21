package gui;

import javax.swing.JFrame;

public class StateVisualizer {
	public static final int WIDTH = 500, HEIGHT = 500;
	private static final String TITLE = "Visualizer";
	private JFrame frame;
	private Scroller s;

	public StateVisualizer() {}

	public void initialize() {
		frame = new JFrame(TITLE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		s = new Scroller();
		frame.add(s);
		frame.setVisible(true);
	}
	
	public void print(String message) {
		if(s == null) return;	//Not ready yet
		s.print(message);
	}
}
