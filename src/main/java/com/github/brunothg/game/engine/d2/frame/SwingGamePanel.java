package com.github.brunothg.game.engine.d2.frame;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import com.github.brunothg.game.engine.d2.scene.LoadingScene;
import com.github.brunothg.game.engine.d2.scene.Scene;
import com.github.brunothg.game.engine.d2.stage.SwingStage;
import com.github.brunothg.game.engine.time.Clock;

/**
 * Swing component for embedding game-engine in normal swing applications.
 * 
 * @author Marvin Bruns
 *
 */
public class SwingGamePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private SwingStage stage;
	private Clock clock;

	public SwingGamePanel() {

		initialize();
	}

	private void initialize() {

		setLayout(new BorderLayout());

		stage = new SwingStage();
		stage.setScene(new LoadingScene());
		super.add(stage, BorderLayout.CENTER);

		clock = new Clock();
		clock.addClockListener(stage);
		clock.start();
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
	 * Change the {@link SwingStage} that is used by this {@link SwingGameFrame} .
	 * Normally there's no reason to change the default stage.
	 * 
	 * @param stage
	 */
	public void setStage(SwingStage stage) {

		if (stage == null) {
			throw new IllegalArgumentException("Null value not allowed");
		}
		clock.removeClockListener(this.stage);
		super.remove(this.stage);

		this.stage = stage;
		super.add(this.stage, BorderLayout.CENTER);
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
	 * Destroy GamePanel. Not useable after this method.
	 */
	public void dispose() {
		clock.destroy();
	}

	@Override
	protected void finalize() throws Throwable {
		try {
			clock.destroy();
		} catch (Exception e) {
		} finally {
			super.finalize();
		}
	}
}
