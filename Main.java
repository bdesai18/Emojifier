import java.awt.Color;
import java.awt.image.BufferedImage;

public class Main {
	public static void main(String[] args){
		ColorProcessor c;
		Picture2 img = new Picture2("Emoji Natur-01.png");
		c = new ColorProcessor(img, 1);
		c.getAverageColor(0);
		ColorProcessor d = new ColorProcessor(img, 160);
		Color x = d.getAvgColor(0, 0);
		System.out.print( x.getRed() + " " + x.getGreen() + " " + x.getBlue());
        int totR = 0;
        int totG = 0;
        int totB = 0;
        String start = "Emoji Natur-";
        for(int i = 1; i <= 64; i++) {
            ColorProcessor c1;
            if(i < 10) {
                c1 = new ColorProcessor(new Picture2(start + "0" + i + ".png"), 160);
            } else {
                c1 = new ColorProcessor(new Picture2(start + i + ".png"), 160);
            }
            Color x1 = c1.getAvgColor(0, 0);
            System.out.println(x1.getRed() + " " + x1.getGreen() + " " + x1.getBlue());
            totR += x1.getRed();
            totG += x1.getGreen();
            totB += x1.getBlue();
        }
        System.out.println();
        System.out.println(totR / 64 + " " + totG / 64 + " " + totB / 64);

        Picture2 pic = new Picture2("Emoji Natur-01.png");
        BufferedImage bi = pic.getBufferedImage();
        int sss = bi.getRGB(0, 0);
        int a = (sss>>24)&0xFF;
        int r = (sss>>16)&0xFF;
        int g = (sss>>8)&0xFF;
        int b = (sss)&0xFF;
        System.out.println(a + " " + r + " " + g + " " + b);
	}
}
