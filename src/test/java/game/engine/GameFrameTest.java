package game.engine;

import game.engine.frame.GameFrame;
import game.engine.stage.scene.FPSScene;
import game.engine.stage.scene.LoadingScene;

public class GameFrameTest {

	public static void main(String[] args) {

		GameFrame gameFrame = new GameFrame();
		gameFrame.setScene(new FPSScene(new LoadingScene(true)));

		gameFrame.setVisible(true);
	}

}
