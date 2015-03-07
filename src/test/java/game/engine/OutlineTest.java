package game.engine;

import game.engine.image.ImageUtils;
import game.engine.time.TimeUtils;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class OutlineTest {

	/**
	 * String representing a URL for the test image
	 */
	private static final String URL_IMAGE = "http://icons.iconarchive.com/icons/icons8/windows-8/128/Transport-Motorcycle-icon.png";

	public static void main(String[] args) throws Exception {

		System.out.println("Start");

		JFrame disp = new JFrame();
		disp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		disp.setSize(500, 500);
		disp.setLocationRelativeTo(null);
		disp.setLayout(new GridLayout(1, 3));

		BufferedImage orig = getImage();

		long time;

		time = System.nanoTime();
		BufferedImage outT = ImageUtils.shapeToImage(
				ImageUtils.getOutlines(orig, 0, 0, null, true), Color.BLUE,
				true);
		time = System.nanoTime() - time;
		System.out.println("Outline global -> " + TimeUtils.Seconds(time)
				+ " sec");

		time = System.nanoTime();
		BufferedImage outF = ImageUtils.shapeToImage(
				ImageUtils.getOutlines(orig, 0, 0, null, false), Color.BLUE,
				true);
		time = System.nanoTime() - time;
		System.out.println("Outline with free -> " + TimeUtils.Seconds(time)
				+ " sec");

		disp.add(new JLabel(new ImageIcon(orig)));
		disp.add(new JLabel(new ImageIcon(outT)));
		disp.add(new JLabel(new ImageIcon(outF)));

		disp.setVisible(true);

		System.out.println("Stop");
	}

	private static BufferedImage getImage() throws MalformedURLException,
			IOException {
		return ImageIO.read(new URL(URL_IMAGE));
	}

}
