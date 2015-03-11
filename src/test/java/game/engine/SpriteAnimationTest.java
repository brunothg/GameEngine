package game.engine;

import game.engine.image.InternalImage;
import game.engine.image.sprite.InvertedSprite;
import game.engine.image.sprite.Sprite;
import game.engine.image.sprite.WeakSprite;
import game.engine.stage.SwingStage;
import game.engine.stage.scene.FPSScene;
import game.engine.stage.scene.Scene;
import game.engine.stage.scene.object.AnimatedSceneObject;
import game.engine.stage.scene.object.Point;
import game.engine.time.Clock;
import game.engine.time.TimeUtils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.EventListener;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

public class SpriteAnimationTest {

	public static void main(String[] args) throws Exception {

		InternalImage.setRootFolder("/game/engine/images/");

		final Sprite sprite;

		// sprite = new WeakSprite(ImageUtils.BufferedImage(InternalImage
		// .load("animatedSprite.png")), 32, 32);

		// sprite = new WeakSprite(
		// ImageIO.read(new URL(
		// "https://highergroundz.files.wordpress.com/2012/07/spritesheetvolt_run.png")),
		// 280, 385);

		sprite = new InvertedSprite(new WeakSprite(ImageIO.read(new URL(
				"http://i.stack.imgur.com/i7oRv.png")), 64, 69), false, true,
				true);

		final AnimatedSceneObject animation = new AnimatedSceneObject(sprite,
				TimeUtils.NanosecondsOfMilliseconds(100));
		animation.setDrawBoundingBox(true);

		JFrame disp1 = new JFrame("Sprite animation");
		disp1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		disp1.setLayout(new BorderLayout());
		disp1.setSize(500, 500);
		disp1.setLocationRelativeTo(null);

		JPanel spriteP = new JPanel();
		spriteP.setLayout(new GridLayout(sprite.getRows(), sprite.getColumns(),
				10, 10));
		disp1.add(new JScrollPane(spriteP), BorderLayout.CENTER);

		for (int y = 0; y < sprite.getRows(); y++) {
			for (int x = 0; x < sprite.getColumns(); x++) {
				spriteP.add(new JLabel(new ImageIcon(sprite.getTile(x, y))));
			}
		}

		JPanel rowselect = new JPanel();
		rowselect.setLayout(new BoxLayout(rowselect, BoxLayout.Y_AXIS));

		ButtonGroup btngrp = new ButtonGroup();
		for (int i = 0; i < animation.getAnimationRowCount(); i++) {

			final JRadioButton rbtn = new JRadioButton("Animation " + (i + 1));
			btngrp.add(rbtn);

			if (i == 0) {
				rbtn.setSelected(true);
			}

			final int row = i;

			rbtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if (rbtn.isSelected()) {

						synchronized (animation) {
							animation.setAnimationRow(row);
						}
					}
				}
			});

			rowselect.add(rbtn);
		}

		disp1.add(new JScrollPane(rowselect), BorderLayout.EAST);

		SwingStage stage = new SwingStage();
		stage.setMinimumSize(new Dimension(animation.getWidth() + 10, animation
				.getHeight() + 10));
		stage.setPreferredSize(stage.getMinimumSize());
		disp1.add(stage, BorderLayout.WEST);

		stage.setScene(new FPSScene(new Scene() {

			@Override
			public void paintScene(Graphics2D g, int width, int height,
					long elapsedTime) {

				g.setColor(Color.WHITE);
				g.fillRect(0, 0, width, height);

				synchronized (animation) {
					animation.setTopLeftPosition(new Point((width / 2)
							- (animation.getWidth() / 2), (height / 2)
							- (animation.getHeight() / 2)));
					animation.paintOnScene(g, elapsedTime);
				}
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
