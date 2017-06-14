package src;
import java.awt.Color;
import java.io.File;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.IOException;

import javax.swing.JFrame;

public class Main {
	
	String imgFile = "test.png";
	final JFileChooser fc = new JFileChooser();
	JPanel ioPanel;
	JPanel imgPanel;
	JFrame frame;

    public static final int EMOJI_SIZE = 128;
    public Main(){
    	frame = new JFrame("Emojifier");
    	frame.setLayout(new BorderLayout());
    	
    	//I/O components
    	this.ioPanel = new JPanel(new GridLayout(1,3));
    	JButton button1 = new JButton("Load");
    	JButton button2 = new JButton("Save");
    	JButton button3 = new JButton("Clear");
    	ioPanel.add(button1);
    	ioPanel.add(button2);
    	ioPanel.add(button3);
    	frame.add(ioPanel, BorderLayout.NORTH);
    	
    	//Image frame
    	imgPanel = new JPanel();
    	Image image = null;
    	
    	try {
    		image = ImageIO.read(getClass().getResource("/" + imgFile));
    	}
    	catch(IOException e){}
    	
    	JLabel label = new JLabel(new ImageIcon(image));
    	imgPanel.add(label);
    	frame.add(imgPanel, BorderLayout.CENTER);
    	
    	frame.pack();
    	frame.setVisible(true);
    	
    	button3.addActionListener(new ClearListener());
    	button2.addActionListener(new LoadListener());
    }
    

	public static void main(String[] args){
		new Main();
	}
	
	private class ClearListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			imgPanel.setVisible(false);
		}
	}
	
	private class LoadListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			int returnVal = fc.showOpenDialog(imgPanel);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            imgFile = file.toString();
	            frame.pack();
			}
			imgPanel.setVisible(true);
		}
	}
	
}
