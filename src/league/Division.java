package league;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents a Division within a sports league.
 * 
 * @author paultouma
 *
 */
public class Division {

	private String divisionName;
	private List<Team> teams = new ArrayList<Team>();
	private Conference conference;

	private Map<Date, Team> divisionLeader = new HashMap<Date, Team>();

	/**
	 * Constructor
	 * 
	 * @param c
	 *            - conference
	 * @param name
	 *            - name of division
	 * @param teamNames
	 *            - list of team names to add
	 */
	public Division(Conference c, String name, List<String> teamNames) {
		this.divisionName = name;
		this.conference = c;
		for (String teamName : teamNames) {
			teams.add(new Team(conference, this, teamName));
		}
	}

	/**
	 * For a given date, sets the leader for the division.
	 * 
	 * @param date
	 *            - date to set leader for
	 */
	public void setDivisionLeader(Date date) {
		setDate(date);
		Collections.sort(teams, new DivisionLeaderComparator());
		divisionLeader.put(date, teams.get(0));
	}

	/**
	 * Sets the current date of all teams in division.
	 * 
	 * @param d
	 *            - date to set
	 */
	private void setDate(Date d) {
		for (Team t : teams) {
			t.setCurrentDate(d);
		}
	}

	@Override
	public boolean equals(Object obj) {
		Division other = (Division) obj;
		return (this.divisionName.equals(other.getName()));
	}

	/**
	 * Returns all of the teams in the division.
	 * 
	 * @return list of teams
	 */
	public List<Team> getAllTeams() {
		return teams;
	}

	/**
	 * Returns division name.
	 * 
	 * @return division name
	 */
	public String getName() {
		return divisionName;
	}

	/**
	 * Returns division leader for a date
	 * 
	 * @param date
	 *            - date to get leader of
	 * @return division leader
	 */
	public Team getDivisionLeaderForDate(Date date) {
		return divisionLeader.get(date);
	}
}
