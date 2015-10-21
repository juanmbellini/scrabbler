package gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class Scroller extends JPanel {
	private static final long serialVersionUID = 5566538477072735282L;
	private static final int WIDTH = StateVisualizer.WIDTH, HEIGHT = StateVisualizer.HEIGHT-30;
	private JScrollPane sp;
	private JTextPane tp;
	private Document doc;
	
	public Scroller() {
		init();
	}
	
	private void init() {
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(null);
		
		tp = new JTextPane();
		tp.setText("Visualizer ready!\n\n");
		tp.setEditable(false);
		tp.setSize(WIDTH, HEIGHT);
		doc = tp.getDocument();
		
		sp = new JScrollPane(tp);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setSize(WIDTH, HEIGHT);
		sp.setVisible(true);
		this.add(sp);
	}
	
	public void print(String message) {
		try {
			doc.insertString(doc.getLength(), message, null);
		}
		catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
