package game.engine.xxx;

import game.engine.frame.FullScreenGameFrame;
import game.engine.image.ImageUtils;
import game.engine.stage.scene.FPSScene;
import game.engine.stage.scene.LoadingScene;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FullScreenGameFrameTest {

	public static void main(String[] args) {

		final FullScreenGameFrame gameFrame = new FullScreenGameFrame();
		gameFrame.setScene(new FPSScene(new LoadingScene(true)));
		gameFrame.setCursor(ImageUtils.createEmptyCursor(null));
		gameFrame.setVisible(true);

		gameFrame.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				}
			}

		});

		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
				}

				System.exit(0);
			}
		}).start();
	}

}