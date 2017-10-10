package com.github.brunothg.game.engine.xxx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventListener;

import com.github.brunothg.game.engine.d2.commons.GraphicScaleStrategy;
import com.github.brunothg.game.engine.d2.commons.Point;
import com.github.brunothg.game.engine.d2.frame.SwingGameFrame;
import com.github.brunothg.game.engine.d2.scene.ScaleScene;

public class ScaleSceneTest {

	public static void main(String[] args) {

		SwingGameFrame gameFrame = new SwingGameFrame();
		gameFrame.setScene(new ScaleScene(100, 100, GraphicScaleStrategy.Fit) {
			public EventListener[] getEventListeners() {
				return new EventListener[] { new MouseAdapter() {
					public void mouseMoved(MouseEvent e) {
						int x = e.getX();
						int y = e.getY();

						Point local = convertGlobalToLocal(new Point(x, y));

						System.out.println(
								x + ", " + y + " => " + local.getX() + ", " + local.getY() + " (" + getFactorX() + ", "
										+ getFactorY() + ") (" + getTranslateX() + ", " + getTranslateY() + ")");
					}
				} };
			}

			@Override
			protected void paintFixedScene(Graphics2D g, long elapsedTime) {
				g.setColor(Color.GREEN);
				g.fillRect(0, 0, 100, 100);

				g.setColor(Color.BLUE);
				g.drawRect(0, 0, 100, 100);

				g.setColor(Color.RED);
				g.drawLine(0, 0, 100, 100);
			}
		});

		gameFrame.setVisible(true);
	}

}
