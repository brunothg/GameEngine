package game.engine;

import game.engine.image.ImageUtils;
import game.engine.image.InternalImage;
import game.engine.image.sprite.BufferedSprite;
import game.engine.image.sprite.Sprite;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class SpriteTileTest {

	public static void main(String[] args) {

		InternalImage.setRootFolder("/game/engine/images/");

		Sprite sprite = new BufferedSprite(
				ImageUtils.BufferedImage(InternalImage.load("tile.png")), 32,
				32);

		JFrame disp = new JFrame();
		disp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		disp.setLayout(new GridLayout(sprite.getRows(), sprite.getColumns(),
				10, 10));
		disp.setSize(500, 500);
		disp.setLocationRelativeTo(null);

		for (int y = 0; y < sprite.getRows(); y++) {
			for (int x = 0; x < sprite.getColumns(); x++) {
				disp.add(new JLabel(new ImageIcon(sprite.getTile(x, y))));
			}
		}

		disp.setVisible(true);
	}
}
