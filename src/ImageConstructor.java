package src;
import java.awt.image.BufferedImage;
import java.awt.Color;

/**
 * Created by Brij on 6/14/2017.
 */
public class ImageConstructor {

    public static BufferedImage emojify(BufferedImage picture, int eWidth, int eHeight) {

        BufferedImage b = new BufferedImage(picture.getWidth() / eWidth * Emojifier.EMOJI_SIZE,
                picture.getHeight() / eHeight * Emojifier.EMOJI_SIZE, BufferedImage.TYPE_INT_ARGB);

        ColorProcessor cp = new ColorProcessor(new Picture2(picture));
        double rectW = (double)(picture.getWidth()) / eWidth;
        double rectH = (double)(picture.getHeight()) / eHeight;
        int rW = (int)Math.round(rectW);
        int rH = (int)Math.round(rectH);
        for(int w = 0; w < eWidth; w++) {
            int xL = (int)Math.round(rectW * w);
            for(int h = 0; h < eHeight; h++) {
                int yU = (int)Math.round(rectH * h);
                Color c = cp.getRectangleColor(xL, yU, rW, rH);
                String e = Emojifier.findClosestArrayStyle(c.getRed(), c.getGreen(), c.getBlue());
                Picture2 emoji = new Picture2("Emojis//EmojiOne_3.0_128x128_png/" + e);
                for(int i = 0; i < emoji.getWidth(); i++) {
                    for(int j = 0; j < emoji.getHeight(); j++) {
                        Pixel2 p = emoji.getPixel(j, i);
                        Color col = new Color(p.getRed(), p.getGreen(), p.getBlue());
                        b.setRGB(w * emoji.getWidth() + i, h * emoji.getHeight() + j, col.getRGB());
                    }
                }
            }
        }
        return b;
    }

}
