package src;
import java.awt.Color;
import java.io.File;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.IOException;

import javax.swing.JFrame;

public class Main {
	
	String imgFile = "test.png";
	final JFileChooser fc = new JFileChooser(System.getProperty("user.dir") + "/Emojis");
	
	JPanel ioPanel;
	JPanel imgPanel;
	JFrame frame;
	JLabel label;

    public static final int EMOJI_SIZE = 128;
    public Main(){
    	frame = new JFrame("Emojifier");
    	frame.setLayout(new BorderLayout());
    	
    	//I/O components
    	this.ioPanel = new JPanel(new GridLayout(1,4));
    	JButton loadButton = new JButton("Load");
    	JButton saveButton = new JButton("Save");
    	JButton clearButton = new JButton("Clear");
    	JButton emojifyButton = new JButton("Emojify");
    	
    	ioPanel.add(loadButton);
    	ioPanel.add(emojifyButton);
    	ioPanel.add(saveButton);
    	ioPanel.add(clearButton);
    	frame.add(ioPanel, BorderLayout.NORTH);
    	
    	//Image frame
    	imgPanel = new JPanel();
    	Image image = null;
    	
    	try {
    		image = ImageIO.read(getClass().getResource("/" + imgFile));
    	}
    	catch(IOException e){}
    	
    	label = new JLabel(new ImageIcon(image));
    	imgPanel.add(label);
    	frame.add(imgPanel, BorderLayout.CENTER);
    	
    	frame.pack();
    	frame.setVisible(true);
    	
    	clearButton.addActionListener(new ClearListener());
    	loadButton.addActionListener(new LoadListener());
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
	            BufferedImage img = null;
	            try {
	                img = ImageIO.read(new File(imgFile));
	            } catch (IOException c) {}
	            label.setIcon(new ImageIcon(img));
			}
			imgPanel.setVisible(true);
			frame.pack();
		}
	}
	
	private class EmojiListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			emojify(new BufferedImage(label.getIcon()), 10, 10);
		}
	}
	
}
