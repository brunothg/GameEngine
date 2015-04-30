package game.engine;

import game.engine.frame.SwingGameFrame;
import game.engine.stage.scene.FPSScene;
import game.engine.stage.scene.Scene;
import game.engine.stage.scene.object.LabelObject;
import game.engine.stage.scene.object.Point;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.EventListener;

public class LabelObjectTest
{

	public static void main(String[] args)
	{

		SwingGameFrame gameFrame = new SwingGameFrame();
		gameFrame.setScene(new FPSScene(new Scene()
		{

			LabelObject label = new LabelObject("Das ist ein LabelObject");

			@Override
			public void paintScene(Graphics2D g, int width, int height, long elapsedTime)
			{

				g.setColor(Color.WHITE);
				g.fillRect(0, 0, width, height);

				label.setSize(width, height);
				label.setTopLeftPosition(new Point(0, 0));

				label.paintOnScene(g, elapsedTime);
			}

			@Override
			public EventListener[] getEventListeners()
			{
				return null;
			}
		}));

		gameFrame.setVisible(true);
	}

}
