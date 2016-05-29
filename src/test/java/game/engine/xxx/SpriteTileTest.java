package game.engine.xxx;

import game.engine.image.ImageUtils;
import game.engine.image.InternalImage;
import game.engine.image.sprite.Sprite;
import game.engine.image.sprite.WeakSprite;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class SpriteTileTest {

	public static void main(String[] args) {

		InternalImage.setRootFolder("/game/engine/images/");

		Sprite sprite = new WeakSprite(
				ImageUtils.BufferedImage(InternalImage.load("tile.png")), 32,
				32);
		Sprite sprite2 = sprite.getSubSprite(1, 1, sprite.getColumns() - 1, 1);

		JFrame disp1 = new JFrame("Sprite");
		disp1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		disp1.setLayout(
				new GridLayout(sprite.getRows(), sprite.getColumns(), 10, 10));
		disp1.setSize(500, 500);
		disp1.setLocationRelativeTo(null);

		for (int y = 0; y < sprite.getRows(); y++) {
			for (int x = 0; x < sprite.getColumns(); x++) {
				disp1.add(new JLabel(new ImageIcon(sprite.getTile(x, y))));
			}
		}

		disp1.setVisible(true);

		JFrame disp2 = new JFrame("Derived");
		disp2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		disp2.setLayout(new GridLayout(sprite2.getRows(), sprite2.getColumns(),
				10, 10));
		disp2.setSize(500, 500);
		disp2.setLocationRelativeTo(null);

		for (int y = 0; y < sprite2.getRows(); y++) {
			for (int x = 0; x < sprite2.getColumns(); x++) {
				disp2.add(new JLabel(new ImageIcon(sprite2.getTile(x, y))));
			}
		}

		disp2.setVisible(true);
	}
}
