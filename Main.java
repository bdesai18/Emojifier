

public class Main {
	public static void main(String[] args){
		ColorProcessor c;
		Picture2 img = new Picture2("097255.png");
		c = new ColorProcessor(img, 1);
		c.getAverageColor(0);
	}
}
