package game.engine;

import game.engine.frame.FullScreenGameFrame;

public class SwingGameFrameTest
{

	public static void main(String[] args)
	{

		final FullScreenGameFrame gameFrame = new FullScreenGameFrame();

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
