package game.engine;

import game.engine.stage.SwingStage;
import game.engine.stage.scene.FPSScene;
import game.engine.stage.scene.Scene;
import game.engine.time.Clock;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.EventListener;

import javax.swing.JFrame;

public class StageFPSTest {

	public static void main(String[] args) {

		Clock clk = new Clock();

		JFrame disp = new JFrame("FPS Stage test");
		disp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		disp.setLayout(new BorderLayout());
		disp.setSize(500, 500);
		disp.setLocationRelativeTo(null);

		SwingStage stage = new SwingStage();
		stage.setBackground(Color.GREEN);
		clk.addClockListener(stage);
		disp.add(stage, BorderLayout.CENTER);

		FPSScene fpsScene = new FPSScene(new Scene() {

			@Override
			public void paintScene(Graphics2D g, int width, int height,
					long elapsedTime) {

				double rand0 = Math.random();

				for (int i = 0; i < rand0 * Math.min(width, height); i++) {

					drawLine(g, width, height, elapsedTime);
				}
			}

			private void drawLine(Graphics2D g, int width, int height,
					long elapsedTime) {

				double rand1 = Math.random();
				double rand2 = Math.random();
				double rand3 = Math.random();
				double rand4 = Math.random();

				Color c = new Color((int) (rand1 * 255), (int) (rand2 * 255),
						(int) (rand3 * 255));
				g.setColor(c);

				g.drawLine((int) (width * rand1), (int) (height * rand2),
						(int) (width * rand3), (int) (height * rand4));
			}

			@Override
			public EventListener[] getEventListeners() {
				return null;
			}
		});

		stage.setScene(fpsScene);

		disp.setVisible(true);
		clk.start();
	}
}
