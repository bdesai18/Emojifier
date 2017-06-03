/* Takes images and divides into sectors, returns the average value
 * of the sectors. */
package src;
import java.util.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ColorProcessor {
	private Picture2 img;
	private Picture2[][] subImgs;
	private ArrayList<Picture2> sectors;
	private int sizeOfSector;
	public ColorProcessor(Picture2 img, int secSize){
		this.img = img;
		sizeOfSector = secSize;
		this.sectors = new ArrayList<Picture2>();
		int width = img.getWidth();
		int height = img.getHeight();
		subImgs = new Picture2[(int)((double)width / secSize + 0.5)][(int)((double)height / secSize + 0.5)];
		this.sectors.add(img);
	}


	private void validateSectorCoordinates(int x, int y) {
		if (x * sizeOfSector > img.getWidth() || y * sizeOfSector > img.getHeight() || y < 0 || x < 0) {
			throw new IllegalArgumentException("X or Y coordinate out of bounds");
		}
		return;
	}

	public Color getAvgColor(int xSec, int ySec) {
		validateSectorCoordinates(xSec, ySec);
		int xL = sizeOfSector * xSec;
		int yU = sizeOfSector * ySec;
		int rTot = 0;
		int gTot = 0;
		int bTot = 0;
		int totAdd = 0;
		for (int x = xL; x < xL + sizeOfSector; x++) {
			for (int y = yU; y < yU + sizeOfSector; y++) {
			    BufferedImage bi = img.getBufferedImage();
			    int argb = bi.getRGB(x, y);
                int a = (argb>>24)&0xFF;
                int r = (argb>>16)&0xFF;
                int g = (argb>>8)&0xFF;
                int b = (argb)&0xFF;
                if(a != 255 || r != 0 || g != 0 || b != 0) {
                    rTot += (argb>>16)&0xFF;
                    gTot += (argb>>8)&0xFF;
                    bTot += (argb)&0xFF;
                    totAdd++;
                }
			}
		}
		return new Color(rTot / totAdd, gTot / totAdd, bTot / totAdd);
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
