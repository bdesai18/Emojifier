/* Takes images and divides into sectors, returns the average value
 * of the sectors. */

import java.util.*;
import java.awt.*;

public class ColorProcessor {
	private Picture2 img;
	private ArrayList<Picture2> sectors;
	public ColorProcessor(Picture2 img, int numSectors){
		this.img = img;
		this.sectors = new ArrayList<Picture2>();
		int width = img.getWidth();
		int height = img.getHeight();
		this.sectors.add(img);
		System.out.println("hacker voice im out");
		/*for(int r=height/numSectors; r<height; r+=height/numSectors){
			for(int c=width/numSectors; c<width; c+=width/numSectors){
				this.sectors.add(new Picture2(c-width/numSectors, r-height/numSectors));
				System.out.println("hacker voice im in");
			}
		}*/
	}
	
	public Color getAverageColor(int sector){
		Picture2 thisSector = sectors.get(0);
		int width = thisSector.getWidth();
		int height = thisSector.getHeight();
		int redBin = 0;
		int greenBin = 0;
		int blueBin = 0;
		Pixel2 p;
		for(int i=0; i<height-2; i+=2){
			for(int j=0; j<width-2; j+=2){
				p = thisSector.getPixel(i, j);
				redBin += p.getRed();
				greenBin += p.getGreen();
				blueBin += p.getBlue();
			}
		}
		System.out.println(5*redBin/(width*height) + " " + 5*greenBin/(width*height) + " " + 5*blueBin/(height*width));
		return new Color(redBin/(width*height), greenBin/(width*height), blueBin/(width*height));
	}
}
