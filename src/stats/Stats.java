package stats;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import league.Team;

/**
 * This class is used to store statistic information for a team.
 * 
 * @author paultouma
 *
 */
public class Stats {

	private SortedMap<Date, WinningPercentage> winningPercentage = new TreeMap<Date, WinningPercentage>();
	private SortedMap<Date, Map<Team, WinningPercentage>> winningPercentageAgainstTeam = new TreeMap<Date, Map<Team, WinningPercentage>>();
	private SortedMap<Date, WinningPercentage> divisionWinningPercentage = new TreeMap<Date, WinningPercentage>();
	private SortedMap<Date, WinningPercentage> conferenceWinningPercentage = new TreeMap<Date, WinningPercentage>();

	private Team myTeam;

	/**
	 * Constructor
	 * 
	 * @param t
	 *            - team stats are for
	 */
	public Stats(Team t) {
		this.myTeam = t;
	}

	/**
	 * Adds statistics to a game.
	 * 
	 * @param other
	 *            - other team
	 * @param previousDate
	 *            - date for previous game date for teams
	 * @param date
	 *            - date for current game
	 * @param isOneOfPlayingTeams
	 *            - whether this class' team reference is playing in game
	 * @param thisTeamWon
	 *            - whether this class' team reference won game
	 */
	public void addStatsForGame(Team other, Date previousDate, Date date, boolean isOneOfPlayingTeams,
			boolean thisTeamWon) {

		WinningPercentage previousWinningPercentage = winningPercentage.size() > 0
				? new WinningPercentage(winningPercentage.get(previousDate)) : new WinningPercentage();

		Map<Team, WinningPercentage> mapAgainstOtherTeams = winningPercentageAgainstTeam.size() > 0
				? new HashMap<Team, WinningPercentage>(winningPercentageAgainstTeam.get(previousDate))
				: new HashMap<Team, WinningPercentage>();

		WinningPercentage previousWinningPercentageAgainstTeam = mapAgainstOtherTeams.get(other) != null
				? new WinningPercentage(mapAgainstOtherTeams.get(other)) : new WinningPercentage();

		WinningPercentage previousDivisionWinningPercentage = divisionWinningPercentage.size() > 0
				? new WinningPercentage(divisionWinningPercentage.get(previousDate)) : new WinningPercentage();

		WinningPercentage previousConferenceWinningPercentage = conferenceWinningPercentage.size() > 0
				? new WinningPercentage(conferenceWinningPercentage.get(previousDate)) : new WinningPercentage();

		if (isOneOfPlayingTeams) {
			previousWinningPercentage.wonGame(thisTeamWon);
			previousWinningPercentageAgainstTeam.wonGame(thisTeamWon);
			mapAgainstOtherTeams.put(other, previousWinningPercentageAgainstTeam);

			if (myTeam.getC().equals(other.getC())) {

				previousConferenceWinningPercentage.wonGame(thisTeamWon);
				if (myTeam.getD().equals(other.getD())) {
					previousDivisionWinningPercentage.wonGame(thisTeamWon);
				}
			}
		}

		winningPercentage.put(date, previousWinningPercentage); // store
		winningPercentageAgainstTeam.put(date, mapAgainstOtherTeams); // store
		divisionWinningPercentage.put(date, previousDivisionWinningPercentage); // store
		conferenceWinningPercentage.put(date, previousConferenceWinningPercentage); // store
	}

	/**
	 * Returns current winning percentage
	 * 
	 * @return winning percentage
	 */
	public WinningPercentage getCurrentWinningPercentage() {
		return winningPercentage.get(myTeam.getCurrentDate());
	}

	/**
	 * Returns current winning percentage against other team
	 * 
	 * @param other
	 *            - other team
	 * @return winning percentage
	 */
	public WinningPercentage getCurrentWinningPercentageAgainstTeam(Team other) {
		Map<Team, WinningPercentage> map = winningPercentageAgainstTeam.get(myTeam.getCurrentDate());
		return map == null ? new WinningPercentage() : map.get(other);
	}

	/**
	 * Returns current division winning percentage
	 * 
	 * @return winning percentage
	 */
	public WinningPercentage getCurrentDivisionWinningPercentage() {
		return divisionWinningPercentage.get(myTeam.getCurrentDate());
	}

	/**
	 * Returns current conference winning percentage
	 * 
	 * @return winning percentage
	 */
	public WinningPercentage getCurrentConferenceWinningPercentage() {
		return conferenceWinningPercentage.get(myTeam.getCurrentDate());
	}

	/**
	 * Returns this class team's reference
	 * 
	 * @return team
	 */
	public Team getTeam() {
		return myTeam;
	}
}
