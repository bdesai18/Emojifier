



/**
 * Staring Point Code for Image Processing Project
 * @author Richard Wicentowski and Tia Newhall (2005)
 */
package src;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.swing.ImageIcon;

public class Picture2 {

    public static int COLOR = BufferedImage.TYPE_INT_RGB;
    public static int GRAY = BufferedImage.TYPE_BYTE_GRAY;

    static int defaultImageType = BufferedImage.TYPE_INT_RGB;

    int imageType;
    BufferedImage bufferedImage;
    WritableRaster raster;

    Picture2() { 
        imageType = defaultImageType;
        bufferedImage = null;
        raster = null;
    }

    /**
     * Create a new picture of the given height and width
     * @param width the width of the picture
     * @param height the height of the picture
     */
    public Picture2(int width, int height) {
        this(width, height, defaultImageType);
    }

    Picture2(int width, int height, int type) {
        bufferedImage = new BufferedImage(width, height, type);
        raster = bufferedImage.getRaster();
        imageType = type;
    }

    /**
     * Read the image in the file with the given filename
     * @param filename
     */
    public Picture2(String filename) {
        this(filename, defaultImageType);
    }

    Picture2(String filename, int type) {
        if ((type != COLOR) && (type != GRAY)) { type = defaultImageType; }
        imageType = type;
        load(filename);
    }

    int getImageType() { return imageType; }

    /**
     * Get the width of the image in pixels
     * @return the width of the image in pixels
     */
    public int getWidth() { return bufferedImage.getWidth(); }
    /**
     * Get the height of the image in pixels
     * @return the height of the image in pixels
     */
    public int getHeight() { return bufferedImage.getHeight(); }

    BufferedImage getBufferedImage() { return bufferedImage; }
    WritableRaster getRaster() { return raster; }

    void load(String filename) {
        ImageIcon icon = new ImageIcon(filename);
        Image image = icon.getImage();
        bufferedImage = new BufferedImage(image.getWidth(null),
                image.getHeight(null),
                imageType);
        Graphics g = bufferedImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        raster = bufferedImage.getRaster();
    }

    /**
     * Replace the given picture with the current picture.
     * <p>
     * For example:
     * </p>
     * <p>
     * pic1.copy(pic2);
     * </p>
     * <p>
     * replaces pic1 with pic2.
     * </p>
     * <p>
     * Note that this is the <b>only</b> effective way of changing the dimensions of an image.
     * </p>
     * @param p The picture to replace the current picture with.
     */
    public void copy(Picture2 p) {
        imageType = p.getImageType();

        bufferedImage = new BufferedImage(p.getWidth(),
                p.getHeight(),
                imageType);

        raster = bufferedImage.getRaster();

        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                // reversing coordinates
                setPixel(y,x, p.getPixel(y, x));
            }
        }
    }

    private void boundsCheck(int row, int col) {
        if (row>=raster.getHeight() || col>=raster.getWidth()) {
            throw new IndexOutOfBoundsException("row "+row+
                    ", col "+col+" is out of bounds! "+
                    "number of rows is "+raster.getHeight()+
                    ", number of columns is "+raster.getWidth());
        
        }
    }
    
    /**
     * Get the pixel at the given x and y coordinates of the image.
     * @param x The x coordinate of the desired pixel
     * @param y The y coordinate of the desired pixel
     * @return The pixel at the given x and y coordinates.
     */
    public Pixel2 getPixel(int row, int col) {
        int[] pixelArray = null;

        try {
            // the raster does (x, y) instead of (row, col)
            // so we reverse the coordinates
            boundsCheck(row, col);
            pixelArray = raster.getPixel(col, row, pixelArray);
            Pixel2 pixel = new Pixel2(pixelArray);

            return pixel;
            
        } catch (IndexOutOfBoundsException e) {
            //System.err.println(e.getMessage());
            processError(e);
            System.exit(1);
            throw new RuntimeException(e);
        }
        
    }


    /**
     * Replaces the pixel at the given x and y coordinates with the given pixel
     * @param x The x coordinate of the pixel to be replaced
     * @param y The y coordinate of the pixel to be replaced
     * @param pixel The pixel with which to replace the pixel at the given coordinates.
     */
    public void setPixel(int row, int col, Pixel2 pixel) {
        int[] pixelArray = pixel.getComponents();

        try {
            boundsCheck(row, col);
            raster.setPixel(col, row, pixelArray);

        } catch (IndexOutOfBoundsException e) {
            // TODO: Process the stack trace and generate an error message
            // that is based on the code in ImageFilters
            processError(e);
            //System.err.println("setPixel() error: ("+row+", "+col+") out of bounds.");
            
            System.exit(1);
            //throw new RuntimeException(e);
        }
    }
    private static void processError(IndexOutOfBoundsException e) {
        for (StackTraceElement st : e.getStackTrace()) {
            //System.out.println(ImageFilters.class.toString().replace("class ", ""));
            if (st.getClassName().contains("ImageFilters")) {
                System.err.println(e.getMessage());
                System.err.println("in file "+st.getFileName()+
                        " at line "+st.getLineNumber());
            }
        }
    }



}
