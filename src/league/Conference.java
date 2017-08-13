package league;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This class represents Conference within a sports league.
 * 
 * @author paultouma
 *
 */
public class Conference {

	private String conferenceName;
	private List<Division> divisions = new ArrayList<Division>();
	private List<Team> allTeams;

	/**
	 * Constructor
	 * 
	 * @param conferenceName
	 *            - name of conference
	 * @param divInfo
	 *            - division information
	 */
	public Conference(String conferenceName, Map<String, List<String>> divInfo) {
		this.conferenceName = conferenceName;

		for (String divisionName : divInfo.keySet()) {
			divisions.add(new Division(this, divisionName, divInfo.get(divisionName)));
		}

		populateAllTeams();
	}

	/**
	 * This method adds teams for divisions in conference.
	 */
	public void populateAllTeams() {
		List<Team> allTeams = new ArrayList<Team>();

		for (Division division : divisions) {
			allTeams.addAll(division.getAllTeams());
		}
		this.allTeams = allTeams;
	}

	/**
	 * This method sets the current date for all of the teams in the conference.
	 * 
	 * @param d
	 *            - date
	 */
	private void setDate(Date d) {
		for (Team t : allTeams) {
			t.setCurrentDate(d);
		}
	}

	@Override
	public boolean equals(Object obj) {
		Conference other = (Conference) obj;
		return (this.conferenceName.equals(other.getConferenceName()));
	}

	/**
	 * Returns all teams in conference.
	 * 
	 * @return all teams
	 */
	public List<Team> getAllTeams() {
		return allTeams;
	}

	/**
	 * Returns conferenceName
	 * 
	 * @return name of conference
	 */
	public String getConferenceName() {
		return conferenceName;
	}

	/**
	 * Calculates and sorts play-off teams in conference for a date.
	 * 
	 * @param date
	 *            - date to get play-off teams for
	 * @return play-off teams
	 */
	public List<Team> getPlayoffTeams(Date date) {
		setDate(date);
		Collections.sort(allTeams, new RegularPlayoffComparator());
		return allTeams.subList(0, 8);
	}

	/**
	 * Returns the teams that are better ranked than a team for a given date
	 * 
	 * @param t
	 *            - team
	 * @param d
	 *            - date
	 * @return teams
	 */
	public List<Team> getTeamsAbove(Team t, Date d) {
		List<Team> toReturn = new ArrayList<Team>();
		RegularPlayoffComparator c = new RegularPlayoffComparator();

		setDate(d);
		for (Team other : allTeams) {
			if (c.compare(other, t) < 0) {
				toReturn.add(other);
			}
		}

		return toReturn;
	}
}
