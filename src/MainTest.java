package src;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.File;

import org.junit.Test;

import src.Main.EmojiTree;

public class MainTest {
	public static final int EMOJI_SIZE = 128;
	
	@Test
	public static void test65Emojis() {
        int totR = 0;
        int totG = 0;
        int totB = 0;
        String start = "Emojis//whatsapp_emoji_collection_by_lechuck80-d5x39i5\\Emoji Nature\\Emoji Natur-";
        for(int i = 1; i <= 65; i++) {
            ColorProcessor c1;
            if(i < 10) {
                c1 = new ColorProcessor(new Picture2(start + "0" + i + ".png"));
            } else {
                c1 = new ColorProcessor(new Picture2(start + i + ".png"));
            }
            Color x1 = c1.getRectangleColor(0, 0, EMOJI_SIZE, EMOJI_SIZE);
            System.out.println(x1.getRed() + " " + x1.getGreen() + " " + x1.getBlue());
            totR += x1.getRed();
            totG += x1.getGreen();
            totB += x1.getBlue();
        }
        System.out.println();
        System.out.println(totR / 65 + " " + totG / 65 + " " + totB / 65);
    }
	
	@Test
	public static void testAllEmojis() {
        String dir = "Emojis//EmojiOne_3.0_128x128_png";
        File fi = new File(dir);
        File[] imgs = fi.listFiles();
        for (File f:imgs) {
            String s = f.getName();
            ColorProcessor c = new ColorProcessor(new Picture2(dir + "//" + s));
            Color x = c.getRectangleColor(0, 0, EMOJI_SIZE, EMOJI_SIZE);
            System.out.println(x.getRed() + " " + x.getGreen() + " " + x.getBlue());
        }
    }
	
	@Test
	public static void testArrayClosest() {
        ColorProcessor c = new ColorProcessor(new Picture2("summer nights.png"));
        ColorProcessor c1 = new ColorProcessor(new Picture2("test.png"));
        Color x = c.getRectangleColor(0, 0, 1600, 1200);
        Color x1 = c1.getRectangleColor(0, 0, 128, 128);
        findClosestArrayStyle(x.getRed(), x.getRed(), x.getBlue());
        findClosestArrayStyle(x1.getRed(), x1.getGreen(), x1.getBlue());
    }
	
	@Test
	public static void testFindClosest() {
        EmojiTree tree = treeOfEmojis();
        ColorProcessor col = new ColorProcessor(new Picture2("summer nights.png"));
        Color c = col.getRectangleColor(0, 0, 128, 128);
        EmojiTree closest = tree.findClosest(c.getRed(), c.getGreen(), c.getBlue());
        System.out.println("The average color of 'test.png' is " +
                c.getRed() + " " + c.getGreen() + " " + c.getBlue());

        System.out.println("The closest emoji's color is " + closest.emojiString + "" +
                " with color " + closest.red + " " + closest.green + " " + closest.blue);
    }

}
