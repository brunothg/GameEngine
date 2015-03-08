package game.engine;

import game.engine.frame.GameFrame;
import game.engine.stage.scene.FPSScene;

public class GameFrameTest {

	public static void main(String[] args) {

		GameFrame gameFrame = new GameFrame();
		gameFrame.setScene(new FPSScene(gameFrame.getScene()));

		gameFrame.setVisible(true);
	}

}
