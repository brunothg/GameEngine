package com.github.brunothg.game.engine.data.highscore;

import java.io.Serializable;

public class HighscoreEntry implements Serializable, Comparable<HighscoreEntry> {

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "HighscoreEntry [points=" + points + ", name=" + name + "]";
	}

	private int points;
	private String name;

	public HighscoreEntry(int points, String name) {
		setPoints(points);
		setName(name);
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(HighscoreEntry o) {
		return -1 * Integer.compare(getPoints(), o.getPoints());
	}

}
