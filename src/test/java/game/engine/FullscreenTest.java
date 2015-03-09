package game.engine;

import game.engine.frame.FullScreenGameFrame;
import game.engine.stage.scene.FPSScene;
import game.engine.stage.scene.LoadingScene;

public class FullscreenTest {

	public static void main(String[] args) {

		FullScreenGameFrame gameFrame = new FullScreenGameFrame();
		gameFrame.setScene(new FPSScene(new LoadingScene()));

		gameFrame.setVisible(true);
	}

}
