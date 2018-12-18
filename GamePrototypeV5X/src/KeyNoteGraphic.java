import java.awt.Color;
import java.awt.Graphics;

public class KeyNoteGraphic {

	public int first;
	public boolean isClick = false;
	public boolean isBad = false;
	public GetNote second;
	public int height, height1;
	public int width, width1;
	private int noteHeight = 40;
	public static int[] modeSpeed = {0,670,1400};
	public static int noteStart = 0;

	public KeyNoteGraphic(GetNote second, int width,int w,int h) {
		this.height = second.l * noteHeight;
		this.width = width;
		this.first = -(height+noteStart);
		this.second = second;
		this.width1 = w;
		this.height1 = h;	
	}

	public void draw(Graphics g, Color[] color) {
		int y = this.first;
		GetNote note = this.second;
		int slot = note.slot;
		g.setColor(new Color(255,182,50));
		g.fillRoundRect(W(640+(160*slot)), H(y), W(160), H(height),10,10);
	}
	
	public static void speedMode(int speedMusic) {
		noteStart = modeSpeed[speedMusic-1];
	}
	
	public int W(double unit) {
		return (int) ((unit/1920)*width1);
	}
	public int H(double unit) {
		return (int) ((unit/1080)*height1);
	}
}
