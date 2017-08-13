package league;

import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.rits.cloning.Cloner;

import stats.Game;
import stats.Stats;

public class Team {

	private String name;
	private Conference c;
	private Division d;
	private Date currentDate;
	private Date dateEliminated = null;

	private SortedMap<Date, Game> myGames = new TreeMap<Date, Game>();
	private Stats stats = new Stats(this);

	/**
	 * Constructor
	 * 
	 * @param c
	 *            - conference
	 * @param d
	 *            - division
	 * @param name
	 *            - name of team
	 */
	public Team(Conference c, Division d, String name) {
		this.c = c;
		this.d = d;
		this.name = name;
	}

	/**
	 * Adds game to a Team
	 * 
	 * @param previousDate
	 *            - the previous date with game information for that team
	 * @param date
	 *            - current date of the game
	 * @param g
	 *            - game
	 * @param simulation
	 *            - whether a simulation or not
	 * @param simWins
	 *            - whether the simulation wins or not
	 * @param simTeams
	 *            - the teams ahead of current team in the simulation; only
	 *            meaningful if simulation is true
	 */
	public void addGame(Date previousDate, Date date, Game g, boolean simulation, boolean simWins,
			List<Team> simTeams) {

		boolean isOneOfPlayingTeams = this.equals(g.getHome()) || this.equals(g.getAway());
		Team other = this.equals(g.getHome()) ? g.getAway() : g.getHome();
		boolean thisTeamWon = (g.getHome().equals(this) && g.getDidHomeWin())
				|| (g.getAway().equals(this) && !g.getDidHomeWin());

		if (isOneOfPlayingTeams) {

			myGames.put(date, g);

			if (simulation) {
				thisTeamWon = simWins;

				if (simTeams.contains(other) && simTeams.contains(this)) {
					thisTeamWon = new RegularPlayoffComparator().compare(this, other) < 0 ? false : true;
				}
			}
		}
		stats.addStatsForGame(other, previousDate, date, isOneOfPlayingTeams, thisTeamWon);

		// update division leader
		d.setDivisionLeader(date);
	}

	/**
	 * Returns a copy of the team to be used for simulation.
	 * 
	 * @param date
	 *            - current date of simulated team
	 * @param simWins
	 *            - whether the simulated team is to win or not
	 * @param simTeams
	 *            - the teams ahead of current team in the simulation
	 * @return deep copy of team to be used for simulation
	 */
	public Team simulateAfterDate(Date date, boolean simWins, List<Team> simTeams) {

		SortedMap<Date, Game> remGames = getGamesAfterDate(date);

		Cloner cloner = new Cloner();
		Team simulatedCopy = cloner.deepClone(this);
		for (Date current : remGames.keySet()) {
			simulatedCopy.addGame(date, date, remGames.get(current), true, simWins, simTeams);
			simulatedCopy.d.setDivisionLeader(date);
		}

		return simulatedCopy;
	}

	/**
	 * Returns the games played by team after specified base date
	 * 
	 * @param base
	 *            - base date
	 * @return map of dates to games after specified base date
	 */
	public SortedMap<Date, Game> getGamesAfterDate(Date base) {

		SortedMap<Date, Game> toReturn = new TreeMap<Date, Game>();
		for (Date d : myGames.keySet()) {
			if (d.after(base)) {
				toReturn.put(d, myGames.get(d));
			}

		}
		return toReturn;
	}

	@Override
	public boolean equals(Object obj) {
		Team other = (Team) obj;
		return (this.name.equals(other.getName()));
	}

	/**
	 * Sets the current date of the team.
	 * 
	 * @param currentDate
	 *            - date
	 */
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	/**
	 * Returns the name of team.
	 * 
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns conference reference.
	 * 
	 * @return conference
	 */
	public Conference getC() {
		return c;
	}

	/**
	 * Returns the division reference.
	 * 
	 * @return division
	 */
	public Division getD() {
		return d;
	}

	/**
	 * Returns the current date of the team
	 * 
	 * @return date
	 */
	public Date getCurrentDate() {
		return currentDate;
	}

	/**
	 * Returns the date eliminated in play-offs for particular team.
	 * 
	 * @return
	 */
	public Date getDateEliminated() {

		return dateEliminated;
	}

	/**
	 * Set the date eliminated in play-offs for particular team.
	 * 
	 * @param dateEliminated
	 */
	public void setDateEliminated(Date dateEliminated) {
		this.dateEliminated = dateEliminated;
	}

	/**
	 * Returns the stats for a team.
	 * 
	 * @return
	 */
	public Stats getStats() {
		return stats;
	}

	// public String toString() {
	// return this.name + " " +
	// this.stats.getCurrentWinningPercentage().getNumWins() + " out of "
	// + this.stats.getCurrentWinningPercentage().getTotalGames();
	// }

}
