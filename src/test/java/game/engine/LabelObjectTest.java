package game.engine;

import game.engine.frame.SwingGameFrame;
import game.engine.stage.scene.FPSScene;
import game.engine.stage.scene.Scene;
import game.engine.stage.scene.object.CachedLabelSceneObject;
import game.engine.stage.scene.object.LabelSceneObject;
import game.engine.stage.scene.object.Point;
import game.engine.stage.scene.object.ScaleStrategy;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.EventListener;

public class LabelObjectTest
{

	public static void main(String[] args)
	{

		SwingGameFrame gameFrame = new SwingGameFrame();
		gameFrame.setScene(new FPSScene(new Scene()
		{

			LabelSceneObject label = new CachedLabelSceneObject("Das ist ein\n LabelObject");

			@Override
			public void paintScene(Graphics2D g, int width, int height, long elapsedTime)
			{

				g.setColor(Color.WHITE);
				g.fillRect(0, 0, width, height);

				label.setSize(width, height);
				label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));
				label.setTopLeftPosition(new Point(0, 0));
				label.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1, new float[] { 4,
						4, 8 }, 0));
				label.setColor(Color.GREEN);
				label.setOutlinePaint(Color.BLACK);

				label.setScaleStrategy(ScaleStrategy.FitParent);

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
