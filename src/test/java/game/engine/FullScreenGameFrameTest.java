package game.engine;

import game.engine.frame.FullScreenGameFrame;
import game.engine.stage.scene.FPSScene;
import game.engine.stage.scene.LoadingScene;

public class FullScreenGameFrameTest
{

	public static void main(String[] args)
	{

		final FullScreenGameFrame gameFrame = new FullScreenGameFrame();
		gameFrame.setScene(new FPSScene(new LoadingScene(true)));

		gameFrame.setVisible(true);

		new Thread(new Runnable()
		{

			@Override
			public void run()
			{

				try
				{
					Thread.sleep(5000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}

				gameFrame.dispose();
			}
		}).start();

	}

}
