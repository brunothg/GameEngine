package com.github.brunothg.game.engine.xxx;

import com.github.brunothg.game.engine.d2.frame.FullScreenGameFrame;
import com.github.brunothg.game.engine.d2.scene.FPSScene;
import com.github.brunothg.game.engine.d2.scene.LoadingScene;
import com.github.brunothg.game.engine.image.ImageUtils;

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

				gameFrame.close();
				System.exit(0);
			}
		}).start();
	}

}
