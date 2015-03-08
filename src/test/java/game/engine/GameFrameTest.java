package game.engine;

import game.engine.frame.GameFrame;

public class GameFrameTest {

	public static void main(String[] args) {

		GameFrame gameFrame = new GameFrame();
		gameFrame.setSize(800, 600, true);

		gameFrame.setVisible(true);
	}

}
