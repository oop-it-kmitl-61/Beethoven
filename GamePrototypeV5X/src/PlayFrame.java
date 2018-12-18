
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

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
                // Get All input keyboard
                for (int j = 0; j < ColorKey.keyNoteCode.length; j++) {
                    if (e.getKeyCode() == ColorKey.keyNoteCode[j]) {  // input from D F J K
                        pp.keyRelease(j);
                        return;
                    }
                }
                // Menu Song
                if (e.getKeyCode() == KeyEvent.VK_ENTER && startgame == false && selectMusic == false && finishgame == false) {
                    selectMusic = true;
                    pp.select();
                    return;
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE && startgame == false && selectMusic == false && finishgame == false) {
                    System.exit(0);
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER && startgame == false && selectMusic == true && finishgame == false) {
                    try { // Load Note Song
                        SongNoteSelect();
                        pp.addNewNote(notes);
                    } catch (FileNotFoundException e2) {
                        // TODO Auto-generated catch block
                        e2.printStackTrace();
                    }

                    // Load Song
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
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER && startgame == true && selectMusic == false && finishgame == true) {
                    // get enter when finishGame
                    selectMusic = true;
                    startgame = false;
                    finishgame = false;
                    pp.playAgain();
                    return;
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT && selectMusic == true) {
                    // get L
                    numberMusic -= 1;
                    if (numberMusic < 0) {
                        numberMusic = 1;
                    }
                    pp.numberMusic(numberMusic);
                    return;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && selectMusic == true) {
                    // get R
                    numberMusic += 1;
                    if (numberMusic > 1) {
                        numberMusic = 0;
                    }
                    pp.numberMusic(numberMusic);
                    return;
                } else if (e.getKeyCode() == KeyEvent.VK_UP && selectMusic == true) {
                    //get Up
                    speedMusic++;
                    if (speedMusic > 3) {
                        speedMusic = 3;
                    }
                    pp.speedMode(speedMusic);
                    KeyNoteGraphic.speedMode(speedMusic);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN && selectMusic == true) {
                    //get Down
                    speedMusic--;
                    if (speedMusic < 1) {
                        speedMusic = 1;
                    }
                    pp.speedMode(speedMusic);
                }
            }

            @Override
            // get keyPress
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
        pp.setFocusable(true);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
        this.setTitle("Beethoven");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1270, 720);
        setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private static int index = 0; // index for divide by 3
    public static List<Double> list = new ArrayList<Double>(); // array of note when on screen
    public static GetNote[] notes; // all Note
    public static int listSize = 0;

    public PlayFrame() {}

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new PlayFrame(notes);
        PlayFrame frame = new PlayFrame();

    }

    public static void Note() {
        // get all note and devide by 3
        int indexTemp = 0;
        int slot = 0, l = 0;
        double delay = 0;
        for (int i = 0; i < index; i += 3) {
            double slot_b = list.get(i);
            slot = (int) slot_b;
            delay = list.get(i + 1);
            double length_b = list.get(i + 2);
            l = (int) length_b;
            notes[indexTemp++] = new GetNote(slot, delay, l);
        }

    }

    // when finish
    public static void finishValue(boolean finishgame2) {
        finishgame = finishgame2;
    }

    // get file WAV. from /song
    public void song() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        AudioInputStream audio0 = AudioSystem.getAudioInputStream(this.getClass().getResource("song/song0.wav"));
        AudioInputStream audio1 = AudioSystem.getAudioInputStream(this.getClass().getResource("song/song1.wav"));
        Clip clip0 = AudioSystem.getClip();
        Clip clip1 = AudioSystem.getClip();
        clip0.open(audio0);
        clip1.open(audio1);
        if (numberMusic == 0) {
            clip0.start(); // play song0 if number is 0 
        } else if (numberMusic == 1) {
            clip1.start(); // play song1 if number is 1 
        }
    }

    // Load Note form text
    public void SongNoteSelect() throws FileNotFoundException {
        list.clear();
        index = 0;
        if (numberMusic == 0) {
            InputStream in = getClass().getResourceAsStream("song/song0-1.txt");
            InputStreamReader inrd = new InputStreamReader(in);
            BufferedReader r = new BufferedReader(inrd);
            String line = null;
            double num = 0;
            try {
                while ((line = r.readLine()) != null) {
                    list.add(Double.parseDouble(line));
                    index += 1;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (numberMusic == 1) {
            InputStream in = getClass().getResourceAsStream("song/song1-x.txt");
            InputStreamReader inrd = new InputStreamReader(in);
            BufferedReader r = new BufferedReader(inrd);
            String line = null;
            double num = 0;
            try {
                while ((line = r.readLine()) != null) {
                    list.add(Double.parseDouble(line));
                    index += 1;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        listSize = list.size();
        notes = new GetNote[listSize / 3];
        Note();
    }

    public static void runMusic(boolean runMusic) {
        playMusic = runMusic;
    }

}
