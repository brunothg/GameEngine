package game.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.engine.frame.FullScreenGameFrame;
import game.engine.stage.scene.FPSScene;
import game.engine.stage.scene.LoadingScene;

public class FullScreenGameFrameTest {

	public static void main(String[] args) {

		final FullScreenGameFrame gameFrame = new FullScreenGameFrame();
		gameFrame.setScene(new FPSScene(new LoadingScene(true)));

		gameFrame.setVisible(true);

		gameFrame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				System.out.println(e.getKeyCode());

				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});

		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				gameFrame.dispose();
			}
		}).start();

	}

}
