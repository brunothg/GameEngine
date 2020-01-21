package com.github.brunothg.game.engine.xxx;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.github.brunothg.game.engine.d2.frame.SwingGameFrame;
import com.github.brunothg.game.engine.d2.scene.HighscoreScene;
import com.github.brunothg.game.engine.data.highscore.Highscore;
import com.github.brunothg.game.engine.data.highscore.HighscoreEntry;

public class HighscoreSceneTest {

	public static void main(String[] args) {

		SwingGameFrame gameFrame = new SwingGameFrame();
		gameFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				gameFrame.close();
			}
		});

		Highscore highscore = new Highscore();
		highscore.setMaximumScores(15);
		for (int i = 0; i < 100; i++) {
			highscore.addScore(new HighscoreEntry((int) (i * 10 * Math.random()), i + "-Nr"));
		}
		gameFrame.setScene(new HighscoreScene(highscore));

		gameFrame.setVisible(true);
	}

}
