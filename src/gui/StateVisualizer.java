package gui;

import java.awt.FontFormatException;
import java.io.IOException;

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
		try {
			s = new Scroller();
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.add(s);
		frame.setVisible(true);
	}
	
	public void print(String message) {
		if(s == null) return;	//Not ready yet
		s.print(message);
	}
}
