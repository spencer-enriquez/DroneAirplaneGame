import java.util.*;

public class Scoreboard {
	
	private ArrayList<Boolean> games;
	private String scoreFormat;
	public static final int  WIN_TIME = 90;
	public static final int WIN_LIVES_LEFT = 0;

	public Scoreboard() {
		games = new ArrayList<Boolean>();
		scoreFormat = "Score: 0 out of 0";
	}
	
	
	/*
	 * Returns number of wins within arraylist to help format
	 */
	public int getWins() {
		int counter = 0;
		for (boolean game : games) {
			if (game == true)
				counter++;
		}
		return counter;
	}
	
	
	/*
	 * Sets score to current score format
	 */
	public String setScore() {
		scoreFormat = "Score: " + this.getWins() + " out of " + games.size();
		return scoreFormat;
	}
	
	
	
	/*
	 * Checks for win conditions or time and lives left every second
	 * Meant to be checked every second or every time drone is hit
	 * Assumed that Drone starts with positive amount of lives
	 */
	public void checkScore(TimeClock time, Drone drone) {
		if (time.getSeconds() < WIN_TIME && drone.getLives() > WIN_LIVES_LEFT)
			return;
		else if (time.getSeconds() == WIN_TIME || drone.getLives() > WIN_LIVES_LEFT) {
			games.add(true);
		}
		else if (time.getSeconds() < WIN_TIME && drone.getLives() <= WIN_LIVES_LEFT) {
			games.add(false);
		}
	}
}
