package com.github.brunothg.game.engine.xxx;

import com.github.brunothg.game.engine.d2.frame.SwingGameFrame;
import com.github.brunothg.game.engine.d2.scene.FPSScene;
import com.github.brunothg.game.engine.d2.scene.LoadingScene;

public class SwingGameFrameTest
{

	public static void main(String[] args)
	{

		SwingGameFrame gameFrame = new SwingGameFrame();
		gameFrame.setScene(new FPSScene(new LoadingScene(true)));

		gameFrame.setVisible(true);
	}

}
