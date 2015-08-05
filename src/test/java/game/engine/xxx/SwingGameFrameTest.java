package game.engine.xxx;

import game.engine.d2.frame.SwingGameFrame;
import game.engine.d2.stage.scene.FPSScene;
import game.engine.d2.stage.scene.LoadingScene;

public class SwingGameFrameTest {

	public static void main(String[] args) {

		SwingGameFrame gameFrame = new SwingGameFrame();
		gameFrame.setScene(new FPSScene(new LoadingScene(true)));

		gameFrame.setVisible(true);
	}

}
