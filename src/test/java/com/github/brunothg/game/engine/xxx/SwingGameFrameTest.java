package com.github.brunothg.game.engine.xxx;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.github.brunothg.game.engine.d2.frame.SwingGameFrame;
import com.github.brunothg.game.engine.d2.scene.FPSScene;
import com.github.brunothg.game.engine.d2.scene.LoadingScene;

public class SwingGameFrameTest {

	public static void main(String[] args) {

		SwingGameFrame gameFrame = new SwingGameFrame();
		gameFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				gameFrame.close();
			}
		});

		gameFrame.setScene(new FPSScene(new LoadingScene(true)));

		gameFrame.setVisible(true);
	}

}
