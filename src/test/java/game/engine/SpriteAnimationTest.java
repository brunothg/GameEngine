package game.engine;

import game.engine.image.ImageUtils;
import game.engine.image.InternalImage;
import game.engine.image.sprite.Sprite;
import game.engine.image.sprite.WeakSprite;
import game.engine.stage.Stage;
import game.engine.stage.scene.FPSScene;
import game.engine.stage.scene.Scene;
import game.engine.stage.scene.object.AnimatedSceneObject;
import game.engine.stage.scene.object.Point;
import game.engine.time.Clock;
import game.engine.time.TimeUtils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.util.EventListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SpriteAnimationTest {

	public static void main(String[] args) {

		InternalImage.setRootFolder("/game/engine/images/");

		final Sprite sprite = new WeakSprite(
				ImageUtils.BufferedImage(InternalImage
						.load("animatedSprite.png")), 32, 32);

		JFrame disp1 = new JFrame("Sprite");
		disp1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		disp1.setLayout(new BorderLayout());
		disp1.setSize(500, 500);
		disp1.setLocationRelativeTo(null);

		JPanel spriteP = new JPanel();
		spriteP.setLayout(new GridLayout(sprite.getRows(), sprite.getColumns(),
				10, 10));
		disp1.add(spriteP, BorderLayout.NORTH);

		for (int y = 0; y < sprite.getRows(); y++) {
			for (int x = 0; x < sprite.getColumns(); x++) {
				spriteP.add(new JLabel(new ImageIcon(sprite.getTile(x, y))));
			}
		}

		Stage stage = new Stage();
		disp1.add(stage, BorderLayout.CENTER);

		stage.setScene(new FPSScene(new Scene() {

			AnimatedSceneObject animation = new AnimatedSceneObject(sprite,
					TimeUtils.NanosecondsOfMilliseconds(100));

			@Override
			public void paintScene(Graphics2D g, int width, int height,
					long elapsedTime) {

				g.setColor(Color.WHITE);
				g.fillRect(0, 0, width, height);

				animation.setTopLeftPosition(new Point((width / 2)
						- (animation.getWidth() / 2), (height / 2)
						- (animation.getHeight() / 2)));
				animation.paintOnScene(g, elapsedTime);
			}

			@Override
			public EventListener[] getEventListeners() {
				return null;
			}
		}));

		Clock clk = new Clock();
		clk.addClockListener(stage);

		disp1.setVisible(true);
		stage.tick(0, 0);
		clk.start();
	}
}
