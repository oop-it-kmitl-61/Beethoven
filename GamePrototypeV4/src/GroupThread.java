import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class GroupThread {

	private PlayPanel pp;
	private Thread menu;
	private Thread noteThread;
	private Thread gameThread;
	public static int num = 0;
	public GetNote[] notes;
	private long spf = 1;
	private boolean hasNext = true;
	public static boolean pause = false;

	public GroupThread(PlayPanel pp, GetNote[] note2) {
		this.pp = pp;
		notes = note2;
		if (num == 0) {
			menu();
			num += 1;
		} else if (num == 1) {	
			noteThread();
			gameThread();
		}
		

	}
	
	private void menu() {
		menu = new Thread() {

			public void run() {

				try {
					while (!pp.isFinish() || hasNext) {
						sleep(30);
						pp.updateNotes2();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		};

		menu.start();
	}

	private void noteThread() {
		noteThread = new Thread() {

			public void run() {
				try {
					
					for (int i = 0; i < notes.length; i++) {
						hasNext = true;
						int timer = (int) (notes[i].delay * 1000);
						if (timer > 0) {
							sleep(timer);
						}
						pp.addNote(notes[i]);
						

					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				hasNext = false;
			}

		};

		noteThread.start();
	}

	/**
	 * Thread for update note position
	 */
	private void gameThread() {
		gameThread = new Thread() {

			public void run() {

				try {

					while (!pp.isFinish() || hasNext) {
						sleep(spf);
						pp.updateNotes();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				;
				try {
					for (int i = 0; i < 3; i++) {
						sleep(1000);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				pp.finish();

			}

		};

		gameThread.start();

	}


}
