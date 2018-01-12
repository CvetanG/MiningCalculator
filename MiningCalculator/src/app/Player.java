package app;

import java.util.Comparator;

public class Player implements Comparable{
	int score;
	String name;
	
	public Player(int score, String name) {
		super();
		this.score = score;
		this.name = name;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return name + " " + score;
	}

	@Override
	public int compareTo(Object o) {
		Player newP = (Player) o;
		return (newP.score) - (this.score);
	}
	
}
