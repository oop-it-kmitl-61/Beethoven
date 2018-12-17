import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javax.print.attribute.standard.Media;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class PlayFrame extends JFrame {

	private Container canvas;
	private PlayPanel pp;
	public boolean startgame;
	public static boolean exit = false;
	public static boolean finishgame;
	public static boolean selectMusic;
	private static int numberMusic = 0;
	public static boolean playMusic = false;
	public static int speedMusic = 1;
	

	public PlayFrame(GetNote[] list) {
		canvas = getContentPane();
		canvas.setLayout(new BorderLayout());
		pp = new PlayPanel(this, list);
		canvas.add(pp, BorderLayout.CENTER);
		pp.addKeyListener(new KeyListener() {
			 
			@Override
			public void keyReleased(KeyEvent e) {
				
				//System.out.println("startgame:"+startgame+"| selectMusic:"+selectMusic+"|finishgame:"+finishgame);
				for (int j = 0; j < ColorKey.keyNoteCode.length; j++) {
					if (e.getKeyCode() == ColorKey.keyNoteCode[j]) {
						pp.keyRelease(j);
						return;
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER && startgame == false && selectMusic == false && finishgame == false) {
					selectMusic = true;
					pp.select();
					return;
				}
				
//				else if (e.getKeyCode() == KeyEvent.VK_UP && startgame == false && selectMusic == false && finishgame == false) {
//					exit = false;
//					pp.startHome(exit);
//					return;
//				}
//				
//				else if (e.getKeyCode() == KeyEvent.VK_DOWN && startgame == false && selectMusic == false && finishgame == false) {
//					exit = true;
//					pp.startHome(exit);
//					return;
//				} 
				
				else if (e.getKeyCode() == KeyEvent.VK_ESCAPE && startgame == false && selectMusic == false && finishgame == false) {
					System.exit(0);
				}
				
				else if (e.getKeyCode() == KeyEvent.VK_ENTER && startgame == false && selectMusic == true && finishgame == false) {
					// System.out.println("2startgame:"+startgame+"| selectMusic:"+selectMusic+"|
					// finishgame:"+finishgame);

					try { // Load Note Song
						SongNoteSelect();
						pp.addNewNote(notes);
					} catch (FileNotFoundException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					try {
						song();
					} catch (LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UnsupportedAudioFileException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					selectMusic = false;
					startgame = true;
					pp.startGame();
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER && startgame == true && selectMusic == false
						&& finishgame == true) {
					// System.out.println("3startgame:"+startgame+"| selectMusic:"+selectMusic+"|
					// finishgame:"+finishgame);
					selectMusic = true;
					startgame = false;
					finishgame = false;
					pp.playAgain();
					return;
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT && selectMusic == true) {
					// System.out.println("Lstartgame:"+startgame+"| selectMusic:"+selectMusic+"|
					// finishgame:"+finishgame);
					numberMusic -= 1;
					if (numberMusic < 0) {
						numberMusic = 1;
					}
					pp.numberMusic(numberMusic);
					return;
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT && selectMusic == true) {
					// System.out.println("Rstartgame:"+startgame+"| selectMusic:"+selectMusic+"|
					// finishgame:"+finishgame);
					numberMusic += 1;
					if (numberMusic > 1) {
						numberMusic = 0;
					}
					pp.numberMusic(numberMusic);
					return;
				}else if (e.getKeyCode() == KeyEvent.VK_UP && selectMusic == true) {
					speedMusic++;
					if (speedMusic > 3) {
						speedMusic = 3;
					}
					//System.out.println(speedMusic);
					pp.speedMode(speedMusic);
					KeyNoteGraphic.speedMode(speedMusic);
				}else if (e.getKeyCode() == KeyEvent.VK_DOWN && selectMusic == true) {
					speedMusic--;
					if (speedMusic < 1) {
						speedMusic = 1;
					}
					//System.out.println(speedMusic);
					pp.speedMode(speedMusic);
				}
			}
			

			@Override
			public void keyPressed(KeyEvent e) {
				for (int j = 0; j < ColorKey.keyNoteCode.length; j++) {
					if (e.getKeyCode() == ColorKey.keyNoteCode[j]) {
						pp.keyPress(j);
						return;
					}
				}

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		//pp.startHome(exit);
		pp.setFocusable(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setSize(1270, 720); // Have Top Bar [CLOSE]
		
		
//		this.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set Fullscreen
//		this.setUndecorated(true);
		// this.setLocationRelativeTo(null);
		setLocationRelativeTo(null);
		this.setVisible(true);
		//System.out.println(this.getWidth());

	}

	private static int index = 0;
	public static List<Double> list = new ArrayList<Double>();
	public static GetNote[] notes;
	public static int listSize = 0;

	
	public PlayFrame() {
		
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
// TODO code application logic here
		new PlayFrame(notes);
		//new RaceCar();
		PlayFrame frame = new PlayFrame();
		
	}

	public static void Note() {
		
		int indexTemp = 0;
		int slot = 0, l = 0;
		double delay = 0;
		for (int i = 0; i < index; i += 3) {
			//System.out.println(list.toString()+" i = "+i);
			double slot_b = list.get(i);
			slot = (int) slot_b;
			delay = list.get(i + 1);
			double length_b = list.get(i + 2);
			l = (int) length_b;
			notes[indexTemp++] = new GetNote(slot, delay, l);
		}
		
		
	}

	public static void finishValue(boolean finishgame2) {
		// TODO Auto-generated method stub
		finishgame = finishgame2;

	}

	public void song() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		File soundFile0 = new File("song0.wav");
		File soundFile1 = new File("song1.wav");
		AudioInputStream sound0 = AudioSystem.getAudioInputStream(soundFile0);
		AudioInputStream sound1 = AudioSystem.getAudioInputStream(soundFile1);

		DataLine.Info info0 = new DataLine.Info(Clip.class, sound0.getFormat());
		DataLine.Info info1 = new DataLine.Info(Clip.class, sound1.getFormat());
		Clip clip0 = (Clip) AudioSystem.getLine(info0);
		Clip clip1 = (Clip) AudioSystem.getLine(info1);
		clip0.open(sound0);
		clip1.open(sound1);
		if (numberMusic == 0) {
			clip0.start();
		}
		else if(numberMusic == 1) {
			clip1.start();
		}
		
	}

	public static void SongNoteSelect() throws FileNotFoundException {
		list.clear();index=0;
		Scanner[] sc = new Scanner[4];
		sc[0] = new Scanner(new File("song0.txt"));
		sc[1] = new Scanner(new File("song1.txt"));
		sc[2] = new Scanner(new File("song2.txt"));
		//sc[3] = new Scanner(new File("song3.txt"));
		double num;
		//System.out.println("Music N0."+numberMusic);
		while (sc[numberMusic].hasNextLine()) {
			num = sc[numberMusic].nextDouble();
			list.add(num);
			index += 1;
		}
		
		listSize = list.size();
		notes = new GetNote[listSize / 3];
		Note();
	}

	public static void runMusic(boolean runMusic) {
		playMusic = runMusic;
	}

}
