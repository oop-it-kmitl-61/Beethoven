import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Administrator
 */
public class NeedForSpeed extends JFrame {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	public NeedForSpeed() {
		add(new RacaCar());
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
// TODO code application logic here
		NeedForSpeed frame = new NeedForSpeed();
		frame.setTitle("Need For Speed");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 200);
		frame.setVisible(true);
	}

	class RacaCar extends JPanel implements ActionListener {
		private int xBase = 20;

		public RacaCar() {
			Timer timer = new Timer(15, this);
			timer.start();

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			repaint();
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			xBase += 2;
			System.out.println(xBase);
		}
	}
}