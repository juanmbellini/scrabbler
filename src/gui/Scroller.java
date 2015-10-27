package gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

class Scroller extends JPanel {
    private static final long serialVersionUID = 5566538477072735282L;
    private static final int WIDTH = StateVisualizer.WIDTH, HEIGHT = StateVisualizer.HEIGHT-35;
    private JScrollPane sp;
    private JTextPane tp;
    private Font font;
    private Document doc;
    private int shownStatus;
    
    public Scroller() throws FontFormatException, IOException {
        init();
        shownStatus = 0;
    }
    
    private void init() throws FontFormatException, IOException {
        this.setSize(WIDTH, HEIGHT);
        this.setLayout(null);
        
        //Load monospace font so everything is aligned
        font = Font.createFont(Font.TRUETYPE_FONT, new File("res/SourceCodePro-Regular.ttf"));
        font = font.deriveFont(Font.PLAIN, 12);
        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
        
        tp = new JTextPane();
        tp.setText("Visualizer ready!\n\n");
        tp.setEditable(false);
        tp.setSize(WIDTH, HEIGHT);
        tp.setFont(font);
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
            if(message.charAt(0) == '-' && shownStatus >= 50) {	//Flush output on 50 board states written
                doc.remove(0, doc.getLength());
                shownStatus = 0;
            }
            doc.insertString(doc.getLength(), message, null);
            tp.setCaretPosition(doc.getLength());	//Scroll to bottom
            shownStatus++;
        }
        catch (BadLocationException e) {
            System.out.println("Visualizer error.  Continuing execution.");
        }
    }
}
