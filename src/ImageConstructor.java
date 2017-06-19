package src;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Brij on 6/14/2017.
 */
public class ImageConstructor {

    public static final int MAX_128_SIZE = 32 * 32;
    public static final int MAX_64_SIZE = 64 * 64;

    public static BufferedImage emojify(BufferedImage picture, int eWidth, int eHeight) {

        int size = eWidth * eHeight;
        String dir = "Emojis//EmojiOne_3.0_128x128_png/";
        if (size > MAX_128_SIZE) {
            dir = "Emojis//EmojiOne_3.0_64x64_png/";
        } if (size > MAX_64_SIZE) {
            dir = "Emojis//EmojiOne_3.0_32x32_png/";
        }

        BufferedImage b;
        if (size <= MAX_128_SIZE) {
            b = new BufferedImage(eWidth * Emojifier.EMOJI_SIZE,
                    eHeight * Emojifier.EMOJI_SIZE, BufferedImage.TYPE_INT_ARGB);
        } else if (size <= MAX_64_SIZE){
            b = new BufferedImage(eWidth * 64, eHeight * 64, BufferedImage.TYPE_INT_ARGB);
        } else {
            b = new BufferedImage(eWidth * 32, eHeight * 32, BufferedImage.TYPE_INT_ARGB);
        }
        ColorProcessor cp = new ColorProcessor(new Picture2(picture));
        double rectW = (double)(picture.getWidth()) / eWidth;
        double rectH = (double)(picture.getHeight()) / eHeight;

        int rW = (int)Math.round(rectW);
        int rH = (int)Math.round(rectH);

        for(int w = 0; w < eWidth; w++) {
            int xL = Math.min((int)Math.round(rectW * w), picture.getWidth() - rW);
            for(int h = 0; h < eHeight; h++) {
                int yU = Math.min((int)Math.round(rectH * h), picture.getHeight() - rH);
                Color c = cp.getRectangleColor(xL, yU, rW, rH);
                String e;
                if (size > MAX_64_SIZE) {
                    e = Emojifier.findClosestArrayStyle(c.getRed(), c.getGreen(), c.getBlue(), 32);
                }
                else if (size > MAX_128_SIZE){
                    e = Emojifier.findClosestArrayStyle(c.getRed(), c.getGreen(), c.getBlue(), 64);
                } else {
                    e = Emojifier.findClosestArrayStyle(c.getRed(), c.getGreen(), c.getBlue(), 128);
                }
                Picture2 emoji = new Picture2(dir + e);
                BufferedImage emo = emoji.getBufferedImage();
                for(int i = 0; i < emoji.getWidth(); i++) {
                    for(int j = 0; j < emoji.getHeight(); j++) {
                        int rgb = emo.getRGB(i, j);
                        if (((rgb>>24)&0xFF) == 0)
                        {
                            rgb = Color.WHITE.getRGB();
                        }
                        b.setRGB(w * emoji.getWidth() + i, h * emoji.getHeight() + j, rgb);
                    }
                }
            }
        }
        return b;
    }

    public static Image getScaledImage(BufferedImage picture, int eWidth, int eHeight) {
        int width = picture.getWidth();
        int height = picture.getHeight();
        BufferedImage b = emojify(picture, eWidth, eHeight);
        return b.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

}
