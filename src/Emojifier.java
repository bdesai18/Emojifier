package src;

import java.awt.Color;
import java.io.File;


public class Emojifier {
	
	public static final int EMOJI_SIZE = 128;

	private static Emoji[] emojis128, emojis64, emojis32;
	
	public static EmojiTree treeOfEmojis() {
        String dir = "Emojis//EmojiOne_3.0_128x128_png";
        File fi = new File(dir);
        File[] imgs = fi.listFiles();
        String rName = imgs[0].getName();
        ColorProcessor rProc = new ColorProcessor(new Picture2(dir + "//" + rName));
        Color rCol = rProc.getRectangleColor(0, 0, EMOJI_SIZE, EMOJI_SIZE);
        EmojiTree root = new EmojiTree(rCol.getRed(), rCol.getGreen(), rCol.getBlue(), rName);

        for (int i = 1; i < imgs.length; i++) {
            File f = imgs[i];
            String name = f.getName();
            ColorProcessor proc = new ColorProcessor(new Picture2(dir + "//" + name));
            Color col = proc.getRectangleColor(0, 0, EMOJI_SIZE, EMOJI_SIZE);
            EmojiTree chil = new EmojiTree(col.getRed(), col.getGreen(), col.getBlue(), name);
            root.insert(chil);
        }
        return root;
    }

    private static class EmojiTree {
        private int red, green, blue;
        private String emojiString;
        private EmojiTree[] children;
        public final int UNE = 0, UNW = 1, USE = 2, USW = 3, DNE = 4, DNW = 5, DSE = 6, DSW = 7;

        public EmojiTree(int r, int g, int b, String name) {
            red = r;
            green = g;
            blue = b;
            emojiString = name;
            children = new EmojiTree[8];
        }
        public void insert(EmojiTree e) {
            if(e.emojiString.equals(emojiString)) {
                red = e.red;
                green = e.green;
                blue = e.blue;
                for(int i = 0; i < 8; i++) {
                    children[i] = e.children[i];
                }
            }
            else {

                int p = 0;
                if (red > e.red) {
                    p += 4;
                }
                if (green > e.green) {
                    p += 2;
                }
                if (blue > e.blue) {
                    p += 1;
                }

                if(children[p] == null) {
                    children[p] = e;
                } else {
                    children[p].insert(e);
                }
            }
        }

        private double euclDis(int r, int g, int b) {
            return Math.sqrt((red - r) * (red - r) + (green - g) * (green - g) + (blue - b) * (blue - b));
        }

        public EmojiTree findClosest(int r, int g, int b) {
            if (r == red && g == green && b == blue) {
                return this;
            }
            else {
                int p = 0;
                if (red > r) {
                    p += 4;
                }
                if (green > g) {
                    p += 2;
                }
                if (blue > b) {
                    p += 1;
                }
                if (children[p] == null) {
                    return this;
                } else {
                    EmojiTree closestInChildren = children[p].findClosest(r, g, b);
                    double myDis = euclDis(r, g, b);
                    double chDis = closestInChildren.euclDis(r, g, b);
                    if(myDis < chDis) {
                        return this;
                    }
                    return closestInChildren;
                }
            }
        }

    }


    private static class Emoji {
        public int red, green, blue;
        public String name;
        public Emoji(String n, int r, int g, int b) {
            name = n;
            red = r;
            green = g;
            blue = b;
        }
        private double euclDis(int r, int g, int b) {
            return Math.sqrt((r - red) * (r - red) + (g - green) * (g - green) + (b - blue) * (b - blue));
        }
    }

    public static Emoji[] arrayOfEmojis128() {

	    if(emojis128 == null) {
            emojis128 = new Emoji[2427];
            String dir = "Emojis//EmojiOne_3.0_128x128_png";
            File fi = new File(dir);
            File[] imgs = fi.listFiles();
            for(int i = 0; i < imgs.length; i++) {
                ColorProcessor cp = new ColorProcessor(new Picture2(dir + "//" + imgs[i].getName()));
                Color c = cp.getRectangleColor(0, 0, EMOJI_SIZE, EMOJI_SIZE);
                Emoji e = new Emoji(imgs[i].getName(), c.getRed(), c.getGreen(), c.getBlue());
                emojis128[i] = e;
            }
        }
        return emojis128;
    }
    public static Emoji[] arrayOfEmojis64() {

        if(emojis64 == null) {
            emojis64 = new Emoji[2427];
            String dir = "Emojis//EmojiOne_3.0_64x64_png";
            File fi = new File(dir);
            File[] imgs = fi.listFiles();
            for(int i = 0; i < imgs.length; i++) {
                ColorProcessor cp = new ColorProcessor(new Picture2(dir + "//" + imgs[i].getName()));
                Color c = cp.getRectangleColor(0, 0, EMOJI_SIZE / 2, EMOJI_SIZE / 2);
                Emoji e = new Emoji(imgs[i].getName(), c.getRed(), c.getGreen(), c.getBlue());
                emojis64[i] = e;
            }
        }
        return emojis64;
    }
    public static Emoji[] arrayOfEmojis32() {

        if(emojis32 == null) {
            emojis32 = new Emoji[2427];
            String dir = "Emojis//EmojiOne_3.0_32x32_png";
            File fi = new File(dir);
            File[] imgs = fi.listFiles();
            for(int i = 0; i < imgs.length; i++) {
                ColorProcessor cp = new ColorProcessor(new Picture2(dir + "//" + imgs[i].getName()));
                Color c = cp.getRectangleColor(0, 0, EMOJI_SIZE / 4, EMOJI_SIZE / 4);
                Emoji e = new Emoji(imgs[i].getName(), c.getRed(), c.getGreen(), c.getBlue());
                emojis32[i] = e;
            }
        }
        return emojis32;
    }

    public static String findClosestArrayStyle(int r, int g, int b, int dir) {
        Emoji[] em = arrayOfEmojis128();
	    if (dir == 64)
	        em = arrayOfEmojis64();
	    else if (dir == 32) {
	        em = arrayOfEmojis32();
        }
        double minD = Double.MAX_VALUE;
        int ind = 0;
        for (int i = 0; i < em.length; i++) {
            double dis = em[i].euclDis(r, g, b);
            if (dis < minD) {
                ind = i;
                minD = dis;
            }
        }
        Emoji e = em[ind];
        System.out.println("The closest emoji is number " + ind + ", with name " + e.name + ", and values " +
                e.red + " " + e.green + " " + e.blue);
        return e.name;
    }

    public static void testArrayClosest() {
        ColorProcessor c = new ColorProcessor(new Picture2("summer nights.png"));
        ColorProcessor c1 = new ColorProcessor(new Picture2("test.png"));
        Color x = c.getRectangleColor(0, 0, 1600, 1200);
        Color x1 = c1.getRectangleColor(0, 0, 128, 128);
        System.out.println("summer nights.png's color is " + x.getRed() + " " + x.getGreen() + " " + x.getBlue());
        findClosestArrayStyle(x.getRed(), x.getGreen(), x.getBlue(), 128);
        System.out.println("test.png's color is " + x1.getRed() + " " + x1.getGreen() + " " + x1.getBlue());
        findClosestArrayStyle(x1.getRed(), x1.getGreen(), x1.getBlue(), 128);
    }


    public static void testFindClosest() {
        EmojiTree tree = treeOfEmojis();
        ColorProcessor col = new ColorProcessor(new Picture2("summer nights.png"));
        Color c = col.getRectangleColor(0, 0, 1600, 1200);
        EmojiTree closest = tree.findClosest(c.getRed(), c.getGreen(), c.getBlue());
        System.out.println("The average color of 'test.png' is " +
                c.getRed() + " " + c.getGreen() + " " + c.getBlue());

        System.out.println("The closest emoji's color is " + closest.emojiString + "" +
                " with color " + closest.red + " " + closest.green + " " + closest.blue);
    }
}
