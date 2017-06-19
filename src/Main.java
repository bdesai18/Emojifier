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
	
	BufferedImage currPic;

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
    		currPic = (BufferedImage)image;
    	}
    	catch(IOException e){}
    	
    	label = new JLabel(new ImageIcon(image));
    	imgPanel.add(label);
    	frame.add(imgPanel, BorderLayout.CENTER);
    	
    	frame.pack();
    	frame.setVisible(true);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

    	clearButton.addActionListener(new ClearListener());
    	loadButton.addActionListener(new LoadListener());
    	emojifyButton.addActionListener(new EmojiListener());
    	saveButton.addActionListener(new SaveListener());
    }
    

	public static void main(String[] args){
		new Main();
	}
	
	private class ClearListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			imgPanel.setVisible(false);
		}
	}

	private class SaveListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try {
				ImageIcon ii = (ImageIcon)label.getIcon();
				BufferedImage bi = new BufferedImage(ii.getIconWidth(), ii.getIconHeight(), BufferedImage.TYPE_INT_RGB);
				Graphics g = bi.createGraphics();
				ii.paintIcon(null, g, 0,0);
				g.dispose();
				ImageIO.write(bi, "png", new File("output.png"));
			}
			catch (IOException io) {

			}
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
	            currPic = img;
			}
			imgPanel.setVisible(true);
			frame.pack();
		}
	}
	
	private class EmojiListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			label.setIcon(new ImageIcon(ImageConstructor.getScaledImage(currPic, 128, 128)));
			frame.pack();
		}
	}
	
}
