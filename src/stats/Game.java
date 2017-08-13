package stats;

import java.util.Date;

import league.Team;

/**
 * This class is used for organizing game information.
 * 
 * @author paultouma
 *
 */
public class Game {

	private Date date;
	private Team home, away;
	private boolean didHomeWin;

	/**
	 * Conscructor
	 * 
	 * @param date
	 *            - date of game
	 * @param home
	 *            - home team
	 * @param away
	 *            - away team
	 * @param didHomeWin
	 *            - whether home team won
	 */
	public Game(Date date, Team home, Team away, boolean didHomeWin) {
		this.date = date;
		this.home = home;
		this.away = away;
		this.didHomeWin = didHomeWin;
	}

	/**
	 * Returns the date of game
	 * 
	 * @return date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Returns the home team
	 * 
	 * @return home team
	 */
	public Team getHome() {
		return home;
	}

	/**
	 * Returns the away team
	 * 
	 * @return - away team
	 */
	public Team getAway() {
		return away;
	}

	/**
	 * Returns whether home team won
	 * 
	 * @return whether home team won
	 */
	public boolean getDidHomeWin() {
		return didHomeWin;
	}
}
