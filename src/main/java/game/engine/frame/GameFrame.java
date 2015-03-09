package game.engine.frame;

import game.engine.image.EmptyImage;
import game.engine.stage.SwingStage;
import game.engine.stage.scene.LoadingScene;
import game.engine.stage.scene.Scene;
import game.engine.time.Clock;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.Insets;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GameFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SwingStage stage;
	private Clock clock;

	public GameFrame(String title, GraphicsConfiguration gc) {

		super(title, gc);
		initialize();
	}

	public GameFrame(String title) {

		super(title);
		initialize();
	}

	public GameFrame() {

		super("GameEngine");
		initialize();
	}

	private void initialize() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setDefaultIcon();
		setSize(800, 600, true);

		stage = new SwingStage();
		stage.setScene(new LoadingScene());
		add(stage, BorderLayout.CENTER);

		clock = new Clock();
		clock.addClockListener(stage);
		clock.start();
	}

	private void setDefaultIcon() {
		try {
			setIconImage(ImageIO.read(GameFrame.class
					.getResource("/game/engine/media/icon.png")));
		} catch (IOException e) {
			setIconImage(new EmptyImage.AlphaImage());
		}
	}

	/**
	 * Get the stage of this game frame.
	 * 
	 * @return Stage of this frame
	 */
	public SwingStage getStage() {

		return stage;
	}

	/**
	 * Change the {@link SwingStage} that is used by this {@link GameFrame}.
	 * Normally there's no reason to change the default stage.
	 * 
	 * @param stage
	 */
	public void setStage(SwingStage stage) {

		if (stage == null) {
			throw new IllegalArgumentException("Null value not allowed");
		}
		clock.removeClockListener(this.stage);

		this.stage = stage;
		clock.addClockListener(stage);
	}

	/**
	 * @see SwingStage#setScene(Scene)
	 */
	public void setScene(Scene scene) {

		getStage().setScene(scene);
	}

	/**
	 * @see SwingStage#getScene()
	 */
	public Scene getScene() {

		return getStage().getScene();
	}

	/**
	 * @see Clock#setFramesPerSecond(int)
	 */
	public void setFramesPerSecond(int framesPerSecond) {

		clock.setFramesPerSecond(framesPerSecond);
	}

	/**
	 * @see Clock#getFramesPerSecond()
	 */
	public double getFramesPerSecond() {

		return clock.getFramesPerSecond();
	}

	/**
	 * Set the size of this {@link GameFrame}. Setting the inner size may only
	 * work if this frame is visible.
	 * 
	 * @param width
	 *            The width of this frame
	 * @param height
	 *            The height of this frame
	 * @param innerSize
	 *            if true the inner size of this frame is set. This is the size
	 *            inside all decorations.
	 */
	public void setSize(int width, int height, boolean innerSize) {

		setSize(width, height);

		if (!innerSize) {
			return;
		}

		Insets insets = getInsets();

		if (insets == null) {
			return;
		}

		int addWidth = insets.left + insets.right;
		int addHeight = insets.top + insets.bottom;

		setSize(width + addWidth, height + addHeight);
	}
}
