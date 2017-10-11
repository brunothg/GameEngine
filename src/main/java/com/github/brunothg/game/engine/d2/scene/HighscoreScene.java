package com.github.brunothg.game.engine.d2.scene;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.EventListener;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.brunothg.game.engine.d2.commons.Orientation.HorizontalOrientation;
import com.github.brunothg.game.engine.d2.commons.listener.ExitListener;
import com.github.brunothg.game.engine.d2.object.text.LabelSceneObject;
import com.github.brunothg.game.engine.data.highscore.Highscore;
import com.github.brunothg.game.engine.data.highscore.HighscoreEntry;
import com.github.brunothg.game.engine.utils.Null;

/**
 * A {@link Scene} for showing your {@link Highscore}.
 * 
 * @author Marvin Bruns
 *
 */
public class HighscoreScene implements Scene {
	Logger LOG = LoggerFactory.getLogger(HighscoreScene.class);

	private Highscore highscore;

	private int visibleHighscores = 10;
	private AtomicInteger scrollIndex = new AtomicInteger(0);

	private ExitListener<Void> exitListener;

	private Color backgroundColor = Color.LIGHT_GRAY;
	private Color foregroundColor = Color.BLACK;

	private LabelSceneObject txt = new LabelSceneObject();

	public HighscoreScene(Highscore higscore) {
		setHighscore(higscore);
	}

	public HighscoreScene() {
		this(null);
	}

	@Override
	public void paintScene(Graphics2D g, int width, int height, long elapsedTime) {
		Highscore highscore = getHighscore();
		int scrollDelta = scrollIndex.get();

		int visibleHighscores = getVisibleHighscores();
		int rowHeight = (int) ((double) height / visibleHighscores);

		Color bgCol1 = getBackgroundColor();
		Color bgCol2 = bgCol1.brighter();

		g.setColor((visibleHighscores % 2 != 0) ? bgCol1 : bgCol2);
		g.fillRect(0, 0, width, height);

		int heightIndex = 0;
		for (int i = 0; i < visibleHighscores; i++) {
			if (i % 2 == 0) {
				g.setColor(bgCol1);
			} else {
				g.setColor(bgCol2);
			}

			g.fillRect(0, heightIndex, width, rowHeight);

			if (highscore != null && highscore.getScoreCount() >= i + 1 + scrollDelta) {
				HighscoreEntry scoreEntry = highscore.getScore(i + scrollDelta);

				txt.setText("" + (i + 1 + scrollDelta) + ".");
				txt.setHorizontalTextOrientation(HorizontalOrientation.East);
				txt.setSize((int) (width * 0.05), rowHeight);
				txt.setPosition((int) (width * 0.1), rowHeight * i);
				txt.paintOnScene(g, width, height, elapsedTime);

				txt.setText(scoreEntry.getName());
				txt.setHorizontalTextOrientation(HorizontalOrientation.West);
				txt.setSize((int) (width * 0.45), rowHeight);
				txt.setPosition((int) (width * 0.1) + (int) (width * 0.15), rowHeight * i);
				txt.paintOnScene(g, width, height, elapsedTime);

				txt.setText("" + scoreEntry.getPoints());
				txt.setHorizontalTextOrientation(HorizontalOrientation.East);
				txt.setSize((int) (width * 0.3), rowHeight);
				txt.setPosition((int) (width * 0.1) + (int) (width * 0.5), rowHeight * i);
				txt.paintOnScene(g, width, height, elapsedTime);
			}

			heightIndex += rowHeight;
		}
	}

	protected void scroll(int delta) {
		int newScroll = scrollIndex.addAndGet(delta);
		int scoreCount = highscore.getScoreCount();
		int maxScrolls = Math.max(0, scoreCount - getVisibleHighscores());

		if (newScroll < 0) {
			scrollIndex.set(0);
		} else if (newScroll > maxScrolls) {
			scrollIndex.set(maxScrolls);
		}
	}

	protected void exit() {
		if (exitListener != null) {
			exitListener.exit(null);
		} else {
			LOG.warn("Exit event - but no listener");
		}
	}

	private EventListener getKeyListener() {
		return new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
				case KeyEvent.VK_W:
					scroll(-1);
					break;

				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_S:
					scroll(+1);
					break;

				case KeyEvent.VK_ESCAPE:
					exit();
					break;
				}
			}
		};
	}

	@Override
	public EventListener[] getEventListeners() {
		return new EventListener[] { getKeyListener() };
	}

	public Highscore getHighscore() {
		return highscore;
	}

	public void setHighscore(Highscore highscore) {
		this.highscore = highscore;
	}

	public int getVisibleHighscores() {
		return visibleHighscores;
	}

	public void setVisibleHighscores(int visibleHighscores) {
		this.visibleHighscores = visibleHighscores;
	}

	public ExitListener<Void> getExitListener() {
		return exitListener;
	}

	public void setExitListener(ExitListener<Void> exitListener) {
		this.exitListener = exitListener;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = Null.nvl(backgroundColor, Color.BLACK);
	}

	public Color getForegroundColor() {
		return foregroundColor;
	}

	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = Null.nvl(foregroundColor, Color.BLACK);
	}

}
