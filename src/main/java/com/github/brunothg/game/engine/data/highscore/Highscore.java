package com.github.brunothg.game.engine.data.highscore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
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

	/**
	 * Write this Highscore to an {@link OutputStream}. Only Name and Score will be
	 * written.
	 * 
	 * @param out
	 *            The stream for writing
	 * @throws IOException
	 */
	public void store(OutputStream out) throws IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));

		for (HighscoreEntry entry : this) {
			writer.write(entry.getPoints() + ";" + entry.getName());
			writer.newLine();
		}

		writer.close();
	}

	/**
	 * Loads {@link Highscore} from {@link InputStream}. Only Names and Score will
	 * be loaded.
	 * 
	 * @param in
	 *            The stream for reading
	 * @throws IOException
	 */
	public void load(InputStream in) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

		scores.clear();

		String line = null;
		while ((line = reader.readLine()) != null) {
			if (line.indexOf(';') <= 0) {
				continue;
			}

			String pointS = line.substring(0, line.indexOf(';'));
			String nameS = line.substring(line.indexOf(';') + 1, line.length());

			int score = Integer.valueOf(pointS);

			addScore(new HighscoreEntry(score, nameS));
		}

		reader.close();
	}
}
