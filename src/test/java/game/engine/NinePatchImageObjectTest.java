package game.engine;

import game.engine.image.InternalImage;
import game.engine.stage.SwingStage;
import game.engine.stage.scene.Scene;
import game.engine.stage.scene.object.CachedNinePatchImageSceneObject;
import game.engine.stage.scene.object.Point;
import game.engine.time.Clock;

import java.awt.BorderLayout;
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

public class NinePatchImageObjectTest {

	static Object lock = new Object();
	static CachedNinePatchImageSceneObject image;

	public static void main(String[] args) {
		setLaF();

		InternalImage.setRootFolder("/game/engine/images/");

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

			@Override
			public void paintScene(Graphics2D g, int width, int height,
					long elapsedTime) {

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
