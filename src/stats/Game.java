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

	public Game(Date date, Team home, Team away, boolean didHomeWin) {
		this.date = date;
		this.home = home;
		this.away = away;
		this.didHomeWin = didHomeWin;
	}

	public Date getDate() {
		return date;
	}

	public Team getHome() {
		return home;
	}

	public Team getAway() {
		return away;
	}

	public boolean getDidHomeWin() {
		return didHomeWin;
	}
}
