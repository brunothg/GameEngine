package game.engine.xxx;

import game.engine.frame.SwingGameFrame;
import game.engine.stage.scene.FPSScene;
import game.engine.stage.scene.LoadingScene;

public class SwingGameFrameTest {

	public static void main(String[] args) {

		SwingGameFrame gameFrame = new SwingGameFrame();
		gameFrame.setScene(new FPSScene(new LoadingScene(true)));

		gameFrame.setVisible(true);
	}

}
