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
	private int width, height;


	public ColorProcessor(Picture2 img) {
        this.img = img;
        width = img.getWidth();
        height = img.getHeight();
    }


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

	private void validateRectangleCoordinates(int xL, int yU, int w, int h) {
	    if (xL < 0 || xL + w > width || w <= 0 || yU < 0 || yU + h > height || h <= 0) {
	        throw new IllegalArgumentException("Rectangle out of bounds");
        }


    }


	public Color getRectangleColor(int xL, int yU, int wid, int hig) {

	    validateRectangleCoordinates(xL, yU, wid, hig);


		int rTot = 0;
		int gTot = 0;
		int bTot = 0;
		int totAdd = 0;
		for (int x = xL; x < xL + wid; x++) {
			for (int y = yU; y < yU + hig; y++) {
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
	
}
