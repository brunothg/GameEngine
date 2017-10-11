package com.github.brunothg.game.engine.data.highscore;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A Highscore.
 * 
 * @author Marvin Bruns
 *
 */
public class Highscore implements Iterable<HighscoreEntry>, Serializable {

	private static final long serialVersionUID = 1L;

	private List<HighscoreEntry> scores = new LinkedList<>();
	private int maximumScores = 10;

	/**
	 * Add score to this highscore. If maximum scores is reached, the highscore will
	 * be cut.
	 * 
	 * @param score
	 *            The score to be added
	 */
	public void addScore(HighscoreEntry score) {
		scores.add(score);

		updateList();
	}

	public int getMaximumScores() {
		return maximumScores;
	}

	public void setMaximumScores(int maximumScores) {
		this.maximumScores = maximumScores;

		updateList();
	}

	/**
	 * Get Highscore at specific positon
	 * 
	 * @param index
	 *            index of wanted highscore
	 * @return {@link HighscoreEntry}
	 */
	public HighscoreEntry getScore(int index) {
		updateList();
		return scores.get(index);
	}

	/**
	 * Get the score count
	 * 
	 * @return The score count
	 */
	public int getScoreCount() {
		return scores.size();
	}

	protected void updateList() {
		Collections.sort(scores);
		if (scores.size() > getMaximumScores()) {
			scores = scores.subList(0, getMaximumScores());
		}
	}

	@Override
	public Iterator<HighscoreEntry> iterator() {
		Collections.sort(scores);
		return scores.iterator();
	}

}
