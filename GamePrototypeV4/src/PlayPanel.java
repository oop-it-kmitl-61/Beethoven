import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PlayPanel extends JPanel {

	private Color[] colorNote;
	private final JFrame fr;
	public static boolean startgame = false;
	public static boolean selectMusic = false;
	public static boolean finishgame = false;
	private ArrayList<KeyNoteGraphic> noteList = new ArrayList<>();
	private int noteSpeed = 1;
	private int yBound = 750;
	private String accurateText = "";
	private String comboText = "";
	private String scoreText = " ";
	private int combo;
	private int count;
	private Color textColor = Color.black;
	private int perfectRange = 50;
	private int goodRange = 70;
	private int noteWidth = 100;
	private int rangeClick = 100;
	private int BTWkey = 40;
	GetNote[] notes;
	int adjX = 700, adjY = 750;
	int heightKey = 100;
	Font keyNoteFont = new Font("Dosis SemiBold", Font.BOLD, 40);
	BufferedImage imgHome, imgKeyBG, imgBGselect, imgKeyST, imgKeyE, imgKeyL, imgKeyR, tmpL, tmpR, imgbg_end,bar_select,bar_home,bar_finish;
	BufferedImage imgCover[] = new BufferedImage[4];
	BufferedImage imgBg[] = new BufferedImage[4];
	BufferedImage imgKey[] = new BufferedImage[4];
	BufferedImage imgKeyPress[] = new BufferedImage[4];
	BufferedImage tmp[] = new BufferedImage[4];
	private boolean exit;
	public static int numberMusic = 0;
	public static boolean pause = false;
	public static int num = 0;
	public static int delay;
	public static int perfect = 0;
	public static int good = 0;
	public static int bad = 0;
	public static int miss = 0;
	public String speedMode = "SPEED : Normal";

	public void addNewNote(GetNote[] notes2) {
		notes = notes2;
	}

	public void song() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
//		File soundFile0 = new File("song0.wav");
//		File soundFile1 = new File("song1.wav");
//		AudioInputStream sound0 = AudioSystem.getAudioInputStream(soundFile0);
//		AudioInputStream sound1 = AudioSystem.getAudioInputStream(soundFile1);
//
//		DataLine.Info info0 = new DataLine.Info(Clip.class, sound0.getFormat());
//		DataLine.Info info1 = new DataLine.Info(Clip.class, sound1.getFormat());
//		clip0 = (Clip) AudioSystem.getLine(info0);
//		Clip clip1 = (Clip) AudioSystem.getLine(info1);
//		clip0.open(sound0);
//		clip1.open(sound1);
//		//clip0.start();
//		//clip1.start();
//		System.out.println("run music");
		
	}
	
	public void img_load() { // Load image form same Image
		try {
			imgKeyBG = ImageIO.read(new File("pic\\keyBG.png"));
                        imgHome = ImageIO.read(new File("bg_home.jpg"));
			imgBGselect = ImageIO.read(new File("bg_select.png"));
			imgKeyL = ImageIO.read(new File("pic\\keyL.png"));
			tmpL = imgKeyL;
			imgKeyR = ImageIO.read(new File("pic\\keyR.png"));
			tmpR = imgKeyR;
			imgbg_end = ImageIO.read(new File("pic\\BG_endGame.png"));
			bar_select = ImageIO.read(new File("pic\\select_bar.png"));
			bar_home = ImageIO.read(new File("pic\\home_bar.png"));
			bar_finish = ImageIO.read(new File("pic\\finish_bar.png"));
			for (int i = 0; i < 4; i++) {
				imgKey[i] = ImageIO.read(new File("pic\\keyBack.png"));
				tmp[i] = imgKey[i];
				imgKeyPress[i] = ImageIO.read(new File("pic\\keyBackPY.png"));
				imgBg[i] = ImageIO.read(new File(ColorKey.Bg[i]));
				imgCover[i] = ImageIO.read(new File(ColorKey.cover[i]));
			}
		} catch (IOException e) {

		}
	}

	public PlayPanel(JFrame fr, GetNote[] list) {
		img_load();
//		try {
//			song();
//		} catch (LineUnavailableException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnsupportedAudioFileException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		this.fr = fr;

		setLayout(null);
//		colorNote = new Color[ColorKey.colorNoteDefault.length];
//		for (int i = 0; i < colorNote.length; i++) {
//			colorNote[i] = ColorKey.colorNoteDefault[i];
//		}

	}

	public synchronized void paint(Graphics g) {

		super.paint(g);
		FontMetrics fontMetrics = g.getFontMetrics();

		// check if game is started or not
		// if game is start, draw note
		// otherwise, display text to start a game.
		// g.drawImage(background_a, 0, 0, null); //background image each Music

		if (startgame == false && finishgame == false && selectMusic == false) {
			num++;
			// System.out.println(exit+" "+num);
			g.drawImage(imgHome, W(0), H(0), W(1920), H(1080), this);
			g.drawImage(bar_home, W(0), H(880), W(1900), H(130), this);
			g.setColor(Color.white);
			g.setFont(keyNoteFont);
			g.drawString("Press Enter...", W(450), H(600));
			g.drawString("...to START", W(480), H(700));

		}
		if (startgame == false && selectMusic == true && finishgame == false) {
			count = 0;
			perfect = 0;
			good = 0;
			bad = 0;
			miss = 0;
			selectMusic(g);

		} else if (startgame == true) {
			
			runningNote(g);
			

		} else if (finishgame == true) {
		}

		g.setColor(textColor);
		g.drawString(accurateText, W(960 - 15), H(200));
		g.drawString(comboText, W(960 - 15), H(260));
		g.setColor(Color.black);
		g.drawString(scoreText, W(50), H(50));

	}

	public void keyPress(int j) {
		// g.drawImage(imgKeyPress, adjX * (BTWkey)+5, adjY+5, noteWidth-10, 192,null);
		tmp[j] = imgKeyPress[j];
		for (KeyNoteGraphic note : noteList) {
			if (note.second.slot == j) {
				if (!note.isClick) {
					int y = note.first + note.height;
					int diff = Math.abs(y - yBound);
					//System.out.println(note.first + "+" + note.height + "=" + y);
					note.isClick = true;
					if (diff < perfectRange) { // perfect
						comboText = String.valueOf(++combo);
						count += 3;
						perfect++;
						scoreText = "Score : " + String.valueOf(count);
						accurateText = "Perfect";
						textColor = Color.cyan;
					} else if (diff < goodRange) { // good
						comboText = String.valueOf(++combo);
						count += 1;
						good++;
						scoreText = "Score : " + String.valueOf(count);
						accurateText = "Good";
						textColor = Color.green;
					} else if (diff < rangeClick) { // bad
						combo = 0;
						bad++;
						scoreText = "Score : " + String.valueOf(count);
						comboText = "";
						accurateText = "Bad";
						textColor = Color.red;
						note.isBad = true;

					} else {
						note.isClick = false;
					}

				}
				break;
			}
		}
		// repaint();
	}

	public void keyRelease(int j) {
		tmp[j] = imgKey[j];
		for (KeyNoteGraphic note : noteList) {
			if (note.second.slot == j) {
				if (note.second.l > 1) {
					int y = note.first;
					int diff = Math.abs(y - yBound);
					if (diff < perfectRange) { // perfect
						comboText = String.valueOf(++combo);
						perfect++;
						count += 3;
						scoreText = "Score : " + String.valueOf(count);
						accurateText = "Perfect";
						textColor = Color.cyan;
					} else if (diff < goodRange) { // good
						comboText = String.valueOf(++combo);
						good++;
						count += 1;
						scoreText = "Score : " + String.valueOf(count);
						accurateText = "Good";
						textColor = Color.green;
					} else if (diff < rangeClick) { // bad
						combo = 0;
						bad++;
						scoreText = "Score : " + String.valueOf(count);
						comboText = "";
						accurateText = "Bad";
						textColor = Color.red;

						note.isBad = true;
					}
				}
				break;
			}
		}
		// repaint();
	}

	public void updateNotes2() {
		// TODO Auto-generated method stub
		try {
			repaint();
		} catch (Exception e) {

		}
	}

	public void startGame() {
		if (!startgame) {
			startgame = true;
			noteList.clear();
			combo = 0;
			comboText = "";
			accurateText = "";
			scoreText = "Score : " + String.valueOf(count);
			new GroupThread(this, notes);
		
//				clip0.start();
//				System.out.println("runmusic");
//				k++;
			repaint();
		}
	}

	public void addNote(GetNote note) {
		noteList.add(new KeyNoteGraphic(note, noteWidth, fr.getWidth(), fr.getHeight()));
	}

	public void updateNotes() {
		try {
			int i = 0;
			//System.out.println("i" + i);
			//System.out.println(noteList.size());
			for (KeyNoteGraphic note : noteList) {
				note.first += noteSpeed;
				if (note.first - note.height > yBound) {
					if (!note.isClick) { // miss
						combo = 0;
						miss++;
						scoreText = "Score : " + String.valueOf(count);
						comboText = "";
						accurateText = "Miss";
						textColor = Color.white;
					}
					noteList.remove(i);

				}

				i++;

			}

			repaint();
		} catch (Exception e) {

		}
	}

	public boolean isFinish() {
		return noteList.isEmpty();
	}

	public void finish() {
		// startgame = false;
		finishgame = true;
		PlayFrame.finishValue(finishgame);
		comboText = "";
		accurateText = "";
		scoreText = "";
		repaint();
	}

	public void runningNote(Graphics g) {

		g.drawImage(imgBg[numberMusic], W(0), H(0), W(1920), H(1080), null);
		g.setColor(new Color(0, 0, 0, 70));
		g.fillRect(W(640), H(0), W(640), H(1080));
		g.drawImage(imgKeyBG, W(640), H(adjY + 5), W(640), H(302), null);
		for (int i = 0; i < 4; i++) {
			g.drawImage(tmp[i], W(640+(160*i)), H(adjY + 5), W(160), H(192), null);

			g.setColor(Color.white);
			g.setFont(keyNoteFont);
			String[] keyNote = { "D", "F", "J", "K" };
			g.drawString(keyNote[i], W(640+(160*i)+70), H(850));

		}
		for (KeyNoteGraphic p : noteList) {
			g.setColor(new Color(255, 255, 153));
			p.draw(g, colorNote);
			g.drawImage(imgKeyBG, W(640), H(adjY + 5), W(640), H(302), null);
			for (int i = 0; i < 4; i++) {
				g.drawImage(tmp[i], W(640+(160*i)), H(adjY + 5), W(160), H(192), null);
				g.setColor(Color.white);
				g.setFont(keyNoteFont);
				String[] keyNote = { "D", "F", "J", "K" };
				g.drawString(keyNote[i], W(640+(160*i)+70), H(850));

			}
		}
		if (finishgame == true) {
			g.drawImage(imgbg_end, W(0), H(0), W(1920), H(1080), null);
			g.drawImage(imgCover[numberMusic], W(350), H(304), W(640), H(473), null);
			g.setColor(Color.white);
			g.drawImage(bar_finish, W(0), H(880), W(1920), H(200), this);
			g.drawString(String.valueOf(perfect), W(1020), H(348));
			g.drawString(String.valueOf(good), W(1020), H(428));
			g.drawString(String.valueOf(bad), W(1020), H(508));
			g.drawString(String.valueOf(miss), W(1020), H(588));
			g.drawString(String.valueOf(count), W(1300), H(776));

		}
	}

	public void select() {
		selectMusic = true;
		new GroupThread(this, notes);
	}

	public void selectMusic(Graphics g) {
		g.drawImage(imgBGselect, W(0), H(0), W(1920), H(1080), null);
		g.drawImage(bar_select, W(0), H(880), W(1920), H(210), this);
		g.drawImage(imgKeyL, W(520), H(575), W(100), H(100), null);
		g.drawImage(imgKeyR, W(1300), H(575), W(100), H(100), null);
		g.drawImage(imgCover[numberMusic], W(640), H(200), W(640), H(512), null);
		g.setColor(Color.white);
		g.setFont(keyNoteFont);
		g.drawString(speedMode, W(800), H(800));
		count = 0;
		
	}

	// Number of Music
	public void numberMusic(int number) {
		numberMusic = number;
	}

	// Play Game Again when Enter
	public void playAgain() {
		startgame = false;
		selectMusic = true;
		finishgame = false;
	}

	// Change Unit for ANOTHOR Screen
	public int W(double unit) {
		return (int) ((unit / 1920) * fr.getWidth());
	}

	public int H(double unit) {
		return (int) ((unit / 1080) * fr.getHeight());
	}

	public void plusN(int x) {
		// TODO Auto-generated method stub
		//System.out.println(x);
		delay = x;
	}

	public void speedMode(int speedMusic) {
		if (speedMusic == 1) {
			noteSpeed = 1;
			speedMode = "SPEED : Normal";
		} else if (speedMusic == 2) {
			noteSpeed = 2;
			speedMode = "SPEED : Hard";
		} else if (speedMusic == 3) {
			noteSpeed = 3;
			speedMode = "SPEED : Extreame";
		}

	}
}
