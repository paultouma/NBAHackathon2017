package league;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import stats.Game;

/**
 * This is an abstract class that modularize the essence of a sports league.
 * 
 * @author paultouma
 *
 */
public abstract class League {

	protected Set<Conference> conferences = new HashSet<Conference>();
	protected Map<String, Team> teams = new HashMap<String, Team>();
	protected List<Date> datesOfAllGames = new ArrayList<Date>();

	/**
	 * Constructor
	 * 
	 * @param divConfInfoMap
	 *            - all parsed division and conference information
	 * @param games
	 *            - all parsed game information
	 */
	public League(Map<String, Map<String, List<String>>> divConfInfoMap, List<Map<String, String>> games) {

		setUpStructure(divConfInfoMap);
		addGamesToAllTeams(games);
	}

	/**
	 * This method sets up the hierarchy of the league based on divisions and
	 * conferences.
	 * 
	 * @param divConfInfoMap
	 *            - all parsed division and conference information
	 */
	private void setUpStructure(Map<String, Map<String, List<String>>> divConfInfoMap) {

		// create the conferences
		for (String conferenceName : divConfInfoMap.keySet()) {
			conferences.add(new Conference(conferenceName, divConfInfoMap.get(conferenceName)));
		}

		// get all of the teams in the league
		for (Conference conference : conferences) {
			for (Team t : conference.getAllTeams()) {
				teams.put(t.getName(), t);
			}
		}
	}

	/**
	 * This method adds all of the game information to the league.
	 * 
	 * @param games
	 *            - all parsed game information
	 */
	private void addGamesToAllTeams(List<Map<String, String>> games) {

		// iterate through games, and add information to the teams in league
		Date previousDate = null;
		for (Map<String, String> gameInfo : games) {

			@SuppressWarnings("deprecation")
			Date dateOfGame = new Date(gameInfo.get("date"));

			Game g = new Game(dateOfGame, teams.get(gameInfo.get("homeTeam")), teams.get(gameInfo.get("awayTeam")),
					gameInfo.get("winner").equals("Home"));

			for (Team t : teams.values()) {
				t.addGame(previousDate, dateOfGame, g, false, false, null);
			}

			if (!datesOfAllGames.contains(dateOfGame)) {
				datesOfAllGames.add(dateOfGame);
			}

			previousDate = dateOfGame;
		}
	}

	/**
	 * Returns all the teams in the league.
	 * 
	 * @return all of the teams
	 */
	public Collection<Team> getAllTeams() {
		return teams.values();
	}
}
