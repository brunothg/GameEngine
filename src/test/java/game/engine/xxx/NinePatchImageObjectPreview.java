package game.engine.xxx;

import game.engine.d2.stage.SwingStage;
import game.engine.d2.stage.scene.Scene;
import game.engine.d2.stage.scene.object.CachedNinePatchImageSceneObject;
import game.engine.d2.stage.scene.object.Point;
import game.engine.time.Clock;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EventListener;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

/**
 * Small test window for nine patch images
 * 
 * @author Marvin Bruns
 *
 */
public class NinePatchImageObjectPreview {

	static Object lock = new Object();
	static CachedNinePatchImageSceneObject image;

	public static void main(String[] args) {
		setLaF();

		JFrame disp = new JFrame("Nine Patch - *.9.png");
		disp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		disp.setLayout(new BorderLayout());
		disp.setSize(800, 600);

		SwingStage stage = new SwingStage();
		stage.setScene(getScene());
		disp.add(stage, BorderLayout.CENTER);

		Clock clk = new Clock();
		clk.addClockListener(stage);
		clk.start();

		JPanel pnlBtn = new JPanel();
		pnlBtn.setLayout(new FlowLayout(FlowLayout.RIGHT));
		disp.add(pnlBtn, BorderLayout.SOUTH);

		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener(getActionListener());
		pnlBtn.add(btnOpen);

		disp.setLocationRelativeTo(null);
		disp.setVisible(true);
	}

	private static void setLaF() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
	}

	private static ActionListener getActionListener() {

		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser fc = new JFileChooser();
				fc.setMultiSelectionEnabled(false);
				fc.setFileFilter(new FileFilter() {

					@Override
					public String getDescription() {
						return "Nine patch image (*.9.png)";
					}

					@Override
					public boolean accept(File f) {

						if (!f.isFile()) {
							return true;
						}

						if (f.getName().toLowerCase()
								.endsWith(".9.png".toLowerCase())) {
							return true;
						}

						return false;
					}
				});

				int result = fc.showOpenDialog(null);

				if (result == JFileChooser.APPROVE_OPTION) {

					File file = fc.getSelectedFile();
					try {
						BufferedImage img = ImageIO.read(file);
						setImage(img);
					} catch (IOException e1) {

						JOptionPane.showMessageDialog(fc, e1.getMessage(),
								"Error", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		};
	}

	static void setImage(Image img) {

		synchronized (lock) {

			image = new CachedNinePatchImageSceneObject(img);
			image.setTopLeftPosition(new Point(0, 0));
		}
	}

	private static Scene getScene() {

		return new Scene() {

			Color bg1 = new Color(55, 55, 55);
			Color bg2 = new Color(200, 200, 200);

			int bgSize = 10;

			@Override
			public void paintScene(Graphics2D g, int width, int height,
					long elapsedTime) {

				boolean colorToogle = true;
				boolean colorToogleRow = false;

				for (int y = 0; y < height; y += bgSize) {
					for (int x = 0; x < width; x += bgSize) {
						colorToogle = !colorToogle;

						g.setColor((colorToogle) ? bg1 : bg2);
						g.fillRect(x, y, bgSize, bgSize);
					}
					colorToogle = colorToogleRow;
					colorToogleRow = !colorToogleRow;
				}

				synchronized (lock) {

					if (image != null) {

						image.setSize(width, height);
						image.paintOnScene(g, elapsedTime);
					}
				}
			}

			@Override
			public EventListener[] getEventListeners() {
				return null;
			}
		};
	}

}
